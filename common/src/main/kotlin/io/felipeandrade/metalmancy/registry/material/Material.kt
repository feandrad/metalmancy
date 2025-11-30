package io.felipeandrade.metalmancy.registry.material

import io.felipeandrade.metalmancy.util.appendUnlocalizedAll

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
    /**
     * Retrieves the unlocalized name for a specific Part associated with this Material instance.
     */
    fun unlocalizedName(part: Part): String = unlocalizedName(name, part)

    companion object {
        /**
         * Generates the unlocalized resource ID (e.g., "steel_ingot") based on the Part type.
         */
        fun unlocalizedName(materialName: String, part: Part): String = when (part) {
            Part.ORE -> materialName.appendUnlocalizedAll("ore")
            Part.ORE_DEEPSLATE -> materialName.appendUnlocalizedAll("deepslate", "ore")
            Part.RAW_BLOCK -> "raw".appendUnlocalizedAll(materialName, "block")
            Part.BLOCK -> materialName.appendUnlocalizedAll("block")
            Part.RAW_ITEM -> "raw".appendUnlocalizedAll(materialName)
            Part.INGOT -> materialName.appendUnlocalizedAll("ingot")
            Part.NUGGET -> materialName.appendUnlocalizedAll("nugget")
            Part.GEM -> materialName
            Part.DUST -> materialName.appendUnlocalizedAll("dust")
        }
    }
}