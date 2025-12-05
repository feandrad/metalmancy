package io.felipeandrade.metalmancy.registry

import dev.architectury.registry.menu.MenuRegistry
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.menu.CalcinatorMenu
import net.minecraft.core.registries.Registries
import net.minecraft.world.inventory.MenuType

object ModMenus {
    val MENUS: DeferredRegister<MenuType<*>> = DeferredRegister.create(Metalmancy.MOD_ID, Registries.MENU)

    val CALCINATOR_MENU: RegistrySupplier<MenuType<CalcinatorMenu>> = MENUS.register("calcinator") {
        MenuRegistry.ofExtended(::CalcinatorMenu)
    }

    fun register() {
        MENUS.register()
    }
}
