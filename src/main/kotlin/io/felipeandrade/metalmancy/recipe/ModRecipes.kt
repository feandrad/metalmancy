package io.felipeandrade.metalmancy.recipe

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ModRecipes {
    fun initialize() {
        RecipeSerializer.register(Identifier(MOD_ID , CalcinatingRecipe.RECIPE_ID).toString(), CalcinatingRecipe.Serializer())
        Registry.register(Registries.RECIPE_TYPE, Identifier(MOD_ID , CalcinatingRecipe.RECIPE_ID), CalcinatingRecipe.TYPE)
    }
}