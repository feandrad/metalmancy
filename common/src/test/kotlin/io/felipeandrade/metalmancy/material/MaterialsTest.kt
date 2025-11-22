package io.felipeandrade.metalmancy.material

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
    fun `IRON_LIKE_METALS contains only METAL family materials`() {
        assertTrue(Materials.IRON_LIKE_METALS.all { it.family == Family.METAL })
    }
    
    @Test
    fun `GOLD_LIKE_METALS contains only METAL family materials`() {
        assertTrue(Materials.GOLD_LIKE_METALS.all { it.family == Family.METAL })
    }
    
    @Test
    fun `DIAMOND_LIKE_METALS contains only METAL family materials`() {
        assertTrue(Materials.DIAMOND_LIKE_METALS.all { it.family == Family.METAL })
    }
    
    @Test
    fun `SPECIAL_METALS contains only METAL family materials`() {
        assertTrue(Materials.SPECIAL_METALS.all { it.family == Family.METAL })
    }
    
    @Test
    fun `ALLOYS list contains only ALLOY family materials`() {
        assertTrue(Materials.ALLOYS.all { it.family == Family.ALLOY })
    }
    
    @Test
    fun `METALS is union of all metal categories`() {
        val expectedMetals = Materials.COPPER_LIKE_METALS + 
                            Materials.IRON_LIKE_METALS + 
                            Materials.GOLD_LIKE_METALS + 
                            Materials.DIAMOND_LIKE_METALS + 
                            Materials.SPECIAL_METALS
        
        assertEquals(expectedMetals.toSet(), Materials.METALS.toSet())
    }
    
    @Test
    fun `ALL is union of GEMS, SALTS, METALS and ALLOYS`() {
        val expected = Materials.GEMS + Materials.SALTS + Materials.METALS + Materials.ALLOYS
        assertEquals(expected.toSet(), Materials.ALL.toSet())
    }
    
    @Test
    fun `ALL contains every defined material`() {
        // Verify specific materials are in ALL
        assertTrue(Materials.RUBY in Materials.ALL)
        assertTrue(Materials.ZINC in Materials.ALL)
        assertTrue(Materials.SALT in Materials.ALL)
        assertTrue(Materials.BRASS in Materials.ALL)
        assertTrue(Materials.MITHRIL in Materials.ALL)
    }
    
    @Test
    fun `Material names are unique`() {
        val names = Materials.ALL.map { it.name }
        assertEquals(names.size, names.toSet().size, "Material names must be unique")
    }
    
    @Test
    fun `RUBY has correct parts`() {
        assertTrue(Materials.RUBY.hasPart(Part.ORE))
        assertTrue(Materials.RUBY.hasPart(Part.ORE_DEEPSLATE))
        assertTrue(Materials.RUBY.hasPart(Part.GEM))
        assertTrue(Materials.RUBY.hasPart(Part.BLOCK))
    }
    
    @Test
    fun `ZINC has correct parts`() {
        assertTrue(Materials.ZINC.hasPart(Part.ORE))
        assertTrue(Materials.ZINC.hasPart(Part.ORE_DEEPSLATE))
        assertTrue(Materials.ZINC.hasPart(Part.RAW_ITEM))
        assertTrue(Materials.ZINC.hasPart(Part.RAW_BLOCK))
        assertTrue(Materials.ZINC.hasPart(Part.INGOT))
        assertTrue(Materials.ZINC.hasPart(Part.NUGGET))
        assertTrue(Materials.ZINC.hasPart(Part.DUST))
        assertTrue(Materials.ZINC.hasPart(Part.BLOCK))
    }
    
    @Test
    fun `BRASS alloy does not have ore parts`() {
        assertTrue(!Materials.BRASS.hasPart(Part.ORE))
        assertTrue(!Materials.BRASS.hasPart(Part.ORE_DEEPSLATE))
        assertTrue(Materials.BRASS.hasPart(Part.INGOT))
        assertTrue(Materials.BRASS.hasPart(Part.BLOCK))
    }
    
    @Test
    fun `MERCURY has special parts configuration`() {
        assertTrue(Materials.MERCURY.hasPart(Part.ORE))
        assertTrue(Materials.MERCURY.hasPart(Part.ORE_DEEPSLATE))
        assertTrue(Materials.MERCURY.hasPart(Part.DUST))
        // Mercury is liquid, so no ingot or block
        assertTrue(!Materials.MERCURY.hasPart(Part.INGOT))
        assertTrue(!Materials.MERCURY.hasPart(Part.BLOCK))
    }
}
