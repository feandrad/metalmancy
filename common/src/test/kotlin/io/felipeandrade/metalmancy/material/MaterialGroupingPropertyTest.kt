package io.felipeandrade.metalmancy.material

import io.felipeandrade.metalmancy.registry.material.Family
import io.felipeandrade.metalmancy.registry.material.Materials
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

/**
 * Property-based tests for material grouping and categorization.
 * 
 * These tests validate that materials are correctly grouped by family and category.
 */
class MaterialGroupingPropertyTest : StringSpec({
    
    // Feature: material-system, Property 3: Agrupamento por família
    // Validates: Requirements 1.4, 10.1, 10.2
    "Property 3: GEMS contains only GEM family materials" {
        checkAll<Unit>(1) { _ -> // Run once since we're testing static data
            Materials.GEMS.forEach { material ->
                material.family shouldBe Family.GEM
            }
        }
    }
    
    "Property 3: SALTS contains only SALT family materials" {
        checkAll<Unit>(1) { _ ->
            Materials.SALTS.forEach { material ->
                material.family shouldBe Family.SALT
            }
        }
    }
    
    "Property 3: METALS contains only METAL family materials" {
        checkAll<Unit>(1) { _ ->
            Materials.METALS.forEach { material ->
                material.family shouldBe Family.METAL
            }
        }
    }
    
    "Property 3: ALLOYS contains only ALLOY family materials" {
        checkAll<Unit>(1) { _ ->
            Materials.ALLOYS.forEach { material ->
                material.family shouldBe Family.ALLOY
            }
        }
    }
    
    // Feature: material-system, Property 4: Completude da lista ALL
    // Validates: Requirements 10.5
    "Property 4: ALL is union of GEMS, SALTS, STONES, METALS, and ALLOYS" {
        checkAll<Unit>(1) { _ ->
            val expected = (Materials.GEMS + Materials.SALTS + Materials.STONES + Materials.METALS + Materials.ALLOYS).toSet()
            val actual = Materials.ALL.toSet()
            
            actual shouldBe expected
        }
    }
    
    "Property 4: ALL contains every material from each category" {
        checkAll<Unit>(1) { _ ->
            // Every gem should be in ALL
            Materials.GEMS.forEach { gem ->
                (gem in Materials.ALL) shouldBe true
            }
            
            // Every salt should be in ALL
            Materials.SALTS.forEach { salt ->
                (salt in Materials.ALL) shouldBe true
            }
            
            // Every metal should be in ALL
            Materials.METALS.forEach { metal ->
                (metal in Materials.ALL) shouldBe true
            }
            
            // Every alloy should be in ALL
            Materials.ALLOYS.forEach { alloy ->
                (alloy in Materials.ALL) shouldBe true
            }
        }
    }
    
    // Feature: material-system, Property 31: Agrupamento de metais por nível
    // Validates: Requirements 10.3
    "Property 31: COPPER_LIKE_METALS contains only METAL family" {
        checkAll<Unit>(1) { _ ->
            Materials.COPPER_LIKE_METALS.forEach { material ->
                material.family shouldBe Family.METAL
            }
        }
    }
    
    "Property 31: IRON_LIKE_METALS contains only METAL family" {
        checkAll<Unit>(1) { _ ->
            Materials.IRON_LIKE_METALS.forEach { material ->
                material.family shouldBe Family.METAL
            }
        }
    }
    
    "Property 31: GOLD_LIKE_METALS contains only METAL family" {
        checkAll<Unit>(1) { _ ->
            Materials.GOLD_LIKE_METALS.forEach { material ->
                material.family shouldBe Family.METAL
            }
        }
    }
    
    "Property 31: DIAMOND_LIKE_METALS contains only METAL family" {
        checkAll<Unit>(1) { _ ->
            Materials.DIAMOND_LIKE_METALS.forEach { material ->
                material.family shouldBe Family.METAL
            }
        }
    }
    
    "Property 31: SPECIAL_METALS contains only METAL family" {
        checkAll<Unit>(1) { _ ->
            Materials.SPECIAL_METALS.forEach { material ->
                material.family shouldBe Family.METAL
            }
        }
    }
    
    "Property 31: METALS is union of all metal categories" {
        checkAll<Unit>(1) { _ ->
            val expected = (Materials.COPPER_LIKE_METALS + 
                           Materials.IRON_LIKE_METALS + 
                           Materials.GOLD_LIKE_METALS + 
                           Materials.DIAMOND_LIKE_METALS + 
                           Materials.SPECIAL_METALS).toSet()
            val actual = Materials.METALS.toSet()
            
            actual shouldBe expected
        }
    }
    
    "Material names are unique across ALL" {
        checkAll<Unit>(1) { _ ->
            val names = Materials.ALL.map { it.name }
            val uniqueNames = names.toSet()
            
            names.size shouldBe uniqueNames.size
        }
    }
})
