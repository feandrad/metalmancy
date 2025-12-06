package io.felipeandrade.metalmancy.registry

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.blocks.ModBlocks
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item

object ModItems {

    val ESSENCE_DUST: Item = register("essence_dust", Item(Item.Properties().setId(
        net.minecraft.resources.ResourceKey.create(Registries.ITEM, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(Metalmancy.MOD_ID, "essence_dust"))
    )))

    val ESSENCE_BOTTLE: Item = register("essence_bottle", Item(Item.Properties().setId(
        net.minecraft.resources.ResourceKey.create(Registries.ITEM, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(Metalmancy.MOD_ID, "essence_bottle"))
    )))

    val CALCINATOR: Item = register("calcinator", net.minecraft.world.item.BlockItem(ModBlocks.CALCINATOR, Item.Properties().setId(
        net.minecraft.resources.ResourceKey.create(Registries.ITEM, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(Metalmancy.MOD_ID, "calcinator"))
    )))

    fun registerAll() = Unit

    private fun <T : Item> register(name: String, item: T): T =
        net.minecraft.core.Registry.register(net.minecraft.core.registries.BuiltInRegistries.ITEM, Metalmancy.asResource(name), item)
}
