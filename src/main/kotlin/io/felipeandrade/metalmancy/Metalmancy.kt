package io.felipeandrade.metalmancy

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Metalmancy : ModInitializer {
	const val MOD_ID = "metalmancy"
    private val logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		ItemGroup.registerItemGroups()
	}
}