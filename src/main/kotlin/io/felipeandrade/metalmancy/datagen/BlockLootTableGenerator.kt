package io.felipeandrade.metalmancy.datagen

import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.BRASS_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.BRONZE_BLOCK
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
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.RUBY_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.RUBY_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SAPPHIRE_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SAPPHIRE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SILVER_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SILVER_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SILVER_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.SILVER_RAW_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.STEEL_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TIN_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TIN_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TIN_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TIN_RAW_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TITANIUM_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TITANIUM_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TITANIUM_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TITANIUM_RAW_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TOPAZ_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TOPAZ_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.ZINC_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.ZINC_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.ZINC_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.ZINC_RAW_BLOCK
import io.felipeandrade.metalmancy.items.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Block
import net.minecraft.item.Item

class BlockLootTableGenerator(
    dataOutput: FabricDataOutput
) : FabricBlockLootTableProvider(dataOutput) {

    override fun generate() {

        generateOre(
            ModItems.TIN_RAW,
            listOf(TIN_RAW_BLOCK, TIN_BLOCK),
            listOf(TIN_ORE, TIN_DEEPSLATE_ORE)
        )
        generateOre(
            ModItems.ZINC_RAW,
            listOf(ZINC_RAW_BLOCK, ZINC_BLOCK),
            listOf(ZINC_ORE, ZINC_DEEPSLATE_ORE)
        )

        generateOre(
            ModItems.SILVER_RAW,
            listOf(SILVER_RAW_BLOCK, SILVER_BLOCK),
            listOf(SILVER_ORE, SILVER_DEEPSLATE_ORE)
        )

        generateOre(
            ModItems.PLATINUM_RAW,
            listOf(PLATINUM_RAW_BLOCK, PLATINUM_BLOCK),
            listOf(PLATINUM_ORE, PLATINUM_DEEPSLATE_ORE)
        )

        generateOre(
            ModItems.TITANIUM_RAW,
            listOf(TITANIUM_RAW_BLOCK, TITANIUM_BLOCK),
            listOf(TITANIUM_ORE, TITANIUM_DEEPSLATE_ORE)
        )

        generateOre(
            ModItems.COBALT_RAW,
            listOf(COBALT_RAW_BLOCK, COBALT_BLOCK),
            listOf(COBALT_ORE, COBALT_DEEPSLATE_ORE)
        )

        generateOre(
            ModItems.MITHRIL_RAW,
            listOf(MITHRIL_RAW_BLOCK, MITHRIL_BLOCK),
            listOf(MITHRIL_ORE, MITHRIL_DEEPSLATE_ORE)
        )

        generateOre(
            ModItems.ORICHALCUM_RAW,
            listOf(ORICHALCUM_RAW_BLOCK, ORICHALCUM_BLOCK),
            listOf(ORICHALCUM_ORE, ORICHALCUM_DEEPSLATE_ORE)
        )


        miningDrops(RUBY_ORE, ModItems.RUBY)
        miningDrops(RUBY_DEEPSLATE_ORE, ModItems.RUBY)

        miningDrops(SAPPHIRE_ORE, ModItems.SAPPHIRE)
        miningDrops(SAPPHIRE_DEEPSLATE_ORE, ModItems.SAPPHIRE)

        miningDrops(TOPAZ_ORE, ModItems.TOPAZ)
        miningDrops(TOPAZ_DEEPSLATE_ORE, ModItems.TOPAZ)

        addDrop(BRASS_BLOCK)
        addDrop(BRONZE_BLOCK)
        addDrop(STEEL_BLOCK)
    }

    private fun generateOre(
        raw: Item,
        blocks: List<Block>,
        ores: List<Block>
    ) {
        blocks.forEach {
            addDrop(it)
        }
        ores.forEach {
            miningDrops(it, raw)
        }
    }

    private fun miningDrops(oreBlock: Block, rawItem: Item) {
        addDrop(oreBlock, oreDrops(oreBlock, rawItem))
    }
}
