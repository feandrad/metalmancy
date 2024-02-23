package io.felipeandrade.metalmancy

import io.felipeandrade.metalmancy.blocks.ModBlocks
import io.felipeandrade.metalmancy.blocks.entity.ModBlockEntities
import io.felipeandrade.metalmancy.fluid.ModFluids
import io.felipeandrade.metalmancy.items.ModItems
import io.felipeandrade.metalmancy.items.armor.ModArmorItems
import io.felipeandrade.metalmancy.items.tools.ModTools
import io.felipeandrade.metalmancy.recipe.ModRecipes
import io.felipeandrade.metalmancy.world.registerWorldGen
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Metalmancy : ModInitializer {
	const val MOD_ID = "metalmancy"
    internal val logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		ModItems.initialize()
		ModTools.initialize()
		ModArmorItems.initialize()
		ModBlocks.initialize()
		ModBlockEntities.initialize()
		ModFluids.initialize()
		ModRecipes.initialize()
		ItemGroup.initialize()
		registerWorldGen()
	}
}