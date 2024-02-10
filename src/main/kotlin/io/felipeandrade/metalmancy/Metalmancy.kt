package io.felipeandrade.metalmancy

import io.felipeandrade.metalmancy.blocks.ModBlocks
import io.felipeandrade.metalmancy.items.ModItems
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Metalmancy : ModInitializer {
	const val MOD_ID = "metalmancy"
    private val logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		ModItems.initialize()
		ModBlocks.initialize()
		ItemGroup.initialize()
	}
}