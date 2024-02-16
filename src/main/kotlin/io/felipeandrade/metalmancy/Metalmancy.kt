package io.felipeandrade.metalmancy

import io.felipeandrade.metalmancy.blocks.ModBlocks
import io.felipeandrade.metalmancy.items.ModItems
import io.felipeandrade.metalmancy.items.armor.ModArmorItems
import io.felipeandrade.metalmancy.items.tools.ModTools
import io.felipeandrade.metalmancy.world.registerWorldGen
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Metalmancy : ModInitializer {
	const val MOD_ID = "metalmancy"
    private val logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		ModItems.initialize()
		ModTools.initialize()
		ModArmorItems.initialize()
		ModBlocks.initialize()
		ItemGroup.initialize()
		registerWorldGen()
	}
}