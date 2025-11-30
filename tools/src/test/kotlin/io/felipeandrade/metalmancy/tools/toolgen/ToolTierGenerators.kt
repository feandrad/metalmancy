package io.felipeandrade.metalmancy.tools.toolgen

import io.felipeandrade.metalmancy.items.ToolType
import io.felipeandrade.metalmancy.registry.material.Family
import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import tools.toolgen.TierCategories
import tools.toolgen.TierOverride
import tools.toolgen.ToolMaterials
import tools.toolgen.ToolTier

/**
 * Custom generators (Arb) for property-based testing of the ToolTier system.
 * These generators create random but valid instances of ToolTier and related types.
 */

/**
 * Generates random valid ToolTier instances with reasonable property values.
 */
fun Arb.Companion.toolTier(): Arb<ToolTier> = arbitrary {
    ToolTier(
        miningLevel = Arb.int(0..4).bind(),
        durability = Arb.int(32..3000).bind(),
        efficiency = Arb.float(1.0f..15.0f).bind(),
        attackDamageBonus = Arb.float(0.0f..10.0f).bind(),
        enchantability = Arb.int(1..30).bind()
    )
}

/**
 * Generates one of the predefined tier categories.
 */
fun Arb.Companion.tierCategory(): Arb<ToolTier> = arbitrary {
    listOf(
        TierCategories.COPPER_LIKE,
        TierCategories.IRON_LIKE,
        TierCategories.GOLD_LIKE,
        TierCategories.DIAMOND_LIKE,
        TierCategories.MYSTIC
    ).random()
}

/**
 * Generates TierOverride instances with random overrides applied to a base tier.
 */
fun Arb.Companion.tierOverride(): Arb<TierOverride> = arbitrary {
    val baseTier = Arb.tierCategory().bind()
    
    // Randomly decide which properties to override (at least one)
    val overrideMiningLevel = Arb.bool().bind()
    val overrideDurability = Arb.bool().bind()
    val overrideEfficiency = Arb.bool().bind()
    val overrideAttackDamage = Arb.bool().bind()
    val overrideEnchantability = Arb.bool().bind()
    
    // Ensure at least one override is present
    val hasAnyOverride = overrideMiningLevel || overrideDurability || overrideEfficiency || 
                         overrideAttackDamage || overrideEnchantability
    
    if (!hasAnyOverride) {
        // Force at least one override
        TierOverride(
            baseTier = baseTier,
            durability = Arb.int(32..3000).bind()
        )
    } else {
        TierOverride(
            baseTier = baseTier,
            miningLevel = if (overrideMiningLevel) Arb.int(0..4).bind() else null,
            durability = if (overrideDurability) Arb.int(32..3000).bind() else null,
            efficiency = if (overrideEfficiency) Arb.float(1.0f..15.0f).bind() else null,
            attackDamageBonus = if (overrideAttackDamage) Arb.float(0.0f..10.0f).bind() else null,
            enchantability = if (overrideEnchantability) Arb.int(1..30).bind() else null
        )
    }
}

/**
 * Generates random materials with various part configurations.
 */
fun Arb.Companion.material(): Arb<Material> = arbitrary {
    val name = Arb.string(5..15, Codepoint.az()).bind()
    val family = Arb.enum<Family>().bind()
    
    // Generate a random set of parts (at least one)
    val allParts = Part.entries
    val partCount = Arb.int(1..allParts.size).bind()
    val parts = allParts.shuffled().take(partCount).toSet()
    
    Material(name, family, parts)
}

/**
 * Generates materials that are valid for tool generation (have INGOT or GEM parts).
 */
fun Arb.Companion.toolEnabledMaterial(): Arb<Material> = arbitrary {
    val name = Arb.string(5..15, Codepoint.az()).bind()
    val family = Arb.enum<Family>().bind()
    
    // Ensure the material has either INGOT or GEM part
    val hasIngot = Arb.bool().bind()
    val baseParts = if (hasIngot) setOf(Part.INGOT) else setOf(Part.GEM)
    
    // Optionally add more parts
    val additionalParts = Part.entries.filter { it != Part.INGOT && it != Part.GEM }
        .shuffled()
        .take(Arb.int(0..3).bind())
        .toSet()
    
    Material(name, family, baseParts + additionalParts)
}

/**
 * Generates materials from the actual tool-enabled list.
 */
fun Arb.Companion.actualToolEnabledMaterial(): Arb<Material> = arbitrary {
    ToolMaterials.TOOL_ENABLED.random()
}

/**
 * Generates materials that are NOT in the tool-enabled list.
 */
fun Arb.Companion.nonToolEnabledMaterial(): Arb<Material> = arbitrary {
    val allMaterials = Materials.ALL
    val nonToolEnabled = allMaterials.filter { it !in ToolMaterials.TOOL_ENABLED }
    if (nonToolEnabled.isEmpty()) {
        // Fallback: create a material that's definitely not in the list
        Material("test_material", Family.METAL, setOf(Part.INGOT))
    } else {
        nonToolEnabled.random()
    }
}

/**
 * Generates materials without INGOT or GEM parts (invalid for tools).
 */
fun Arb.Companion.materialWithoutRequiredParts(): Arb<Material> = arbitrary {
    val name = Arb.string(5..15, Codepoint.az()).bind()
    val family = Arb.enum<Family>().bind()
    
    // Only use parts that are NOT INGOT or GEM
    val validParts = Part.entries.filter { it != Part.INGOT && it != Part.GEM }
    val partCount = Arb.int(1..validParts.size).bind()
    val parts = validParts.shuffled().take(partCount).toSet()
    
    Material(name, family, parts)
}

/**
 * Generates random ToolType values.
 */
fun Arb.Companion.toolType(): Arb<ToolType> = arbitrary {
    ToolType.entries.random()
}

/**
 * Generates pairs of (Material, ToolType) for tool generation testing.
 */
fun Arb.Companion.materialAndToolType(): Arb<Pair<Material, ToolType>> = arbitrary {
    val material = Arb.actualToolEnabledMaterial().bind()
    val toolType = Arb.toolType().bind()
    Pair(material, toolType)
}
