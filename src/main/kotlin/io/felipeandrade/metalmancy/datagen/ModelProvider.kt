package io.felipeandrade.metalmancy.datagen

import io.felipeandrade.metalmancy.blocks.*
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator

class ModelProvider(
    output: FabricDataOutput
) : FabricModelProvider(output) {

    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {

        // Metals
        blockStateModelGenerator.registerSimpleCubeAll(ZINC_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(ZINC_RAW_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(ZINC_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(ZINC_DEEPSLATE_ORE)

        blockStateModelGenerator.registerSimpleCubeAll(TIN_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(TIN_RAW_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(TIN_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(TIN_DEEPSLATE_ORE)

        blockStateModelGenerator.registerSimpleCubeAll(SILVER_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(SILVER_RAW_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(SILVER_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(SILVER_DEEPSLATE_ORE)

        blockStateModelGenerator.registerSimpleCubeAll(PLATINUM_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(PLATINUM_RAW_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(PLATINUM_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(PLATINUM_DEEPSLATE_ORE)

        blockStateModelGenerator.registerSimpleCubeAll(TITANIUM_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(TITANIUM_RAW_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(TITANIUM_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(TITANIUM_DEEPSLATE_ORE)

        blockStateModelGenerator.registerSimpleCubeAll(COBALT_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(COBALT_RAW_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(COBALT_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(COBALT_DEEPSLATE_ORE)

        blockStateModelGenerator.registerSimpleCubeAll(MITHRIL_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(MITHRIL_RAW_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(MITHRIL_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(MITHRIL_DEEPSLATE_ORE)

        blockStateModelGenerator.registerSimpleCubeAll(ORICHALCUM_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(ORICHALCUM_RAW_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(ORICHALCUM_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(ORICHALCUM_DEEPSLATE_ORE)

        // Alloys
        blockStateModelGenerator.registerSimpleCubeAll(BRASS_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(BRONZE_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(STEEL_BLOCK)

        // Gems
        blockStateModelGenerator.registerSimpleCubeAll(RUBY_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(RUBY_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(RUBY_DEEPSLATE_ORE)

        blockStateModelGenerator.registerSimpleCubeAll(SAPPHIRE_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(SAPPHIRE_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(SAPPHIRE_DEEPSLATE_ORE)

        blockStateModelGenerator.registerSimpleCubeAll(TOPAZ_BLOCK)
        blockStateModelGenerator.registerSimpleCubeAll(TOPAZ_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(TOPAZ_DEEPSLATE_ORE)

    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator): Unit = with(itemModelGenerator) {

    }
}