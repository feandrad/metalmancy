package io.felipeandrade.metalmancy.blocks.entity

import io.felipeandrade.metalmancy.blocks.ModBlocks
import io.felipeandrade.metalmancy.fluid.ModFluids
import io.felipeandrade.metalmancy.items.ModItems
import io.felipeandrade.metalmancy.networking.ModMessages
import io.felipeandrade.metalmancy.screen.CalcinatorScreenHandler
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.block.AbstractFurnaceBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World


private fun ItemStack.canReceive(item: Item): Boolean = isEmpty || this.item == item && count < maxCount

class CalcinatorBlockEntity(
    pos: BlockPos,
    state: BlockState
) : ModMachine(ModBlockEntities.CALCINATOR, pos, state), ExtendedScreenHandlerFactory {

    private var maxBurnTime = 16000
    private var burnTime = 0
        set(value) {
            field = when {
                value < 0 -> 0
                value > maxBurnTime -> maxBurnTime
                else -> value
            }
        }

    private var maxProgress = DEFAULT_COOKING_TOTAL
    private var progress = 0
        set(value) {
            field = when {
                value < 0 -> 0
                value > maxProgress -> maxProgress
                else -> value
            }
        }

    private var inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY)

    val fluidStorage: SingleFluidStorage = object : SingleFluidStorage() {
        override fun getCapacity(variant: FluidVariant): Long = 16 * FluidConstants.BUCKET
        override fun onFinalCommit() {
            if (world?.isClient == false) {
                val data = PacketByteBufs.create()
                variant.toPacket(data)
                data.writeLong(amount)
                data.writeBlockPos(getPos())
                data.writeLong(capacity)

                for (player: ServerPlayerEntity in PlayerLookup.tracking(world as ServerWorld, getPos())) {
                    ServerPlayNetworking.send(player, ModMessages.FLUID_SYNC, data)
                }
            }
            markDirty()
        }
    }

    private val propertyDelegate: PropertyDelegate = object : PropertyDelegate {

        override fun get(index: Int): Int = when (index) {
            PROPERTY_BURN_TIME -> burnTime
            PROPERTY_MAX_BURN_TIME -> burnTime
            PROPERTY_PROGRESS -> progress
            PROPERTY_MAX_PROGRESS -> maxProgress
            else -> -1
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                PROPERTY_BURN_TIME -> burnTime = value
                PROPERTY_MAX_BURN_TIME -> burnTime = value
                PROPERTY_PROGRESS -> progress = value
                PROPERTY_MAX_PROGRESS -> maxProgress = value
            }
        }

        override fun size(): Int = PROPERTY_SIZE
    }

    companion object {
        const val PROPERTY_SIZE = 4
        const val PROPERTY_BURN_TIME = 0
        const val PROPERTY_MAX_BURN_TIME = 1
        const val PROPERTY_PROGRESS = 2
        const val PROPERTY_MAX_PROGRESS = 3

        const val NBT_PROGRESS = "Progress"
        const val NBT_BURN_TIME = "BurnTime"
        const val NBT_MAX_BURN_TIME = "MaxBurnTime"


        const val INVENTORY_SIZE = 5
        const val DEFAULT_COOKING_TOTAL = 300

        const val INPUT_SLOT = 0
        const val FUEL_SLOT = 1
        const val CONTAINER_INPUT_SLOT = 2
        const val CONTAINER_OUTPUT_SLOT = 3
        const val OUTPUT_SLOT = 4
    }

    override fun getItems(): DefaultedList<ItemStack> = inventory

    override fun getContainerName(): Text = Text.translatable(ModBlocks.CALCINATOR.translationKey)

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory): ScreenHandler {
        return CalcinatorScreenHandler(syncId, playerInventory, this, propertyDelegate)
    }

    override fun writeScreenOpeningData(serverPlayerEntity: ServerPlayerEntity, packetByteBuf: PacketByteBuf) {
        packetByteBuf.writeBlockPos(pos)
    }

    private fun isExpFull(): Boolean = fluidStorage.amount >= fluidStorage.capacity

    fun tick(world: World, pos: BlockPos, state: BlockState) {
        if (world.isClient()) return

        val wasBurning: Boolean = isBurning()
        var shouldMarkDirty = false


        if (burnTime <= 0) {
            if (inventory[FUEL_SLOT].isEmpty.not() && inventory[INPUT_SLOT].isEmpty.not() && isExpFull().not()) {
                consumeFuel()
                shouldMarkDirty = true
            } else if (progress > 0) {
                progress -= 3
                shouldMarkDirty = true
            }
        } else {
            burnTime -= 1
            shouldMarkDirty = true
        }

        if (inventory[INPUT_SLOT].isEmpty.not()) {
            if (inventory[OUTPUT_SLOT].canReceive(ModItems.ESSENCE_DUST)) {
                progress++
                if (hasCraftingFinished()) {
                    craftItem()
                    resetProgress()
                }
                shouldMarkDirty = true
            }
        } else if (progress > 0) {
            resetProgress()
            shouldMarkDirty = true
        }

        if (wasBurning != isBurning()) {
            shouldMarkDirty = true
            val newState = state.with(AbstractFurnaceBlock.LIT, isBurning()) as BlockState
            world.setBlockState(pos, newState, Block.NOTIFY_ALL)
        }

        if (inventory[CONTAINER_INPUT_SLOT].item == Items.BUCKET && fluidStorage.amount >= FluidConstants.BUCKET
            && inventory[CONTAINER_OUTPUT_SLOT].canReceive(ModFluids.ESSENCE_BUCKET)
        ) {

            try {
                val tran = Transaction.openOuter()
                fluidStorage.extract(FluidVariant.of(ModFluids.STILL_ESSENCE), FluidConstants.BUCKET, tran)
                tran.commit()
                inventory[CONTAINER_INPUT_SLOT].decrement(1)
                setStack(CONTAINER_OUTPUT_SLOT, ItemStack(ModFluids.ESSENCE_BUCKET))
            } catch (_: Exception) {

            }
        }

        if (shouldMarkDirty) {
            markDirty(world, pos, state)
        }
    }

    private fun consumeFuel() {
        val stack = inventory[FUEL_SLOT]
        val fuelTime = FuelRegistry.INSTANCE.get(stack.item).toInt()
        burnTime += fuelTime
        maxBurnTime = fuelTime
        stack.decrement(1)
    }

    private fun craftItem() {
        removeStack(INPUT_SLOT, 1)
        setStack(OUTPUT_SLOT, ItemStack(ModItems.ESSENCE_DUST, getStack(OUTPUT_SLOT).count + 1))

        try {
            val tran = Transaction.openOuter()
            fluidStorage.insert(FluidVariant.of(ModFluids.STILL_ESSENCE), FluidConstants.BUCKET, tran)
            tran.commit()
        } catch (_: Exception) {

        }
    }

    private fun hasRecipe(result: Item): Boolean {
        return !inventory[INPUT_SLOT].isEmpty && inventory[OUTPUT_SLOT].canReceive(result)
    }


    private fun hasCraftingFinished(): Boolean = progress >= maxProgress

    private fun resetProgress() {
        progress = 0
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        nbt.putShort(NBT_BURN_TIME, burnTime.toShort())
        nbt.putShort(NBT_MAX_BURN_TIME, maxBurnTime.toShort())
        nbt.putInt(NBT_PROGRESS, progress)
        fluidStorage.writeNbt(nbt)
        Inventories.writeNbt(nbt, inventory)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        Inventories.readNbt(nbt, inventory)
        progress = nbt.getInt(NBT_PROGRESS)
        burnTime = nbt.getShort(NBT_BURN_TIME).toInt()
        maxBurnTime = nbt.getShort(NBT_MAX_BURN_TIME).toInt()
        fluidStorage.readNbt(nbt)
    }

    private fun isBurning(): Boolean {
        return burnTime > 0
    }

    fun setFluidLevel(variant: FluidVariant, amount: Long) {
        this.fluidStorage.variant = variant
        this.fluidStorage.amount = amount
    }
}