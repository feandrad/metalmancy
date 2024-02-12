package io.felipeandrade.metalmancy

import io.felipeandrade.metalmancy.datagen.*
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object MetalmancyDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(dataGenerator: FabricDataGenerator) {
		dataGenerator.createPack().apply {
			addProvider { output, future -> BlockTagProvider(output, future) }
			addProvider { output, future -> ItemTagProvider(output, future) }
			addProvider(FabricDataGenerator.Pack.Factory { BlockLootTableGenerator(it) })
			addProvider(FabricDataGenerator.Pack.Factory { RecipeProvider(it) })
			addProvider(FabricDataGenerator.Pack.Factory { ModelProvider(it) })
		}
	}
}