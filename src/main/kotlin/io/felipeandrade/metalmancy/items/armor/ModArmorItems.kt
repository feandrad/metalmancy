package io.felipeandrade.metalmancy.items.armor

import io.felipeandrade.metalmancy.ModItemTags
import io.felipeandrade.metalmancy.common.registerItem
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.registry.tag.ItemTags

class ModArmorItems {
    companion object{
        // Vanilla
        val WOOLEN_HELMET = registerItem("woolen_helmet", ModArmorItem())
        val WOOLEN_CHESTPLATE = registerItem("woolen_chestplate")
        val WOOLEN_LEGGINGS = registerItem("woolen_leggings")
        val WOOLEN_BOOTS = registerItem("woolen_boots")

        val WOODEN_HELMET = registerItem("wooden_helmet")
        val WOODEN_CHESTPLATE = registerItem("wooden_chestplate")
        val WOODEN_LEGGINGS = registerItem("wooden_leggings")
        val WOODEN_BOOTS = registerItem("wooden_boots")

        val COPPER_HELMET = registerItem("cooper_helmet")
        val COPPER_CHESTPLATE = registerItem("cooper_chestplate")
        val COPPER_LEGGINGS = registerItem("cooper_leggings")
        val COPPER_BOOTS = registerItem("cooper_boots")


        // Metals
        val SILVER_HELMET = registerItem("silver_helmet")
        val SILVER_CHESTPLATE = registerItem("silver_chestplate")
        val SILVER_LEGGINGS = registerItem("silver_leggings")
        val SILVER_BOOTS = registerItem("silver_boots")

        val PLATINUM_HELMET = registerItem("platinum_helmet")
        val PLATINUM_CHESTPLATE = registerItem("platinum_chestplate")
        val PLATINUM_LEGGINGS = registerItem("platinum_leggings")
        val PLATINUM_BOOTS = registerItem("platinum_boots")

        val TITANIUM_HELMET = registerItem("titanium_helmet")
        val TITANIUM_CHESTPLATE = registerItem("titanium_chestplate")
        val TITANIUM_LEGGINGS = registerItem("titanium_leggings")
        val TITANIUM_BOOTS = registerItem("titanium_boots")

        val COBALT_HELMET = registerItem("cobalt_helmet")
        val COBALT_CHESTPLATE = registerItem("cobalt_chestplate")
        val COBALT_LEGGINGS = registerItem("cobalt_leggings")
        val COBALT_BOOTS = registerItem("cobalt_boots")

        val MITHRIL_HELMET = registerItem("mithril_helmet")
        val MITHRIL_CHESTPLATE = registerItem("mithril_chestplate")
        val MITHRIL_LEGGINGS = registerItem("mithril_leggings")
        val MITHRIL_BOOTS = registerItem("mithril_boots")

        val ORICHALCUM_HELMET = registerItem("orichalcum_helmet")
        val ORICHALCUM_CHESTPLATE = registerItem("orichalcum_chestplate")
        val ORICHALCUM_LEGGINGS = registerItem("orichalcum_leggings")
        val ORICHALCUM_BOOTS = registerItem("orichalcum_boots")

        // Alloys
        val BRONZE_HELMET = registerItem("bronze_helmet")
        val BRONZE_CHESTPLATE = registerItem("bronze_chestplate")
        val BRONZE_LEGGINGS = registerItem("bronze_leggings")
        val BRONZE_BOOTS = registerItem("bronze_boots")

        val BRASS_HELMET = registerItem("brass_helmet")
        val BRASS_CHESTPLATE = registerItem("brass_chestplate")
        val BRASS_LEGGINGS = registerItem("brass_leggings")
        val BRASS_BOOTS = registerItem("brass_boots")

        val STEEL_HELMET = registerItem("steel_helmet")
        val STEEL_CHESTPLATE = registerItem("steel_chestplate")
        val STEEL_LEGGINGS = registerItem("steel_leggings")
        val STEEL_BOOTS = registerItem("steel_boots")

        fun initialize() {}
        
        fun offerArmorRecipes(exporter: RecipeExporter) {

            // Vanilla Armors
            offerArmors(
                exporter = exporter,
                tag = ItemTags.WOOL,
                helmet = WOOLEN_HELMET,
                chestplate = WOOLEN_CHESTPLATE,
                leggings = WOOLEN_LEGGINGS,
                boots = WOOLEN_BOOTS,
            )
            offerArmors(
                exporter = exporter,
                tag = ItemTags.LOGS,
                helmet = WOODEN_HELMET,
                chestplate = WOODEN_CHESTPLATE,
                leggings = WOODEN_LEGGINGS,
                boots = WOODEN_BOOTS,
            )
            offerArmors(
                exporter = exporter,
                tag = ModItemTags.COPPER_INGOTS,
                helmet = COPPER_HELMET,
                chestplate = COPPER_CHESTPLATE,
                leggings = COPPER_LEGGINGS,
                boots = COPPER_BOOTS,
            )

            // Metal Armors
            offerArmors(
                exporter = exporter,
                tag = ModItemTags.SILVER_INGOTS,
                helmet = SILVER_HELMET,
                chestplate = SILVER_CHESTPLATE,
                leggings = SILVER_LEGGINGS,
                boots = SILVER_BOOTS,
            )
            offerArmors(
                exporter = exporter,
                tag = ModItemTags.PLATINUM_INGOTS,
                helmet = PLATINUM_HELMET,
                chestplate = PLATINUM_CHESTPLATE,
                leggings = PLATINUM_LEGGINGS,
                boots = PLATINUM_BOOTS,
            )
            offerArmors(
                exporter = exporter,
                tag = ModItemTags.TITANIUM_INGOTS,
                helmet = TITANIUM_HELMET,
                chestplate = TITANIUM_CHESTPLATE,
                leggings = TITANIUM_LEGGINGS,
                boots = TITANIUM_BOOTS,
            )
            offerArmors(
                exporter = exporter,
                tag = ModItemTags.COBALT_INGOTS,
                helmet = COBALT_HELMET,
                chestplate = COBALT_CHESTPLATE,
                leggings = COBALT_LEGGINGS,
                boots = COBALT_BOOTS,
            )
            offerArmors(
                exporter = exporter,
                tag = ModItemTags.MITHRIL_INGOTS,
                helmet = MITHRIL_HELMET,
                chestplate = MITHRIL_CHESTPLATE,
                leggings = MITHRIL_LEGGINGS,
                boots = MITHRIL_BOOTS,
            )
            offerArmors(
                exporter = exporter,
                tag = ModItemTags.ORICHALCUM_INGOTS,
                helmet = ORICHALCUM_HELMET,
                chestplate = ORICHALCUM_CHESTPLATE,
                leggings = ORICHALCUM_LEGGINGS,
                boots = ORICHALCUM_BOOTS,
            )

            // Alloy Armors
            offerArmors(
                exporter = exporter,
                tag = ModItemTags.BRONZE_INGOTS,
                helmet = BRONZE_HELMET,
                chestplate = BRONZE_CHESTPLATE,
                leggings = BRONZE_LEGGINGS,
                boots = BRONZE_BOOTS,
            )
            offerArmors(
                exporter = exporter,
                tag = ModItemTags.BRASS_INGOTS,
                helmet = BRASS_HELMET,
                chestplate = BRASS_CHESTPLATE,
                leggings = BRASS_LEGGINGS,
                boots = BRASS_BOOTS,
            )
            offerRecipeFromTag(
                exporter = exporter,
                tag = ModItemTags.STEEL_INGOTS,
                helmet = STEEL_HELMET,
                chestplate = STEEL_CHESTPLATE,
                leggings = STEEL_LEGGINGS,
                boots = STEEL_BOOTS,
            )
        }
    }
}
