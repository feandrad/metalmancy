package io.felipeandrade.metalmancy.registry.material

enum class Family { METAL, ALLOY, GEM, SALT, STONE, BONE, FABRIC }

enum class Part(val isBlock: Boolean = false) {
    ORE(true), ORE_DEEPSLATE(true),
    RAW_BLOCK(true), BLOCK(true),
    RAW_ITEM, INGOT, NUGGET, GEM, DUST
}

data class Material(
    val name: String,
    val family: Family,
    val parts: Set<Part>
) {
    fun unlocalizedName(part: Part): String = unlocalizedName(name, part)

    companion object {
        fun unlocalizedName(materialName: String, part: Part): String = when (part) {
            Part.ORE           -> "${materialName}_ore"
            Part.ORE_DEEPSLATE -> "${materialName}_deepslate_ore"
            Part.RAW_BLOCK     -> "raw_${materialName}_block"
            Part.BLOCK         -> "${materialName}_block"
            Part.RAW_ITEM      -> "raw_${materialName}"
            Part.INGOT         -> "${materialName}_ingot"
            Part.NUGGET        -> "${materialName}_nugget"
            Part.GEM           -> materialName
            Part.DUST          -> "${materialName}_dust"
        }
    }
}
