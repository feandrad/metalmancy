package io.felipeandrade.metalmancy.material

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        assertEquals("sapphire_gem", material.unlocalizedName(Part.GEM))
    }
    
    @Test
    fun `unlocalizedName generates correct block name`() {
        val material = Material("ruby", Family.GEM, setOf(Part.BLOCK))
        assertEquals("ruby_block", material.unlocalizedName(Part.BLOCK))
    }
    
    @Test
    fun `unlocalizedName throws exception for part not in material`() {
        val material = Material("ruby", Family.GEM, setOf(Part.ORE))
        assertThrows<IllegalArgumentException> {
            material.unlocalizedName(Part.INGOT)
        }
    }
    
    @Test
    fun `hasPart returns true for existing part`() {
        val material = Material("zinc", Family.METAL, setOf(Part.ORE, Part.INGOT))
        assertTrue(material.hasPart(Part.ORE))
        assertTrue(material.hasPart(Part.INGOT))
    }
    
    @Test
    fun `hasPart returns false for non-existing part`() {
        val material = Material("zinc", Family.METAL, setOf(Part.ORE))
        assertFalse(material.hasPart(Part.INGOT))
    }
    
    @Test
    fun `Material requires non-blank name`() {
        assertThrows<IllegalArgumentException> {
            Material("", Family.GEM, setOf(Part.ORE))
        }
    }
    
    @Test
    fun `Material requires at least one part`() {
        assertThrows<IllegalArgumentException> {
            Material("ruby", Family.GEM, emptySet())
        }
    }
}
