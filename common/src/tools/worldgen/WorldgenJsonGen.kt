package tools

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import java.io.File
import java.io.FileWriter

/**
 * Roda com: ./gradlew :common:generateJson
 * (ou rode 'syncGeneratedWorldgen' para copiar para resources)
 */
fun main(args: Array<String>) {
    var outDir = "build/generated"
    var i = 0
    while (i < args.size) {
        when (args[i]) {
            "--out" -> {
                outDir = args.getOrNull(i + 1) ?: outDir; i++
            }
        }
        i++
    }

    val gson = GsonBuilder().setPrettyPrinting().create()

    val configuredDir = dir(outDir, "configured_feature")
    val placedDir = dir(outDir, "placed_feature")

    val configCounts = mutableMapOf<String, Int>()
    val placedCounts = mutableMapOf<String, Int>()

    for (e in OreGenEntries.overworld) {
        // configured_feature
        val configuredName = e.toConfiguredName()
        val configFileName = nextUnique(configuredName, configCounts)
        gson.writeJson(
            configuredDir,
            "$configFileName.json",
            e.toConfiguredFeatureJson()
        )

        // placed_feature
        val placedName = e.toPlacedName()
        val placedFileName = nextUnique(placedName, placedCounts)
        gson.writeJson(
            placedDir,
            "$placedFileName.json",
            e.toPlacedFeature(configFileName)
        )
    }

    println("[generateJson] OK → $outDir")
    println("[generateJson] Dica: rode ':common:syncGeneratedWorldgen' para copiar em resources.")
}

internal fun OreGen.toConfiguredName(): String = if (suffix != null)
    "${ore}_${suffix}" else ore

internal fun OreGen.toPlacedName(): String = if (suffix != null)
    "oregen_${ore}_${suffix}" else "oregen_${ore}"

internal fun OreGen.toPlacedFeature(configFileName: String): LinkedHashMap<String, Any> {
    val placement = mutableListOf<Map<String, Any>>()
    placement += linkedMapOf("type" to "minecraft:count", "count" to countPerChunk)
    placement += linkedMapOf("type" to "minecraft:in_square")
    val height = linkedMapOf<String, Any>(
        "type" to heightType.id,
        "min_inclusive" to linkedMapOf("absolute" to yRange.first),
        "max_inclusive" to linkedMapOf("absolute" to yRange.last)
    )
    placement += linkedMapOf("type" to "minecraft:height_range", "height" to height)
    placement += linkedMapOf("type" to "minecraft:biome")
    val placed = linkedMapOf(
        "feature" to "$MOD_ID:$configFileName",
        "placement" to placement
    )
    return placed
}

internal fun OreGen.toConfiguredFeatureJson(): LinkedHashMap<String, Any> {
    val root = linkedMapOf<String, Any>()
    root["type"] = "minecraft:ore"
    val config = linkedMapOf<String, Any>()
    config["size"] = veinSize
    config["discard_chance_on_air_exposure"] = 0.0
    val jsonTargets = mutableListOf<Map<String, Any>>()
    if (targets != null) {
        for (target in targets) {
            jsonTargets += linkedMapOf(
                "target" to linkedMapOf(
                    "predicate_type" to target.predicateType.id,
                    "block" to target.block
                ),
                "state" to linkedMapOf("Name" to "$MOD_ID:$ore")
            )
        }
    } else {
        jsonTargets += linkedMapOf(
            "target" to linkedMapOf(
                "predicate_type" to "minecraft:tag_match",
                "tag" to "minecraft:stone_ore_replaceables"
            ),
            "state" to linkedMapOf("Name" to "$MOD_ID:$ore")
        )
        if (!deepslate.isNullOrBlank()) {
            jsonTargets += linkedMapOf(
                "target" to linkedMapOf(
                    "predicate_type" to "minecraft:tag_match",
                    "tag" to "minecraft:deepslate_ore_replaceables"
                ),
                "state" to linkedMapOf("Name" to "$MOD_ID:$deepslate")
            )
        }
    }
    config["targets"] = jsonTargets
    root["config"] = config
    return root
}

/* ============================= helpers ============================= */

private fun dir(base: String, vararg parts: String): File {
    var f = File(base)
    for (p in parts) f = File(f, p)
    if (!f.exists()) f.mkdirs()
    return f
}

internal fun nextUnique(base: String, counts: MutableMap<String, Int>): String {
    val n = counts.compute(base) { _, prev -> (prev ?: 0) + 1 }!!
    return if (n == 1) base else "${base}_$n"
}

private fun Gson.writeJson(dir: File, fileName: String, json: Map<String, Any>) {
    val out = File(dir, fileName)
    FileWriter(out).use { w -> toJson(json, w) }
    println("[generateJson] wrote ${out.path}")
}