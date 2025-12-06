package io.felipeandrade.metalmancy.client

import io.felipeandrade.metalmancy.client.screen.CalcinatorScreen
import io.felipeandrade.metalmancy.registry.ModMenus
import io.felipeandrade.metalmancy.util.ReflectionUtils

object ClientSetup {
    fun init() {
        ReflectionUtils.registerScreenFactory(ModMenus.CALCINATOR_MENU.get(), ::CalcinatorScreen)
    }
}
