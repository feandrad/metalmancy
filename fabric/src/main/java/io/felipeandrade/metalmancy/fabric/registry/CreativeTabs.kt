package io.felipeandrade.metalmancy.fabric.registry

import io.felipeandrade.metalmancy.items.MaterialItems
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import io.felipeandrade.metalmancy.items.ToolItems
import io.felipeandrade.metalmancy.items.ToolType
import io.felipeandrade.metalmancy.registry.ModItems

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
        
        // Functional Blocks tab
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
            .register(ItemGroupEvents.ModifyEntries { entries: FabricItemGroupEntries ->
                entries.addAfter(Items.BLAST_FURNACE, ItemStack(ModItems.CALCINATOR))
            })
        
        // Ingredients tab - only items (raw items, ingots, nuggets, gems, dust)
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
            .register(ItemGroupEvents.ModifyEntries { entries: FabricItemGroupEntries ->
                // Raw items after raw gold
                entries.addAfter(
                    Items.RAW_GOLD,
                    listOf(
                        ItemStack(ModItems.ESSENCE_DUST),
                        ItemStack(ModItems.ESSENCE_BOTTLE)
                    ) +
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

        // Combat tab - Swords and Axes
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
            .register(ItemGroupEvents.ModifyEntries { entries: FabricItemGroupEntries ->
                // Add swords after netherite sword
                entries.addAfter(
                    Items.NETHERITE_SWORD,
                    ToolItems.BRASS[ToolType.SWORD],
                    ToolItems.BRONZE[ToolType.SWORD],
                    ToolItems.ALUMINUM[ToolType.SWORD],
                    ToolItems.STEEL[ToolType.SWORD],
                    ToolItems.INVAR[ToolType.SWORD],
                    ToolItems.SILVER[ToolType.SWORD],
                    ToolItems.ELECTRUM[ToolType.SWORD],
                    ToolItems.COBALT[ToolType.SWORD],
                    ToolItems.PLATINUM[ToolType.SWORD],
                    ToolItems.TITANIUM[ToolType.SWORD],
                    ToolItems.TOPAZ[ToolType.SWORD],
                    ToolItems.RUBY[ToolType.SWORD],
                    ToolItems.SAPPHIRE[ToolType.SWORD],
                    ToolItems.MITHRIL[ToolType.SWORD],
                    ToolItems.ORICHALCUM[ToolType.SWORD]
                )
                
                // Add axes after netherite axe
                entries.addAfter(
                    Items.NETHERITE_AXE,
                    ToolItems.BRASS[ToolType.AXE],
                    ToolItems.BRONZE[ToolType.AXE],
                    ToolItems.ALUMINUM[ToolType.AXE],
                    ToolItems.STEEL[ToolType.AXE],
                    ToolItems.INVAR[ToolType.AXE],
                    ToolItems.SILVER[ToolType.AXE],
                    ToolItems.ELECTRUM[ToolType.AXE],
                    ToolItems.COBALT[ToolType.AXE],
                    ToolItems.PLATINUM[ToolType.AXE],
                    ToolItems.TITANIUM[ToolType.AXE],
                    ToolItems.TOPAZ[ToolType.AXE],
                    ToolItems.RUBY[ToolType.AXE],
                    ToolItems.SAPPHIRE[ToolType.AXE],
                    ToolItems.MITHRIL[ToolType.AXE],
                    ToolItems.ORICHALCUM[ToolType.AXE]
                )
            })

        // Tools tab - Pickaxes, Shovels, Hoes
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .register(ItemGroupEvents.ModifyEntries { entries: FabricItemGroupEntries ->
                // Add shovels after netherite shovel
                entries.addAfter(
                    Items.NETHERITE_SHOVEL,
                    ToolItems.BRASS[ToolType.SHOVEL],
                    ToolItems.BRONZE[ToolType.SHOVEL],
                    ToolItems.ALUMINUM[ToolType.SHOVEL],
                    ToolItems.STEEL[ToolType.SHOVEL],
                    ToolItems.INVAR[ToolType.SHOVEL],
                    ToolItems.SILVER[ToolType.SHOVEL],
                    ToolItems.ELECTRUM[ToolType.SHOVEL],
                    ToolItems.COBALT[ToolType.SHOVEL],
                    ToolItems.PLATINUM[ToolType.SHOVEL],
                    ToolItems.TITANIUM[ToolType.SHOVEL],
                    ToolItems.TOPAZ[ToolType.SHOVEL],
                    ToolItems.RUBY[ToolType.SHOVEL],
                    ToolItems.SAPPHIRE[ToolType.SHOVEL],
                    ToolItems.MITHRIL[ToolType.SHOVEL],
                    ToolItems.ORICHALCUM[ToolType.SHOVEL]
                )

                // Add pickaxes after netherite pickaxe
                entries.addAfter(
                    Items.NETHERITE_PICKAXE,
                    ToolItems.BRASS[ToolType.PICKAXE],
                    ToolItems.BRONZE[ToolType.PICKAXE],
                    ToolItems.ALUMINUM[ToolType.PICKAXE],
                    ToolItems.STEEL[ToolType.PICKAXE],
                    ToolItems.INVAR[ToolType.PICKAXE],
                    ToolItems.SILVER[ToolType.PICKAXE],
                    ToolItems.ELECTRUM[ToolType.PICKAXE],
                    ToolItems.COBALT[ToolType.PICKAXE],
                    ToolItems.PLATINUM[ToolType.PICKAXE],
                    ToolItems.TITANIUM[ToolType.PICKAXE],
                    ToolItems.TOPAZ[ToolType.PICKAXE],
                    ToolItems.RUBY[ToolType.PICKAXE],
                    ToolItems.SAPPHIRE[ToolType.PICKAXE],
                    ToolItems.MITHRIL[ToolType.PICKAXE],
                    ToolItems.ORICHALCUM[ToolType.PICKAXE]
                )
                
                // Add hoes after netherite hoe
                entries.addAfter(
                    Items.NETHERITE_HOE,
                    ToolItems.BRASS[ToolType.HOE],
                    ToolItems.BRONZE[ToolType.HOE],
                    ToolItems.ALUMINUM[ToolType.HOE],
                    ToolItems.STEEL[ToolType.HOE],
                    ToolItems.INVAR[ToolType.HOE],
                    ToolItems.SILVER[ToolType.HOE],
                    ToolItems.ELECTRUM[ToolType.HOE],
                    ToolItems.COBALT[ToolType.HOE],
                    ToolItems.PLATINUM[ToolType.HOE],
                    ToolItems.TITANIUM[ToolType.HOE],
                    ToolItems.TOPAZ[ToolType.HOE],
                    ToolItems.RUBY[ToolType.HOE],
                    ToolItems.SAPPHIRE[ToolType.HOE],
                    ToolItems.MITHRIL[ToolType.HOE],
                    ToolItems.ORICHALCUM[ToolType.HOE]
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
