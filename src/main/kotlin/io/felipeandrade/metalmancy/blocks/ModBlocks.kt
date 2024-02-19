package io.felipeandrade.metalmancy.blocks

import io.felipeandrade.metalmancy.common.registerBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks


class ModBlocks {

    companion object {
        // Ore
        val RUBY_ORE = registerBlock("ruby_ore", Block(FabricBlockSettings.copy(Blocks.EMERALD_ORE)))
        val SAPPHIRE_ORE = registerBlock("sapphire_ore", Block(FabricBlockSettings.copy(Blocks.EMERALD_ORE)))
        val TOPAZ_ORE = registerBlock("topaz_ore", Block(FabricBlockSettings.copy(Blocks.EMERALD_ORE)))

        val ZINC_ORE = registerBlock("zinc_ore", Block(FabricBlockSettings.copy(Blocks.COPPER_ORE)))
        val TIN_ORE = registerBlock("tin_ore", Block(FabricBlockSettings.copy(Blocks.COPPER_ORE)))
        val SILVER_ORE = registerBlock("silver_ore", Block(FabricBlockSettings.copy(Blocks.IRON_ORE)))
        val PLATINUM_ORE = registerBlock("platinum_ore", Block(FabricBlockSettings.copy(Blocks.DIAMOND_ORE)))
        val TITANIUM_ORE = registerBlock("titanium_ore", Block(FabricBlockSettings.copy(Blocks.DIAMOND_ORE)))
        val COBALT_ORE = registerBlock("cobalt_ore", Block(FabricBlockSettings.copy(Blocks.DIAMOND_ORE)))
        val MITHRIL_ORE = registerBlock("mithril_ore", Block(FabricBlockSettings.copy(Blocks.DIAMOND_ORE)))
        val ORICHALCUM_ORE = registerBlock("orichalcum_ore", Block(FabricBlockSettings.copy(Blocks.DIAMOND_ORE)))

        // Deepslate Ore
        val RUBY_DEEPSLATE_ORE =
            registerBlock("ruby_deepslate_ore", Block(FabricBlockSettings.copy(Blocks.DEEPSLATE_EMERALD_ORE)))
        val SAPPHIRE_DEEPSLATE_ORE =
            registerBlock("sapphire_deepslate_ore", Block(FabricBlockSettings.copy(Blocks.DEEPSLATE_EMERALD_ORE)))
        val TOPAZ_DEEPSLATE_ORE =
            registerBlock("topaz_deepslate_ore", Block(FabricBlockSettings.copy(Blocks.DEEPSLATE_EMERALD_ORE)))

        val ZINC_DEEPSLATE_ORE =
            registerBlock("zinc_deepslate_ore", Block(FabricBlockSettings.copy(Blocks.DEEPSLATE_COPPER_ORE)))
        val TIN_DEEPSLATE_ORE =
            registerBlock("tin_deepslate_ore", Block(FabricBlockSettings.copy(Blocks.DEEPSLATE_COPPER_ORE)))
        val SILVER_DEEPSLATE_ORE =
            registerBlock("silver_deepslate_ore", Block(FabricBlockSettings.copy(Blocks.DEEPSLATE_IRON_ORE)))
        val PLATINUM_DEEPSLATE_ORE =
            registerBlock("platinum_deepslate_ore", Block(FabricBlockSettings.copy(Blocks.DEEPSLATE_DIAMOND_ORE)))
        val TITANIUM_DEEPSLATE_ORE =
            registerBlock("titanium_deepslate_ore", Block(FabricBlockSettings.copy(Blocks.DEEPSLATE_DIAMOND_ORE)))
        val COBALT_DEEPSLATE_ORE =
            registerBlock("cobalt_deepslate_ore", Block(FabricBlockSettings.copy(Blocks.DEEPSLATE_DIAMOND_ORE)))
        val MITHRIL_DEEPSLATE_ORE =
            registerBlock("mithril_deepslate_ore", Block(FabricBlockSettings.copy(Blocks.DEEPSLATE_DIAMOND_ORE)))
        val ORICHALCUM_DEEPSLATE_ORE =
            registerBlock("orichalcum_deepslate_ore", Block(FabricBlockSettings.copy(Blocks.DEEPSLATE_DIAMOND_ORE)))

        // Raw
        val ZINC_RAW_BLOCK = registerBlock("zinc_raw_block", Block(FabricBlockSettings.copy(Blocks.RAW_COPPER_BLOCK)))
        val TIN_RAW_BLOCK = registerBlock("tin_raw_block", Block(FabricBlockSettings.copy(Blocks.RAW_COPPER_BLOCK)))
        val SILVER_RAW_BLOCK = registerBlock("silver_raw_block", Block(FabricBlockSettings.copy(Blocks.RAW_IRON_BLOCK)))
        val PLATINUM_RAW_BLOCK =
            registerBlock("platinum_raw_block", Block(FabricBlockSettings.copy(Blocks.RAW_IRON_BLOCK)))
        val TITANIUM_RAW_BLOCK =
            registerBlock("titanium_raw_block", Block(FabricBlockSettings.copy(Blocks.RAW_IRON_BLOCK)))
        val COBALT_RAW_BLOCK = registerBlock("cobalt_raw_block", Block(FabricBlockSettings.copy(Blocks.RAW_IRON_BLOCK)))
        val MITHRIL_RAW_BLOCK =
            registerBlock("mithril_raw_block", Block(FabricBlockSettings.copy(Blocks.RAW_IRON_BLOCK)))
        val ORICHALCUM_RAW_BLOCK =
            registerBlock("orichalcum_raw_block", Block(FabricBlockSettings.copy(Blocks.RAW_IRON_BLOCK)))


        // Gem
        val RUBY_BLOCK = registerBlock("ruby_block", Block(FabricBlockSettings.copy(Blocks.EMERALD_BLOCK)))
        val SAPPHIRE_BLOCK = registerBlock("sapphire_block", Block(FabricBlockSettings.copy(Blocks.EMERALD_BLOCK)))
        val TOPAZ_BLOCK = registerBlock("topaz_block", Block(FabricBlockSettings.copy(Blocks.EMERALD_BLOCK)))


        // Metal
        val BRASS_BLOCK = registerBlock("brass_block", Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)))
        val BRONZE_BLOCK = registerBlock("bronze_block", Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)))
        val STEEL_BLOCK = registerBlock("steel_block", Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)))

        val ZINC_BLOCK = registerBlock("zinc_block", Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)))
        val TIN_BLOCK = registerBlock("tin_block", Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)))
        val SILVER_BLOCK = registerBlock("silver_block", Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)))
        val PLATINUM_BLOCK = registerBlock("platinum_block", Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)))
        val TITANIUM_BLOCK = registerBlock("titanium_block", Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)))
        val COBALT_BLOCK = registerBlock("cobalt_block", Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)))
        val MITHRIL_BLOCK = registerBlock("mithril_block", Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)))
        val ORICHALCUM_BLOCK = registerBlock("orichalcum_block", Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)))

        // Machines
        val CALCINATOR = registerBlock("calcinator", CalcinatorBlock(FabricBlockSettings.copy(Blocks.STONE)))
        val WATER_TURBINE = registerBlock("water_turbine", WaterTurbineBlock(FabricBlockSettings.copy(Blocks.STONE)))

        //        val SMOKER = Blocks.register(
//            "smoker", SmokerBlock(
//                AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(
//                    Instrument.BASEDRUM
//                ).requiresTool().strength(3.5f).luminance(Blocks.createLightLevelFromLitBlockState(13))
//            ) as Block
//        )
//        val BLAST_FURNACE: Block? = Blocks.register(
//            "blast_furnace", BlastFurnaceBlock(
//                AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(
//                    Instrument.BASEDRUM
//                ).requiresTool().strength(3.5f).luminance(Blocks.createLightLevelFromLitBlockState(13))
//            ) as Block
//        )


        fun initialize() {}
    }
}