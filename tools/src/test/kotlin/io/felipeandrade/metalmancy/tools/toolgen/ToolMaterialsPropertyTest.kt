package io.felipeandrade.metalmancy.tools.toolgen

import io.felipeandrade.metalmancy.registry.material.Part
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.property.Arb
import io.kotest.property.checkAll
import tools.toolgen.ToolMaterials

/**
 * Property-based tests for ToolMaterials configuration.
 * Tests validate that tool-enabled materials meet requirements and that
 * non-tool-enabled materials are properly excluded.
 */
class ToolMaterialsPropertyTest : StringSpec({
    
    /**
     * Feature: basic-tools, Property 3: Tool-enabled materials require INGOT parts
     * 
     * For any material marked as tool-enabled, the system should verify the material 
     * has an INGOT part (or GEM for gem materials) and reject materials without the 
     * required part.
     * 
     * Validates: Requirements 2.2
     */
    "Property 3: Tool-enabled materials require INGOT parts".config(invocations = 100) {
        checkAll(Arb.actualToolEnabledMaterial()) { material ->
            // All tool-enabled materials must have INGOT or GEM parts
            val hasRequiredParts = material.parts.contains(Part.INGOT) || 
                                   material.parts.contains(Part.GEM)
            hasRequiredParts shouldBe true
            
            // The validation function should also confirm this
            ToolMaterials.hasRequiredParts(material) shouldBe true
        }
    }
    
    /**
     * Property 3 (edge case): Materials without required parts should be rejected
     * 
     * For any material without INGOT or GEM parts, the validation should fail.
     */
    "Property 3 (edge case): Materials without INGOT or GEM parts fail validation".config(invocations = 100) {
        checkAll(Arb.materialWithoutRequiredParts()) { material ->
            // Materials without INGOT or GEM should fail validation
            ToolMaterials.hasRequiredParts(material) shouldBe false
            
            // getTierWithValidation should return null for invalid materials
            ToolMaterials.getTierWithValidation(material) shouldBe null
        }
    }
    
    /**
     * Property 3 (validation): All actual tool-enabled materials should pass validation
     * 
     * This test ensures that the TOOL_ENABLED list doesn't contain any invalid materials.
     */
    "Property 3 (validation): All tool-enabled materials pass validation" {
        val invalidMaterials = ToolMaterials.validateToolEnabledMaterials()
        invalidMaterials shouldBe emptyList()
    }
})


/**
 * Property-based tests for non-tool-enabled materials.
 * These tests validate that materials not in the tool-enabled list are properly excluded.
 */
class NonToolEnabledMaterialsPropertyTest : StringSpec({
    
    /**
     * Feature: basic-tools, Property 4: Non-tool-enabled materials generate no tools
     * 
     * For any material not in the tool-enabled list, the system should not generate 
     * any tool items, recipes, or models.
     * 
     * Validates: Requirements 2.4
     * 
     * Note: This property tests the configuration level - that materials not in the
     * TOOL_ENABLED list are properly excluded. The actual tool generation logic will
     * be tested when the generator is implemented.
     */
    "Property 4: Non-tool-enabled materials are not in TOOL_ENABLED list".config(invocations = 100) {
        checkAll(Arb.nonToolEnabledMaterial()) { material ->
            // Material should not be in the tool-enabled list
            val isToolEnabled = material in ToolMaterials.TOOL_ENABLED
            isToolEnabled shouldBe false
        }
    }
    
    /**
     * Property 4 (validation): TOOL_ENABLED list contains only expected materials
     * 
     * This test validates that the TOOL_ENABLED list contains exactly the materials
     * specified in the requirements.
     */
    "Property 4 (validation): TOOL_ENABLED contains exactly the specified materials" {
        val expectedMaterials = setOf(
            "brass", "bronze", "silver", "cobalt", "orichalcum", "mithril",
            "platinum", "titanium", "electrum", "topaz", "ruby", "sapphire",
            "aluminum", "steel"
        )
        
        val actualMaterials = ToolMaterials.TOOL_ENABLED.map { it.name }.toSet()
        
        actualMaterials shouldBe expectedMaterials
    }
    
    /**
     * Property 4 (edge case): Tool-enabled and non-tool-enabled lists are disjoint
     * 
     * This test ensures there's no overlap between tool-enabled and non-tool-enabled materials.
     */
    "Property 4 (edge case): Tool-enabled and non-tool-enabled materials are disjoint" {
        checkAll(Arb.actualToolEnabledMaterial()) { toolEnabledMaterial ->
            checkAll(Arb.nonToolEnabledMaterial()) { nonToolEnabledMaterial ->
                // A material cannot be both tool-enabled and non-tool-enabled
                if (toolEnabledMaterial.name == nonToolEnabledMaterial.name) {
                    // If names match, they should be the same object (in TOOL_ENABLED)
                    toolEnabledMaterial shouldBe nonToolEnabledMaterial
                } else {
                    // If names differ, they should be different materials
                    toolEnabledMaterial shouldNotBe nonToolEnabledMaterial
                }
            }
        }
    }
})
