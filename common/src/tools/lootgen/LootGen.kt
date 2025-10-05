package tools.lootgen

import com.google.gson.GsonBuilder
import io.felipeandrade.metalmancy.registry.material.Part
import java.io.File

fun main(args: Array<String>) {
    var outDir = "build/generated/loot_table"
    var i = 0
    while (i < args.size) {
        when (args[i]) {
            "--out" -> {
                outDir = args.getOrNull(i + 1) ?: outDir; i++
            }
        }
        i++
    }

    val targetFile = File("$outDir/blocks")
    targetFile.mkdirs()

    for (entry in LootEntries.entries) {
        val lootTable = createBlockLootTable(entry)
        val json = GsonBuilder().setPrettyPrinting().create().toJson(lootTable)
        File(targetFile, entry.fileName).writeText(json)
    }
}

private fun createBlockLootTable(entry: GeneratedLoot): Map<String, Any> {
    val silkTouchCondition = mapOf(
        "condition" to "minecraft:match_tool",
        "predicate" to mapOf(
            "enchantments" to listOf(
                mapOf(
                    "enchantment" to "minecraft:silk_touch",
                    "levels" to mapOf("min" to 1)
                )
            )
        )
    )

    val silkTouchAlternative = mapOf(
        "type" to "minecraft:item",
        "conditions" to listOf(silkTouchCondition),
        "name" to "metalmancy:${entry.oreName}_ore"
    )

    val fortuneFunctions = listOf(
        mapOf(
            "function" to "minecraft:apply_bonus",
            "enchantment" to "minecraft:fortune",
            "formula" to "minecraft:ore_drops"
        ),
        mapOf("function" to "minecraft:explosion_decay")
    )

    val normalDropAlternative = mapOf(
        "type" to "minecraft:item",
        "name" to getDropName(entry),
        "functions" to fortuneFunctions
    )

    val alternativesEntry = mapOf(
        "type" to "minecraft:alternatives",
        "children" to listOf(silkTouchAlternative, normalDropAlternative)
    )

    val pool = mapOf(
        "rolls" to 1.0,
        "bonus_rolls" to 0.0,
        "entries" to listOf(alternativesEntry)
    )

    return mapOf(
        "type" to "minecraft:block",
        "pools" to listOf(pool),
        "random_sequence" to "metalmancy:blocks/${entry.oreName}_ore"
    )
}

private fun getDropName(entry: GeneratedLoot): String {
    return when (entry.drop) {
        Part.RAW_ITEM -> "metalmancy:raw_${entry.oreName}"
        Part.DUST -> "metalmancy:${entry.oreName}_dust"
        else -> "metalmancy:${entry.oreName}"
    }
}
