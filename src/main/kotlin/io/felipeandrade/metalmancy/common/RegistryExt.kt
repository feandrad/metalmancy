package io.felipeandrade.metalmancy.common

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

fun registerItem(unlocalizedName: String, item: Item = Item(Item.Settings())): Item =
    Registry.register(Registries.ITEM, Identifier(MOD_ID, unlocalizedName), item)

fun registerBlockItem(unlocalizedName: String, block: Block): BlockItem = Registry.register(
    Registries.ITEM,
    Identifier(MOD_ID, unlocalizedName),
    BlockItem(block, Item.Settings())
)

fun registerBlock(unlocalizedName: String, block: Block): Block {
    registerBlockItem(unlocalizedName, block)
    return Registry.register(Registries.BLOCK, Identifier(MOD_ID, unlocalizedName), block)
}