package io.felipeandrade.metalmancy.items

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.Metalmancy.resourceKey
import io.felipeandrade.metalmancy.registry.material.Materials
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items.registerItem
import net.minecraft.world.item.ToolMaterial

object ToolItems {

    val BRASS = registerAllTools(ToolStats(Materials.BRASS, ToolMaterial.IRON, 190, 6.5F, 1.8F, 18))
    val BRONZE = registerAllTools(ToolStats(Materials.BRONZE, ToolMaterial.IRON, 200, 6.0F, 2.2F, 14))
    val ALUMINUM = registerAllTools(ToolStats(Materials.ALUMINUM, ToolMaterial.IRON, 180, 8.5F, 1.0F, 20))
    val STEEL = registerAllTools(ToolStats(Materials.STEEL, ToolMaterial.IRON, 500, 7.5F, 3.0F, 16))
    val INVAR = registerAllTools(ToolStats(Materials.INVAR, ToolMaterial.IRON, 750, 7.0F, 3.5F, 12))

    val SILVER = registerAllTools(ToolStats(Materials.SILVER, ToolMaterial.GOLD, 40, 10.5F, 0.5F, 22))
    val ELECTRUM = registerAllTools(ToolStats(Materials.ELECTRUM, ToolMaterial.GOLD, 100, 12.0F, 0.8F, 30))
    val COBALT = registerAllTools(ToolStats(Materials.COBALT, ToolMaterial.DIAMOND, 1800, 8.5F, 4.0F, 25))

    val PLATINUM = registerAllTools(ToolStats(Materials.PLATINUM, ToolMaterial.DIAMOND, 1300, 7.0F, 4.0F, 18))
    val TITANIUM = registerAllTools(ToolStats(Materials.TITANIUM, ToolMaterial.DIAMOND, 1600, 9.5F, 2.8F, 10))
    val TOPAZ = registerAllTools(ToolStats(Materials.TOPAZ, ToolMaterial.DIAMOND, 1600, 8.5F, 3.0F, 14))
    val RUBY = registerAllTools(ToolStats(Materials.RUBY, ToolMaterial.DIAMOND, 1600, 7.5F, 4.5F, 14))
    val SAPPHIRE = registerAllTools(ToolStats(Materials.SAPPHIRE, ToolMaterial.DIAMOND, 2000, 8.0F, 3.0F, 14))

    val MITHRIL = registerAllTools(ToolStats(Materials.MITHRIL, ToolMaterial.NETHERITE, 2800, 11.0F, 4.0F, 25))
    val ORICHALCUM = registerAllTools(ToolStats(Materials.ORICHALCUM, ToolMaterial.NETHERITE, 3000, 8.5F, 6.0F, 18))


    fun registerAllTools(stats: ToolStats): Map<ToolType, Item> {
        val result: MutableMap<ToolType, Item> = mutableMapOf()

        ToolType.entries.forEach { type ->
            result[type] = registerTool(type, stats)
        }

        return result
    }

    fun registerTool(type: ToolType, stats: ToolStats): Item {
        val unlocalizedName = Metalmancy.unlocalizedName(stats.material.name, type.unlocalizedSuffix)
        val key = resourceKey(unlocalizedName, Registries.ITEM)
        val properties: Item.Properties = Item.Properties().setId(key)

        return register( unlocalizedName, type.toolProperties(properties, stats))
    }

    fun registerAll() = Unit

    private fun register(
        path: String,
        properties: Item.Properties,
        factory: (Item.Properties) -> Item = { prop -> Item(prop) },
    ): Item {
        val key = resourceKey(path, Registries.ITEM)
        return registerItem(key, factory, properties)
    }
}
