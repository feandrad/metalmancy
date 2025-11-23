package io.felipeandrade.metalmancy.material

import io.felipeandrade.metalmancy.registry.material.Family
import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MaterialsTest {
    
    @Test
    fun `GEMS list contains only GEM family materials`() {
        assertTrue(Materials.GEMS.all { it.family == Family.GEM })
    }
    
    @Test
    fun `SALTS list contains only SALT family materials`() {
        assertTrue(Materials.SALTS.all { it.family == Family.SALT })
    }
    
    @Test
    fun `COPPER_LIKE_METALS contains only METAL family materials`() {
        assertTrue(Materials.COPPER_LIKE_METALS.all { it.family == Family.METAL })
    }
    
    @Test
    fun `IRON_LIKE_METALS contains only METAL or ALLOY family materials`() {
        assertTrue(Materials.IRON_LIKE_METALS.all { it.family == Family.METAL || it.family == Family.ALLOY })
    }
    
    @Test
    fun `GOLD_LIKE_METALS contains only METAL or STONE family materials`() {
        assertTrue(Materials.GOLD_LIKE_METALS.all { it.family == Family.METAL || it.family == Family.STONE })
    }
    
    @Test
    fun `DIAMOND_LIKE_METALS contains only METAL family materials`() {
        assertTrue(Materials.DIAMOND_LIKE_METALS.all { it.family == Family.METAL })
    }
    
    @Test
    fun `METALS is union of all metal categories`() {
        val expectedMetals = Materials.COPPER_LIKE_METALS + 
                            Materials.IRON_LIKE_METALS + 
                            Materials.GOLD_LIKE_METALS + 
                            Materials.DIAMOND_LIKE_METALS
        
        assertEquals(expectedMetals.toSet(), Materials.METALS.toSet())
    }
    
    @Test
    fun `ALL is union of GEMS, SALTS, STONES, METALS and ALLOYS`() {
        val expected = Materials.GEMS + Materials.SALTS + Materials.STONES + Materials.METALS + Materials.ALLOYS
        assertEquals(expected.toSet(), Materials.ALL.toSet())
    }
    
    @Test
    fun `ALL contains every defined material`() {
        // Verify specific materials are in ALL
        assertTrue(Materials.RUBY in Materials.ALL)
        assertTrue(Materials.ZINC in Materials.ALL)
        assertTrue(Materials.SALT in Materials.ALL)
        assertTrue(Materials.MITHRIL in Materials.ALL)
    }
    
    @Test
    fun `Material names are unique`() {
        val names = Materials.ALL.map { it.name }
        assertEquals(names.size, names.toSet().size, "Material names must be unique")
    }
    
    @Test
    fun `RUBY has correct parts`() {
        assertTrue(Part.ORE in Materials.RUBY.parts)
        assertTrue(Part.ORE_DEEPSLATE in Materials.RUBY.parts)
        assertTrue(Part.GEM in Materials.RUBY.parts)
        assertTrue(Part.BLOCK in Materials.RUBY.parts)
    }
    
    @Test
    fun `ZINC has correct parts`() {
        assertTrue(Part.ORE in Materials.ZINC.parts)
        assertTrue(Part.ORE_DEEPSLATE in Materials.ZINC.parts)
        assertTrue(Part.RAW_ITEM in Materials.ZINC.parts)
        assertTrue(Part.RAW_BLOCK in Materials.ZINC.parts)
        assertTrue(Part.INGOT in Materials.ZINC.parts)
        assertTrue(Part.NUGGET in Materials.ZINC.parts)
        assertTrue(Part.BLOCK in Materials.ZINC.parts)
    }
    
    @Test
    fun `BRASS alloy does not have ore parts`() {
        assertTrue(Part.ORE !in Materials.BRASS.parts)
        assertTrue(Part.ORE_DEEPSLATE !in Materials.BRASS.parts)
        assertTrue(Part.INGOT in Materials.BRASS.parts)
        assertTrue(Part.BLOCK in Materials.BRASS.parts)
    }
    
    @Test
    fun `MERCURY has special parts configuration`() {
        assertTrue(Part.ORE in Materials.MERCURY.parts)
        assertTrue(Part.ORE_DEEPSLATE in Materials.MERCURY.parts)
        assertTrue(Part.GEM in Materials.MERCURY.parts)
        // Mercury (cinnabar) is a stone/gem, so no ingot or block
        assertTrue(Part.INGOT !in Materials.MERCURY.parts)
        assertTrue(Part.BLOCK !in Materials.MERCURY.parts)
    }
}
