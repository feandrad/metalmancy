package io.felipeandrade.metalmancy.tools.toolgen

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.checkAll
import tools.toolgen.TierCategories
import tools.toolgen.TierOverride

/**
 * Property-based tests for the ToolTier system.
 * 
 * These tests validate universal properties that should hold for all tool tiers.
 */
class ToolTierPropertyTest : StringSpec({
    
    // Feature: basic-tools, Property 1: Tool tier configuration accepts all required properties
    // Validates: Requirements 1.1
    "Property 1: Tool tier configuration accepts all required properties" {
        checkAll(100, Arb.toolTier()) { tier ->
            // All properties should be accessible and within valid ranges
            (tier.miningLevel >= 0) shouldBe true
            (tier.durability > 0) shouldBe true
            (tier.efficiency > 0.0f) shouldBe true
            (tier.attackDamageBonus >= 0.0f) shouldBe true
            (tier.enchantability > 0) shouldBe true
        }
    }
    
    // Feature: basic-tools, Property 2: Tier categories accept base values and overrides
    // Validates: Requirements 1.2, 1.3, 1.4, 1.5
    "Property 2: Tier categories accept base values and overrides" {
        checkAll(100, Arb.tierOverride()) { override ->
            val appliedTier = override.apply()
            
            // If a property is overridden, the applied tier should use the override value
            if (override.miningLevel != null) {
                appliedTier.miningLevel shouldBe override.miningLevel
            } else {
                appliedTier.miningLevel shouldBe override.baseTier.miningLevel
            }
            
            if (override.durability != null) {
                appliedTier.durability shouldBe override.durability
            } else {
                appliedTier.durability shouldBe override.baseTier.durability
            }
            
            if (override.efficiency != null) {
                appliedTier.efficiency shouldBe override.efficiency
            } else {
                appliedTier.efficiency shouldBe override.baseTier.efficiency
            }
            
            if (override.attackDamageBonus != null) {
                appliedTier.attackDamageBonus shouldBe override.attackDamageBonus
            } else {
                appliedTier.attackDamageBonus shouldBe override.baseTier.attackDamageBonus
            }
            
            if (override.enchantability != null) {
                appliedTier.enchantability shouldBe override.enchantability
            } else {
                appliedTier.enchantability shouldBe override.baseTier.enchantability
            }
        }
    }
    
    "Tier categories have correct default values" {
        // COPPER_LIKE
        TierCategories.COPPER_LIKE.miningLevel shouldBe 1
        TierCategories.COPPER_LIKE.durability shouldBe 200
        TierCategories.COPPER_LIKE.efficiency shouldBe 4.0f
        TierCategories.COPPER_LIKE.enchantability shouldBe 14
        
        // IRON_LIKE
        TierCategories.IRON_LIKE.miningLevel shouldBe 2
        TierCategories.IRON_LIKE.durability shouldBe 250
        TierCategories.IRON_LIKE.efficiency shouldBe 6.0f
        TierCategories.IRON_LIKE.enchantability shouldBe 14
        
        // GOLD_LIKE
        TierCategories.GOLD_LIKE.miningLevel shouldBe 0
        TierCategories.GOLD_LIKE.durability shouldBe 32
        TierCategories.GOLD_LIKE.efficiency shouldBe 12.0f
        TierCategories.GOLD_LIKE.enchantability shouldBe 22
        
        // DIAMOND_LIKE
        TierCategories.DIAMOND_LIKE.miningLevel shouldBe 3
        TierCategories.DIAMOND_LIKE.durability shouldBe 1561
        TierCategories.DIAMOND_LIKE.efficiency shouldBe 8.0f
        TierCategories.DIAMOND_LIKE.enchantability shouldBe 10
        
        // MYSTIC
        TierCategories.MYSTIC.miningLevel shouldBe 4
        TierCategories.MYSTIC.durability shouldBe 2031
        TierCategories.MYSTIC.efficiency shouldBe 9.0f
        TierCategories.MYSTIC.enchantability shouldBe 15
    }
    
    "TierOverride with no overrides returns base tier" {
        checkAll(100, Arb.tierCategory()) { baseTier ->
            val override = TierOverride(baseTier = baseTier)
            val appliedTier = override.apply()
            
            appliedTier.miningLevel shouldBe baseTier.miningLevel
            appliedTier.durability shouldBe baseTier.durability
            appliedTier.efficiency shouldBe baseTier.efficiency
            appliedTier.attackDamageBonus shouldBe baseTier.attackDamageBonus
            appliedTier.enchantability shouldBe baseTier.enchantability
        }
    }
    
    "TierOverride with all overrides uses override values" {
        checkAll(100, Arb.tierCategory()) { baseTier ->
            val newMiningLevel = (baseTier.miningLevel + 1) % 5
            val newDurability = baseTier.durability + 100
            val newEfficiency = baseTier.efficiency + 1.0f
            val newAttackDamage = baseTier.attackDamageBonus + 1.0f
            val newEnchantability = baseTier.enchantability + 5
            
            val override = TierOverride(
                baseTier = baseTier,
                miningLevel = newMiningLevel,
                durability = newDurability,
                efficiency = newEfficiency,
                attackDamageBonus = newAttackDamage,
                enchantability = newEnchantability
            )
            val appliedTier = override.apply()
            
            appliedTier.miningLevel shouldBe newMiningLevel
            appliedTier.durability shouldBe newDurability
            appliedTier.efficiency shouldBe newEfficiency
            appliedTier.attackDamageBonus shouldBe newAttackDamage
            appliedTier.enchantability shouldBe newEnchantability
        }
    }
})
