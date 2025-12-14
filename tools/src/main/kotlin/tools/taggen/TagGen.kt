package tools.taggen

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import io.felipeandrade.metalmancy.items.ToolType
import tools.toolgen.ToolMaterials
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
    val tagsDir = dir(outDir, "minecraft", "tags", "item")

    val swords = JsonArray()
    val axes = JsonArray()
    val pickaxes = JsonArray()
    val shovels = JsonArray()
    val hoes = JsonArray()

    val validMaterials = ToolMaterials.TOOL_ENABLED.filter { material ->
        ToolMaterials.hasRequiredParts(material) && ToolMaterials.getTierWithValidation(material) != null
    }

    for (material in validMaterials) {
        swords.add("$MOD_ID:${ToolType.SWORD.getUnlocalizedName(material.name)}")
        axes.add("$MOD_ID:${ToolType.AXE.getUnlocalizedName(material.name)}")
        pickaxes.add("$MOD_ID:${ToolType.PICKAXE.getUnlocalizedName(material.name)}")
        shovels.add("$MOD_ID:${ToolType.SHOVEL.getUnlocalizedName(material.name)}")
        hoes.add("$MOD_ID:${ToolType.HOE.getUnlocalizedName(material.name)}")
    }

    writeTag(gson, tagsDir, "swords", swords)
    writeTag(gson, tagsDir, "axes", axes)
    writeTag(gson, tagsDir, "pickaxes", pickaxes)
    writeTag(gson, tagsDir, "shovels", shovels)
    writeTag(gson, tagsDir, "hoes", hoes)
    
    println("[TagGen] Complete.")
}

private fun writeTag(gson: com.google.gson.Gson, dir: File, name: String, values: JsonArray) {
    val json = JsonObject()
    json.addProperty("replace", false)
    json.add("values", values)
    
    val out = File(dir, "$name.json")
    FileWriter(out).use { w -> gson.toJson(json, w) }
    println("[TagGen] wrote ${out.path}")
}

private fun dir(base: String, vararg parts: String): File {
    var f = File(base)
    for (p in parts) f = File(f, p)
    if (!f.exists()) f.mkdirs()
    return f
}
