package io.felipeandrade.metalmancy.tools

import com.google.gson.JsonObject
import io.felipeandrade.metalmancy.Metalmancy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tools.itemgen.GeneratedBlockItem
import tools.itemgen.GeneratedItem

class GeneratedItemTest {

    @Test
    fun `GeneratedItem should generate correct item model`() {
        val item = GeneratedItem("test_item")
        val expectedJson = JsonObject().apply {
            addProperty("parent", "minecraft:item/generated")
            add("textures", JsonObject().apply {
                addProperty("layer0", "${Metalmancy.MOD_ID}:item/test_item")
            })
        }
        assertEquals(expectedJson, item.generateItemModel())
    }

    @Test
    fun `GeneratedItem should generate correct item render`() {
        val item = GeneratedItem("test_item")
        val expectedJson = JsonObject().apply {
            add("model", JsonObject().apply {
                addProperty("type", "minecraft:model")
                addProperty("model", "${Metalmancy.MOD_ID}:item/test_item")
            })
        }
        assertEquals(expectedJson, item.generateItemRender())
    }

    @Test
    fun `GeneratedBlockItem should generate correct item model`() {
        val item = GeneratedBlockItem("test_block_item")
        val expectedJson = JsonObject().apply {
            addProperty("parent", "${Metalmancy.MOD_ID}:block/test_block_item")
        }
        assertEquals(expectedJson, item.generateItemModel())
    }
}
