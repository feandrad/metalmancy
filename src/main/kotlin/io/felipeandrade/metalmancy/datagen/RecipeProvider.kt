package io.felipeandrade.metalmancy.datagen

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import io.felipeandrade.metalmancy.ModItemTags
import io.felipeandrade.metalmancy.blocks.ModBlocks
import io.felipeandrade.metalmancy.items.ModItems
import io.felipeandrade.metalmancy.items.armor.ModArmorItems
import io.felipeandrade.metalmancy.items.tools.ModTools
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.advancement.AdvancementCriterion
import net.minecraft.block.Blocks
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.RecipeProvider.*
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.tag.ItemTags
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

class RecipeProvider(dataOutput: FabricDataOutput) : FabricRecipeProvider(dataOutput) {

    override fun generate(exporter: RecipeExporter) {

        // Vanilla
        offerCompactingRecipes(exporter, ModItems.COPPER_NUGGET, Items.COPPER_INGOT)
        offerCompactingRecipes(exporter, ModItems.COPPER_HEAP, ModItems.COPPER_DUST)
        offerSmeltingAndBlasting(exporter, listOf(ModItems.COPPER_CRUSHED, ModItems.COPPER_DUST), Items.COPPER_INGOT, 0.7f, 200, RecipeCategory.MISC)

        offerCompactingRecipes(exporter, ModItems.IRON_HEAP, ModItems.IRON_DUST)
        offerSmeltingAndBlasting(exporter, listOf(ModItems.IRON_CRUSHED, ModItems.IRON_DUST), Items.IRON_INGOT, 0.7f, 200, RecipeCategory.MISC)
        
        offerCompactingRecipes(exporter, ModItems.GOLD_HEAP, ModItems.GOLD_DUST)
        offerSmeltingAndBlasting(exporter, listOf(ModItems.GOLD_CRUSHED, ModItems.GOLD_DUST), Items.GOLD_INGOT, 0.7f, 200, RecipeCategory.MISC)
        
        offerCompactingRecipes(exporter, ModItems.DIAMOND_NUGGET, Items.DIAMOND)
        offerCompactingRecipes(exporter, ModItems.DIAMOND_HEAP, ModItems.DIAMOND_DUST)
        offerSmeltingAndBlasting(exporter, listOf(ModItems.DIAMOND_CRUSHED, ModItems.DIAMOND_DUST), Items.DIAMOND, 0.7f, 200, RecipeCategory.MISC)

        offerMachines(exporter)

        // Metals
        offerOreMaterial(
            exporter = exporter,
            ingot = ModItems.TIN_INGOT,
            nugget = ModItems.TIN_NUGGET,
            block = ModBlocks.TIN_BLOCK,
            raw = ModItems.TIN_RAW,
            rawBlock = ModBlocks.TIN_RAW_BLOCK,
            heap = ModItems.TIN_HEAP,
            dust = ModItems.TIN_DUST,
            ingotSmelts = listOf(
                ModItems.TIN_RAW,
                ModItems.TIN_DUST,
                ModItems.TIN_CRUSHED,
                ModBlocks.TIN_ORE,
                ModBlocks.TIN_DEEPSLATE_ORE
            )
        )
        offerOreMaterial(
            exporter = exporter,
            ingot = ModItems.ZINC_INGOT,
            nugget = ModItems.ZINC_NUGGET,
            block = ModBlocks.ZINC_BLOCK,
            raw = ModItems.ZINC_RAW,
            rawBlock = ModBlocks.ZINC_RAW_BLOCK,
            heap = ModItems.ZINC_HEAP,
            dust = ModItems.ZINC_DUST,
            ingotSmelts = listOf(
                ModItems.ZINC_RAW,
                ModItems.ZINC_DUST,
                ModItems.ZINC_CRUSHED,
                ModBlocks.ZINC_ORE,
                ModBlocks.ZINC_DEEPSLATE_ORE
            )
        )
        offerOreMaterial(
            exporter = exporter,
            ingot = ModItems.SILVER_INGOT,
            nugget = ModItems.SILVER_NUGGET,
            block = ModBlocks.SILVER_BLOCK,
            raw = ModItems.SILVER_RAW,
            rawBlock = ModBlocks.SILVER_RAW_BLOCK,
            heap = ModItems.SILVER_HEAP,
            dust = ModItems.SILVER_DUST,
            ingotSmelts = listOf(
                ModItems.SILVER_RAW,
                ModItems.SILVER_DUST,
                ModItems.SILVER_CRUSHED,
                ModBlocks.SILVER_ORE,
                ModBlocks.SILVER_DEEPSLATE_ORE
            )
        )
        offerOreMaterial(
            exporter = exporter,
            ingot = ModItems.PLATINUM_INGOT,
            nugget = ModItems.PLATINUM_NUGGET,
            block = ModBlocks.PLATINUM_BLOCK,
            raw = ModItems.PLATINUM_RAW,
            rawBlock = ModBlocks.PLATINUM_RAW_BLOCK,
            heap = ModItems.PLATINUM_HEAP,
            dust = ModItems.PLATINUM_DUST,
            ingotSmelts = listOf(
                ModItems.PLATINUM_RAW,
                ModItems.PLATINUM_DUST,
                ModItems.PLATINUM_CRUSHED,
                ModBlocks.PLATINUM_ORE,
                ModBlocks.PLATINUM_DEEPSLATE_ORE
            )
        )
        offerOreMaterial(
            exporter = exporter,
            ingot = ModItems.TITANIUM_INGOT,
            nugget = ModItems.TITANIUM_NUGGET,
            block = ModBlocks.TITANIUM_BLOCK,
            raw = ModItems.TITANIUM_RAW,
            rawBlock = ModBlocks.TITANIUM_RAW_BLOCK,
            heap = ModItems.TITANIUM_HEAP,
            dust = ModItems.TITANIUM_DUST,
            ingotSmelts = listOf(
                ModItems.TITANIUM_RAW,
                ModItems.TITANIUM_DUST,
                ModItems.TITANIUM_CRUSHED,
                ModBlocks.TITANIUM_ORE,
                ModBlocks.TITANIUM_DEEPSLATE_ORE
            )
        )
        offerOreMaterial(
            exporter = exporter,
            ingot = ModItems.COBALT_INGOT,
            nugget = ModItems.COBALT_NUGGET,
            block = ModBlocks.COBALT_BLOCK,
            raw = ModItems.COBALT_RAW,
            rawBlock = ModBlocks.COBALT_RAW_BLOCK,
            heap = ModItems.COBALT_HEAP,
            dust = ModItems.COBALT_DUST,
            ingotSmelts = listOf(
                ModItems.COBALT_RAW,
                ModItems.COBALT_DUST,
                ModItems.COBALT_CRUSHED,
                ModBlocks.COBALT_ORE,
                ModBlocks.COBALT_DEEPSLATE_ORE
            )
        )
        offerOreMaterial(
            exporter = exporter,
            ingot = ModItems.MITHRIL_INGOT,
            nugget = ModItems.MITHRIL_NUGGET,
            block = ModBlocks.MITHRIL_BLOCK,
            raw = ModItems.MITHRIL_RAW,
            rawBlock = ModBlocks.MITHRIL_RAW_BLOCK,
            heap = ModItems.MITHRIL_HEAP,
            dust = ModItems.MITHRIL_DUST,
            ingotSmelts = listOf(
                ModItems.MITHRIL_RAW,
                ModItems.MITHRIL_DUST,
                ModItems.MITHRIL_CRUSHED,
                ModBlocks.MITHRIL_ORE,
                ModBlocks.MITHRIL_DEEPSLATE_ORE
            )
        )
        offerOreMaterial(
            exporter = exporter,
            ingot = ModItems.ORICHALCUM_INGOT,
            nugget = ModItems.ORICHALCUM_NUGGET,
            block = ModBlocks.ORICHALCUM_BLOCK,
            raw = ModItems.ORICHALCUM_RAW,
            rawBlock = ModBlocks.ORICHALCUM_RAW_BLOCK,
            heap = ModItems.ORICHALCUM_HEAP,
            dust = ModItems.ORICHALCUM_DUST,
            ingotSmelts = listOf(
                ModItems.ORICHALCUM_RAW,
                ModItems.ORICHALCUM_DUST,
                ModItems.ORICHALCUM_CRUSHED,
                ModBlocks.ORICHALCUM_ORE,
                ModBlocks.ORICHALCUM_DEEPSLATE_ORE
            )
        )

        // Alloy
        offerAlloyMaterial(
            exporter = exporter,
            ingot = ModItems.BRONZE_INGOT,
            nugget = ModItems.BRONZE_NUGGET,
            block = ModBlocks.BRONZE_BLOCK,
            heap = ModItems.BRONZE_HEAP,
            dust = ModItems.BRONZE_DUST,
            ingotSmelts = listOf(ModItems.BRONZE_DUST, ModItems.BRONZE_CRUSHED)
        )
        offerAlloyMaterial(
            exporter = exporter,
            ingot = ModItems.BRASS_INGOT,
            nugget = ModItems.BRASS_NUGGET,
            block = ModBlocks.BRASS_BLOCK,
            heap = ModItems.BRASS_HEAP,
            dust = ModItems.BRASS_DUST,
            ingotSmelts = listOf(ModItems.BRASS_DUST, ModItems.BRASS_CRUSHED)
        )
        offerAlloyMaterial(
            exporter = exporter,
            ingot = ModItems.STEEL_INGOT,
            nugget = ModItems.STEEL_NUGGET,
            block = ModBlocks.STEEL_BLOCK,
            heap = ModItems.STEEL_HEAP,
            dust = ModItems.STEEL_DUST,
            ingotSmelts = listOf(ModItems.STEEL_DUST, ModItems.STEEL_CRUSHED)
        )

        // Bronze
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BRONZE_DUST, 4)
            .input(ModItems.TIN_DUST, 1)
            .input(ModItems.COPPER_DUST, 3)
            .criterion(hasItem(ModItems.TIN_DUST), conditionsFromItem(ModItems.TIN_DUST))
            .offerTo(exporter, Identifier(MOD_ID, getRecipeName(ModItems.BRONZE_DUST)))

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BRONZE_CRUSHED, 4)
            .input(ModItems.TIN_CRUSHED, 1)
            .input(ModItems.COPPER_CRUSHED, 3)
            .criterion(
                hasItem(ModItems.TIN_CRUSHED),
                conditionsFromItem(ModItems.TIN_CRUSHED)
            )
            .offerTo(exporter, Identifier(MOD_ID, getRecipeName(ModItems.BRONZE_CRUSHED)))

        // TODO: Remove after Hammer crush Recipe is implemented
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BRONZE_INGOT, 4)
            .input(ModItems.TIN_INGOT, 1)
            .input(Items.COPPER_INGOT, 3)
            .criterion(hasItem(ModItems.TIN_RAW), conditionsFromItem(ModItems.TIN_RAW))
            .offerTo(exporter, Identifier(MOD_ID, "${getRecipeName(ModItems.BRONZE_INGOT)}_temp"))

        // Brass
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BRASS_DUST, 4)
            .input(ModItems.ZINC_DUST, 1)
            .input(ModItems.COPPER_DUST, 3)
            .criterion(
                hasItem(ModItems.ZINC_DUST),
                conditionsFromItem(ModItems.ZINC_DUST)
            )
            .offerTo(exporter, Identifier(MOD_ID, getRecipeName(ModItems.BRASS_DUST)))

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BRASS_CRUSHED, 4)
            .input(ModItems.ZINC_CRUSHED, 1)
            .input(ModItems.COPPER_CRUSHED, 3)
            .criterion(
                hasItem(ModItems.ZINC_CRUSHED),
                conditionsFromItem(ModItems.ZINC_CRUSHED)
            )
            .offerTo(exporter, Identifier(MOD_ID, getRecipeName(ModItems.BRASS_CRUSHED)))

        // TODO: Remove after Hammer crush Recipe is implemented
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BRASS_INGOT, 4)
            .input(ModItems.ZINC_INGOT, 1)
            .input(Items.COPPER_INGOT, 3)
            .criterion(hasItem(ModItems.ZINC_RAW), conditionsFromItem(ModItems.ZINC_RAW))
            .offerTo(exporter, Identifier(MOD_ID, "${getRecipeName(ModItems.BRASS_INGOT)}_temp"))

        // Armors
        offerArmorRecipes(exporter, ItemTags.WOOL, ModArmorItems.WOOLEN_BOOTS, ModArmorItems.WOOLEN_LEGGINGS, ModArmorItems.WOOLEN_CHESTPLATE, ModArmorItems.WOOLEN_HELMET)
        offerArmorRecipes(exporter, ItemTags.LOGS, ModArmorItems.WOODEN_BOOTS, ModArmorItems.WOODEN_LEGGINGS, ModArmorItems.WOODEN_CHESTPLATE, ModArmorItems.WOODEN_HELMET)
        offerArmorRecipes(exporter, ModItemTags.COPPER_INGOTS, ModArmorItems.COPPER_BOOTS, ModArmorItems.COPPER_LEGGINGS, ModArmorItems.COPPER_CHESTPLATE, ModArmorItems.COPPER_HELMET)

        offerArmorRecipes(exporter, ModItemTags.SILVER_INGOTS, ModArmorItems.SILVER_BOOTS, ModArmorItems.SILVER_LEGGINGS, ModArmorItems.SILVER_CHESTPLATE, ModArmorItems.SILVER_HELMET)
        offerArmorRecipes(exporter, ModItemTags.PLATINUM_INGOTS, ModArmorItems.PLATINUM_BOOTS, ModArmorItems.PLATINUM_LEGGINGS, ModArmorItems.PLATINUM_CHESTPLATE, ModArmorItems.PLATINUM_HELMET)
        offerArmorRecipes(exporter, ModItemTags.TITANIUM_INGOTS, ModArmorItems.TITANIUM_BOOTS, ModArmorItems.TITANIUM_LEGGINGS, ModArmorItems.TITANIUM_CHESTPLATE, ModArmorItems.TITANIUM_HELMET)
        offerArmorRecipes(exporter, ModItemTags.COBALT_INGOTS, ModArmorItems.COBALT_BOOTS, ModArmorItems.COBALT_LEGGINGS, ModArmorItems.COBALT_CHESTPLATE, ModArmorItems.COBALT_HELMET)
        offerArmorRecipes(exporter, ModItemTags.MITHRIL_INGOTS, ModArmorItems.MITHRIL_BOOTS, ModArmorItems.MITHRIL_LEGGINGS, ModArmorItems.MITHRIL_CHESTPLATE, ModArmorItems.MITHRIL_HELMET)
        offerArmorRecipes(exporter, ModItemTags.ORICHALCUM_INGOTS, ModArmorItems.ORICHALCUM_BOOTS, ModArmorItems.ORICHALCUM_LEGGINGS, ModArmorItems.ORICHALCUM_CHESTPLATE, ModArmorItems.ORICHALCUM_HELMET)

        offerArmorRecipes(exporter, ModItemTags.BRONZE_INGOTS, ModArmorItems.BRONZE_BOOTS, ModArmorItems.BRONZE_LEGGINGS, ModArmorItems.BRONZE_CHESTPLATE, ModArmorItems.BRONZE_HELMET)
        offerArmorRecipes(exporter, ModItemTags.BRASS_INGOTS, ModArmorItems.BRASS_BOOTS, ModArmorItems.BRASS_LEGGINGS, ModArmorItems.BRASS_CHESTPLATE, ModArmorItems.BRASS_HELMET)
        offerArmorRecipes(exporter, ModItemTags.STEEL_INGOTS, ModArmorItems.STEEL_BOOTS, ModArmorItems.STEEL_LEGGINGS, ModArmorItems.STEEL_CHESTPLATE, ModArmorItems.STEEL_HELMET)

        // Tools
        offerToolRecipes(exporter, ModItemTags.BONES, ModTools.BONE_AXE, ModTools.BONE_PICKAXE, ModTools.BONE_SHOVEL, ModTools.BONE_HOE, ModTools.BONE_SWORD)
        offerToolRecipes(exporter, ModItemTags.FLINTS, ModTools.FLINT_AXE, ModTools.FLINT_PICKAXE, ModTools.FLINT_SHOVEL, ModTools.FLINT_HOE, ModTools.FLINT_SWORD)
        offerToolRecipes(exporter, ModItemTags.COPPER_INGOTS, ModTools.COPPER_AXE, ModTools.COPPER_PICKAXE, ModTools.COPPER_SHOVEL, ModTools.COPPER_HOE, ModTools.COPPER_SWORD)

        offerToolRecipes(exporter, ModItemTags.SILVER_INGOTS, ModTools.SILVER_AXE, ModTools.SILVER_PICKAXE, ModTools.SILVER_SHOVEL, ModTools.SILVER_HOE, ModTools.SILVER_SWORD)
        offerToolRecipes(exporter, ModItemTags.PLATINUM_INGOTS, ModTools.PLATINUM_AXE, ModTools.PLATINUM_PICKAXE, ModTools.PLATINUM_SHOVEL, ModTools.PLATINUM_HOE, ModTools.PLATINUM_SWORD)
        offerToolRecipes(exporter, ModItemTags.TITANIUM_INGOTS, ModTools.TITANIUM_AXE, ModTools.TITANIUM_PICKAXE, ModTools.TITANIUM_SHOVEL, ModTools.TITANIUM_HOE, ModTools.TITANIUM_SWORD)
        offerToolRecipes(exporter, ModItemTags.COBALT_INGOTS, ModTools.COBALT_AXE, ModTools.COBALT_PICKAXE, ModTools.COBALT_SHOVEL, ModTools.COBALT_HOE, ModTools.COBALT_SWORD)
        offerToolRecipes(exporter, ModItemTags.MITHRIL_INGOTS, ModTools.MITHRIL_AXE, ModTools.MITHRIL_PICKAXE, ModTools.MITHRIL_SHOVEL, ModTools.MITHRIL_HOE, ModTools.MITHRIL_SWORD)
        offerToolRecipes(exporter, ModItemTags.ORICHALCUM_INGOTS, ModTools.ORICHALCUM_AXE, ModTools.ORICHALCUM_PICKAXE, ModTools.ORICHALCUM_SHOVEL, ModTools.ORICHALCUM_HOE, ModTools.ORICHALCUM_SWORD)

        offerToolRecipes(exporter, ModItemTags.RUBYS, ModTools.RUBY_AXE, ModTools.RUBY_PICKAXE, ModTools.RUBY_SHOVEL, ModTools.RUBY_HOE, ModTools.RUBY_SWORD)
        offerToolRecipes(exporter, ModItemTags.SAPPHIRES, ModTools.SAPPHIRE_AXE, ModTools.SAPPHIRE_PICKAXE, ModTools.SAPPHIRE_SHOVEL, ModTools.SAPPHIRE_HOE, ModTools.SAPPHIRE_SWORD)
        offerToolRecipes(exporter, ModItemTags.TOPAZES, ModTools.TOPAZ_AXE, ModTools.TOPAZ_PICKAXE, ModTools.TOPAZ_SHOVEL, ModTools.TOPAZ_HOE, ModTools.TOPAZ_SWORD)

        offerToolRecipes(exporter, ModItemTags.BRASS_INGOTS, ModTools.BRASS_AXE, ModTools.BRASS_PICKAXE, ModTools.BRASS_SHOVEL, ModTools.BRASS_HOE, ModTools.BRASS_SWORD)
        offerToolRecipes(exporter, ModItemTags.BRONZE_INGOTS, ModTools.BRONZE_AXE, ModTools.BRONZE_PICKAXE, ModTools.BRONZE_SHOVEL, ModTools.BRONZE_HOE, ModTools.BRONZE_SWORD)
        offerToolRecipes(exporter, ModItemTags.STEEL_INGOTS, ModTools.STEEL_AXE, ModTools.STEEL_PICKAXE, ModTools.STEEL_SHOVEL, ModTools.STEEL_HOE, ModTools.STEEL_SWORD)


        offerIronAlternatives(exporter)
    }

    private fun offerMachines(exporter: RecipeExporter) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.CALCINATOR)
            .pattern("# #")
            .pattern("ifi")
            .pattern("#i#") // .pattern("#g#")
            .input('f', Blocks.FURNACE)
            .input('#', Blocks.SMOOTH_STONE)
            .input('i', ModItemTags.IRON_ALTERNATIVE)
//            .input('g', ModItems.IRON_GEAR)
            .criterion(HAS_TAG_ITEM, conditionsFromTag(ModItemTags.IRON_ALTERNATIVE) as AdvancementCriterion<*>)
            .offerTo(exporter, Identifier(MOD_ID, getRecipeName(ModBlocks.CALCINATOR)))

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.WATER_TURBINE)
            .pattern("###")
            .pattern("iii")  // .pattern("igi")
            .pattern("###")
            .input('#', Blocks.SMOOTH_STONE)
            .input('i', ModItemTags.IRON_ALTERNATIVE)
//            .input('g', ModItems.IRON_GEAR)
            .criterion(HAS_TAG_ITEM, conditionsFromTag(ModItemTags.IRON_ALTERNATIVE) as AdvancementCriterion<*>)
            .offerTo(exporter, Identifier(MOD_ID, getRecipeName(ModBlocks.WATER_TURBINE)))
    }

    private fun offerArmorRecipes(
        exporter: RecipeExporter,
        tag: TagKey<Item>,
        boots: Item,
        leggings: Item,
        chestplate: Item,
        helmet: Item,
    ) {
        boots.offerBoots(exporter, tag)
        leggings.offerLeggings(exporter, tag)
        chestplate.offerChestplate(exporter, tag)
        helmet.offerHelmet(exporter, tag)
    }


    private fun offerToolRecipes(
        exporter: RecipeExporter,
        tag: TagKey<Item>,
        axe: Item,
        pickaxe: Item,
        shovel: Item,
        hoe: Item,
        sword: Item,
    ) {
        axe.offerAxe(exporter, tag, Items.STICK)
        pickaxe.offerPickaxe(exporter, tag, Items.STICK)
        shovel.offerShovel(exporter, tag, Items.STICK)
        hoe.offerHoe(exporter, tag, Items.STICK)
        sword.offerSword(exporter, tag, Items.STICK)
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
    heap: ItemConvertible,
    dust: ItemConvertible,
    ingotSmelts: List<ItemConvertible> = listOf(),
    ingotExp: Float = 0.7f,
    ingotCookTime: Int = 200,
    nuggetSmelts: List<ItemConvertible> = listOf(),
    nuggetExp: Float = 0.7f,
    nuggetCookTime: Int = 200,
    recipeCategory: RecipeCategory = RecipeCategory.MISC,
) {
    offerCompactingRecipes(exporter, raw, rawBlock, recipeCategory)
    offerCompactingRecipes(exporter, heap, dust)
    offerProgressiveCompactingRecipes(exporter, nugget, ingot, block, recipeCategory)
    if (ingotSmelts.isNotEmpty()) {
        offerSmeltingAndBlasting(exporter, ingotSmelts, ingot, ingotExp, ingotCookTime, recipeCategory)
    }
    if (nuggetSmelts.isNotEmpty()) {
        offerSmeltingAndBlasting(exporter, nuggetSmelts, nugget, nuggetExp, nuggetCookTime, recipeCategory)
    }
}

fun offerAlloyMaterial(
    exporter: RecipeExporter,
    ingot: ItemConvertible,
    nugget: ItemConvertible,
    block: ItemConvertible,
    heap: ItemConvertible,
    dust: ItemConvertible,
    ingotSmelts: List<ItemConvertible> = listOf(),
    ingotExp: Float = 0.7f,
    ingotCookTime: Int = 200,
    nuggetSmelts: List<ItemConvertible> = listOf(),
    nuggetExp: Float = 0.7f,
    nuggetCookTime: Int = 200,
    recipeCategory: RecipeCategory = RecipeCategory.MISC,
) {
    offerProgressiveCompactingRecipes(exporter, nugget, ingot, block, recipeCategory)
    offerCompactingRecipes(exporter, heap, dust)

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

fun Item.offerHelmet(exporter: RecipeExporter, tag: TagKey<Item>) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, this)
        .pattern("###")
        .pattern("# #")
        .input('#', tag)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(this)))
}

fun Item.offerChestplate(exporter: RecipeExporter, tag: TagKey<Item>) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, this)
        .pattern("# #")
        .pattern("###")
        .pattern("###")
        .input('#', tag)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(this)))
}

fun Item.offerLeggings(exporter: RecipeExporter, tag: TagKey<Item>) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, this)
        .pattern("###")
        .pattern("# #")
        .pattern("# #")
        .input('#', tag)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(this)))
}

fun Item.offerBoots(exporter: RecipeExporter, tag: TagKey<Item>) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, this)
        .pattern("# #")
        .pattern("# #")
        .input('#', tag)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(this)))
}

// Tools
fun Item.offerPickaxe(exporter: RecipeExporter, tag: TagKey<Item>, handle: ItemConvertible) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, this)
        .pattern("###")
        .pattern(" | ")
        .pattern(" | ")
        .input('#', tag)
        .input('|', handle)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(this)))
}

fun Item.offerAxe(exporter: RecipeExporter, tag: TagKey<Item>, handle: ItemConvertible) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, this)
        .pattern("##")
        .pattern("#|")
        .pattern(" |")
        .input('#', tag)
        .input('|', handle)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(this)))
}

fun Item.offerShovel(exporter: RecipeExporter, tag: TagKey<Item>, handle: ItemConvertible) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, this)
        .pattern("#")
        .pattern("|")
        .pattern("|")
        .input('#', tag)
        .input('|', handle)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(this)))
}

fun Item.offerHoe(exporter: RecipeExporter, tag: TagKey<Item>, handle: ItemConvertible) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, this)
        .pattern("##")
        .pattern(" |")
        .pattern(" |")
        .input('#', tag)
        .input('|', handle)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(this)))
}

fun Item.offerSword(exporter: RecipeExporter, tag: TagKey<Item>, handle: ItemConvertible) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, this)
        .pattern("#")
        .pattern("#")
        .pattern("|")
        .input('#', tag)
        .input('|', handle)
        .criterion(HAS_TAG_ITEM, conditionsFromTag(tag) as AdvancementCriterion<*>)
        .offerTo(exporter, Identifier(MOD_ID, getRecipeName(this)))
}

const val HAS_TAG_ITEM = "has_tag_item"