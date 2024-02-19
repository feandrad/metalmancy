package io.felipeandrade.metalmancy.screen

import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity
import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity.Companion.INVENTORY_SIZE
import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity.Companion.PROPERTY_BURN_TIME
import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity.Companion.PROPERTY_MAX_BURN_TIME
import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity.Companion.PROPERTY_MAX_PROGRESS
import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity.Companion.PROPERTY_PROGRESS
import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity.Companion.PROPERTY_SIZE
import io.felipeandrade.metalmancy.screen.slot.FuelSlot
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.FurnaceOutputSlot
import net.minecraft.screen.slot.Slot
import net.minecraft.util.math.MathHelper

class CalcinatorScreenHandler(
    syncId: Int,
    playerInventory: PlayerInventory,
    blockEntity: BlockEntity?,
    private val propertyDelegate: PropertyDelegate
) : ScreenHandler(ModScreenHandlers.CALCINATOR_SCREEN_HANDLER, syncId) {

    private val inventory: Inventory = blockEntity as Inventory

    constructor(syncId: Int, playerInventory: PlayerInventory, buf: PacketByteBuf) : this(
        syncId, playerInventory, playerInventory.player.world.getBlockEntity(buf.readBlockPos()),
        ArrayPropertyDelegate(PROPERTY_SIZE)
    )

    init {
        checkSize(inventory, INVENTORY_SIZE)
        inventory.onOpen(playerInventory.player)
        addSlot(Slot(inventory, CalcinatorBlockEntity.INPUT_SLOT, 25, 17))
        addSlot(FuelSlot(inventory, CalcinatorBlockEntity.FUEL_SLOT, 25, 53))
        addSlot(Slot(inventory, CalcinatorBlockEntity.CONTAINER_INPUT_SLOT, 136, 17))
        addSlot(
            FurnaceOutputSlot(
                playerInventory.player,
                inventory,
                CalcinatorBlockEntity.CONTAINER_OUTPUT_SLOT,
                136,
                53
            )
        )
        addSlot(FurnaceOutputSlot(playerInventory.player, inventory, CalcinatorBlockEntity.OUTPUT_SLOT, 80, 35))
        // 119, 17 , 127, 68 Exp bar
        addPlayerInventory(playerInventory)
        addPlayerHotbar(playerInventory)
        addProperties(propertyDelegate)
    }

    override fun quickMove(player: PlayerEntity, invSlot: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[invSlot]
        if (slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (invSlot < inventory.size()) {
                if (!insertItem(originalStack, inventory.size(), slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY
            }
            if (originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }
        }
        return newStack
    }

    override fun canUse(player: PlayerEntity): Boolean {
        return inventory.canPlayerUse(player)
    }

    private fun addPlayerInventory(playerInventory: PlayerInventory) {
        for (i in 0..2) {
            for (l in 0..8) {
                addSlot(Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18))
            }
        }
    }

    private fun addPlayerHotbar(playerInventory: PlayerInventory) {
        for (i in 0..8) {
            addSlot(Slot(playerInventory, i, 8 + i * 18, 142))
        }
    }

    private fun scaledProgress(progress: Int, maxProgress: Int, adjust: Float): Int =
        if (maxProgress == 0 || progress == 0) 0
        else MathHelper.ceil((progress * adjust) / maxProgress)

    fun isCrafting(): Boolean = propertyDelegate[PROPERTY_PROGRESS] > 0
    fun isBurning(): Boolean = propertyDelegate[PROPERTY_BURN_TIME] > 0

    fun getBurnProgress(): Int = scaledProgress(
        propertyDelegate[PROPERTY_BURN_TIME],
        propertyDelegate[PROPERTY_MAX_BURN_TIME],
        14f
    )

    fun getCraftProgress(): Int = scaledProgress(
        propertyDelegate[PROPERTY_PROGRESS],
        propertyDelegate[PROPERTY_MAX_PROGRESS],
        24f
    )
}