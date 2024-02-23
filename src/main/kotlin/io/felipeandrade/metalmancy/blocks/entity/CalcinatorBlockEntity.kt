package io.felipeandrade.metalmancy.blocks.entity

import io.felipeandrade.metalmancy.blocks.ModBlocks
import io.felipeandrade.metalmancy.fluid.ModFluids
import io.felipeandrade.metalmancy.items.ModItems
import io.felipeandrade.metalmancy.network.ModMessages
import io.felipeandrade.metalmancy.recipe.CalcinatingRecipe
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
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeEntry
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World


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
        override fun getCapacity(variant: FluidVariant): Long = FLUID_CAPACITY
        override fun onFinalCommit() {
            syncFluid(amount, variant, capacity)
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

    override fun getItems(): DefaultedList<ItemStack> = inventory

    override fun getContainerName(): Text = Text.translatable(ModBlocks.CALCINATOR.translationKey)

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory): ScreenHandler {
        return CalcinatorScreenHandler(syncId, playerInventory, this, propertyDelegate)
    }

    override fun writeScreenOpeningData(playerEntity: ServerPlayerEntity, buf: PacketByteBuf) {
        if (playerEntity.world.isClient.not()) {
            buf.writeBlockPos(pos)
            syncFluid(fluidStorage.amount, fluidStorage.variant, fluidStorage.capacity)
        }
    }

    private fun isExpFull(): Boolean = fluidStorage.amount >= fluidStorage.capacity

    private fun syncFluid(amount: Long, fluidVariant: FluidVariant, capacity: Long) {
        if (world?.isClient == false) {
            val data = PacketByteBufs.create()
            fluidVariant.toPacket(data)
            data.writeLong(amount)
            data.writeBlockPos(getPos())
            data.writeLong(capacity)

            for (player: ServerPlayerEntity in PlayerLookup.tracking(world as ServerWorld, getPos())) {
                ServerPlayNetworking.send(player, ModMessages.FLUID_SYNC, data)
            }
        }
    }

    fun tick(world: World, pos: BlockPos, state: BlockState) {
        if (world.isClient()) return

        val wasBurning: Boolean = isBurning()
        var shouldMarkDirty = false

        val recipe = getCurrentRecipe() ?: CalcinatingRecipe.defaultRecipe(inventory[INPUT_SLOT])
        val hasRecipe = inventory[INPUT_SLOT].isEmpty.not()
                && inventory[OUTPUT_SLOT].canReceive(recipe.value.output.item)

        if (burnTime <= 0) {
            if (inventory[FUEL_SLOT].isEmpty.not() && hasRecipe && isExpFull().not()) {
                consumeFuel()
                shouldMarkDirty = true
            } else if (progress > 0) {
                progress -= 3
                shouldMarkDirty = true
            }
        } else {
            burnTime -= 1
        }

        if (hasRecipe) {
            progress++
            if (hasCraftingFinished()) {
                craftItem(recipe)
                resetProgress()
            }
            shouldMarkDirty = true

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
                shouldMarkDirty = true
            } catch (_: Exception) {
            }
        }

        if (shouldMarkDirty) {
            markDirty(world, pos, state)
        }
    }

    private fun consumeFuel() {
        val stack = inventory[FUEL_SLOT]
        val fuelTime = FuelRegistry.INSTANCE.get(stack.item)?.toInt() ?: return
        burnTime += fuelTime
        maxBurnTime = fuelTime
        stack.decrement(1)
    }

    private fun craftItem(recipe: RecipeEntry<CalcinatingRecipe>) {
        removeStack(INPUT_SLOT, 1)

        if (recipe.value.ingredient == Ingredient.ofItems(ModItems.ESSENCE_DUST)) {
            setStack(OUTPUT_SLOT, ItemStack.EMPTY)
        } else {
            setStack(OUTPUT_SLOT, ItemStack(recipe.value.output.item, getStack(OUTPUT_SLOT).count + 1))
        }

        try {
            val tran = Transaction.openOuter()
            fluidStorage.insert(FluidVariant.of(ModFluids.STILL_ESSENCE), recipe.value.essence, tran)
            tran.commit()
        } catch (_: Exception) {
        }
    }

    private fun getCurrentRecipe(): RecipeEntry<CalcinatingRecipe>? {
        val inv = SimpleInventory(size())
        for (i in 0 until size()) {
            inv.setStack(i, getStack(i))
        }
        val result = world?.recipeManager?.getFirstMatch(CalcinatingRecipe.TYPE, inv, world) ?: return null
        if (result.isPresent.not()) return null
        return result.get()
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
        Inventories.readNbt(nbt, inventory)
        fluidStorage.readNbt(nbt)
        progress = nbt.getInt(NBT_PROGRESS)
        burnTime = nbt.getShort(NBT_BURN_TIME).toInt()
        maxBurnTime = nbt.getShort(NBT_MAX_BURN_TIME).toInt()
        super.readNbt(nbt)
    }

    private fun isBurning(): Boolean {
        return burnTime > 0
    }

    fun setFluidLevel(variant: FluidVariant, amount: Long) {
        this.fluidStorage.variant = variant
        this.fluidStorage.amount = amount
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
        const val FLUID_CAPACITY = 16 * FluidConstants.BUCKET

        const val INPUT_SLOT = 0
        const val FUEL_SLOT = 1
        const val CONTAINER_INPUT_SLOT = 2
        const val CONTAINER_OUTPUT_SLOT = 3
        const val OUTPUT_SLOT = 4
    }
}

private fun ItemStack.canReceive(item: Item): Boolean = isEmpty || this.item == item && count < maxCount
