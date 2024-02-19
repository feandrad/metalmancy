package io.felipeandrade.metalmancy.recipe

import io.felipeandrade.metalmancy.blocks.ModBlocks
import net.minecraft.item.ItemStack
import net.minecraft.recipe.*
import net.minecraft.recipe.book.CookingRecipeCategory

class CalcinatingRecipe(
    type: RecipeType<*>,
    group: String,
    category: CookingRecipeCategory,
    ingredient: Ingredient,
    result: ItemStack,
    experience: Float,
    cookingTime: Int
) : AbstractCookingRecipe(type, group, category, ingredient, result, experience, cookingTime) {

    override fun createIcon(): ItemStack {
        return ItemStack(ModBlocks.CALCINATOR)
    }

    override fun getSerializer(): RecipeSerializer<*> = CALCINATING

    companion object {
        val RECIPE_ID = "calcinating"
        val CALCINATING: RecipeSerializer<BlastingRecipe> = RecipeSerializer.register(
            RECIPE_ID,
            CookingRecipeSerializer(
                { group: String?, category: CookingRecipeCategory?, ingredient: Ingredient?, result: ItemStack?, experience: Float, cookingTime: Int ->
                    BlastingRecipe(group, category, ingredient, result, experience, cookingTime)
                },
                300
            )
        )
    }
}