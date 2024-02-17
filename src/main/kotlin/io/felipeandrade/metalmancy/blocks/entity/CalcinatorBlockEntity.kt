package io.felipeandrade.metalmancy.blocks.entity

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import io.felipeandrade.metalmancy.screen.CalcinatorScreenHandler
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos


class CalcinatorBlockEntity(
    pos: BlockPos,
    state: BlockState
) : ModMachine(ModBlockEntities.CALCINATOR, pos, state), ExtendedScreenHandlerFactory {

    private var progress = 0
    private var maxProgress = 72

    private val inventory = DefaultedList.ofSize(4, ItemStack.EMPTY)

    private val propertyDelegate: PropertyDelegate = object : PropertyDelegate {
        override fun get(index: Int): Int = when (index) {
            0 -> this@CalcinatorBlockEntity.progress
            1 -> this@CalcinatorBlockEntity.maxProgress
            else -> 0
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> this@CalcinatorBlockEntity.progress = value
                1 -> this@CalcinatorBlockEntity.maxProgress = value
                else -> {}
            }
        }

        override fun size(): Int = 2
    }

    companion object {
        const val INPUT_SLOT = 0
        const val FUEL_SLOT = 1
        const val CONTAINER_INPUT_SLOT = 2
        const val CONTAINER_OUTPUT_SLOT = 3
    }

    override fun getItems(): DefaultedList<ItemStack> = inventory

    override fun getContainerName(): Text = Text.translatable(Identifier(MOD_ID, "calcinator").toTranslationKey())

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory?): ScreenHandler {
        TODO("Not yet implemented")
    }

    override fun writeScreenOpeningData(serverPlayerEntity: ServerPlayerEntity?, packetByteBuf: PacketByteBuf) {
        packetByteBuf.writeBlockPos(pos)
    }

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler? {
        return CalcinatorScreenHandler(syncId, playerInventory, this, propertyDelegate)
    }

//    fun tick(world: World, pos: BlockPos?, state: BlockState?) {
//        if (world.isClient()) return
//
//        if (!isActive()) {
//            resetProgress()
//            markDirty(world, pos, state)
//            return
//        }
//
//
//        if (hasRecipe()) {
//            increaseCraftProgress()
//            markDirty(world, pos, state)
//            if (hasCraftingFinished()) {
//                craftItem()
//                resetProgress()
//            }
//        } else {
//            resetProgress()
//        }
//    }
//
//    private fun isActive(): Boolean {
//        return inventory[Companion.INPUT_SLOT]
//    }

    private fun resetProgress() {
        progress = 0
    }

    private fun hasCraftingFinished(): Boolean {
        return progress >= maxProgress
    }

    private fun increaseCraftProgress() {
        progress++
    }
}