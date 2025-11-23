package io.felipeandrade.metalmancy.material

import io.felipeandrade.metalmancy.registry.material.Family
import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Part
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MaterialTest {
    
    @Test
    fun `Material preserves all fields`() {
        val material = Material("ruby", Family.GEM, setOf(Part.ORE, Part.GEM))
        
        assertEquals("ruby", material.name)
        assertEquals(Family.GEM, material.family)
        assertEquals(setOf(Part.ORE, Part.GEM), material.parts)
    }
    
    @Test
    fun `unlocalizedName generates correct ore name`() {
        val material = Material("ruby", Family.GEM, setOf(Part.ORE))
        assertEquals("ruby_ore", material.unlocalizedName(Part.ORE))
    }
    
    @Test
    fun `unlocalizedName generates correct deepslate ore name`() {
        val material = Material("zinc", Family.METAL, setOf(Part.ORE_DEEPSLATE))
        assertEquals("zinc_deepslate_ore", material.unlocalizedName(Part.ORE_DEEPSLATE))
    }
    
    @Test
    fun `unlocalizedName generates correct ingot name`() {
        val material = Material("zinc", Family.METAL, setOf(Part.INGOT))
        assertEquals("zinc_ingot", material.unlocalizedName(Part.INGOT))
    }
    
    @Test
    fun `unlocalizedName generates correct gem name`() {
        val material = Material("sapphire", Family.GEM, setOf(Part.GEM))
        assertEquals("sapphire", material.unlocalizedName(Part.GEM))
    }
    
    @Test
    fun `unlocalizedName generates correct block name`() {
        val material = Material("ruby", Family.GEM, setOf(Part.BLOCK))
        assertEquals("ruby_block", material.unlocalizedName(Part.BLOCK))
    }
    

    
    @Test
    fun `hasPart returns true for existing part`() {
        val material = Material("zinc", Family.METAL, setOf(Part.ORE, Part.INGOT))
        assertTrue(Part.ORE in material.parts)
        assertTrue(Part.INGOT in material.parts)
    }
    
    @Test
    fun `hasPart returns false for non-existing part`() {
        val material = Material("zinc", Family.METAL, setOf(Part.ORE))
        assertFalse(Part.GEM in material.parts)
    }
    

}
