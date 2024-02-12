package io.felipeandrade.metalmancy.items.armor

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import io.felipeandrade.metalmancy.datagen.HAS_TAG_ITEM
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.advancement.AdvancementCriterion
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.RecipeProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.Item
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

class ModArmorItem(
    val unlocalizedName: String,
    material: ArmorMaterial,
    type: Type,
    settings: Settings = FabricItemSettings()
): ArmorItem(material, type, settings) {

    fun offerRecipeFromTag(exporter: RecipeExporter, tag: TagKey<Item>) {
        when (type!!) {
            Type.HELMET -> offerHelmetFromTag(exporter, tag)
            Type.CHESTPLATE -> offerChestplateFromTag(exporter, tag)
            Type.LEGGINGS -> offerLeggingsFromTag(exporter, tag)
            Type.BOOTS -> offerBootsFromTag(exporter, tag)
        }
    }

    private fun offerHelmetFromTag(exporter: RecipeExporter, tag: TagKey<Item>) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, this)
            .pattern("###")
            .pattern("# #")
            .input('#', tag)
            .criterion(HAS_TAG_ITEM, RecipeProvider.conditionsFromTag(tag) as AdvancementCriterion<*>)
            .offerTo(exporter, Identifier(MOD_ID, RecipeProvider.getRecipeName(this)))
    }

    private fun offerChestplateFromTag(exporter: RecipeExporter, tag: TagKey<Item>) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, this)
            .pattern("# #")
            .pattern("###")
            .pattern("###")
            .input('#', tag)
            .criterion(HAS_TAG_ITEM, RecipeProvider.conditionsFromTag(tag) as AdvancementCriterion<*>)
            .offerTo(exporter, Identifier(MOD_ID, RecipeProvider.getRecipeName(this)))
    }

    private fun offerLeggingsFromTag(exporter: RecipeExporter, tag: TagKey<Item>) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, this)
            .pattern("###")
            .pattern("# #")
            .pattern("# #")
            .input('#', tag)
            .criterion(HAS_TAG_ITEM, RecipeProvider.conditionsFromTag(tag) as AdvancementCriterion<*>)
            .offerTo(exporter, Identifier(MOD_ID, RecipeProvider.getRecipeName(this)))
    }

    private fun offerBootsFromTag(exporter: RecipeExporter, tag: TagKey<Item>) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, this)
            .pattern("# #")
            .pattern("# #")
            .input('#', tag)
            .criterion(HAS_TAG_ITEM, RecipeProvider.conditionsFromTag(tag) as AdvancementCriterion<*>)
            .offerTo(exporter, Identifier(MOD_ID, RecipeProvider.getRecipeName(this)))
    }
}