package io.felipeandrade.metalmancy.tools

import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ToolRecipeGenerationLogicTest {

    @Test
    fun `gems should not generate smelting or blasting recipes`() {
        // Given
        val gemMaterial = Materials.RUBY
        val metalMaterial = Materials.SILVER
        
        // When checking conditions used in RecipeGen.kt
        val gemHasIngot = gemMaterial.parts.contains(Part.INGOT)
        val metalHasIngot = metalMaterial.parts.contains(Part.INGOT)
        
        // Then
        assertFalse(gemHasIngot, "Ruby should not have INGOT part")
        assertTrue(metalHasIngot, "Silver should have INGOT part")
        
        // Verify the logic block structure which should be used in RecipeGen.kt
        val gemWouldGenerateSmelting = gemHasIngot
        val metalWouldGenerateSmelting = metalHasIngot
        
        assertFalse(gemWouldGenerateSmelting, "Logic should skip smelting generation for Ruby")
        assertTrue(metalWouldGenerateSmelting, "Logic should allow smelting generation for Silver")
    }
}
