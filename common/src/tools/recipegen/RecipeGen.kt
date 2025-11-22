package tools.recipegen

import com.google.gson.GsonBuilder
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import java.io.File
import java.io.FileWriter

fun main(args: Array<String>) {
    var outDir = "build/generated/data"
    var i = 0
    while (i < args.size) {
        when (args[i]) {
            "--out" -> {
                outDir = args.getOrNull(i + 1) ?: outDir
                i++
            }
        }
        i++
    }

    val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    val recipesDir = dir(outDir, MOD_ID, "recipes")

    for (recipe in RecipeEntries.recipes) {
        val recipeJson = recipe.generateRecipe()
        gson.writeJson(recipesDir, "${recipe.unlocalizedName}.json", recipeJson)
    }

    println("[RecipeGen] OK → $outDir")
}

private fun dir(base: String, vararg parts: String): File {
    var f = File(base)
    for (p in parts) f = File(f, p)
    if (!f.exists()) f.mkdirs()
    return f
}

private fun com.google.gson.Gson.writeJson(dir: File, fileName: String, json: com.google.gson.JsonObject) {
    val out = File(dir, fileName)
    out.parentFile?.mkdirs()  // Create parent directories if needed
    FileWriter(out).use { w -> toJson(json, w) }
    println("[RecipeGen] wrote ${out.path}")
}
