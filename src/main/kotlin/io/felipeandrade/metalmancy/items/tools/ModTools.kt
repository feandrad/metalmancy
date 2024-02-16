package io.felipeandrade.metalmancy.items.tools

import io.felipeandrade.metalmancy.common.registerItem
import net.minecraft.item.*

class ModTools {
    companion object{
        // Pickaxes
        val COPPER_PICKAXE = registerItem("copper_pickaxe", PickaxeItem(ModToolMaterial.COPPER, 1, -2.8f, Item.Settings()) as Item )

        val SILVER_PICKAXE = registerItem("silver_pickaxe", PickaxeItem(ModToolMaterial.SILVER, 1, -2.8f, Item.Settings()) as Item )
        val PLATINUM_PICKAXE = registerItem("platinum_pickaxe", PickaxeItem(ModToolMaterial.PLATINUM, 1, -2.8f, Item.Settings()) as Item )
        val TITANIUM_PICKAXE = registerItem("titanium_pickaxe", PickaxeItem(ModToolMaterial.TITANIUM, 1, -2.8f, Item.Settings()) as Item )
        val COBALT_PICKAXE = registerItem("cobalt_pickaxe", PickaxeItem(ModToolMaterial.COBALT, 1, -2.8f, Item.Settings()) as Item )
        val MITHRIL_PICKAXE = registerItem("mithril_pickaxe", PickaxeItem(ModToolMaterial.MITHRIL, 1, -2.8f, Item.Settings()) as Item )
        val ORICHALCUM_PICKAXE = registerItem("orichalcum_pickaxe", PickaxeItem(ModToolMaterial.ORICHALCUM, 1, -2.8f, Item.Settings()) as Item )

        val BRONZE_PICKAXE = registerItem("bronze_pickaxe", PickaxeItem(ModToolMaterial.BRONZE, 1, -2.8f, Item.Settings()) as Item )
        val BRASS_PICKAXE = registerItem("brass_pickaxe", PickaxeItem(ModToolMaterial.BRASS, 1, -2.8f, Item.Settings()) as Item )
        val STEEL_PICKAXE = registerItem("steel_pickaxe", PickaxeItem(ModToolMaterial.STEEL, 1, -2.8f, Item.Settings()) as Item )

        // Axes
        val COPPER_AXE = registerItem("copper_axe", AxeItem(ModToolMaterial.COPPER, 7f, -3f, Item.Settings()) as Item )

        val SILVER_AXE = registerItem("silver_axe", AxeItem(ModToolMaterial.SILVER, 7f, -3f, Item.Settings()) as Item )
        val PLATINUM_AXE = registerItem("platinum_axe", AxeItem(ModToolMaterial.PLATINUM, 7f, -3f, Item.Settings()) as Item )
        val TITANIUM_AXE = registerItem("titanium_axe", AxeItem(ModToolMaterial.TITANIUM, 7f, -3f, Item.Settings()) as Item )
        val COBALT_AXE = registerItem("cobalt_axe", AxeItem(ModToolMaterial.COBALT, 7f, -3f, Item.Settings()) as Item )
        val MITHRIL_AXE = registerItem("mithril_axe", AxeItem(ModToolMaterial.MITHRIL, 7f, -3f, Item.Settings()) as Item )
        val ORICHALCUM_AXE = registerItem("orichalcum_axe", AxeItem(ModToolMaterial.ORICHALCUM, 7f, -3f, Item.Settings()) as Item )

        val BRONZE_AXE = registerItem("bronze_axe", AxeItem(ModToolMaterial.BRONZE, 7f, -3f, Item.Settings()) as Item )
        val BRASS_AXE = registerItem("brass_axe", AxeItem(ModToolMaterial.BRASS, 7f, -3f, Item.Settings()) as Item )
        val STEEL_AXE = registerItem("steel_axe", AxeItem(ModToolMaterial.STEEL, 7f, -3f, Item.Settings()) as Item )

        // Shovels
        val COPPER_SHOVEL = registerItem("copper_shovel", ShovelItem(ModToolMaterial.COPPER, 1.5f, -3f, Item.Settings()) as Item )

        val SILVER_SHOVEL = registerItem("silver_shovel", ShovelItem(ModToolMaterial.SILVER, 1.5f, -3f, Item.Settings()) as Item )
        val PLATINUM_SHOVEL = registerItem("platinum_shovel", ShovelItem(ModToolMaterial.PLATINUM, 1.5f, -3f, Item.Settings()) as Item )
        val TITANIUM_SHOVEL = registerItem("titanium_shovel", ShovelItem(ModToolMaterial.TITANIUM, 1.5f, -3f, Item.Settings()) as Item )
        val COBALT_SHOVEL = registerItem("cobalt_shovel", ShovelItem(ModToolMaterial.COBALT, 1.5f, -3f, Item.Settings()) as Item )
        val MITHRIL_SHOVEL = registerItem("mithril_shovel", ShovelItem(ModToolMaterial.MITHRIL, 1.5f, -3f, Item.Settings()) as Item )
        val ORICHALCUM_SHOVEL = registerItem("orichalcum_shovel", ShovelItem(ModToolMaterial.ORICHALCUM, 1.5f, -3f, Item.Settings()) as Item )

        val BRONZE_SHOVEL = registerItem("bronze_shovel", ShovelItem(ModToolMaterial.BRONZE, 1.5f, -3f, Item.Settings()) as Item )
        val BRASS_SHOVEL = registerItem("brass_shovel", ShovelItem(ModToolMaterial.BRASS, 1.5f, -3f, Item.Settings()) as Item )
        val STEEL_SHOVEL = registerItem("steel_shovel", ShovelItem(ModToolMaterial.STEEL, 1.5f, -3f, Item.Settings()) as Item )

        // Hoes
        val COPPER_HOE = registerItem("copper_hoe", HoeItem(ModToolMaterial.COPPER, 0, -2f, Item.Settings()) as Item )

        val SILVER_HOE = registerItem("silver_hoe", HoeItem(ModToolMaterial.SILVER, 0, -1f, Item.Settings()) as Item )
        val PLATINUM_HOE = registerItem("platinum_hoe", HoeItem(ModToolMaterial.PLATINUM, 0, 0f, Item.Settings()) as Item )
        val TITANIUM_HOE = registerItem("titanium_hoe", HoeItem(ModToolMaterial.TITANIUM, 0, 0f, Item.Settings()) as Item )
        val COBALT_HOE = registerItem("cobalt_hoe", HoeItem(ModToolMaterial.COBALT, 0, 0f, Item.Settings()) as Item )
        val MITHRIL_HOE = registerItem("mithril_hoe", HoeItem(ModToolMaterial.MITHRIL, 0, 0f, Item.Settings()) as Item )
        val ORICHALCUM_HOE = registerItem("orichalcum_hoe", HoeItem(ModToolMaterial.ORICHALCUM, 0, 0f, Item.Settings()) as Item )

        val BRONZE_HOE = registerItem("bronze_hoe", HoeItem(ModToolMaterial.BRONZE, 0, -1f, Item.Settings()) as Item )
        val BRASS_HOE = registerItem("brass_hoe", HoeItem(ModToolMaterial.BRASS, 0, -1f, Item.Settings()) as Item )
        val STEEL_HOE = registerItem("steel_hoe", HoeItem(ModToolMaterial.STEEL, 0, 0f, Item.Settings()) as Item )

        // Swords
        val COPPER_SWORD = registerItem("copper_sword", SwordItem(ModToolMaterial.COPPER, 1, -2.8f, Item.Settings()) as Item )

        val SILVER_SWORD = registerItem("silver_sword", SwordItem(ModToolMaterial.SILVER, 1, -2.8f, Item.Settings()) as Item )
        val PLATINUM_SWORD = registerItem("platinum_sword", SwordItem(ModToolMaterial.PLATINUM, 1, -2.8f, Item.Settings()) as Item )
        val TITANIUM_SWORD = registerItem("titanium_sword", SwordItem(ModToolMaterial.TITANIUM, 1, -2.8f, Item.Settings()) as Item )
        val COBALT_SWORD = registerItem("cobalt_sword", SwordItem(ModToolMaterial.COBALT, 1, -2.8f, Item.Settings()) as Item )
        val MITHRIL_SWORD = registerItem("mithril_sword", SwordItem(ModToolMaterial.MITHRIL, 1, -2.8f, Item.Settings()) as Item )
        val ORICHALCUM_SWORD = registerItem("orichalcum_sword", SwordItem(ModToolMaterial.ORICHALCUM, 1, -2.8f, Item.Settings()) as Item )

        val BRONZE_SWORD = registerItem("bronze_sword", SwordItem(ModToolMaterial.BRONZE, 1, -2.8f, Item.Settings()) as Item )
        val BRASS_SWORD = registerItem("brass_sword", SwordItem(ModToolMaterial.BRASS, 1, -2.8f, Item.Settings()) as Item )
        val STEEL_SWORD = registerItem("steel_sword", SwordItem(ModToolMaterial.STEEL, 1, -2.8f, Item.Settings()) as Item )

//        val WOODEN_SWORD = Items.register("wooden_sword", SwordItem(ToolMaterials.WOOD, 3, -2.4f, Item.Settings()) as Item)
//        val STONE_SWORD = Items.register("stone_sword", SwordItem(ToolMaterials.STONE, 3, -2.4f, Item.Settings()) as Item)
//        val GOLDEN_SWORD = Items.register("golden_sword", SwordItem(ToolMaterials.GOLD, 3, -2.4f, Item.Settings()) as Item)
//        val IRON_SWORD = Items.register("iron_sword", SwordItem(ToolMaterials.IRON, 3, -2.4f, Item.Settings()) as Item)
//        val DIAMOND_SWORD = Items.register("diamond_sword", SwordItem(ToolMaterials.DIAMOND, 3, -2.4f, Item.Settings()) as Item)
//        val NETHERITE_SWORD = Items.register("netherite_sword", SwordItem(ToolMaterials.NETHERITE, 3, -2.4f, Item.Settings().fireproof()) as Item)
//
//        val WOODEN_SHOVEL = Items.register("wooden_shovel", ShovelItem(ToolMaterials.WOOD, 1.5f, -3.0f, Item.Settings()) as Item)
//        val STONE_SHOVEL = Items.register("stone_shovel", ShovelItem(ToolMaterials.STONE, 1.5f, -3.0f, Item.Settings()) as Item)
//        val GOLDEN_SHOVEL = Items.register("golden_shovel", ShovelItem(ToolMaterials.GOLD, 1.5f, -3.0f, Item.Settings()) as Item)
//        val IRON_SHOVEL = Items.register("iron_shovel", ShovelItem(ToolMaterials.IRON, 1.5f, -3.0f, Item.Settings()) as Item)
//        val DIAMOND_SHOVEL = Items.register("diamond_shovel", ShovelItem(ToolMaterials.DIAMOND, 1.5f, -3.0f, Item.Settings()) as Item)
//        val NETHERITE_SHOVEL = Items.register("netherite_shovel", ShovelItem(ToolMaterials.NETHERITE, 1.5f, -3.0f, Item.Settings().fireproof()) as Item)
//
//        val WOODEN_PICKAXE = Items.register("wooden_pickaxe", PickaxeItem(ToolMaterials.WOOD, 1, -2.8f, Item.Settings()) as Item)
//        val STONE_PICKAXE = Items.register("stone_pickaxe", PickaxeItem(ToolMaterials.STONE, 1, -2.8f, Item.Settings()) as Item)
//        val GOLDEN_PICKAXE = Items.register("golden_pickaxe", PickaxeItem(ToolMaterials.GOLD, 1, -2.8f, Item.Settings()) as Item)
//        val IRON_PICKAXE = Items.register("iron_pickaxe", PickaxeItem(ToolMaterials.IRON, 1, -2.8f, Item.Settings()) as Item)
//        val DIAMOND_PICKAXE = Items.register("diamond_pickaxe", PickaxeItem(ToolMaterials.DIAMOND, 1, -2.8f, Item.Settings()) as Item)
//        val NETHERITE_PICKAXE = Items.register("netherite_pickaxe", PickaxeItem(ToolMaterials.NETHERITE, 1, -2.8f, Item.Settings().fireproof()) as Item)
//
//        val WOODEN_AXE = Items.register("wooden_axe", AxeItem(ToolMaterials.WOOD, 6.0f, -3.2f, Item.Settings()) as Item)
//        val STONE_AXE = Items.register("stone_axe", AxeItem(ToolMaterials.STONE, 7.0f, -3.2f, Item.Settings()) as Item)
//        val GOLDEN_AXE = Items.register("golden_axe", AxeItem(ToolMaterials.GOLD, 6.0f, -3.0f, Item.Settings()) as Item)
//        val IRON_AXE = Items.register("iron_axe", AxeItem(ToolMaterials.IRON, 6.0f, -3.1f, Item.Settings()) as Item)
//        val DIAMOND_AXE = Items.register("diamond_axe", AxeItem(ToolMaterials.DIAMOND, 5.0f, -3.0f, Item.Settings()) as Item)
//        val NETHERITE_AXE = Items.register("netherite_axe", AxeItem(ToolMaterials.NETHERITE, 5.0f, -3.0f, Item.Settings().fireproof()) as Item)
//
//        val WOODEN_HOE = Items.register("wooden_hoe", HoeItem(ToolMaterials.WOOD, 0, -3.0f, Item.Settings()) as Item)
//        val STONE_HOE = Items.register("stone_hoe", HoeItem(ToolMaterials.STONE, -1, -2.0f, Item.Settings()) as Item)
//        val GOLDEN_HOE = Items.register("golden_hoe", HoeItem(ToolMaterials.GOLD, 0, -3.0f, Item.Settings()) as Item)
//        val IRON_HOE = Items.register("iron_hoe", HoeItem(ToolMaterials.IRON, -2, -1.0f, Item.Settings()) as Item)
//        val DIAMOND_HOE = Items.register("diamond_hoe", HoeItem(ToolMaterials.DIAMOND, -3, 0.0f, Item.Settings()) as Item)
//        val NETHERITE_HOE = Items.register("netherite_hoe", HoeItem(ToolMaterials.NETHERITE, -4, 0.0f, Item.Settings().fireproof()) as Item)


        fun initialize() {}
    }
}