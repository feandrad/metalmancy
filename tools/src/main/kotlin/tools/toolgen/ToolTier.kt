package tools.toolgen

import io.felipeandrade.metalmancy.registry.material.Material

/**
 * Represents the properties of a tool tier, including mining level, durability,
 * efficiency, attack damage bonus, and enchantability.
 */
data class ToolTier(
    val miningLevel: Int,
    val durability: Int,
    val efficiency: Float,
    val attackDamageBonus: Float,
    val enchantability: Int
)


/**
 * Predefined tier categories that mirror Minecraft's vanilla progression.
 * Materials can be assigned to these categories or provide custom tier properties.
 */
object TierCategories {
    /**
     * COPPER_LIKE: Mining level 1 (stone-tier blocks), 200 durability, 4.0 efficiency, 14 enchantability
     * Fills the gap between stone and iron tools.
     */
    val COPPER_LIKE = ToolTier(
        miningLevel = 1,
        durability = 200,
        efficiency = 4.0f,
        attackDamageBonus = 0.0f,
        enchantability = 14
    )

    /**
     * IRON_LIKE: Mining level 2 (iron-tier blocks), 250 durability, 6.0 efficiency, 14 enchantability
     * Equivalent to vanilla iron tools.
     */
    val IRON_LIKE = ToolTier(
        miningLevel = 2,
        durability = 250,
        efficiency = 6.0f,
        attackDamageBonus = 0.0f,
        enchantability = 14
    )

    /**
     * GOLD_LIKE: Mining level 0 (wood-tier blocks), 32 durability, 12.0 efficiency, 22 enchantability
     * Fast but fragile, like vanilla gold tools.
     */
    val GOLD_LIKE = ToolTier(
        miningLevel = 0,
        durability = 32,
        efficiency = 12.0f,
        attackDamageBonus = 0.0f,
        enchantability = 22
    )

    /**
     * DIAMOND_LIKE: Mining level 3 (diamond-tier blocks), 1561 durability, 8.0 efficiency, 10 enchantability
     * Equivalent to vanilla diamond tools.
     */
    val DIAMOND_LIKE = ToolTier(
        miningLevel = 3,
        durability = 1561,
        efficiency = 8.0f,
        attackDamageBonus = 0.0f,
        enchantability = 10
    )

    /**
     * MYSTIC: Mining level 4 (netherite-tier blocks), 2031 durability, 9.0 efficiency, 15 enchantability
     * Matches or exceeds vanilla netherite tools.
     */
    val MYSTIC = ToolTier(
        miningLevel = 4,
        durability = 2031,
        efficiency = 9.0f,
        attackDamageBonus = 0.0f,
        enchantability = 15
    )
}

/**
 * Configuration for tool tier overrides. Allows materials to customize specific
 * properties while inheriting others from their base tier category.
 */
data class TierOverride(
    val baseTier: ToolTier,
    val miningLevel: Int? = null,
    val durability: Int? = null,
    val efficiency: Float? = null,
    val attackDamageBonus: Float? = null,
    val enchantability: Int? = null
) {
    /**
     * Applies the overrides to the base tier, returning a new ToolTier with
     * override values where specified and base values otherwise.
     */
    fun apply(): ToolTier = ToolTier(
        miningLevel = miningLevel ?: baseTier.miningLevel,
        durability = durability ?: baseTier.durability,
        efficiency = efficiency ?: baseTier.efficiency,
        attackDamageBonus = attackDamageBonus ?: baseTier.attackDamageBonus,
        enchantability = enchantability ?: baseTier.enchantability
    )
}

/**
 * Maps materials to their tool tiers. Materials are assigned to tier categories
 * based on their property groups defined in Materials.kt.
 */
object MaterialTiers {
    private val tierMap = mutableMapOf<Material, ToolTier>()
    private val overrideMap = mutableMapOf<Material, TierOverride>()

    /**
     * Gets the tool tier for a material. Returns the tier with any overrides applied.
     */
    fun getTier(material: Material): ToolTier {
        return overrideMap[material]?.apply() ?: tierMap[material] 
            ?: throw IllegalArgumentException("Material ${material.name} does not have a tool tier defined")
    }

    /**
     * Sets a base tier for a material without overrides.
     */
    fun setTier(material: Material, tier: ToolTier) {
        tierMap[material] = tier
    }

    /**
     * Sets a tier with overrides for a material.
     */
    fun setTierWithOverride(material: Material, override: TierOverride) {
        overrideMap[material] = override
    }

    /**
     * Checks if a material has a tier defined.
     */
    fun hasTier(material: Material): Boolean {
        return tierMap.containsKey(material) || overrideMap.containsKey(material)
    }
}
