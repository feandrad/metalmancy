package io.felipeandrade.metalmancy.fabric.registry

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import java.util.function.Predicate

object WorldGen {
    private val alreadyAdded = mutableSetOf<ResourceLocation>()

    fun register() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(object :
            SimpleSynchronousResourceReloadListener {
            override fun getFabricId(): ResourceLocation =
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "worldgen_auto_biome_loader")

            override fun onResourceManagerReload(resourceManager: ResourceManager) {
                val step = GenerationStep.Decoration.UNDERGROUND_ORES
                val selector = BiomeSelectors.foundInOverworld()

                // Liste recursos dentro do caminho (sem namespace no prefix)
                val prefix = "worldgen/placed_feature"
                val filter = Predicate<ResourceLocation> { rl ->
                    rl.path.endsWith(".json") && rl.namespace == MOD_ID
                }
                val resources = resourceManager.listResources(prefix, filter)

                for ((rl, _) in resources) {
                    // rl: metalmancy:worldgen/placed_feature/ore_tin_small.json
                    val name = rl.path.substringAfter("$prefix/").removeSuffix(".json") // ore_tin_small
                    val key: ResourceKey<PlacedFeature> =
                        ResourceKey.create(
                            Registries.PLACED_FEATURE,
                            ResourceLocation.fromNamespaceAndPath(MOD_ID, name)
                        )

                    // Evitar duplicar em reloads
                    val id = key.location()
                    if (alreadyAdded.add(id)) {
                        BiomeModifications.addFeature(selector, step, key)
                    }
                }
            }
        })
    }
}
