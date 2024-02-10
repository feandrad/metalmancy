package io.felipeandrade.metalmancy

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import io.felipeandrade.metalmancy.blocks.ModBlocks
import io.felipeandrade.metalmancy.items.ModItems
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class ItemGroup {

    companion object {
        val MATERIALS: ItemGroup = Registry.register(
            Registries.ITEM_GROUP,
            Identifier(MOD_ID, "materials"),
            FabricItemGroup.builder()
                .icon { ItemStack(Items.IRON_INGOT) }
                .displayName(Text.translatable("itemGroup.metalmancy.materials"))
                .entries { _, entries ->
                    with(entries) {
                        add(ModBlocks.TIN_BLOCK)
                        add(ModBlocks.TIN_ORE)
                        add(ModBlocks.TIN_DEEPSLATE_ORE)
                        add(ModBlocks.TIN_RAW_BLOCK)
                        add(ModItems.TIN_CRUSHED)
                        add(ModItems.TIN_DUST)
                        add(ModItems.TIN_INGOT)
                        add(ModItems.TIN_NUGGET)
                        add(ModItems.TIN_RAW)
                        add(ModItems.TIN_HEAP)
                        add(ModItems.TIN_GEAR)
                        add(ModItems.TIN_ROD)
                        add(ModItems.TIN_PLATE)
                        add(ModItems.TIN_CABLE)

                        add(ModBlocks.ZINC_BLOCK)
                        add(ModBlocks.ZINC_ORE)
                        add(ModBlocks.ZINC_DEEPSLATE_ORE)
                        add(ModBlocks.ZINC_RAW_BLOCK)
                        add(ModItems.ZINC_CRUSHED)
                        add(ModItems.ZINC_DUST)
                        add(ModItems.ZINC_INGOT)
                        add(ModItems.ZINC_NUGGET)
                        add(ModItems.ZINC_RAW)
                        add(ModItems.ZINC_HEAP)
                        add(ModItems.ZINC_GEAR)
                        add(ModItems.ZINC_ROD)
                        add(ModItems.ZINC_PLATE)
                        add(ModItems.ZINC_CABLE)

                        add(ModBlocks.SILVER_BLOCK)
                        add(ModBlocks.SILVER_ORE)
                        add(ModBlocks.SILVER_DEEPSLATE_ORE)
                        add(ModBlocks.SILVER_RAW_BLOCK)
                        add(ModItems.SILVER_CRUSHED)
                        add(ModItems.SILVER_DUST)
                        add(ModItems.SILVER_INGOT)
                        add(ModItems.SILVER_NUGGET)
                        add(ModItems.SILVER_RAW)
                        add(ModItems.SILVER_HEAP)
                        add(ModItems.SILVER_GEAR)
                        add(ModItems.SILVER_ROD)
                        add(ModItems.SILVER_PLATE)
                        add(ModItems.SILVER_CABLE)

                        add(ModBlocks.PLATINUM_BLOCK)
                        add(ModBlocks.PLATINUM_ORE)
                        add(ModBlocks.PLATINUM_DEEPSLATE_ORE)
                        add(ModBlocks.PLATINUM_RAW_BLOCK)
                        add(ModItems.PLATINUM_CRUSHED)
                        add(ModItems.PLATINUM_DUST)
                        add(ModItems.PLATINUM_INGOT)
                        add(ModItems.PLATINUM_NUGGET)
                        add(ModItems.PLATINUM_RAW)
                        add(ModItems.PLATINUM_HEAP)
                        add(ModItems.PLATINUM_GEAR)
                        add(ModItems.PLATINUM_ROD)
                        add(ModItems.PLATINUM_PLATE)
                        add(ModItems.PLATINUM_CABLE)

                        add(ModBlocks.TITANIUM_BLOCK)
                        add(ModBlocks.TITANIUM_ORE)
                        add(ModBlocks.TITANIUM_DEEPSLATE_ORE)
                        add(ModBlocks.TITANIUM_RAW_BLOCK)
                        add(ModItems.TITANIUM_CRUSHED)
                        add(ModItems.TITANIUM_DUST)
                        add(ModItems.TITANIUM_INGOT)
                        add(ModItems.TITANIUM_NUGGET)
                        add(ModItems.TITANIUM_RAW)
                        add(ModItems.TITANIUM_HEAP)
                        add(ModItems.TITANIUM_GEAR)
                        add(ModItems.TITANIUM_ROD)
                        add(ModItems.TITANIUM_PLATE)
                        add(ModItems.TITANIUM_CABLE)

                        add(ModBlocks.COBALT_BLOCK)
                        add(ModBlocks.COBALT_ORE)
                        add(ModBlocks.COBALT_DEEPSLATE_ORE)
                        add(ModBlocks.COBALT_RAW_BLOCK)
                        add(ModItems.COBALT_CRUSHED)
                        add(ModItems.COBALT_DUST)
                        add(ModItems.COBALT_INGOT)
                        add(ModItems.COBALT_NUGGET)
                        add(ModItems.COBALT_RAW)
                        add(ModItems.COBALT_HEAP)
                        add(ModItems.COBALT_GEAR)
                        add(ModItems.COBALT_ROD)
                        add(ModItems.COBALT_PLATE)
                        add(ModItems.COBALT_CABLE)

                        add(ModBlocks.MITHRIL_BLOCK)
                        add(ModBlocks.MITHRIL_ORE)
                        add(ModBlocks.MITHRIL_DEEPSLATE_ORE)
                        add(ModBlocks.MITHRIL_RAW_BLOCK)
                        add(ModItems.MITHRIL_CRUSHED)
                        add(ModItems.MITHRIL_DUST)
                        add(ModItems.MITHRIL_INGOT)
                        add(ModItems.MITHRIL_NUGGET)
                        add(ModItems.MITHRIL_RAW)
                        add(ModItems.MITHRIL_HEAP)
                        add(ModItems.MITHRIL_GEAR)
                        add(ModItems.MITHRIL_ROD)
                        add(ModItems.MITHRIL_PLATE)
                        add(ModItems.MITHRIL_CABLE)

                        add(ModBlocks.ORICHALCUM_BLOCK)
                        add(ModBlocks.ORICHALCUM_ORE)
                        add(ModBlocks.ORICHALCUM_DEEPSLATE_ORE)
                        add(ModBlocks.ORICHALCUM_RAW_BLOCK)
                        add(ModItems.ORICHALCUM_CRUSHED)
                        add(ModItems.ORICHALCUM_DUST)
                        add(ModItems.ORICHALCUM_INGOT)
                        add(ModItems.ORICHALCUM_NUGGET)
                        add(ModItems.ORICHALCUM_RAW)
                        add(ModItems.ORICHALCUM_HEAP)
                        add(ModItems.ORICHALCUM_GEAR)
                        add(ModItems.ORICHALCUM_ROD)
                        add(ModItems.ORICHALCUM_PLATE)
                        add(ModItems.ORICHALCUM_CABLE)

                        add(ModBlocks.BRONZE_BLOCK)
                        add(ModItems.BRONZE_CRUSHED)
                        add(ModItems.BRONZE_DUST)
                        add(ModItems.BRONZE_INGOT)
                        add(ModItems.BRONZE_NUGGET)
                        add(ModItems.BRONZE_HEAP)
                        add(ModItems.BRONZE_GEAR)
                        add(ModItems.BRONZE_ROD)
                        add(ModItems.BRONZE_PLATE)
                        add(ModItems.BRONZE_CABLE)

                        add(ModBlocks.BRASS_BLOCK)
                        add(ModItems.BRASS_CRUSHED)
                        add(ModItems.BRASS_DUST)
                        add(ModItems.BRASS_INGOT)
                        add(ModItems.BRASS_NUGGET)
                        add(ModItems.BRASS_HEAP)
                        add(ModItems.BRASS_GEAR)
                        add(ModItems.BRASS_ROD)
                        add(ModItems.BRASS_PLATE)
                        add(ModItems.BRASS_CABLE)

                        add(ModBlocks.STEEL_BLOCK)
                        add(ModItems.STEEL_CRUSHED)
                        add(ModItems.STEEL_DUST)
                        add(ModItems.STEEL_INGOT)
                        add(ModItems.STEEL_NUGGET)
                        add(ModItems.STEEL_HEAP)
                        add(ModItems.STEEL_GEAR)
                        add(ModItems.STEEL_ROD)
                        add(ModItems.STEEL_PLATE)
                        add(ModItems.STEEL_CABLE)

                        add(ModBlocks.RUBY_BLOCK)
                        add(ModBlocks.RUBY_ORE)
                        add(ModBlocks.RUBY_DEEPSLATE_ORE)
                        add(ModItems.RUBY)

                        add(ModBlocks.SAPPHIRE_BLOCK)
                        add(ModBlocks.SAPPHIRE_ORE)
                        add(ModBlocks.SAPPHIRE_DEEPSLATE_ORE)
                        add(ModItems.SAPPHIRE)

                        add(ModBlocks.TOPAZ_BLOCK)
                        add(ModBlocks.TOPAZ_ORE)
                        add(ModBlocks.TOPAZ_DEEPSLATE_ORE)
                        add(ModItems.TOPAZ)

                    }
                }
                .build()
        )

        val TOOLS: ItemGroup = Registry.register(
            Registries.ITEM_GROUP,
            Identifier(MOD_ID, "tools"),
            FabricItemGroup.builder()
                .icon { ItemStack(Items.DIAMOND_PICKAXE) }
                .displayName(Text.translatable("itemGroup.metalmancy.tools"))
                .entries { _, entries ->

                }
                .build()
        )

        fun initialize() {}
    }
}