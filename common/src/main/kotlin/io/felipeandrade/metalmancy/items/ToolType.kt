package io.felipeandrade.metalmancy.items

import io.felipeandrade.metalmancy.Metalmancy
import net.minecraft.world.item.Item

/**
 * Represents the five tool types that can be crafted from tool-enabled materials.
 * Each tool type has specific properties including attack damage, attack speed,
 * and the number of ingots required for crafting.
 */
enum class ToolType(
    val unlocalizedSuffix: String,
    val ingotCount: Int,
    val attackDamage: Float,
    val attackSpeed: Float,
    val factory: (Item.Properties, ToolStats) -> Item
) {
    SWORD("sword", 2, 3.0f, -2.4f, { prop, stats ->
        Metalmancy.helper.createSword(stats.tier, 3, -2.4f, prop.durability(stats.durability))
    }),
    AXE("axe", 3, 6.0f, -3.1f, { prop, stats ->
        Metalmancy.helper.createAxe(stats.tier, 6.0f, -3.1f, prop.durability(stats.durability))
    }),
    PICKAXE("pickaxe", 3, 1.0f, -2.8f, { prop, stats ->
        Metalmancy.helper.createPickaxe(stats.tier, 1, -2.8f, prop.durability(stats.durability))
    }),
    SHOVEL("shovel", 1, 1.5f, -3.0f, { prop, stats ->
        Metalmancy.helper.createShovel(stats.tier, 1.5f, -3.0f, prop.durability(stats.durability))
    }),
    HOE("hoe", 2, 0.0f, -3.0f, { prop, stats ->
        Metalmancy.helper.createHoe(stats.tier, 0, -3.0f, prop.durability(stats.durability))
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
