package io.felipeandrade.metalmancy.tools

import io.felipeandrade.metalmancy.registry.material.Part
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import tools.lootgen.*

class LootGenTest {

    @Test
    fun `getDropName should return correct names`() {
        assertEquals("metalmancy:raw_test_material", getDropName(GeneratedLoot("test_material", Part.RAW_ITEM)))
        assertEquals("metalmancy:test_material_dust", getDropName(GeneratedLoot("test_material", Part.DUST)))
        assertEquals("metalmancy:test_material", getDropName(GeneratedLoot("test_material", Part.GEM)))
    }

    @Test
    fun `getLootTableFileName should return correct filenames`() {
        val entry = GeneratedLoot("zinc", Part.RAW_ITEM)
        assertEquals("zinc_ore.json", getLootTableFileName(entry, Part.ORE))
        assertEquals("zinc_deepslate_ore.json", getLootTableFileName(entry, Part.ORE_DEEPSLATE))
        assertEquals("zinc_block.json", getLootTableFileName(entry, Part.BLOCK))
        assertEquals("raw_zinc_block.json", getLootTableFileName(entry, Part.RAW_BLOCK))
    }

    @Test
    fun `createOreLootTable should generate correct structure with Fortune and Silk Touch`() {
        val loot = GeneratedLoot("test_material", Part.RAW_ITEM)
        val table = createOreLootTable(loot, Part.ORE)

        assertEquals("minecraft:block", table["type"])
        assertEquals("metalmancy:blocks/test_material_ore", table["random_sequence"])

        val pools = table["pools"] as List<Map<String, Any>>
        assertEquals(1, pools.size)

        val pool = pools[0]
        assertEquals(1.0, pool["rolls"])
        assertEquals(0.0, pool["bonus_rolls"])

        val entries = pool["entries"] as List<Map<String, Any>>
        assertEquals(1, entries.size)

        val entry = entries[0]
        assertEquals("minecraft:alternatives", entry["type"])

        val children = entry["children"] as List<Map<String, Any>>
        assertEquals(2, children.size)

        // Silk Touch alternative
        val silkTouchChild = children[0]
        assertEquals("minecraft:item", silkTouchChild["type"])
        assertEquals("metalmancy:test_material_ore", silkTouchChild["name"])

        // Normal drop with Fortune
        val normalDropChild = children[1]
        assertEquals("minecraft:item", normalDropChild["type"])
        assertEquals("metalmancy:raw_test_material", normalDropChild["name"])
        
        val functions = normalDropChild["functions"] as List<Map<String, Any>>
        assertEquals(2, functions.size)
        assertEquals("minecraft:apply_bonus", functions[0]["function"])
        assertEquals("minecraft:fortune", functions[0]["enchantment"])
        assertEquals("minecraft:explosion_decay", functions[1]["function"])
    }

    @Test
    fun `createSimpleBlockLootTable should generate correct structure without Fortune`() {
        val loot = GeneratedLoot("zinc", Part.BLOCK, listOf(Part.BLOCK), isOre = false)
        val table = createSimpleBlockLootTable(loot, Part.BLOCK)

        assertEquals("minecraft:block", table["type"])
        assertEquals("metalmancy:blocks/zinc_block", table["random_sequence"])

        val pools = table["pools"] as List<Map<String, Any>>
        assertEquals(1, pools.size)

        val pool = pools[0]
        assertEquals(1.0, pool["rolls"])
        assertEquals(0.0, pool["bonus_rolls"])

        // Check that there's no Fortune - just a simple item entry
        val entries = pool["entries"] as List<Map<String, Any>>
        assertEquals(1, entries.size)
        
        val entry = entries[0]
        assertEquals("minecraft:item", entry["type"])
        assertEquals("metalmancy:zinc_block", entry["name"])
        assertFalse(entry.containsKey("functions"), "Simple block entry should not have functions")

        // Check explosion decay is at pool level
        val poolFunctions = pool["functions"] as List<Map<String, Any>>
        assertEquals(1, poolFunctions.size)
        assertEquals("minecraft:explosion_decay", poolFunctions[0]["function"])
    }

    @Test
    fun `createSimpleBlockLootTable should handle raw blocks correctly`() {
        val loot = GeneratedLoot("zinc", Part.RAW_BLOCK, listOf(Part.RAW_BLOCK), isOre = false)
        val table = createSimpleBlockLootTable(loot, Part.RAW_BLOCK)

        assertEquals("minecraft:block", table["type"])
        assertEquals("metalmancy:blocks/raw_zinc_block", table["random_sequence"])

        val pools = table["pools"] as List<Map<String, Any>>
        val pool = pools[0]
        val entries = pool["entries"] as List<Map<String, Any>>
        val entry = entries[0]
        
        assertEquals("metalmancy:raw_zinc_block", entry["name"])
    }
}
