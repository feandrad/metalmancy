package io.felipeandrade.metalmancy.blocks.entity

import io.felipeandrade.metalmancy.Metalmancy.logger
import io.felipeandrade.metalmancy.blocks.ModBlocks
import io.felipeandrade.metalmancy.items.ModItems
import io.felipeandrade.metalmancy.screen.CalcinatorScreenHandler
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.AbstractFurnaceBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
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

    private var maxExperience = DEFAULT_EXPERIENCE_MAX
    private var experience = 0
        set(value) {
            field = when {
                value < 0 -> 0
                value > maxExperience -> maxExperience
                else -> value
            }
        }

    private var inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY)

    private val propertyDelegate: PropertyDelegate = object : PropertyDelegate {

        override fun get(index: Int): Int = when (index) {
            PROPERTY_BURN_TIME -> burnTime
            PROPERTY_MAX_BURN_TIME -> burnTime
            PROPERTY_PROGRESS -> progress
            PROPERTY_MAX_PROGRESS -> maxProgress
            PROPERTY_EXPERIENCE -> experience
            PROPERTY_MAX_EXPERIENCE -> maxExperience
            else -> -1
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                PROPERTY_BURN_TIME -> burnTime = value
                PROPERTY_MAX_BURN_TIME -> burnTime = value
                PROPERTY_PROGRESS -> progress = value
                PROPERTY_MAX_PROGRESS -> maxProgress = value
                PROPERTY_EXPERIENCE -> experience = value
                PROPERTY_MAX_EXPERIENCE -> maxExperience = value
            }
        }

        override fun size(): Int = PROPERTY_SIZE
    }

    companion object {
        const val PROPERTY_SIZE = 6
        const val PROPERTY_BURN_TIME = 0
        const val PROPERTY_MAX_BURN_TIME = 1
        const val PROPERTY_PROGRESS = 2
        const val PROPERTY_MAX_PROGRESS = 3
        const val PROPERTY_EXPERIENCE = 4
        const val PROPERTY_MAX_EXPERIENCE = 5

        const val NBT_PROGRESS = "Progress"
        const val NBT_EXP = "Experience"
        const val NBT_BURN_TIME = "BurnTime"
        const val NBT_MAX_BURN_TIME = "MaxBurnTime"

        const val INVENTORY_SIZE = 5
        const val DEFAULT_COOKING_TOTAL = 300
        const val DEFAULT_EXPERIENCE_MAX = 16000

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

    private fun isExpFull(): Boolean = experience >= maxExperience

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
        logger.debug("Added Fuel: $fuelTime -> $burnTime")
    }

    private fun craftItem() {
        removeStack(INPUT_SLOT, 1)
        setStack(OUTPUT_SLOT, ItemStack(ModItems.ESSENCE_DUST, getStack(OUTPUT_SLOT).count + 1))
        experience += 1
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
        nbt.putInt(NBT_EXP, experience)
        Inventories.writeNbt(nbt, inventory)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        Inventories.readNbt(nbt, inventory)
        progress = nbt.getInt(NBT_PROGRESS)
        experience = nbt.getInt(NBT_EXP)
        burnTime = nbt.getShort(NBT_BURN_TIME).toInt()
        maxBurnTime = nbt.getShort(NBT_MAX_BURN_TIME).toInt()
    }

    private fun isBurning(): Boolean {
        return burnTime > 0
    }
}