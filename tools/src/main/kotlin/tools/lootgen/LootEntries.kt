package tools.lootgen

import io.felipeandrade.metalmancy.registry.material.Part

object LootEntries {
    val oreEntries = listOf(
        // Gems - drop GEM from ORE
        GeneratedLoot("ruby", Part.GEM),
        GeneratedLoot("sapphire", Part.GEM),
        GeneratedLoot("topaz", Part.GEM),

        // Alchemy - drop GEM or DUST from ORE
        GeneratedLoot("cinnabar", Part.GEM),
        GeneratedLoot("potash", Part.DUST),
        GeneratedLoot("rock_salt", Part.GEM),

        // Metals - drop RAW_ITEM from ORE
        GeneratedLoot("tin"),
        GeneratedLoot("zinc"),
        GeneratedLoot("lead"),
        GeneratedLoot("nickel"),

        GeneratedLoot("silver"),
        GeneratedLoot("aluminum"),
        GeneratedLoot("cobalt"),
        GeneratedLoot("manganese"),

        GeneratedLoot("platinum"),
        GeneratedLoot("titanium"),
        GeneratedLoot("lithium"),
        GeneratedLoot("uranium"),

        GeneratedLoot("mithril"),
        GeneratedLoot("orichalcum"),
    )

    val blockEntries = listOf(
        // Gem blocks - drop themselves
        GeneratedLoot("ruby", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("sapphire", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("topaz", Part.BLOCK, listOf(Part.BLOCK), isOre = false),

        // Salt blocks - drop themselves
        GeneratedLoot("potash", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("rock_salt", Part.BLOCK, listOf(Part.BLOCK), isOre = false),

        // Metal blocks - drop themselves
        GeneratedLoot("tin", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("zinc", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("lead", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("nickel", Part.BLOCK, listOf(Part.BLOCK), isOre = false),

        GeneratedLoot("silver", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("aluminum", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("cobalt", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("manganese", Part.BLOCK, listOf(Part.BLOCK), isOre = false),

        GeneratedLoot("platinum", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("titanium", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("lithium", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("uranium", Part.BLOCK, listOf(Part.BLOCK), isOre = false),

        GeneratedLoot("mithril", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("orichalcum", Part.BLOCK, listOf(Part.BLOCK), isOre = false),

        // Alloy blocks - drop themselves
        GeneratedLoot("pewter", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("brass", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("bronze", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("steel", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("electrum", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
        GeneratedLoot("invar", Part.BLOCK, listOf(Part.BLOCK), isOre = false),
    )

    val rawBlockEntries = listOf(
        // Raw metal blocks - drop themselves
        GeneratedLoot("tin", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),
        GeneratedLoot("zinc", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),
        GeneratedLoot("lead", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),
        GeneratedLoot("nickel", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),

        GeneratedLoot("silver", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),
        GeneratedLoot("aluminum", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),
        GeneratedLoot("cobalt", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),
        GeneratedLoot("manganese", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),

        GeneratedLoot("platinum", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),
        GeneratedLoot("titanium", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),
        GeneratedLoot("lithium", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),
        GeneratedLoot("uranium", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),

        GeneratedLoot("mithril", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),
        GeneratedLoot("orichalcum", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false),
    )

    val entries = oreEntries + blockEntries + rawBlockEntries
}
