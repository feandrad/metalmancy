package io.felipeandrade.metalmancy.fabric.registry

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import java.io.InputStreamReader
import java.util.function.Predicate

object WorldGen {
    // (featureId, selectorKey, stepName) to dedupe across reloads
    private val alreadyAdded = mutableSetOf<Triple<ResourceLocation, String, String>>()

    fun register() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(object :
            SimpleSynchronousResourceReloadListener {
            override fun getFabricId(): ResourceLocation =
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "worldgen_auto_biome_loader")

            override fun onResourceManagerReload(resourceManager: ResourceManager) {
                // 1) Parse $MOD_ID biome_modifier JSONs into: feature -> [(selector, step)]
                data class Injection(
                    val selectorKey: String,
                    val selector: Predicate<BiomeSelectionContext>,
                    val step: GenerationStep.Decoration
                )
                val injectionsByFeature = mutableMapOf<ResourceLocation, MutableList<Injection>>()

                val modifierDir = "$MOD_ID/biome_modifier"
                val modifierFilter = Predicate<ResourceLocation> { it.path.endsWith(".json") }

                for ((loc, res) in resourceManager.listResources(modifierDir, modifierFilter)) {
                    val root: JsonObject = res.open().use { input ->
                        JsonParser.parseReader(InputStreamReader(input)).asJsonObject
                    }

                    val type = root.getAsJsonPrimitive("type")?.asString ?: continue
                    if (type != "$MOD_ID:add_features" && type != "forge:add_features") continue

                    val stepKey = root.getAsJsonPrimitive("step")?.asString ?: "underground_ores"
                    val step = stepFromKey(stepKey) ?: GenerationStep.Decoration.UNDERGROUND_ORES

                    val features = root.getAsJsonArray("features")?.mapNotNull { it.asString } ?: emptyList()
                    if (features.isEmpty()) continue

                    val biomesEl = root.get("biomes") ?: continue
                    val biomeTokens: List<String> = when {
                        biomesEl.isJsonArray -> biomesEl.asJsonArray.mapNotNull { it.asString }
                        biomesEl.isJsonPrimitive -> listOf(biomesEl.asString)
                        else -> emptyList()
                    }
                    if (biomeTokens.isEmpty()) continue

                    val selectors: List<Injection> = biomeTokens.flatMap { token ->
                        if (token.startsWith("#")) {
                            val tagId = ResourceLocation.parse(token.substring(1))
                            findBiomeKeysInTag(resourceManager, tagId).map { biomeKey ->
                                Injection("key:${biomeKey.location()}", BiomeSelectors.includeByKey(biomeKey), step)
                            }
                        } else {
                            val biomeId = ResourceLocation.parse(token)
                            val key = ResourceKey.create(Registries.BIOME, biomeId)
                            listOf(Injection("key:$biomeId", BiomeSelectors.includeByKey(key), step))
                        }
                    }

                    for (feat in features) {
                        val featId = ResourceLocation.parse(feat)
                        if (featId.namespace != MOD_ID) continue // only gate our own features
                        injectionsByFeature.getOrPut(featId) { mutableListOf() }.addAll(selectors)
                    }
                }

                // 2) Discover our placed_features in resources
                val prefix = "worldgen/placed_feature"
                val pfFilter = Predicate<ResourceLocation> { rl ->
                    rl.namespace == MOD_ID && rl.path.startsWith(prefix) && rl.path.endsWith(".json")
                }
                val placed = resourceManager.listResources(prefix, pfFilter)

                // 3) Inject: by modifiers if present; otherwise fallback to Overworld
                for ((rl, _) in placed) {
                    val name = rl.path.substringAfter("$prefix/").removeSuffix(".json")
                    val featureId = ResourceLocation.fromNamespaceAndPath(MOD_ID, name)
                    val pfKey: ResourceKey<PlacedFeature> =
                        ResourceKey.create(Registries.PLACED_FEATURE, featureId)

                    val injections = injectionsByFeature[featureId]
                    println("Feature: $featureId, Injections: ${injections?.map { it.selectorKey }}")

                    if (injections.isNullOrEmpty()) {
                        continue
                    }

                    for (inj in injections) {
                        println("  - Injection: selectorKey=${inj.selectorKey}, step=${inj.step.name}")
                        val dedupe = Triple(featureId, inj.selectorKey, inj.step.name)
                        if (alreadyAdded.add(dedupe)) {
                            BiomeModifications.addFeature(inj.selector, inj.step, pfKey)
                        }
                    }
                }
            }
        })
    }

    private fun findBiomeKeysInTag(resourceManager: ResourceManager, tagId: ResourceLocation): Set<ResourceKey<Biome>> {
        val biomeIds = mutableSetOf<ResourceLocation>()
        resolveTag(resourceManager, tagId, biomeIds, mutableSetOf())
        return biomeIds.map { ResourceKey.create(Registries.BIOME, it) }.toSet()
    }

    private fun resolveTag(
        resourceManager: ResourceManager,
        tagId: ResourceLocation,
        biomeIds: MutableSet<ResourceLocation>,
        visitedTags: MutableSet<ResourceLocation>
    ) {
        if (!visitedTags.add(tagId)) return

        val tagPath = ResourceLocation.fromNamespaceAndPath(tagId.namespace, "tags/worldgen/biome/${tagId.path}.json")

        resourceManager.getResource(tagPath).ifPresent { resource ->
            val root = resource.open().use { JsonParser.parseReader(InputStreamReader(it)).asJsonObject }
            if (!root.has("values")) return@ifPresent

            for (element in root.getAsJsonArray("values")) {
                val entry = element.asString
                if (entry.startsWith("#")) {
                    resolveTag(resourceManager, ResourceLocation.parse(entry.substring(1)), biomeIds, visitedTags)
                } else {
                    biomeIds.add(ResourceLocation.parse(entry))
                }
            }
        }
    }

    private fun stepFromKey(key: String): GenerationStep.Decoration? = when (key.lowercase()) {
        "raw_generation" -> GenerationStep.Decoration.RAW_GENERATION
        "lakes" -> GenerationStep.Decoration.LAKES
        "local_modifications" -> GenerationStep.Decoration.LOCAL_MODIFICATIONS
        "underground_structures" -> GenerationStep.Decoration.UNDERGROUND_STRUCTURES
        "surface_structures" -> GenerationStep.Decoration.SURFACE_STRUCTURES
        "strongholds" -> GenerationStep.Decoration.STRONGHOLDS
        "underground_ores" -> GenerationStep.Decoration.UNDERGROUND_ORES
        "underground_decoration" -> GenerationStep.Decoration.UNDERGROUND_DECORATION
        "fluid_springs" -> GenerationStep.Decoration.FLUID_SPRINGS
        "vegetal_decoration" -> GenerationStep.Decoration.VEGETAL_DECORATION
        "top_layer_modification" -> GenerationStep.Decoration.TOP_LAYER_MODIFICATION
        else -> null
    }
}
