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
        json.addProperty("cookingtime", cookingTime)
        json.addProperty("experience", experience)
        if (group != null) {
            json.addProperty("group", group)
        }
        json.addProperty("ingredient", ingredient)
        val resultJson = JsonObject()
        resultJson.addProperty("id", result)
        json.add("result", resultJson)
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
        json.addProperty("cookingtime", cookingTime)
        json.addProperty("experience", experience)
        if (group != null) {
            json.addProperty("group", group)
        }
        json.addProperty("ingredient", ingredient)
        val resultJson = JsonObject()
        resultJson.addProperty("id", result)
        json.add("result", resultJson)
        return json
    }
}

class ShapedRecipe(
    unlocalizedName: String,
    private val pattern: List<String>,
    private val key: Map<Char, String>,
    private val result: String,
    private val count: Int = 1,
    private val group: String? = null,
    private val category: String = "misc",
) : GeneratedRecipe(unlocalizedName) {
    override fun generateRecipe(): JsonObject {
        val json = JsonObject()
        json.addProperty("type", "minecraft:crafting_shaped")
        json.addProperty("category", category)
        if (group != null) {
            json.addProperty("group", group)
        }
        val keyJson = JsonObject()
        key.forEach { (char, item) ->
            keyJson.addProperty(char.toString(), item)
        }
        json.add("key", keyJson)
        val patternArray = com.google.gson.JsonArray()
        pattern.forEach { patternArray.add(it) }
        json.add("pattern", patternArray)
        val resultJson = JsonObject()
        resultJson.addProperty("count", count)
        resultJson.addProperty("id", result)
        json.add("result", resultJson)
        return json
    }
}

class ShapelessRecipe(
    unlocalizedName: String,
    private val ingredients: List<String>,
    private val result: String,
    private val count: Int = 1,
    private val group: String? = null,
    private val category: String = "misc",
) : GeneratedRecipe(unlocalizedName) {
    override fun generateRecipe(): JsonObject {
        val json = JsonObject()
        json.addProperty("type", "minecraft:crafting_shapeless")
        json.addProperty("category", category)
        if (group != null) {
            json.addProperty("group", group)
        }
        val ingredientsArray = com.google.gson.JsonArray()
        ingredients.forEach { ingredient ->
            ingredientsArray.add(ingredient)
        }
        json.add("ingredients", ingredientsArray)
        val resultJson = JsonObject()
        resultJson.addProperty("count", count)
        resultJson.addProperty("id", result)
        json.add("result", resultJson)
        return json
    }
}
