package io.felipeandrade.metalmancy.tools.toolgen

import io.felipeandrade.metalmancy.registry.material.Part
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
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
