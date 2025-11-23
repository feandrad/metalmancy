package io.felipeandrade.metalmancy.items

import io.felipeandrade.metalmancy.Metalmancy.resourceKey
import io.felipeandrade.metalmancy.blocks.MaterialBlocks
import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items.registerItem
import net.minecraft.world.level.block.Block

object MaterialItems {
    val RUBY: Map<Part, Item> = mapItems(Materials.RUBY, MaterialBlocks.GEMS[Materials.RUBY])
    val TOPAZ: Map<Part, Item> = mapItems(Materials.TOPAZ, MaterialBlocks.GEMS[Materials.TOPAZ])
    val SAPPHIRE: Map<Part, Item> = mapItems(Materials.SAPPHIRE, MaterialBlocks.GEMS[Materials.SAPPHIRE])

    val SALT: Map<Part, Item> = mapItems(Materials.SALT,MaterialBlocks.SALTS[Materials.SALT])
    val POTASH: Map<Part, Item> = mapItems(Materials.POTASH,MaterialBlocks.SALTS[Materials.POTASH])
    val MERCURY: Map<Part, Item> = mapItems(Materials.MERCURY, MaterialBlocks.STONES[Materials.MERCURY])

    val ZINC: Map<Part, Item> = mapItems(Materials.ZINC)
    val TIN: Map<Part, Item> = mapItems(Materials.TIN)
    val LEAD: Map<Part, Item> = mapItems(Materials.LEAD)
    val NICKEL: Map<Part, Item> = mapItems(Materials.NICKEL)

    val ALUMINUM: Map<Part, Item> = mapItems(Materials.ALUMINUM)
    val MANGANESE: Map<Part, Item> = mapItems(Materials.MANGANESE)
    val SILVER: Map<Part, Item> = mapItems(Materials.SILVER)
    val COBALT: Map<Part, Item> = mapItems(Materials.COBALT)

    val PLATINUM: Map<Part, Item> = mapItems(Materials.PLATINUM)
    val TITANIUM: Map<Part, Item> = mapItems(Materials.TITANIUM)
    val LITHIUM: Map<Part, Item> = mapItems(Materials.LITHIUM)
    val URANIUM: Map<Part, Item> = mapItems(Materials.URANIUM)

    val MITHRIL: Map<Part, Item> = mapItems(Materials.MITHRIL)
    val ORICHALCUM: Map<Part, Item> = mapItems(Materials.ORICHALCUM)

    val PEWTER: Map<Part, Item> = mapItems(Materials.PEWTER, MaterialBlocks.ALLOYS[Materials.PEWTER])
    val BRASS: Map<Part, Item> = mapItems(Materials.BRASS, MaterialBlocks.ALLOYS[Materials.BRASS])
    val BRONZE: Map<Part, Item> = mapItems(Materials.BRONZE, MaterialBlocks.ALLOYS[Materials.BRONZE])
    val STEEL: Map<Part, Item> = mapItems(Materials.STEEL, MaterialBlocks.ALLOYS[Materials.STEEL])
    val ELECTRUM: Map<Part, Item> = mapItems(Materials.ELECTRUM, MaterialBlocks.ALLOYS[Materials.ELECTRUM])
    val INVAR: Map<Part, Item> = mapItems(Materials.INVAR, MaterialBlocks.ALLOYS[Materials.INVAR])

    // Groupings
    val GEMS = mapOf(
        Materials.RUBY to RUBY,
        Materials.SAPPHIRE to SAPPHIRE,
        Materials.TOPAZ to TOPAZ
    )
    val SALTS = mapOf(
        Materials.SALT to SALT,
        Materials.POTASH to POTASH
    )
    val METALS = mapOf(
        Materials.ZINC to ZINC,
        Materials.TIN to TIN,
        Materials.LEAD to LEAD,
        Materials.NICKEL to NICKEL,
        Materials.ALUMINUM to ALUMINUM,
        Materials.MANGANESE to MANGANESE,
        Materials.SILVER to SILVER,
        Materials.COBALT to COBALT,
        Materials.PLATINUM to PLATINUM,
        Materials.TITANIUM to TITANIUM,
        Materials.LITHIUM to LITHIUM,
        Materials.URANIUM to URANIUM,
        Materials.MITHRIL to MITHRIL,
        Materials.ORICHALCUM to ORICHALCUM
    )
    val ALLOYS = mapOf(
        Materials.PEWTER to PEWTER,
        Materials.BRASS to BRASS,
        Materials.BRONZE to BRONZE,
        Materials.STEEL to STEEL,
        Materials.ELECTRUM to ELECTRUM,
        Materials.INVAR to INVAR
    )

    fun registerAll() = Unit

    private fun mapItems(
        material: Material,
        blocks: Map<Part, Block>? = MaterialBlocks.METALS[material]
    ): Map<Part, Item> {
        val blockItems: Map<Part, Item> = blocks?.entries?.associate { (part, block) ->
            part to register(Material.unlocalizedName(material.name, part), block)
        } ?: emptyMap()
        val items: Map<Part, Item> = material.parts.filter { it.isBlock.not() }.associateWith { part ->
            register(material.unlocalizedName(part))
        }
        return blockItems + items
    }

    private fun register(
        path: String,
        block: Block,
        properties: Item.Properties = Item.Properties()
    ): BlockItem {
        val key = resourceKey(path, Registries.ITEM)
        val props = properties.useBlockDescriptionPrefix().setId(key)
        return Registry.register(BuiltInRegistries.ITEM, key, BlockItem(block, props))
    }

    private fun register(
        path: String,
        factory: (Item.Properties) -> Item = { prop -> Item(prop) },
        properties: Item.Properties = Item.Properties()
    ): Item {
        val key = resourceKey(path, Registries.ITEM)
        return registerItem(key, factory, properties)
    }
}
