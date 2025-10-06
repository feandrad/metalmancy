package io.felipeandrade.metalmancy.tools

import io.felipeandrade.metalmancy.registry.material.Part
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tools.lootgen.GeneratedLoot
import tools.lootgen.createBlockLootTable
import tools.lootgen.getDropName

class LootGenTest {

    @Test
    fun `getDropName should return correct names`() {
        assertEquals("metalmancy:raw_test_ore", getDropName(GeneratedLoot("test_ore", Part.RAW_ITEM)))
        assertEquals("metalmancy:test_ore_dust", getDropName(GeneratedLoot("test_ore", Part.DUST)))
        assertEquals("metalmancy:test_ore", getDropName(GeneratedLoot("test_ore", Part.GEM)))
    }

    @Test
    fun `createBlockLootTable should generate correct structure`() {
        val loot = GeneratedLoot("test_ore", Part.RAW_ITEM)
        val table = createBlockLootTable(loot)

        assertEquals("minecraft:block", table["type"])
        assertEquals("metalmancy:blocks/test_ore_ore", table["random_sequence"])

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

        val silkTouchChild = children[0]
        assertEquals("minecraft:item", silkTouchChild["type"])
        assertEquals("metalmancy:test_ore_ore", silkTouchChild["name"])

        val normalDropChild = children[1]
        assertEquals("minecraft:item", normalDropChild["type"])
        assertEquals("metalmancy:raw_test_ore", normalDropChild["name"])
    }
}
