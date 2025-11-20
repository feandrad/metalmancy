package tools.recipegen

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part

object RecipeEntries {
    val recipes = mutableListOf<GeneratedRecipe>()

    init {
        Materials.ALL.forEach { material ->
            recipes.addAll(Recipes.byFamily(material))
        }
    }
}