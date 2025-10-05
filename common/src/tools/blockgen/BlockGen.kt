package tools.blockgen

import com.google.gson.GsonBuilder
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
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

    val blockStatesDir = dir(outDir, MOD_ID, "blockstates")
    val blockModelsDir = dir(outDir, MOD_ID, "models", "block")

    for (block in BlockEntries.blocks) {
        // Generate block state
        val blockStateJson = block.generateBlockState()
        gson.writeJson(blockStatesDir, "${block.unlocalizedName}.json", blockStateJson)

        // Generate block model
        val blockModelJson = block.generateBlockModel()
        gson.writeJson(blockModelsDir, "${block.unlocalizedName}.json", blockModelJson)
    }

    println("[BlockGen] OK → $outDir")
}

private fun dir(base: String, vararg parts: String): File {
    var f = File(base)
    for (p in parts) f = File(f, p)
    if (!f.exists()) f.mkdirs()
    return f
}

private fun com.google.gson.Gson.writeJson(dir: File, fileName: String, json: com.google.gson.JsonObject) {
    val out = File(dir, fileName)
    FileWriter(out).use { w -> toJson(json, w) }
    println("[BlockGen] wrote ${out.path}")
}
