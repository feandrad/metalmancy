package io.felipeandrade.metalmancy.tools

import com.google.gson.JsonObject
import io.felipeandrade.metalmancy.Metalmancy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tools.blockgen.DefaultBlock

class DefaultBlockTest {

    @Test
    fun `generateBlockState should return correct JSON`() {
        val block = DefaultBlock("test_block")
        val expectedJson = JsonObject().apply {
            add("variants", JsonObject().apply {
                add("", JsonObject().apply {
                    addProperty("model", "${Metalmancy.MOD_ID}:block/test_block")
                })
            })
        }
        assertEquals(expectedJson, block.generateBlockState())
    }

    @Test
    fun `generateBlockModel should return correct JSON`() {
        val block = DefaultBlock("test_block")
        val expectedJson = JsonObject().apply {
            addProperty("parent", "minecraft:block/cube_all")
            add("textures", JsonObject().apply {
                addProperty("all", "${Metalmancy.MOD_ID}:block/test_block")
            })
        }
        assertEquals(expectedJson, block.generateBlockModel())
    }

    @Test
    fun `generateItemModel should return correct JSON`() {
        val block = DefaultBlock("test_block")
        val expectedJson = JsonObject().apply {
            addProperty("parent", "${Metalmancy.MOD_ID}:block/test_block")
        }
        assertEquals(expectedJson, block.generateItemModel())
    }
}
