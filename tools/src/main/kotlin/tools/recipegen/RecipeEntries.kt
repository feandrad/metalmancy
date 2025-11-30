package tools.recipegen

import io.felipeandrade.metalmancy.registry.material.Materials

object RecipeEntries {
    val recipes = mutableListOf<GeneratedRecipe>()

    init {
        Materials.ALL.forEach { material ->
            recipes.addAll(Recipes.byFamily(material))
        }
    }
}