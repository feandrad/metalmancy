package tools.toolgen

/**
 * Represents the five tool types that can be crafted from tool-enabled materials.
 * Each tool type has specific properties including attack damage, attack speed,
 * and the number of ingots required for crafting.
 */
enum class ToolType(
    /**
     * The suffix used in unlocalized names (e.g., "sword" for "brass_sword")
     */
    val unlocalizedSuffix: String,
    
    /**
     * The base attack damage modifier for this tool type.
     * Note: Axes may have tier-specific damage values (+6 to +9).
     */
    val attackDamage: Float,
    
    /**
     * The attack speed modifier for this tool type.
     * Note: Axes may have tier-specific speed values (-3.0 to -3.2).
     * Note: Hoes have tier-specific speed values.
     */
    val attackSpeed: Float,
    
    /**
     * The number of ingots required to craft this tool.
     * Also determines the nugget yield when recycling (1 nugget per ingot).
     */
    val ingotCount: Int
) {
    /**
     * Sword: 2 ingots + 1 stick in vertical pattern
     * Attack: +3 damage, -2.4 speed
     */
    SWORD("sword", 3.0f, -2.4f, 2),
    
    /**
     * Axe: 3 ingots + 2 sticks in axe pattern (with mirrored variant)
     * Attack: +6 damage (varies by tier: +6 to +9), -3.0 speed (varies by tier: -3.0 to -3.2)
     */
    AXE("axe", 6.0f, -3.0f, 3),
    
    /**
     * Pickaxe: 3 ingots + 2 sticks in pickaxe pattern
     * Attack: +1 damage, -2.8 speed
     */
    PICKAXE("pickaxe", 1.0f, -2.8f, 3),
    
    /**
     * Shovel: 1 ingot + 2 sticks in vertical pattern
     * Attack: +1.5 damage, -3.0 speed
     */
    SHOVEL("shovel", 1.5f, -3.0f, 1),
    
    /**
     * Hoe: 2 ingots + 2 sticks in hoe pattern (with mirrored variant)
     * Attack: +0 damage, variable speed based on tier
     */
    HOE("hoe", 0.0f, -3.0f, 2);
    
    /**
     * Returns the unlocalized name for a tool of this type made from the given material.
     * Format: "{material_name}_{tool_type}"
     * Example: "brass_sword", "titanium_pickaxe"
     */
    fun getUnlocalizedName(materialName: String): String {
        return "${materialName}_${unlocalizedSuffix}"
    }
}
