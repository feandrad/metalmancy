package io.felipeandrade.metalmancy.recipe

import net.minecraft.recipe.BlastingRecipe
import net.minecraft.recipe.RecipeType

class ModRecipes {
    companion object{
        val CALCINATING =  RecipeType.register<BlastingRecipe?>(CalcinatingRecipe.RECIPE_ID)

        fun initialize() {}
    }
}