package io.felipeandrade.metalmancy.items.armor

import io.felipeandrade.metalmancy.common.registerItem
import net.minecraft.item.ArmorItem

class ModArmorItems {
    companion object{
        // Vanilla
        val WOOLEN_HELMET= registerItem("woolen_helmet", ModArmorItem(ModArmorMaterial.WOOL, ArmorItem.Type.HELMET))
        val WOOLEN_CHESTPLATE = registerItem("woolen_chestplate", ModArmorItem(ModArmorMaterial.WOOL, ArmorItem.Type.CHESTPLATE))
        val WOOLEN_LEGGINGS = registerItem("woolen_leggings", ModArmorItem(ModArmorMaterial.WOOL, ArmorItem.Type.LEGGINGS))
        val WOOLEN_BOOTS = registerItem("woolen_boots", ModArmorItem(ModArmorMaterial.WOOL, ArmorItem.Type.BOOTS))

        val WOODEN_HELMET= registerItem("wooden_helmet", ModArmorItem(ModArmorMaterial.WOOD, ArmorItem.Type.HELMET))
        val WOODEN_CHESTPLATE = registerItem("wooden_chestplate", ModArmorItem(ModArmorMaterial.WOOD, ArmorItem.Type.CHESTPLATE))
        val WOODEN_LEGGINGS = registerItem("wooden_leggings", ModArmorItem(ModArmorMaterial.WOOD, ArmorItem.Type.LEGGINGS))
        val WOODEN_BOOTS = registerItem("wooden_boots", ModArmorItem(ModArmorMaterial.WOOD, ArmorItem.Type.BOOTS))

        val COPPER_HELMET= registerItem("copper_helmet", ModArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.HELMET))
        val COPPER_CHESTPLATE = registerItem("copper_chestplate", ModArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.CHESTPLATE))
        val COPPER_LEGGINGS = registerItem("copper_leggings", ModArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.LEGGINGS))
        val COPPER_BOOTS = registerItem("copper_boots", ModArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.BOOTS))

        // Metals
        val SILVER_HELMET= registerItem("silver_helmet", ModArmorItem(ModArmorMaterial.SILVER, ArmorItem.Type.HELMET))
        val SILVER_CHESTPLATE = registerItem("silver_chestplate", ModArmorItem(ModArmorMaterial.SILVER, ArmorItem.Type.CHESTPLATE))
        val SILVER_LEGGINGS = registerItem("silver_leggings", ModArmorItem(ModArmorMaterial.SILVER, ArmorItem.Type.LEGGINGS))
        val SILVER_BOOTS = registerItem("silver_boots", ModArmorItem(ModArmorMaterial.SILVER, ArmorItem.Type.BOOTS))

        val PLATINUM_HELMET= registerItem("platinum_helmet", ModArmorItem(ModArmorMaterial.PLATINUM, ArmorItem.Type.HELMET))
        val PLATINUM_CHESTPLATE = registerItem("platinum_chestplate", ModArmorItem(ModArmorMaterial.PLATINUM, ArmorItem.Type.CHESTPLATE))
        val PLATINUM_LEGGINGS = registerItem("platinum_leggings", ModArmorItem(ModArmorMaterial.PLATINUM, ArmorItem.Type.LEGGINGS))
        val PLATINUM_BOOTS = registerItem("platinum_boots", ModArmorItem(ModArmorMaterial.PLATINUM, ArmorItem.Type.BOOTS))

        val TITANIUM_HELMET= registerItem("titanium_helmet", ModArmorItem(ModArmorMaterial.TITANIUM, ArmorItem.Type.HELMET))
        val TITANIUM_CHESTPLATE = registerItem("titanium_chestplate", ModArmorItem(ModArmorMaterial.TITANIUM, ArmorItem.Type.CHESTPLATE))
        val TITANIUM_LEGGINGS = registerItem("titanium_leggings", ModArmorItem(ModArmorMaterial.TITANIUM, ArmorItem.Type.LEGGINGS))
        val TITANIUM_BOOTS = registerItem("titanium_boots", ModArmorItem(ModArmorMaterial.TITANIUM, ArmorItem.Type.BOOTS))

        val COBALT_HELMET= registerItem("cobalt_helmet", ModArmorItem(ModArmorMaterial.COBALT, ArmorItem.Type.HELMET))
        val COBALT_CHESTPLATE = registerItem("cobalt_chestplate", ModArmorItem(ModArmorMaterial.COBALT, ArmorItem.Type.CHESTPLATE))
        val COBALT_LEGGINGS = registerItem("cobalt_leggings", ModArmorItem(ModArmorMaterial.COBALT, ArmorItem.Type.LEGGINGS))
        val COBALT_BOOTS = registerItem("cobalt_boots", ModArmorItem(ModArmorMaterial.COBALT, ArmorItem.Type.BOOTS))

        val MITHRIL_HELMET= registerItem("mithril_helmet", ModArmorItem(ModArmorMaterial.MITHRIL, ArmorItem.Type.HELMET))
        val MITHRIL_CHESTPLATE = registerItem("mithril_chestplate", ModArmorItem(ModArmorMaterial.MITHRIL, ArmorItem.Type.CHESTPLATE))
        val MITHRIL_LEGGINGS = registerItem("mithril_leggings", ModArmorItem(ModArmorMaterial.MITHRIL, ArmorItem.Type.LEGGINGS))
        val MITHRIL_BOOTS = registerItem("mithril_boots", ModArmorItem(ModArmorMaterial.MITHRIL, ArmorItem.Type.BOOTS))

        val ORICHALCUM_HELMET= registerItem("orichalcum_helmet", ModArmorItem(ModArmorMaterial.ORICHALCUM, ArmorItem.Type.HELMET))
        val ORICHALCUM_CHESTPLATE = registerItem("orichalcum_chestplate", ModArmorItem(ModArmorMaterial.ORICHALCUM, ArmorItem.Type.CHESTPLATE))
        val ORICHALCUM_LEGGINGS = registerItem("orichalcum_leggings", ModArmorItem(ModArmorMaterial.ORICHALCUM, ArmorItem.Type.LEGGINGS))
        val ORICHALCUM_BOOTS = registerItem("orichalcum_boots", ModArmorItem(ModArmorMaterial.ORICHALCUM, ArmorItem.Type.BOOTS))

        // Alloys
        val BRONZE_HELMET= registerItem("bronze_helmet", ModArmorItem(ModArmorMaterial.BRONZE, ArmorItem.Type.HELMET))
        val BRONZE_CHESTPLATE = registerItem("bronze_chestplate", ModArmorItem(ModArmorMaterial.BRONZE, ArmorItem.Type.CHESTPLATE))
        val BRONZE_LEGGINGS = registerItem("bronze_leggings", ModArmorItem(ModArmorMaterial.BRONZE, ArmorItem.Type.LEGGINGS))
        val BRONZE_BOOTS = registerItem("bronze_boots", ModArmorItem(ModArmorMaterial.BRONZE, ArmorItem.Type.BOOTS))

        val BRASS_HELMET= registerItem("brass_helmet", ModArmorItem(ModArmorMaterial.BRASS, ArmorItem.Type.HELMET))
        val BRASS_CHESTPLATE = registerItem("brass_chestplate", ModArmorItem(ModArmorMaterial.BRASS, ArmorItem.Type.CHESTPLATE))
        val BRASS_LEGGINGS = registerItem("brass_leggings", ModArmorItem(ModArmorMaterial.BRASS, ArmorItem.Type.LEGGINGS))
        val BRASS_BOOTS = registerItem("brass_boots", ModArmorItem(ModArmorMaterial.BRASS, ArmorItem.Type.BOOTS))

        val STEEL_HELMET= registerItem("steel_helmet", ModArmorItem(ModArmorMaterial.STEEL, ArmorItem.Type.HELMET))
        val STEEL_CHESTPLATE = registerItem("steel_chestplate", ModArmorItem(ModArmorMaterial.STEEL, ArmorItem.Type.CHESTPLATE))
        val STEEL_LEGGINGS = registerItem("steel_leggings", ModArmorItem(ModArmorMaterial.STEEL, ArmorItem.Type.LEGGINGS))
        val STEEL_BOOTS = registerItem("steel_boots", ModArmorItem(ModArmorMaterial.STEEL, ArmorItem.Type.BOOTS))

        fun initialize() {}
    }
}
