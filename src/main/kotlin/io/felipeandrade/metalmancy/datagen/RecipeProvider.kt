package io.felipeandrade.metalmancy.datagen

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import io.felipeandrade.metalmancy.ModItemTags
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TIN_BLOCK
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TIN_DEEPSLATE_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TIN_ORE
import io.felipeandrade.metalmancy.blocks.ModBlocks.Companion.TIN_RAW_BLOCK
import io.felipeandrade.metalmancy.items.ModItems.Companion.TIN_CRUSHED
import io.felipeandrade.metalmancy.items.ModItems.Companion.TIN_DUST
import io.felipeandrade.metalmancy.items.ModItems.Companion.TIN_INGOT
import io.felipeandrade.metalmancy.items.ModItems.Companion.TIN_NUGGET
import io.felipeandrade.metalmancy.items.ModItems.Companion.TIN_RAW
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.advancement.AdvancementCriterion
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.RecipeProvider.*
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.tag.ItemTags
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

class GSWRecipeProvider(dataOutput: FabricDataOutput) : FabricRecipeProvider(dataOutput) {

    override fun generate(exporter: RecipeExporter) {

        offerOreMaterial(
            exporter = exporter,
            ingot = TIN_INGOT,
            nugget = TIN_NUGGET,
            block = TIN_BLOCK,
            raw = TIN_RAW,
            rawBlock = TIN_RAW_BLOCK,
            ingotSmelts = listOf(TIN_RAW, TIN_DUST, TIN_CRUSHED, TIN_ORE, TIN_DEEPSLATE_ORE)
        )

//        TinMaterial.MATERIAL.generateRecipes(this, exporter)
//        SilverMaterial.MATERIAL.generateRecipes(this, exporter)
//        PlatinumMaterial.MATERIAL.generateRecipes(this, exporter)
//        TitaniumMaterial.MATERIAL.generateRecipes(this, exporter)
//        CobaltMaterial.MATERIAL.generateRecipes(this, exporter)
//        MithrilMaterial.MATERIAL.generateRecipes(this, exporter)
//        OrichalcumMaterial.MATERIAL.generateRecipes(this, exporter)
//
//        BronzeMaterial.MATERIAL.generateRecipes(this, exporter)
//        SteelMaterial.MATERIAL.generateRecipes(this, exporter)
//
//        TopazMaterial.MATERIAL.generateRecipes(this, exporter)
//        SapphireMaterial.MATERIAL.generateRecipes(this, exporter)
//        RubyMaterial.MATERIAL.generateRecipes(this, exporter)
//
//        CopperMaterial.MATERIAL.generateRecipes(this, exporter)
//        IronMaterial.MATERIAL.generateRecipes(this, exporter)
//        GoldMaterial.MATERIAL.generateRecipes(this, exporter)
//        DiamondMaterial.MATERIAL.generateRecipes(this, exporter)
//        NetheriteMaterial.MATERIAL.generateRecipes(this, exporter)
//
//        WoodMaterial.MATERIAL.generateRecipes(this, exporter)
//        StoneMaterial.MATERIAL.generateRecipes(this, exporter)
//        BoneMaterial.MATERIAL.generateRecipes(this, exporter)
//        FlintMaterial.MATERIAL.generateRecipes(this, exporter)
//        CoalMaterial.MATERIAL.generateRecipes(this, exporter)
//        WoolMaterial.MATERIAL.generateRecipes(this, exporter)
//        LeatherMaterial.MATERIAL.generateRecipes(this, exporter)

        offerIronAlternatives(exporter)
    }
}

fun offerCompactingRecipes(
    exporter: RecipeExporter,
    input: ItemConvertible,
    compacted: ItemConvertible,
    recipeCategory: RecipeCategory = RecipeCategory.MISC,
) {
    offerReversibleCompactingRecipes(
        exporter,
        recipeCategory,
        input,
        recipeCategory,
        compacted,
        getItemPath(compacted) + "_from_" + getItemPath(input),
        getItemPath(compacted),
        getItemPath(input) + "_from_" + getItemPath(compacted),
        getItemPath(input)
    )
}

fun offerProgressiveCompactingRecipes(
    exporter: RecipeExporter,
    nugget: ItemConvertible,
    ingot: ItemConvertible,
    block: ItemConvertible,
    recipeCategory: RecipeCategory = RecipeCategory.MISC,
) {
    offerCompactingRecipes(exporter, nugget, ingot, recipeCategory)
    offerCompactingRecipes(exporter, ingot, block, recipeCategory)
}

fun offerOreMaterial(
    exporter: RecipeExporter,
    ingot: ItemConvertible,
    nugget: ItemConvertible,
    block: ItemConvertible,
    raw: ItemConvertible,
    rawBlock: ItemConvertible,
    ingotSmelts: List<ItemConvertible> = listOf(),
    ingotExp: Float = 0.7f,
    ingotCookTime: Int = 200,
    nuggetSmelts: List<ItemConvertible> = listOf(),
    nuggetExp: Float = 0.7f,
    nuggetCookTime: Int = 200,
    recipeCategory: RecipeCategory = RecipeCategory.MISC,
) {
    offerCompactingRecipes(exporter, raw, rawBlock, recipeCategory)
    offerProgressiveCompactingRecipes(exporter, nugget, ingot, block, recipeCategory)
    if (ingotSmelts.isNotEmpty()) {
        offerSmeltingAndBlasting(exporter, ingotSmelts, ingot, ingotExp, ingotCookTime, recipeCategory)
    }
    if (nuggetSmelts.isNotEmpty()) {
        offerSmeltingAndBlasting(exporter, nuggetSmelts, nugget, nuggetExp, nuggetCookTime, recipeCategory)
    }
}

fun offerSmeltingAndBlasting(
    exporter: RecipeExporter,
    inputList: List<ItemConvertible>,
    output: ItemConvertible,
    exp: Float = 0.1f,
    cookTime: Int = 200,
    recipeCategory: RecipeCategory = RecipeCategory.MISC,
) {
    offerSmelting(exporter, inputList, recipeCategory, output, exp, cookTime, getItemPath(output))
    offerBlasting(exporter, inputList, recipeCategory, output, exp, cookTime / 2, getItemPath(output))
}

fun offerIronAlternatives(exporter: RecipeExporter) {
    offerShieldRecipe(exporter, ModItemTags.IRON_ALTERNATIVE)
    offerBucketRecipe(exporter, ModItemTags.IRON_ALTERNATIVE)
    offerShearsRecipe(exporter, ModItemTags.IRON_ALTERNATIVE)
    offerCompassRecipe(exporter, ModItemTags.IRON_ALTERNATIVE)
}

fun offerShieldRecipe(exporter: RecipeExporter, tag: TagKey<Item>) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.SHIELD)
        .pattern("#i#")
        .pattern("###")
        .pattern(" # ")
        .input('#', ItemTags.PLANKS)
        .input('i', tag)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(Items.SHIELD)))
}

fun offerBucketRecipe(exporter: RecipeExporter, tag: TagKey<Item>) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.BUCKET)
        .pattern("# #")
        .pattern(" # ")
        .input('#', tag)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(Items.BUCKET)))
}

fun offerShearsRecipe(exporter: RecipeExporter, tag: TagKey<Item>) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.SHEARS)
        .pattern(" #")
        .pattern("# ")
        .input('#', tag)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(Items.SHEARS)))
}

fun offerCompassRecipe(exporter: RecipeExporter, tag: TagKey<Item>) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.COMPASS)
        .pattern(" # ")
        .pattern("#R#")
        .pattern(" # ")
        .input('#', tag)
        .input('R', Items.REDSTONE)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(Items.COMPASS)))
}

 const val HAS_TAG_ITEM = "has_tag_item"