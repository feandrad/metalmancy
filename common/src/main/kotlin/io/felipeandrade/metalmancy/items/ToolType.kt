package io.felipeandrade.metalmancy.items

/**
 * Represents the five tool types that can be crafted from tool-enabled materials.
 * Each tool type has specific properties including attack damage, attack speed,
 * and the number of ingots required for crafting.
 */
enum class ToolType(
    val unlocalizedSuffix: String,
    val attackDamage: Float,
    val attackSpeed: Float,
    val ingotCount: Int
) {
    SWORD("sword", 3.0f, -2.4f, 2),
    AXE("axe", 6.0f, -3.0f, 3),
    PICKAXE("pickaxe", 1.0f, -2.8f, 3),
    SHOVEL("shovel", 1.5f, -3.0f, 1),
    HOE("hoe", 0.0f, -3.0f, 2);

    fun getUnlocalizedName(materialName: String): String {
        return "${materialName}_${unlocalizedSuffix}"
    }
}

enum class PowerToolType(
    val unlocalizedSuffix: String,
) {
    JackHammer("jackhammer"),
    ChainSaw("chainsaw");

    fun getUnlocalizedName(materialName: String): String {
        return "${materialName}_${unlocalizedSuffix}"
    }
}
