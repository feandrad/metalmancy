package io.felipeandrade.metalmancy.client

import dev.architectury.registry.menu.MenuRegistry
import io.felipeandrade.metalmancy.client.screen.CalcinatorScreen
import io.felipeandrade.metalmancy.registry.ModMenus

object ClientSetup {
    fun init() {
        MenuRegistry.registerScreenFactory(ModMenus.CALCINATOR_MENU.get(), ::CalcinatorScreen)
    }
}
