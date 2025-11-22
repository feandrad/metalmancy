package io.felipeandrade.metalmancy.material

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PartTest {
    
    @Test
    fun `ORE is a block part`() {
        assertTrue(Part.ORE.isBlock)
    }
    
    @Test
    fun `ORE_DEEPSLATE is a block part`() {
        assertTrue(Part.ORE_DEEPSLATE.isBlock)
    }
    
    @Test
    fun `RAW_BLOCK is a block part`() {
        assertTrue(Part.RAW_BLOCK.isBlock)
    }
    
    @Test
    fun `BLOCK is a block part`() {
        assertTrue(Part.BLOCK.isBlock)
    }
    
    @Test
    fun `INGOT is not a block part`() {
        assertFalse(Part.INGOT.isBlock)
    }
    
    @Test
    fun `NUGGET is not a block part`() {
        assertFalse(Part.NUGGET.isBlock)
    }
    
    @Test
    fun `GEM is not a block part`() {
        assertFalse(Part.GEM.isBlock)
    }
    
    @Test
    fun `DUST is not a block part`() {
        assertFalse(Part.DUST.isBlock)
    }
    
    @Test
    fun `RAW_ITEM is not a block part`() {
        assertFalse(Part.RAW_ITEM.isBlock)
    }
    
    @Test
    fun `ORE suffix is correct`() {
        assertEquals("ore", Part.ORE.suffix())
    }
    
    @Test
    fun `ORE_DEEPSLATE suffix is correct`() {
        assertEquals("deepslate_ore", Part.ORE_DEEPSLATE.suffix())
    }
    
    @Test
    fun `RAW_BLOCK suffix is correct`() {
        assertEquals("raw_block", Part.RAW_BLOCK.suffix())
    }
    
    @Test
    fun `BLOCK suffix is correct`() {
        assertEquals("block", Part.BLOCK.suffix())
    }
    
    @Test
    fun `RAW_ITEM suffix is correct`() {
        assertEquals("raw", Part.RAW_ITEM.suffix())
    }
    
    @Test
    fun `INGOT suffix is correct`() {
        assertEquals("ingot", Part.INGOT.suffix())
    }
    
    @Test
    fun `NUGGET suffix is correct`() {
        assertEquals("nugget", Part.NUGGET.suffix())
    }
    
    @Test
    fun `GEM suffix is correct`() {
        assertEquals("gem", Part.GEM.suffix())
    }
    
    @Test
    fun `DUST suffix is correct`() {
        assertEquals("dust", Part.DUST.suffix())
    }
}
