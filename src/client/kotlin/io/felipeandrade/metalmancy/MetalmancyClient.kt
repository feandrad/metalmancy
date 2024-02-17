package io.felipeandrade.metalmancy

import io.felipeandrade.metalmancy.screen.CalcinatorScreen
import io.felipeandrade.metalmancy.screen.ModScreenHandlers
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.gui.screen.ingame.HandledScreens

object MetalmancyClient : ClientModInitializer {
    override fun onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.CALCINATOR_SCREEN_HANDLER) { handler, inventory, title ->
            CalcinatorScreen(handler, inventory, title)
        }
    }
}