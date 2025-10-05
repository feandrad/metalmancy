package io.felipeandrade.metalmancy.fabric.registry

import io.felipeandrade.metalmancy.items.MaterialItems
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

object CreativeTabs {
    fun register() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS)
            .register(ItemGroupEvents.ModifyEntries { entries: FabricItemGroupEntries ->
                entries.addAfter(
                    Items.DEEPSLATE_EMERALD_ORE,
                    MaterialItems.RUBY.values.map { ItemStack(it) } +
                            MaterialItems.SAPPHIRE.values.map { ItemStack(it) } +
                            MaterialItems.TOPAZ.values.map { ItemStack(it) }
                )
                entries.addAfter(
                    Items.DEEPSLATE_COPPER_ORE,
                    MaterialItems.TIN.values.map { ItemStack(it) } +
                            MaterialItems.ZINC.values.map { ItemStack(it) }
                )
                entries.addAfter(
                    Items.DEEPSLATE_IRON_ORE,
                    MaterialItems.ALUMINUM.values.map { ItemStack(it) } +
                    MaterialItems.SILVER.values.map { ItemStack(it) }
                )
                entries.addAfter(
                    Items.DEEPSLATE_DIAMOND_ORE,
                    MaterialItems.PLATINUM.values.map { ItemStack(it) } +
                            MaterialItems.TITANIUM.values.map { ItemStack(it) } +
                            MaterialItems.COBALT.values.map { ItemStack(it) } +
                            MaterialItems.MITHRIL.values.map { ItemStack(it) } +
                            MaterialItems.ORICHALCUM.values.map { ItemStack(it) }
                )
            })
    }
}
