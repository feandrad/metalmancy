package tools.itemgen

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.felipeandrade.metalmancy.Metalmancy
import tools.blockgen.BlockEntries
import java.io.File
import java.io.FileWriter

fun main(args: Array<String>) {
    var outDir = "build/generated/assets"
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

    val gson = GsonBuilder().setPrettyPrinting().create()

    val itemModelsDir = dir(outDir, Metalmancy.MOD_ID, "models", "item")
    val itemsDir = dir(outDir, Metalmancy.MOD_ID, "items")

    for (item in ItemEntries.items) {
        // Generate item model
        val itemModelJson = item.generateItemModel()
        gson.writeJson(itemModelsDir, "${item.unlocalizedName}.json", itemModelJson)

        // Generate item render
        val itemRenderJson = item.generateItemRender()
        gson.writeJson(itemsDir, "${item.unlocalizedName}.json", itemRenderJson)
    }

    for (block in BlockEntries.blocks) {
        // Generate item model
        val itemModelJson = block.generateItemModel()
        gson.writeJson(itemModelsDir, "${block.unlocalizedName}.json", itemModelJson)

        // Generate item render
        val itemRenderJson = block.generateItemRender()
        gson.writeJson(itemsDir, "${block.unlocalizedName}.json", itemRenderJson)
    }

    println("[ItemGen] OK → $outDir")
}

private fun dir(base: String, vararg parts: String): File {
    var f = File(base)
    for (p in parts) f = File(f, p)
    if (!f.exists()) f.mkdirs()
    return f
}

private fun Gson.writeJson(dir: File, fileName: String, json: com.google.gson.JsonObject) {
    val out = File(dir, fileName)
    FileWriter(out).use { w -> toJson(json, w) }
    println("[ItemGen] wrote ${out.path}")
}
