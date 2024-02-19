package io.felipeandrade.metalmancy.datagen

import io.felipeandrade.metalmancy.ModBlockTags
import io.felipeandrade.metalmancy.blocks.ModBlocks
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.CALCINATOR
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.COBALT_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.COBALT_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.COBALT_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.COBALT_RAW_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.MITHRIL_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.MITHRIL_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.MITHRIL_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.MITHRIL_RAW_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.ORICHALCUM_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.ORICHALCUM_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.ORICHALCUM_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.ORICHALCUM_RAW_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.PLATINUM_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.PLATINUM_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.PLATINUM_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.PLATINUM_RAW_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.RUBY_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.RUBY_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.RUBY_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SAPPHIRE_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SAPPHIRE_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SAPPHIRE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SILVER_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SILVER_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SILVER_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SILVER_RAW_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TIN_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TIN_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TIN_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TIN_RAW_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TITANIUM_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TITANIUM_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TITANIUM_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TITANIUM_RAW_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TOPAZ_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TOPAZ_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TOPAZ_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.WATER_TURBINE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.ZINC_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.ZINC_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.ZINC_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.ZINC_RAW_BLOCK
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.registry.tag.BlockTags
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import java.util.concurrent.CompletableFuture

class BlockTagProvider(
    output: FabricDataOutput, completableFuture: CompletableFuture<WrapperLookup>
) : BlockTagProvider(output, completableFuture) {
    override fun configure(arg: WrapperLookup) {
        ores()
        materialBlocks()

        getOrCreateTagBuilder(ModBlockTags.MACHINES)
            .add(CALCINATOR)
            .add(WATER_TURBINE)

        pickaxeMineable()
    }

    private fun ores() {

        getOrCreateTagBuilder(ModBlockTags.ZINC_ORES)
            .add(ZINC_ORE)
            .add(ZINC_DEEPSLATE_ORE)

        getOrCreateTagBuilder(ModBlockTags.TIN_ORES)
            .add(TIN_ORE)
            .add(TIN_DEEPSLATE_ORE)

        getOrCreateTagBuilder(ModBlockTags.SILVER_ORES)
            .add(SILVER_ORE)
            .add(SILVER_DEEPSLATE_ORE)

        getOrCreateTagBuilder(ModBlockTags.PLATINUM_ORES)
            .add(PLATINUM_ORE)
            .add(PLATINUM_DEEPSLATE_ORE)

        getOrCreateTagBuilder(ModBlockTags.TITANIUM_ORES)
            .add(TITANIUM_ORE)
            .add(TITANIUM_DEEPSLATE_ORE)

        getOrCreateTagBuilder(ModBlockTags.COBALT_ORES)
            .add(COBALT_ORE)
            .add(COBALT_DEEPSLATE_ORE)

        getOrCreateTagBuilder(ModBlockTags.MITHRIL_ORES)
            .add(MITHRIL_ORE)
            .add(MITHRIL_DEEPSLATE_ORE)

        getOrCreateTagBuilder(ModBlockTags.ORICHALCUM_ORES)
            .add(ORICHALCUM_ORE)
            .add(ORICHALCUM_DEEPSLATE_ORE)


        getOrCreateTagBuilder(ModBlockTags.RUBY_ORES)
            .add(RUBY_ORE)
            .add(RUBY_DEEPSLATE_ORE)

        getOrCreateTagBuilder(ModBlockTags.SAPPHIRE_ORES)
            .add(SAPPHIRE_ORE)
            .add(SAPPHIRE_DEEPSLATE_ORE)

        getOrCreateTagBuilder(ModBlockTags.TOPAZ_ORES)
            .add(TOPAZ_ORE)
            .add(TOPAZ_DEEPSLATE_ORE)
    }

    private fun materialBlocks() {
        getOrCreateTagBuilder(ModBlockTags.TIN_BLOCKS)
            .add(TIN_RAW_BLOCK)
            .add(TIN_BLOCK)
            .forceAddTag(ModBlockTags.TIN_ORES)

        getOrCreateTagBuilder(ModBlockTags.ZINC_BLOCKS)
            .add(ZINC_RAW_BLOCK)
            .add(ZINC_BLOCK)
            .forceAddTag(ModBlockTags.ZINC_ORES)

        getOrCreateTagBuilder(ModBlockTags.SILVER_BLOCKS)
            .add(SILVER_RAW_BLOCK)
            .add(SILVER_BLOCK)
            .forceAddTag(ModBlockTags.SILVER_ORES)

        getOrCreateTagBuilder(ModBlockTags.PLATINUM_BLOCKS)
            .add(PLATINUM_RAW_BLOCK)
            .add(PLATINUM_BLOCK)
            .forceAddTag(ModBlockTags.PLATINUM_ORES)

        getOrCreateTagBuilder(ModBlockTags.TITANIUM_BLOCKS)
            .add(TITANIUM_RAW_BLOCK)
            .add(TITANIUM_BLOCK)
            .forceAddTag(ModBlockTags.TITANIUM_ORES)

        getOrCreateTagBuilder(ModBlockTags.COBALT_BLOCKS)
            .add(COBALT_RAW_BLOCK)
            .add(COBALT_BLOCK)
            .forceAddTag(ModBlockTags.COBALT_ORES)

        getOrCreateTagBuilder(ModBlockTags.MITHRIL_BLOCKS)
            .add(MITHRIL_RAW_BLOCK)
            .add(MITHRIL_BLOCK)
            .forceAddTag(ModBlockTags.MITHRIL_ORES)

        getOrCreateTagBuilder(ModBlockTags.ORICHALCUM_BLOCKS)
            .add(ORICHALCUM_RAW_BLOCK)
            .add(ORICHALCUM_BLOCK)
            .forceAddTag(ModBlockTags.ORICHALCUM_ORES)

        getOrCreateTagBuilder(ModBlockTags.BRASS_BLOCKS)
            .add(ModBlocks.BRASS_BLOCK)

        getOrCreateTagBuilder(ModBlockTags.BRONZE_BLOCKS)
            .add(ModBlocks.BRONZE_BLOCK)

        getOrCreateTagBuilder(ModBlockTags.STEEL_BLOCKS)
            .add(ModBlocks.STEEL_BLOCK)

        getOrCreateTagBuilder(ModBlockTags.RUBY_BLOCKS)
            .add(RUBY_BLOCK)
            .forceAddTag(ModBlockTags.RUBY_ORES)

        getOrCreateTagBuilder(ModBlockTags.SAPPHIRE_BLOCKS)
            .add(SAPPHIRE_BLOCK)
            .forceAddTag(ModBlockTags.SAPPHIRE_ORES)

        getOrCreateTagBuilder(ModBlockTags.TOPAZ_BLOCKS)
            .add(TOPAZ_BLOCK)
            .forceAddTag(ModBlockTags.TOPAZ_ORES)
    }

    private fun pickaxeMineable() {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
            .forceAddTag(ModBlockTags.ZINC_BLOCKS)
            .forceAddTag(ModBlockTags.TIN_BLOCKS)
            .forceAddTag(ModBlockTags.SILVER_BLOCKS)
            .forceAddTag(ModBlockTags.PLATINUM_BLOCKS)
            .forceAddTag(ModBlockTags.TITANIUM_BLOCKS)
            .forceAddTag(ModBlockTags.COBALT_BLOCKS)
            .forceAddTag(ModBlockTags.MITHRIL_BLOCKS)
            .forceAddTag(ModBlockTags.ORICHALCUM_BLOCKS)
            .forceAddTag(ModBlockTags.BRASS_BLOCKS)
            .forceAddTag(ModBlockTags.BRONZE_BLOCKS)
            .forceAddTag(ModBlockTags.STEEL_BLOCKS)
            .forceAddTag(ModBlockTags.RUBY_BLOCKS)
            .forceAddTag(ModBlockTags.SAPPHIRE_BLOCKS)
            .forceAddTag(ModBlockTags.TOPAZ_BLOCKS)
            .forceAddTag(ModBlockTags.MACHINES)


        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier("fabric", "needs_tool_level_4")))
            .forceAddTag(ModBlockTags.MITHRIL_BLOCKS)
            .forceAddTag(ModBlockTags.ORICHALCUM_BLOCKS)

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
            .forceAddTag(ModBlockTags.SILVER_BLOCKS)
            .forceAddTag(ModBlockTags.PLATINUM_BLOCKS)
            .forceAddTag(ModBlockTags.TITANIUM_BLOCKS)
            .forceAddTag(ModBlockTags.COBALT_BLOCKS)
            .forceAddTag(ModBlockTags.BRASS_BLOCKS)
            .forceAddTag(ModBlockTags.BRONZE_BLOCKS)
            .forceAddTag(ModBlockTags.STEEL_BLOCKS)
            .forceAddTag(ModBlockTags.RUBY_BLOCKS)
            .forceAddTag(ModBlockTags.SAPPHIRE_BLOCKS)
            .forceAddTag(ModBlockTags.TOPAZ_BLOCKS)

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
            .forceAddTag(ModBlockTags.ZINC_BLOCKS)
            .forceAddTag(ModBlockTags.TIN_BLOCKS)
    }
}