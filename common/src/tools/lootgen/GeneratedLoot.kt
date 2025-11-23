package tools.lootgen

import io.felipeandrade.metalmancy.registry.material.Part

data class GeneratedLoot(
    val materialName: String,
    val drop: Part = Part.RAW_ITEM,
    val parts: List<Part> = listOf(Part.ORE, Part.ORE_DEEPSLATE),
    val isOre: Boolean = true
)
