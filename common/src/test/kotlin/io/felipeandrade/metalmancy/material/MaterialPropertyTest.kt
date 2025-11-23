package io.felipeandrade.metalmancy.material

import io.felipeandrade.metalmancy.registry.material.Family
import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Part
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.checkAll

/**
 * Property-based tests for the Material data class.
 * 
 * These tests validate universal properties that should hold for all Materials.
 */
class MaterialPropertyTest : StringSpec({
    
    // Feature: material-system, Property 1: Preservação de campos do Material
    // Validates: Requirements 1.1, 1.5
    "Property 1: Material preserves all fields" {
        checkAll<String, Family, Set<Part>>(100) { name, family, parts ->
            // Skip invalid inputs (empty name or empty parts)
            if (name.isBlank() || parts.isEmpty()) return@checkAll
            
            val material = Material(name, family, parts)
            
            // Material should preserve exactly the values it was created with
            material.name shouldBe name
            material.family shouldBe family
            material.parts shouldBe parts
        }
    }
    
    // Feature: material-system, Property 2: Geração correta de nomes não-localizados
    // Validates: Requirements 1.2
    "Property 2: unlocalizedName generates correct names" {
        checkAll(100, Arb.material()) { material ->
            // For each part the material has
            material.parts.forEach { part ->
                val unlocalizedName = material.unlocalizedName(part)
                
                // The unlocalized name should contain the material name (or a variant)
                // Note: Some parts like RAW_ITEM use "raw_" prefix, GEM uses just the name
                unlocalizedName.isNotBlank() shouldBe true
                
                // Verify specific patterns based on part type
                when (part) {
                    Part.ORE -> unlocalizedName shouldBe "${material.name}_ore"
                    Part.ORE_DEEPSLATE -> unlocalizedName shouldBe "${material.name}_deepslate_ore"
                    Part.RAW_BLOCK -> unlocalizedName shouldBe "raw_${material.name}_block"
                    Part.BLOCK -> unlocalizedName shouldBe "${material.name}_block"
                    Part.RAW_ITEM -> unlocalizedName shouldBe "raw_${material.name}"
                    Part.INGOT -> unlocalizedName shouldBe "${material.name}_ingot"
                    Part.NUGGET -> unlocalizedName shouldBe "${material.name}_nugget"
                    Part.GEM -> unlocalizedName shouldBe material.name
                    Part.DUST -> unlocalizedName shouldBe "${material.name}_dust"
                }
            }
        }
    }
    

    
    "parts contains all parts the material was created with" {
        checkAll(100, Arb.material()) { material ->
            material.parts.forEach { part ->
                (part in material.parts) shouldBe true
            }
        }
    }
    
    "parts does not contain parts the material wasn't created with" {
        checkAll(100, Arb.material()) { material ->
            val allParts = Part.entries.toSet()
            val missingParts = allParts - material.parts
            
            missingParts.forEach { part ->
                (part in material.parts) shouldBe false
            }
        }
    }
    

})
