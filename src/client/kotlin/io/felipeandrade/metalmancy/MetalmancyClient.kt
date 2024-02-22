package io.felipeandrade.metalmancy

import io.felipeandrade.metalmancy.fluid.ModFluids
import io.felipeandrade.metalmancy.network.FluidSyncS2CPacket
import io.felipeandrade.metalmancy.network.ModMessages
import io.felipeandrade.metalmancy.screen.CalcinatorScreen
import io.felipeandrade.metalmancy.screen.ModScreenHandlers
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.client.render.RenderLayer
import net.minecraft.util.Identifier


object MetalmancyClient : ClientModInitializer {
    override fun onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.CALCINATOR_SCREEN_HANDLER) { handler, inventory, title ->
            CalcinatorScreen(handler, inventory, title)
        }

        FluidRenderHandlerRegistry.INSTANCE.register(
            ModFluids.STILL_ESSENCE, ModFluids.FLOWING_ESSENCE, SimpleFluidRenderHandler(
                Identifier("minecraft:block/water_still"),
                Identifier("minecraft:block/water_flow"),
                0x4CC248
            )
        )

        BlockRenderLayerMap.INSTANCE.putFluids(
            RenderLayer.getTranslucent(),
            ModFluids.STILL_ESSENCE,
            ModFluids.FLOWING_ESSENCE
        )

        // Register client packages
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.FLUID_SYNC, FluidSyncS2CPacket::receive)


    }
}