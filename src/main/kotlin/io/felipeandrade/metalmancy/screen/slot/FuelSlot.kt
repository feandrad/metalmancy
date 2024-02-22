package io.felipeandrade.metalmancy.screen.slot

import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.screen.slot.Slot

class FuelSlot (
    inventory: Inventory,
    index: Int,
    x: Int,
    y: Int,
) : Slot(inventory, index, x, y) {
    override fun canInsert(stack: ItemStack): Boolean {
        return FuelRegistry.INSTANCE.get(stack.item) != null
    }

    override fun getMaxItemCount(stack: ItemStack): Int {
        return if (isBucket(stack)) 1 else super.getMaxItemCount(stack)
    }

    companion object {
        fun isBucket(stack: ItemStack): Boolean {
            return stack.isOf(Items.BUCKET)
        }
    }
}


