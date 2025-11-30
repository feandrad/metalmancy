package io.felipeandrade.metalmancy.fabric

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.blocks.MaterialBlocks
import io.felipeandrade.metalmancy.fabric.platform.FabricPlatformHelper
import io.felipeandrade.metalmancy.fabric.registry.CreativeTabs
import io.felipeandrade.metalmancy.fabric.registry.WorldGen
import io.felipeandrade.metalmancy.items.MaterialItems
import io.felipeandrade.metalmancy.items.ToolItems
import net.fabricmc.api.ModInitializer

class MetalmancyFabric : ModInitializer {
    override fun onInitialize() {
        Metalmancy.init(FabricPlatformHelper())
        MaterialBlocks.registerAll()
        MaterialItems.registerAll()
        ToolItems.registerAll()
        WorldGen.register()
        CreativeTabs.register()
    }
}