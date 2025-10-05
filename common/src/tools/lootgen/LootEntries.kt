package tools.lootgen

import io.felipeandrade.metalmancy.registry.material.Part

object LootEntries {
    val entries = listOf(
        GeneratedLoot("ruby", Part.GEM),
        GeneratedLoot("sapphire", Part.GEM),
        GeneratedLoot("topaz", Part.GEM),

        GeneratedLoot("cinnabar", Part.GEM),
        GeneratedLoot("potash", Part.DUST),
        GeneratedLoot("rock_salt", Part.GEM),

        GeneratedLoot("tin"),
        GeneratedLoot("zinc"),
        GeneratedLoot("lead"),

        GeneratedLoot("silver"),
        GeneratedLoot("aluminum"),
        GeneratedLoot("cobalt"),
        GeneratedLoot("manganese"),
        GeneratedLoot("nickel"),

        GeneratedLoot("platinum"),
        GeneratedLoot("titanium"),
        GeneratedLoot("lithium"),
        GeneratedLoot("uranium"),

        GeneratedLoot("mithril"),
        GeneratedLoot("orichalcum"),
    )
}
