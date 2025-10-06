package io.felipeandrade.metalmancy.tools

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tools.*

class WorldgenJsonGenTest {

    @Test
    fun `toConfiguredName should return correct names`() {
        val oreGen = OreGen("test_ore")
        assertEquals("test_ore", oreGen.toConfiguredName())

        val oreGenWithSuffix = OreGen("test_ore", suffix = "bonus")
        assertEquals("test_ore_bonus", oreGenWithSuffix.toConfiguredName())
    }

    @Test
    fun `toPlacedName should return correct names`() {
        val oreGen = OreGen("test_ore")
        assertEquals("oregen_test_ore", oreGen.toPlacedName())

        val oreGenWithSuffix = OreGen("test_ore", suffix = "bonus")
        assertEquals("oregen_test_ore_bonus", oreGenWithSuffix.toPlacedName())
    }

    @Test
    fun `nextUnique should return unique names`() {
        val counts = mutableMapOf<String, Int>()
        assertEquals("test", nextUnique("test", counts))
        assertEquals("test_2", nextUnique("test", counts))
        assertEquals("another", nextUnique("another", counts))
        assertEquals("test_3", nextUnique("test", counts))
    }

    @Test
    fun `toConfiguredFeatureJson should generate correct structure`() {
        val oreGen = OreGen("test_ore", "deepslate_test_ore", veinSize = 8)
        val json = oreGen.toConfiguredFeatureJson()

        assertEquals("minecraft:ore", json["type"])
        val config = json["config"] as Map<String, Any>
        assertEquals(8, config["size"])
        assertEquals(0.0, config["discard_chance_on_air_exposure"])

        val targets = config["targets"] as List<Map<String, Any>>
        assertEquals(2, targets.size)

        val target1 = targets[0]["target"] as Map<String, Any>
        assertEquals("minecraft:tag_match", target1["predicate_type"])
        assertEquals("minecraft:stone_ore_replaceables", target1["tag"])
        val state1 = targets[0]["state"] as Map<String, Any>
        assertEquals("metalmancy:test_ore", state1["Name"])

        val target2 = targets[1]["target"] as Map<String, Any>
        assertEquals("minecraft:tag_match", target2["predicate_type"])
        assertEquals("minecraft:deepslate_ore_replaceables", target2["tag"])
        val state2 = targets[1]["state"] as Map<String, Any>
        assertEquals("metalmancy:deepslate_test_ore", state2["Name"])
    }

    @Test
    fun `toPlacedFeature should generate correct structure`() {
        val oreGen = OreGen("test_ore", yRange = -32..32, countPerChunk = 10)
        val json = oreGen.toPlacedFeature("test_config")

        assertEquals("metalmancy:test_config", json["feature"])
        val placement = json["placement"] as List<Map<String, Any>>
        assertEquals(4, placement.size)
        assertEquals(mapOf("type" to "minecraft:count", "count" to 10), placement[0])
        assertEquals(mapOf("type" to "minecraft:in_square"), placement[1])
        assertEquals("minecraft:height_range", (placement[2])["type"])
        assertEquals(mapOf("type" to "minecraft:biome"), placement[3])
    }
}
