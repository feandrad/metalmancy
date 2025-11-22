package io.felipeandrade.metalmancy.material

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
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
                
                // The unlocalized name should contain the material name
                unlocalizedName shouldContain material.name
                
                // The unlocalized name should contain the part suffix
                unlocalizedName shouldContain part.suffix()
                
                // The unlocalized name should follow the pattern: name_suffix
                unlocalizedName shouldBe "${material.name}_${part.suffix()}"
            }
        }
    }
    
    "Material name cannot be blank" {
        checkAll(100, Arb.family(), Arb.partSet()) { family, parts ->
            try {
                Material("", family, parts)
                throw AssertionError("Should have thrown exception for blank name")
            } catch (e: IllegalArgumentException) {
                // Expected
            }
        }
    }
    
    "Material must have at least one part" {
        checkAll(100, Arb.materialName(), Arb.family()) { name, family ->
            try {
                Material(name, family, emptySet())
                throw AssertionError("Should have thrown exception for empty parts")
            } catch (e: IllegalArgumentException) {
                // Expected
            }
        }
    }
    
    "hasPart returns true for parts in the material" {
        checkAll(100, Arb.material()) { material ->
            material.parts.forEach { part ->
                material.hasPart(part) shouldBe true
            }
        }
    }
    
    "hasPart returns false for parts not in the material" {
        checkAll(100, Arb.material()) { material ->
            val allParts = Part.entries.toSet()
            val missingParts = allParts - material.parts
            
            missingParts.forEach { part ->
                material.hasPart(part) shouldBe false
            }
        }
    }
    
    "unlocalizedName throws for parts not in material" {
        checkAll(100, Arb.material()) { material ->
            val allParts = Part.entries.toSet()
            val missingParts = allParts - material.parts
            
            missingParts.forEach { part ->
                try {
                    material.unlocalizedName(part)
                    throw AssertionError("Should have thrown exception for missing part")
                } catch (e: IllegalArgumentException) {
                    // Expected
                }
            }
        }
    }
})
