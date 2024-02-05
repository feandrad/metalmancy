package io.felipeandrade.metalmancy

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object ItemGroup {
    val MATERIALS: ItemGroup = Registry.register(
        Registries.ITEM_GROUP,
        Identifier(MOD_ID, "materials"),
        FabricItemGroup.builder()
            .icon { ItemStack(Items.IRON_INGOT) }
            .displayName(Text.translatable("itemGroup.metalmancy.materials"))
            .entries { _, entries ->

            }
            .build()
    )

    val TOOLS: ItemGroup = Registry.register(
        Registries.ITEM_GROUP,
        Identifier(MOD_ID, "tools"),
        FabricItemGroup.builder()
            .icon { ItemStack(Items.DIAMOND_PICKAXE) }
            .displayName(Text.translatable("itemGroup.metalmancy.tools"))
            .entries { _, entries ->

            }
            .build()
    )

    fun registerItemGroups() {}
}