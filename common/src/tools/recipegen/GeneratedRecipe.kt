package tools.recipegen

import com.google.gson.JsonObject

abstract class GeneratedRecipe(val unlocalizedName: String) {
    abstract fun generateRecipe(): JsonObject
}
