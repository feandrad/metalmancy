package io.felipeandrade.metalmancy.registry

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import io.felipeandrade.metalmancy.Metalmancy
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item

object ModItems {
    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(Metalmancy.MOD_ID, Registries.ITEM)

    val ESSENCE_DUST: RegistrySupplier<Item> = ITEMS.register("essence_dust") {
        Item(Item.Properties())
    }

    val ESSENCE_BOTTLE: RegistrySupplier<Item> = ITEMS.register("essence_bottle") {
        Item(Item.Properties())
    }

    fun register() {
        ITEMS.register()
    }
}
