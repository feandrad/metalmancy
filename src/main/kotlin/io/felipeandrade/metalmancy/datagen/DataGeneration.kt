package io.felipeandrade.metalmancy.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack

class DataGeneration : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(dataGenerator: FabricDataGenerator) {
        dataGenerator.createPack().apply {
            addProvider { output, future -> BlockTagProvider(output, future) }
            addProvider { output, future -> ItemTagProvider(output, future) }
            addProvider(Pack.Factory { BlockLootTableGenerator(it) })
            addProvider(Pack.Factory { RecipeProvider(it) })
            addProvider(Pack.Factory { ModelProvider(it) })
        }
    }
}

