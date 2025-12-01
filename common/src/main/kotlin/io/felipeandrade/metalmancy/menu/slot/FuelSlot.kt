package io.felipeandrade.metalmancy.menu.slot

import net.minecraft.world.Container
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

class FuelSlot(container: Container, slot: Int, x: Int, y: Int) : Slot(container, slot, x, y) {
    override fun mayPlace(stack: ItemStack): Boolean {
        // TODO: Fix FuelRegistry check
        return true
    }

    override fun getMaxStackSize(stack: ItemStack): Int {
        return if (isBucket(stack)) 1 else super.getMaxStackSize(stack)
    }

    companion object {
        fun isBucket(stack: ItemStack): Boolean {
            return stack.`is`(Items.BUCKET)
        }
    }
}
