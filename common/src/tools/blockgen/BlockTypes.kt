package tools.blockgen

import com.google.gson.JsonObject
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID

class DefaultBlock(unlocalizedName: String) : GeneratedBlock(unlocalizedName) {
    override fun generateBlockState(): JsonObject {
        val model = "$MOD_ID:block/${this@DefaultBlock.unlocalizedName}"
        val json = JsonObject()
        val variants = JsonObject()
        val emptyVariant = JsonObject()
        emptyVariant.addProperty("model", model)
        variants.add("", emptyVariant)
        json.add("variants", variants)
        return json
    }

    override fun generateBlockModel(): JsonObject {
        val json = JsonObject()
        json.addProperty("parent", "minecraft:block/cube_all")
        val textures = JsonObject()
        textures.addProperty("all", "$MOD_ID:block/${this@DefaultBlock.unlocalizedName}")
        json.add("textures", textures)
        return json
    }

    override fun generateItemModel(): JsonObject {
        val json = JsonObject()
        json.addProperty("parent", "$MOD_ID:block/${this@DefaultBlock.unlocalizedName}")
        return json
    }

    override fun generateItemRender(): JsonObject {
        val json = JsonObject()
        val modelObject = JsonObject()
        modelObject.addProperty("type", "minecraft:model")
        modelObject.addProperty("model", "$MOD_ID:item/${this@DefaultBlock.unlocalizedName}")
        json.add("model", modelObject)
        return json
    }
}
