package io.felipeandrade.metalmancy.fabric.registry

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.biome.v1.ModificationPhase
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.tags.TagKey
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import java.io.InputStreamReader
import java.util.function.Predicate

object WorldGen {
    private val GSON = Gson()

    fun register() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(object :
            SimpleSynchronousResourceReloadListener {
            override fun getFabricId(): ResourceLocation =
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "worldgen_auto_biome_loader")

            override fun onResourceManagerReload(resourceManager: ResourceManager) {
                val modifierDir = "modifier/biome"
                val modifierFilter = Predicate<ResourceLocation> { it.path.endsWith(".json") && it.namespace == MOD_ID }

                resourceManager.listResources(modifierDir, modifierFilter).forEach { (_, resource) ->
                    val root: JsonObject = resource.open().use { input ->
                        GSON.fromJson(InputStreamReader(input), JsonObject::class.java)
                    }

                    val step = GenerationStep.Decoration.UNDERGROUND_ORES

                    val features = root.getAsJsonArray("features")?.mapNotNull { it.asString } ?: emptyList()
                    if (features.isEmpty()) return@forEach

                    val biomesEl = root.get("biomes") ?: return@forEach
                    val biomeSelectors = parseBiomeSelectors(biomesEl)
                    if (biomeSelectors.isEmpty()) return@forEach

                    val selector = Predicate<BiomeSelectionContext> { context ->
                        biomeSelectors.any { it.test(context) }
                    }

                    for (feat in features) {
                        val featId = ResourceLocation.parse(feat)
                        val pfKey: ResourceKey<PlacedFeature> =
                            ResourceKey.create(Registries.PLACED_FEATURE, featId)

                        val modification = BiomeModifications.create(featId)
                        modification.add(ModificationPhase.ADDITIONS, selector) { context ->
                            context.generationSettings.addFeature(step, pfKey)
                        }
                    }
                }
            }
        })
    }

    private fun parseBiomeSelectors(jsonElement: JsonElement): List<Predicate<BiomeSelectionContext>> {
        val selectors = mutableListOf<Predicate<BiomeSelectionContext>>()
        when {
            jsonElement.isJsonPrimitive -> {
                val token = jsonElement.asString
                selectors.add(createSelector(token))
            }
            jsonElement.isJsonArray -> {
                jsonElement.asJsonArray.forEach {
                    val token = it.asString
                    selectors.add(createSelector(token))
                }
            }
        }
        return selectors
    }

    private fun createSelector(token: String): Predicate<BiomeSelectionContext> {
        return if (token.startsWith("#")) {
            val tagId = ResourceLocation.parse(token.substring(1))
            val tagKey = TagKey.create(Registries.BIOME, tagId)
            BiomeSelectors.tag(tagKey)
        } else {
            val biomeId = ResourceLocation.parse(token)
            val biomeKey = ResourceKey.create(Registries.BIOME, biomeId)
            BiomeSelectors.includeByKey(biomeKey)
        }
    }
}
