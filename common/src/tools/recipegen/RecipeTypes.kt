package tools.recipegen

import com.google.gson.JsonObject

class SmeltingRecipe(
    unlocalizedName: String,
    private val ingredient: String,
    private val result: String,
    private val experience: Double = 0.1,
    private val cookingTime: Int = 200,
    private val group: String? = null,
    private val category: String = "misc",
) : GeneratedRecipe(unlocalizedName) {
    override fun generateRecipe(): JsonObject {
        val json = JsonObject()
        json.addProperty("type", "minecraft:smelting")
        json.addProperty("category", category)
        if (group != null) {
            json.addProperty("group", group)
        }
        val ingredientJson = JsonObject()
        if (ingredient.startsWith("#")) {
            ingredientJson.addProperty("tag", ingredient.substring(1))
        } else {
            ingredientJson.addProperty("item", ingredient)
        }
        json.add("ingredient", ingredientJson)
        val resultJson = JsonObject()
        resultJson.addProperty("id", result)
        json.add("result", resultJson)
        json.addProperty("experience", experience)
        json.addProperty("cookingtime", cookingTime)
        return json
    }
}

class BlastingRecipe(
    unlocalizedName: String,
    private val ingredient: String,
    private val result: String,
    private val experience: Double = 0.1,
    private val cookingTime: Int = 100,
    private val group: String? = null,
    private val category: String = "misc",
) : GeneratedRecipe(unlocalizedName) {
    override fun generateRecipe(): JsonObject {
        val json = JsonObject()
        json.addProperty("type", "minecraft:blasting")
        json.addProperty("category", category)
        if (group != null) {
            json.addProperty("group", group)
        }
        val ingredientJson = JsonObject()
        if (ingredient.startsWith("#")) {
            ingredientJson.addProperty("tag", ingredient.substring(1))
        } else {
            ingredientJson.addProperty("item", ingredient)
        }
        json.add("ingredient", ingredientJson)
        val resultJson = JsonObject()
        resultJson.addProperty("id", result)
        json.add("result", resultJson)
        json.addProperty("experience", experience)
        json.addProperty("cookingtime", cookingTime)
        return json
    }
}
