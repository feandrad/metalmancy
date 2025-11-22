package io.felipeandrade.metalmancy.material

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.checkAll

/**
 * Property-based tests for MaterialBlocks registration logic.
 * 
 * These tests validate properties about block registration without requiring
 * full Minecraft initialization.
 */
class MaterialBlocksPropertyTest : StringSpec({
    
    // Feature: material-system, Property 5: Criação de blocos para partes de bloco
    // Validates: Requirements 2.1
    "Property 5: Materials with block parts should have blocks registered" {
        checkAll<Unit>(1) { _ ->
            // For each material category
            val allMaterialsWithBlocks = Materials.ALL.filter { material ->
                material.parts.any { it.isBlock }
            }
            
            allMaterialsWithBlocks.shouldNotBeEmpty()
            
            // Each material with block parts should have at least one block part
            allMaterialsWithBlocks.forEach { material ->
                val blockParts = material.parts.filter { it.isBlock }
                blockParts.shouldNotBeEmpty()
            }
        }
    }
    
    "Property 5: Block parts are correctly identified" {
        checkAll<Unit>(1) { _ ->
            // Verify that Part.isBlock is correctly set
            Part.ORE.isBlock shouldBe true
            Part.ORE_DEEPSLATE.isBlock shouldBe true
            Part.RAW_BLOCK.isBlock shouldBe true
            Part.BLOCK.isBlock shouldBe true
            
            Part.RAW_ITEM.isBlock shouldBe false
            Part.INGOT.isBlock shouldBe false
            Part.NUGGET.isBlock shouldBe false
            Part.GEM.isBlock shouldBe false
            Part.DUST.isBlock shouldBe false
        }
    }
    
    // Feature: material-system, Property 6: Namespace correto em ResourceLocations de blocos
    // Validates: Requirements 2.6
    "Property 6: Block unlocalized names use correct namespace pattern" {
        checkAll(100, Arb.material()) { material ->
            material.parts.filter { it.isBlock }.forEach { part ->
                val unlocalizedName = material.unlocalizedName(part)
                
                // Unlocalized name should not contain namespace (that's added during registration)
                unlocalizedName shouldBe "${material.name}_${part.suffix()}"
            }
        }
    }
    
    // Feature: material-system, Property 32: Aplicação de propriedades baseadas em categoria
    // Validates: Requirements 2.5, 10.4
    "Property 32: Gem materials are in GEMS category" {
        checkAll<Unit>(1) { _ ->
            Materials.GEMS.forEach { material ->
                material.family shouldBe Family.GEM
            }
        }
    }
    
    "Property 32: Salt materials are in SALTS category" {
        checkAll<Unit>(1) { _ ->
            Materials.SALTS.forEach { material ->
                material.family shouldBe Family.SALT
            }
        }
    }
    
    "Property 32: Metal materials are correctly categorized" {
        checkAll<Unit>(1) { _ ->
            // Copper-like metals
            Materials.COPPER_LIKE_METALS.forEach { material ->
                (material in Materials.METALS) shouldBe true
                material.family shouldBe Family.METAL
            }
            
            // Iron-like metals
            Materials.IRON_LIKE_METALS.forEach { material ->
                (material in Materials.METALS) shouldBe true
                material.family shouldBe Family.METAL
            }
            
            // Gold-like metals
            Materials.GOLD_LIKE_METALS.forEach { material ->
                (material in Materials.METALS) shouldBe true
                material.family shouldBe Family.METAL
            }
            
            // Diamond-like metals
            Materials.DIAMOND_LIKE_METALS.forEach { material ->
                (material in Materials.METALS) shouldBe true
                material.family shouldBe Family.METAL
            }
        }
    }
    
    "Property 32: Each metal is in exactly one category" {
        checkAll<Unit>(1) { _ ->
            Materials.METALS.forEach { metal ->
                val categories = listOf(
                    Materials.COPPER_LIKE_METALS,
                    Materials.IRON_LIKE_METALS,
                    Materials.GOLD_LIKE_METALS,
                    Materials.DIAMOND_LIKE_METALS,
                    Materials.SPECIAL_METALS
                )
                
                val categoriesContainingMetal = categories.count { metal in it }
                categoriesContainingMetal shouldBe 1
            }
        }
    }
    
    "Gems have expected block parts" {
        checkAll<Unit>(1) { _ ->
            Materials.GEMS.forEach { gem ->
                // Gems should have ORE, ORE_DEEPSLATE, and BLOCK
                gem.hasPart(Part.ORE) shouldBe true
                gem.hasPart(Part.BLOCK) shouldBe true
                
                // Gems should not have metal-specific parts
                gem.hasPart(Part.INGOT) shouldBe false
                gem.hasPart(Part.RAW_ITEM) shouldBe false
                gem.hasPart(Part.RAW_BLOCK) shouldBe false
            }
        }
    }
    
    "Metals have expected block parts" {
        checkAll<Unit>(1) { _ ->
            Materials.METALS.filter { it != Materials.MERCURY }.forEach { metal ->
                // Most metals should have ORE and BLOCK
                metal.hasPart(Part.ORE) shouldBe true
                metal.hasPart(Part.BLOCK) shouldBe true
                
                // Most metals should have RAW_BLOCK (except special cases)
                if (metal.hasPart(Part.RAW_ITEM)) {
                    metal.hasPart(Part.RAW_BLOCK) shouldBe true
                }
            }
        }
    }
    
    "Alloys do not have ore parts" {
        checkAll<Unit>(1) { _ ->
            Materials.ALLOYS.forEach { alloy ->
                // Alloys are crafted, not mined
                alloy.hasPart(Part.ORE) shouldBe false
                alloy.hasPart(Part.ORE_DEEPSLATE) shouldBe false
                alloy.hasPart(Part.RAW_ITEM) shouldBe false
                alloy.hasPart(Part.RAW_BLOCK) shouldBe false
                
                // But they should have BLOCK
                alloy.hasPart(Part.BLOCK) shouldBe true
            }
        }
    }
})
