package io.felipeandrade.metalmancy.items

import net.minecraft.world.item.Item
import tools.toolgen.ToolStats

/**
 * Represents the five tool types that can be crafted from tool-enabled materials.
 * Each tool type has specific properties including attack damage, attack speed,
 * and the number of ingots required for crafting.
 */
enum class ToolType(
    val unlocalizedSuffix: String,
    val ingotCount: Int,
    val toolProperties: (Item.Properties, ToolStats) -> Item.Properties
) {
    SWORD("sword", 2, { prop, stats ->
        prop.sword(stats.tier, stats.damage, stats.speed)
    }),
    AXE("axe", 3, { prop, stats ->
        prop.axe(stats.tier, stats.damage, stats.speed)
    }),
    PICKAXE("pickaxe", 3, { prop, stats ->
        prop.pickaxe(stats.tier, stats.damage, stats.speed)
    }),
    SHOVEL("shovel", 1, { prop, stats ->
        prop.shovel(stats.tier, stats.damage, stats.speed)
    }),
    HOE("hoe", 2, { prop, stats ->
        prop.hoe(stats.tier, stats.damage, stats.speed)
    });

    fun getUnlocalizedName(materialName: String): String {
        return "${materialName}_${unlocalizedSuffix}"
    }
}

// New tools:
// Trowel: Right click to place a random block from your toolbar.
// Mortar Hawk: inprint a palette o blocks with a right click interface.
//  - While a Trowel is in your other hand, it will make sure it will get items from your entire inventory and only uses that palette of blocks
// Mortar Tub: An Inventory for blocks with 4 slots, but much more quantity per stack.
//  - Right click to place in the ground.
//  - Shift+Right click while in the ground to fill your inventory with an even distribution of those blocks.
// ToolBelt: Holds several tools. Right click opens the inventory interface.
//  - When attacking a block, automatically change to a tool that can mine it and uses it's durability/enchantments, etc.

enum class PowerToolType(
    val unlocalizedSuffix: String,
) {
    RotaryTiller("tiller"),     // Till, place blocks/rails in front of your feet as you move
    Trimmer("trimmer"),         // Shear + Hoe (minus tilling)
    JackHammer("jackhammer"),   // Shovel + Pick
    ChainSaw("chainsaw");       // Sword + Axe

    fun getUnlocalizedName(materialName: String): String {
        return "${materialName}_${unlocalizedSuffix}"
    }
}
