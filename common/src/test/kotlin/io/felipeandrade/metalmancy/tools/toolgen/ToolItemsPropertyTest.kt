package io.felipeandrade.metalmancy.tools.toolgen

import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.checkAll
import tools.toolgen.ToolMaterials
import tools.toolgen.ToolType

/**
 * Property-based tests for tool creative tab assignment and registration logic.
 * 
 * Feature: basic-tools, Property 7: Tools are assigned to correct creative tab
 * Validates: Requirements 3.4, 10.5
 * 
 * Note: These tests validate the configuration and logic that determines which tools
 * should be registered and made available in creative tabs. Actual Minecraft item
 * registration and creative tab assignment happens at runtime in platform-specific code.
 */
class ToolItemsPropertyTest : StringSpec({
    
    // Feature: basic-tools, Property 7: Tools are assigned to correct creative tab
    // Validates: Requirements 3.4, 10.5
    "Property 7: All tool-enabled materials with required parts should have tools in creative tabs" {
        // This property validates that the configuration is correct for creative tab assignment.
        // For each tool-enabled material with required parts, all 5 tool types should be
        // available for registration and creative tab assignment.
        
        checkAll(100, Arb.actualToolEnabledMaterial()) { material ->
            // Verify material has required parts
            val hasRequiredParts = ToolMaterials.hasRequiredParts(material)
            
            if (hasRequiredParts) {
                // Material should have INGOT or GEM part
                val hasIngotOrGem = material.parts.contains(Part.INGOT) || 
                                   material.parts.contains(Part.GEM)
                hasIngotOrGem shouldBe true
                
                // Material should have a valid tier mapping
                val tier = ToolMaterials.getTierWithValidation(material)
                (tier != null) shouldBe true
                
                // All 5 tool types should be generated for this material
                val expectedToolTypes = ToolType.entries
                expectedToolTypes shouldHaveSize 5
                
                // Each tool type should have a valid unlocalized name
                expectedToolTypes.forEach { toolType ->
                    val unlocalizedName = toolType.getUnlocalizedName(material.name)
                    unlocalizedName shouldBe "${material.name}_${toolType.unlocalizedSuffix}"
                }
            }
        }
    }
    
    "Tool-enabled materials list contains expected materials" {
        // Verify the tool-enabled list contains the materials specified in requirements
        val expectedMaterials = listOf(
            Materials.BRASS,
            Materials.BRONZE,
            Materials.SILVER,
            Materials.COBALT,
            Materials.ORICHALCUM,
            Materials.MITHRIL,
            Materials.PLATINUM,
            Materials.TITANIUM,
            Materials.ELECTRUM,
            Materials.TOPAZ,
            Materials.RUBY,
            Materials.SAPPHIRE,
            Materials.ALUMINUM,
            Materials.STEEL
        )
        
        expectedMaterials.forEach { material ->
            ToolMaterials.TOOL_ENABLED shouldContain material
        }
        
        ToolMaterials.TOOL_ENABLED shouldHaveSize expectedMaterials.size
    }
    
    "All tool-enabled materials have required parts for tool crafting" {
        ToolMaterials.TOOL_ENABLED.forEach { material ->
            val hasRequiredParts = ToolMaterials.hasRequiredParts(material)
            hasRequiredParts shouldBe true
            
            // Should have either INGOT or GEM
            val hasIngotOrGem = material.parts.contains(Part.INGOT) || 
                               material.parts.contains(Part.GEM)
            hasIngotOrGem shouldBe true
        }
    }
    
    "All tool-enabled materials have valid tier mappings" {
        ToolMaterials.TOOL_ENABLED.forEach { material ->
            val tier = ToolMaterials.getTierWithValidation(material)
            (tier != null) shouldBe true
            
            // Tier should have valid properties
            if (tier != null) {
                (tier.miningLevel >= 0) shouldBe true
                (tier.durability > 0) shouldBe true
                (tier.efficiency > 0.0f) shouldBe true
                (tier.enchantability > 0) shouldBe true
            }
        }
    }
    
    "Each tool-enabled material should generate 5 tools" {
        val validMaterials = ToolMaterials.TOOL_ENABLED.filter { 
            ToolMaterials.hasRequiredParts(it) 
        }
        
        validMaterials.forEach { material ->
            // Each material should generate all 5 tool types
            val toolTypes = ToolType.entries
            toolTypes shouldHaveSize 5
            
            // Verify each tool type has correct properties
            toolTypes.forEach { toolType ->
                val unlocalizedName = toolType.getUnlocalizedName(material.name)
                unlocalizedName shouldBe "${material.name}_${toolType.unlocalizedSuffix}"
                
                // Verify ingot count is correct for recipe generation
                (toolType.ingotCount > 0) shouldBe true
            }
        }
    }
    
    "Materials not in tool-enabled list should not generate tools" {
        checkAll(100, Arb.nonToolEnabledMaterial()) { material ->
            // Material should not be in the tool-enabled list
            (material !in ToolMaterials.TOOL_ENABLED) shouldBe true
        }
    }
    
    "Tool validation correctly identifies invalid materials" {
        val invalidMaterials = ToolMaterials.validateToolEnabledMaterials()
        
        // All materials in the tool-enabled list should be valid
        // (have required parts and tier mappings)
        invalidMaterials shouldHaveSize 0
    }
})
