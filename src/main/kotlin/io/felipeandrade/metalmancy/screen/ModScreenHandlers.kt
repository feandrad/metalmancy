package io.felipeandrade.metalmancy.screen

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier


class ModScreenHandlers {
    companion object {
        val CALCINATOR_SCREEN_HANDLER: ScreenHandlerType<CalcinatorScreenHandler> = Registry.register(
            Registries.SCREEN_HANDLER, Identifier(MOD_ID, "calcinator"),
            ExtendedScreenHandlerType { syncId, inventory, buf -> CalcinatorScreenHandler(syncId, inventory, buf) }
        )

        fun initialize() {}
    }
}