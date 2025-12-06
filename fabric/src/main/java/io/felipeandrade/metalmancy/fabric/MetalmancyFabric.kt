package io.felipeandrade.metalmancy.fabric

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.fabric.platform.FabricPlatformHelper
import io.felipeandrade.metalmancy.fabric.registry.CreativeTabs
import io.felipeandrade.metalmancy.fabric.registry.WorldGen
import net.fabricmc.api.ModInitializer

class MetalmancyFabric : ModInitializer {
    override fun onInitialize() {
        Metalmancy.init(FabricPlatformHelper())
        WorldGen.register()
        CreativeTabs.register()
    }
}