package io.felipeandrade.metalmancy.neoforge

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.Metalmancy.init
import io.felipeandrade.metalmancy.blocks.MaterialBlocks
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.registries.RegisterEvent


@Mod(Metalmancy.MOD_ID)
class MetalmancyNeoForge {
    init {
        // Run our common setup.
        init(NeoForgePlatformHelper())
    }
}

@EventBusSubscriber(modid = Metalmancy.MOD_ID)
class ModRegistryEvents {
    @SubscribeEvent
    fun registerContent(event: RegisterEvent) {
        register(event, Registries.BLOCK, MaterialBlocks::registerAll);
    }
}


private fun <T> register(event: RegisterEvent, registerAt: ResourceKey<T?>?, runnable: Runnable) {
    if (event.registryKey === registerAt) runnable.run()
}