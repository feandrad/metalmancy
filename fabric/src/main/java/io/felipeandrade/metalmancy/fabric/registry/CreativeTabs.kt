package io.felipeandrade.metalmancy.fabric.registry

import io.felipeandrade.metalmancy.items.MaterialItems
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

object CreativeTabs {
    fun register() {
        // Natural Blocks tab - only blocks (ores, raw blocks, storage blocks)
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS)
            .register(ItemGroupEvents.ModifyEntries { entries: FabricItemGroupEntries ->
                entries.addAfter(
                    Items.DEEPSLATE_EMERALD_ORE,
                    MaterialItems.RUBY.filterBlocks() +
                            MaterialItems.SAPPHIRE.filterBlocks() +
                            MaterialItems.TOPAZ.filterBlocks()
                )
                entries.addAfter(
                    Items.DEEPSLATE_COPPER_ORE,
                    MaterialItems.TIN.filterBlocks() +
                            MaterialItems.ZINC.filterBlocks()
                )
                entries.addAfter(
                    Items.DEEPSLATE_IRON_ORE,
                    MaterialItems.ALUMINUM.filterBlocks() +
                    MaterialItems.SILVER.filterBlocks()
                )
                entries.addAfter(
                    Items.DEEPSLATE_DIAMOND_ORE,
                    MaterialItems.PLATINUM.filterBlocks() +
                            MaterialItems.TITANIUM.filterBlocks() +
                            MaterialItems.COBALT.filterBlocks() +
                            MaterialItems.MITHRIL.filterBlocks() +
                            MaterialItems.ORICHALCUM.filterBlocks()
                )
            })
        
        // Ingredients tab - only items (raw items, ingots, nuggets, gems, dust)
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
            .register(ItemGroupEvents.ModifyEntries { entries: FabricItemGroupEntries ->
                // Raw items after raw gold
                entries.addAfter(
                    Items.RAW_GOLD,
                    MaterialItems.RUBY.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                            MaterialItems.SAPPHIRE.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                            MaterialItems.TOPAZ.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                            MaterialItems.TIN.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                            MaterialItems.ZINC.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                            MaterialItems.ALUMINUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                            MaterialItems.SILVER.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                            MaterialItems.PLATINUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                            MaterialItems.TITANIUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                            MaterialItems.COBALT.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                            MaterialItems.MITHRIL.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                            MaterialItems.ORICHALCUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM)
                )
                
                // Gems after diamond
                entries.addAfter(
                    Items.DIAMOND,
                    MaterialItems.RUBY.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.GEM) +
                            MaterialItems.SAPPHIRE.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.GEM) +
                            MaterialItems.TOPAZ.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.GEM)
                )
                
                // Ingots after gold ingot
                entries.addAfter(
                    Items.GOLD_INGOT,
                    MaterialItems.TIN.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                            MaterialItems.ZINC.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                            MaterialItems.ALUMINUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                            MaterialItems.SILVER.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                            MaterialItems.PLATINUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                            MaterialItems.TITANIUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                            MaterialItems.COBALT.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                            MaterialItems.MITHRIL.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                            MaterialItems.ORICHALCUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT)
                )
                
                // Nuggets after gold nugget
                entries.addAfter(
                    Items.GOLD_NUGGET,
                    MaterialItems.TIN.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                            MaterialItems.ZINC.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                            MaterialItems.ALUMINUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                            MaterialItems.SILVER.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                            MaterialItems.PLATINUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                            MaterialItems.TITANIUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                            MaterialItems.COBALT.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                            MaterialItems.MITHRIL.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                            MaterialItems.ORICHALCUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET)
                )
            })
    }
    
    private fun Map<io.felipeandrade.metalmancy.registry.material.Part, net.minecraft.world.item.Item>.filterBlocks(): List<ItemStack> {
        return this.filter { it.key.isBlock }.values.map { ItemStack(it) }
    }
    
    private fun Map<io.felipeandrade.metalmancy.registry.material.Part, net.minecraft.world.item.Item>.filterItems(): List<ItemStack> {
        return this.filter { !it.key.isBlock }.values.map { ItemStack(it) }
    }
    
    private fun Map<io.felipeandrade.metalmancy.registry.material.Part, net.minecraft.world.item.Item>.filterByPartType(partType: io.felipeandrade.metalmancy.registry.material.Part): List<ItemStack> {
        return this.filter { it.key == partType }.values.map { ItemStack(it) }
    }
}
