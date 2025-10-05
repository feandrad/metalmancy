package tools.blockgen

import com.google.gson.JsonObject

abstract class GeneratedBlock(val unlocalizedName: String) {
    abstract fun generateBlockState(): JsonObject
    abstract fun generateBlockModel(): JsonObject
    abstract fun generateItemModel(): JsonObject
    abstract fun generateItemRender(): JsonObject
}
