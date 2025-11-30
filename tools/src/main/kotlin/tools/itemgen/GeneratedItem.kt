package tools.itemgen

import com.google.gson.JsonObject
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID

open class GeneratedItem(val unlocalizedName: String)  {
    open fun generateItemModel(): JsonObject {
        val json = JsonObject()
        json.addProperty("parent", "minecraft:item/generated")
        val textures = JsonObject()
        textures.addProperty("layer0", "$MOD_ID:item/$unlocalizedName")
        json.add("textures", textures)
        return json
    }

    open fun generateItemRender(): JsonObject {
        val json = JsonObject()
        val modelObject = JsonObject()
        modelObject.addProperty("type", "minecraft:model")
        modelObject.addProperty("model", "$MOD_ID:item/$unlocalizedName")
        json.add("model", modelObject)
        return json
    }
}

class GeneratedBlockItem(unlocalizedName: String) : GeneratedItem(unlocalizedName) {
    override fun generateItemModel(): JsonObject {
        val json = JsonObject()
        json.addProperty("parent", "$MOD_ID:block/$unlocalizedName")
        return json
    }
}
