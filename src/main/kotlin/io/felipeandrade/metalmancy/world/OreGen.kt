package io.felipeandrade.metalmancy.world

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.placementmodifier.*

fun registerWorldGen() {
    BiomeModifications.addFeature(
        BiomeSelectors.foundInOverworld(),
        GenerationStep.Feature.UNDERGROUND_ORES,
        RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier(MOD_ID, "zinc_ore_placed")
        )
    )

    BiomeModifications.addFeature(
        BiomeSelectors.foundInOverworld(),
        GenerationStep.Feature.UNDERGROUND_ORES,
        RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier(MOD_ID, "tin_ore_placed")
        )
    )

    BiomeModifications.addFeature(
        BiomeSelectors.foundInOverworld(),
        GenerationStep.Feature.UNDERGROUND_ORES,
        RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier(MOD_ID, "silver_ore_placed")
        )
    )

    BiomeModifications.addFeature(
        BiomeSelectors.foundInOverworld(),
        GenerationStep.Feature.UNDERGROUND_ORES,
        RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier(MOD_ID, "platinum_ore_placed")
        )
    )

    BiomeModifications.addFeature(
        BiomeSelectors.foundInOverworld(),
        GenerationStep.Feature.UNDERGROUND_ORES,
        RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier(MOD_ID, "titanium_ore_placed")
        )
    )

    BiomeModifications.addFeature(
        BiomeSelectors.foundInOverworld(),
        GenerationStep.Feature.UNDERGROUND_ORES,
        RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier(MOD_ID, "cobalt_ore_placed")
        )
    )

    BiomeModifications.addFeature(
        BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE),
        GenerationStep.Feature.UNDERGROUND_ORES,
        RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier(MOD_ID, "ruby_ore_placed")
        )
    )

    BiomeModifications.addFeature(
        BiomeSelectors.tag(ConventionalBiomeTags.ICY),
        GenerationStep.Feature.UNDERGROUND_ORES,
        RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier(MOD_ID, "sapphire_ore_placed")
        )
    )

    BiomeModifications.addFeature(
        BiomeSelectors.tag(ConventionalBiomeTags.DESERT),
        GenerationStep.Feature.UNDERGROUND_ORES,
        RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier(MOD_ID, "topaz_ore_placed")
        )
    )
}

fun modifiers(countModifier: PlacementModifier, heightModifier: PlacementModifier): List<PlacementModifier> {
    return listOf(
        countModifier,
        SquarePlacementModifier.of(),
        heightModifier,
        BiomePlacementModifier.of()
    )
}

fun modifiersWithCount(count: Int, heightModifier: PlacementModifier): List<PlacementModifier> {
    return modifiers(CountPlacementModifier.of(count), heightModifier)
}

fun modifiersWithRarity(chance: Int, heightModifier: PlacementModifier): List<PlacementModifier> {
    return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier)
}
