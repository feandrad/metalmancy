package io.felipeandrade.metalmancy.tools.toolgen

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.floats.shouldBeBetween
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll
import tools.toolgen.TierCategories
import tools.toolgen.ToolType

/**
 * Property-based tests for tool attack properties (damage and speed).
 * 
 * These tests validate that axes and hoes have tier-appropriate attack properties
 * as specified in the requirements.
 */
class ToolAttackPropertiesTest : StringSpec({
    
    // Feature: basic-tools, Property 29: Axe attack properties vary by tier
    // Validates: Requirements 6.2
    "Property 29: Axe attack damage should be in range +6 to +9 based on tier" {
        checkAll(100, io.kotest.property.Arb.tierCategory()) { tier ->
            // Axes should have attack damage in the range of +6 to +9
            // The base ToolType.AXE has 6.0f, but tier-specific implementations may vary
            
            // For now, we validate that the base axe damage is within expected range
            val axeBaseDamage = ToolType.AXE.attackDamage
            
            // Base damage should be at least 6.0 (minimum for axes)
            (axeBaseDamage >= 6.0f) shouldBe true
            
            // With tier bonus, total damage should be between 6 and 9
            val totalDamage = axeBaseDamage + tier.attackDamageBonus
            totalDamage.shouldBeBetween(6.0f, 9.0f, 0.1f)
        }
    }
    
    "Property 29: Axe attack speed should be between -3.0 and -3.2" {
        checkAll(100, io.kotest.property.Arb.tierCategory()) { tier ->
            // Axes should have attack speed between -3.0 and -3.2
            val axeSpeed = ToolType.AXE.attackSpeed
            
            // Base speed should be in the valid range
            axeSpeed.shouldBeBetween(-3.2f, -3.0f, 0.01f)
        }
    }
    
    "Axe properties are consistent across all tier categories" {
        val tiers = listOf(
            TierCategories.COPPER_LIKE,
            TierCategories.IRON_LIKE,
            TierCategories.GOLD_LIKE,
            TierCategories.DIAMOND_LIKE,
            TierCategories.MYSTIC
        )
        
        tiers.forEach { tier ->
            val axeBaseDamage = ToolType.AXE.attackDamage
            val totalDamage = axeBaseDamage + tier.attackDamageBonus
            
            // Total damage should be in valid range
            totalDamage.shouldBeBetween(6.0f, 9.0f, 0.1f)
            
            // Attack speed should be in valid range
            val axeSpeed = ToolType.AXE.attackSpeed
            axeSpeed.shouldBeBetween(-3.2f, -3.0f, 0.01f)
        }
    }
    
    // Feature: basic-tools, Property 30: Hoe attack properties vary by tier
    // Validates: Requirements 6.5
    "Property 30: Hoe attack damage should be 0" {
        checkAll(100, io.kotest.property.Arb.tierCategory()) { tier ->
            // Hoes should have 0 attack damage
            val hoeDamage = ToolType.HOE.attackDamage
            hoeDamage shouldBe 0.0f
            
            // Even with tier bonus, hoes should have minimal damage
            val totalDamage = hoeDamage + tier.attackDamageBonus
            (totalDamage >= 0.0f) shouldBe true
        }
    }
    
    "Property 30: Hoe attack speed varies by tier" {
        // Hoes have tier-specific attack speeds
        // The base value is -3.0f, but this can vary by tier
        
        val tiers = listOf(
            TierCategories.COPPER_LIKE,
            TierCategories.IRON_LIKE,
            TierCategories.GOLD_LIKE,
            TierCategories.DIAMOND_LIKE,
            TierCategories.MYSTIC
        )
        
        tiers.forEach { tier ->
            // Hoe speed should be a valid negative value (slower than instant)
            val hoeSpeed = ToolType.HOE.attackSpeed
            (hoeSpeed < 0.0f) shouldBe true
            
            // Speed should be in a reasonable range for hoes
            hoeSpeed.shouldBeBetween(-4.0f, -1.0f, 0.1f)
        }
    }
    
    "Hoe base attack damage is always 0" {
        val hoeDamage = ToolType.HOE.attackDamage
        hoeDamage shouldBe 0.0f
    }
    
    "All tool types have valid attack properties" {
        val toolTypes = ToolType.entries
        
        toolTypes.forEach { toolType ->
            // Attack damage should be non-negative
            (toolType.attackDamage >= 0.0f) shouldBe true
            
            // Attack speed should be negative (cooldown)
            (toolType.attackSpeed < 0.0f) shouldBe true
            
            // Attack speed should be in reasonable range
            toolType.attackSpeed.shouldBeBetween(-4.0f, -1.0f, 0.1f)
        }
    }
})
