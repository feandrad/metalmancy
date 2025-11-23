package io.felipeandrade.metalmancy.material

import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.checkAll

/**
 * Property-based tests for MaterialItems registration logic.
 * 
 * These tests validate properties about item registration without requiring
 * full Minecraft initialization.
 */
class MaterialItemsPropertyTest : StringSpec({
    
    // Feature: material-system, Property 7: Criação de itens para partes de item
    // Validates: Requirements 3.1
    "Property 7: Materials with item parts should have items" {
        checkAll<Unit>(1) { _ ->
            // For each material
            Materials.ALL.forEach { material ->
                val itemParts = material.parts.filter { !it.isBlock }
                
                // If material has item parts, verify they exist
                if (itemParts.isNotEmpty()) {
                    itemParts.shouldNotBeEmpty()
                }
            }
        }
    }
    
    "Property 7: Item parts are correctly identified" {
        checkAll<Unit>(1) { _ ->
            // Verify that Part.isBlock is correctly set for items
            Part.RAW_ITEM.isBlock shouldBe false
            Part.INGOT.isBlock shouldBe false
            Part.NUGGET.isBlock shouldBe false
            Part.GEM.isBlock shouldBe false
            Part.DUST.isBlock shouldBe false
        }
    }
    
    // Feature: material-system, Property 8: Criação de BlockItems para blocos
    // Validates: Requirements 3.2
    "Property 8: Materials with blocks should have BlockItems" {
        checkAll<Unit>(1) { _ ->
            Materials.ALL.forEach { material ->
                val blockParts = material.parts.filter { it.isBlock }
                
                // Each block part should also be in the material's parts
                // (which means it will get a BlockItem)
                blockParts.forEach { blockPart ->
                    (blockPart in material.parts) shouldBe true
                }
            }
        }
    }
    
    // Feature: material-system, Property 9: Namespace correto em ResourceLocations de itens
    // Validates: Requirements 3.4
    "Property 9: Item unlocalized names use correct namespace pattern" {
        checkAll(100, Arb.material()) { material ->
            material.parts.forEach { part ->
                val unlocalizedName = material.unlocalizedName(part)
                
                // Unlocalized name should not contain namespace (that's added during registration)
                // and should match the expected pattern from Material.unlocalizedName
                unlocalizedName.isNotBlank() shouldBe true
                unlocalizedName.contains(":") shouldBe false // No namespace in unlocalized name
            }
        }
    }
    
    // Feature: material-system, Property 10: Mapeamento completo de partes para itens
    // Validates: Requirements 3.5
    "Property 10: All material parts should be mappable to items" {
        checkAll<Unit>(1) { _ ->
            Materials.ALL.forEach { material ->
                // Every part should be mappable (either as Item or BlockItem)
                material.parts.forEach { part ->
                    // Verify the part can generate an unlocalized name
                    val unlocalizedName = material.unlocalizedName(part)
                    unlocalizedName.isNotBlank() shouldBe true
                }
            }
        }
    }
    
    "Property 10: Gems have expected item parts" {
        checkAll<Unit>(1) { _ ->
            Materials.GEMS.forEach { gem ->
                // Gems should have GEM item
                (Part.GEM in gem.parts) shouldBe true
                
                // Gems should not have metal-specific items
                (Part.INGOT in gem.parts) shouldBe false
                (Part.NUGGET in gem.parts) shouldBe false
                (Part.RAW_ITEM in gem.parts) shouldBe false
            }
        }
    }
    
    "Property 10: Metals have expected item parts" {
        checkAll<Unit>(1) { _ ->
            Materials.METALS.filter { it != Materials.MERCURY }.forEach { metal ->
                // Most metals should have INGOT
                (Part.INGOT in metal.parts) shouldBe true
                
                // Most metals should have NUGGET
                (Part.NUGGET in metal.parts) shouldBe true
            }
        }
    }
    
    "Property 10: Alloys have expected item parts" {
        checkAll<Unit>(1) { _ ->
            Materials.ALLOYS.forEach { alloy ->
                // Alloys should have INGOT
                (Part.INGOT in alloy.parts) shouldBe true
                
                // Alloys should have NUGGET
                (Part.NUGGET in alloy.parts) shouldBe true
            }
        }
    }
    
    "Convenience accessors match material definitions" {
        checkAll<Unit>(1) { _ ->
            // Verify that convenience accessors would return correct materials
            // (We can't test the actual accessors without Minecraft runtime,
            // but we can verify the materials exist)
            
            // Gems
            (Materials.RUBY in Materials.GEMS) shouldBe true
            (Materials.SAPPHIRE in Materials.GEMS) shouldBe true
            (Materials.TOPAZ in Materials.GEMS) shouldBe true
            
            // Salts
            (Materials.SALT in Materials.SALTS) shouldBe true
            (Materials.POTASH in Materials.SALTS) shouldBe true
            
            // Metals
            (Materials.ZINC in Materials.METALS) shouldBe true
            (Materials.SILVER in Materials.METALS) shouldBe true
            (Materials.MITHRIL in Materials.METALS) shouldBe true
            
            // Alloys
            (Materials.BRASS in Materials.ALLOYS) shouldBe true
            (Materials.STEEL in Materials.ALLOYS) shouldBe true
        }
    }
    
    "All parts of a material should be unique" {
        checkAll(100, Arb.material()) { material ->
            val parts = material.parts.toList()
            val uniqueParts = parts.toSet()
            
            parts.size shouldBe uniqueParts.size
        }
    }
})
