package tools.lootgen

import io.felipeandrade.metalmancy.registry.material.Part

data class GeneratedLoot(
    val oreName: String,
    val drop: Part = Part.RAW_ITEM,
    val ores: List<Part> = listOf(Part.ORE, Part.ORE_DEEPSLATE),
) {
    val fileName = "${oreName}_ore.json"
}
