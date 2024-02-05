package io.felipeandrade.metalmancy.datagen

import io.felipeandrade.metalmancy.blocks.*
import io.felipeandrade.metalmancy.items.*
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Block
import net.minecraft.item.Item

class BlockLootTableGenerator(
    dataOutput: FabricDataOutput
) : FabricBlockLootTableProvider(dataOutput) {

    override fun generate() {

        generateOre(
            TIN_RAW,
            listOf(TIN_RAW_BLOCK, TIN_BLOCK),
            listOf(TIN_ORE, TIN_DEEPSLATE_ORE)
        )
        generateOre(
            ZINC_RAW,
            listOf(ZINC_RAW_BLOCK, ZINC_BLOCK),
            listOf(ZINC_ORE, ZINC_DEEPSLATE_ORE)
        )

        generateOre(
            SILVER_RAW,
            listOf(SILVER_RAW_BLOCK, SILVER_BLOCK),
            listOf(SILVER_ORE, SILVER_DEEPSLATE_ORE)
        )

        generateOre(
            PLATINUM_RAW,
            listOf(PLATINUM_RAW_BLOCK, PLATINUM_BLOCK),
            listOf(PLATINUM_ORE, PLATINUM_DEEPSLATE_ORE)
        )

        generateOre(
            TITANIUM_RAW,
            listOf(TITANIUM_RAW_BLOCK, TITANIUM_BLOCK),
            listOf(TITANIUM_ORE, TITANIUM_DEEPSLATE_ORE)
        )

        generateOre(
            COBALT_RAW,
            listOf(COBALT_RAW_BLOCK, COBALT_BLOCK),
            listOf(COBALT_ORE, COBALT_DEEPSLATE_ORE)
        )

        generateOre(
            MITHRIL_RAW,
            listOf(MITHRIL_RAW_BLOCK, MITHRIL_BLOCK),
            listOf(MITHRIL_ORE, MITHRIL_DEEPSLATE_ORE)
        )

        generateOre(
            ORICHALCUM_RAW,
            listOf(ORICHALCUM_RAW_BLOCK, ORICHALCUM_BLOCK),
            listOf(ORICHALCUM_ORE, ORICHALCUM_DEEPSLATE_ORE)
        )


        miningDrops(RUBY_ORE, RUBY)
        miningDrops(RUBY_DEEPSLATE_ORE, RUBY)

        miningDrops(SAPPHIRE_ORE, SAPPHIRE)
        miningDrops(SAPPHIRE_DEEPSLATE_ORE, SAPPHIRE)

        miningDrops(TOPAZ_ORE, TOPAZ)
        miningDrops(TOPAZ_DEEPSLATE_ORE, TOPAZ)

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
