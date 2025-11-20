package tools.recipegen

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.registry.material.Family
import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Part

object Recipes {
    fun byFamily(material: Material): List<GeneratedRecipe> {
        return when (material.family) {
            Family.METAL -> metalRecipes(material)
            Family.GEM -> gemRecipes(material)
            else -> emptyList()
        }
    }

    private fun metalRecipes(material: Material): List<GeneratedRecipe> {
        val recipes = mutableListOf<GeneratedRecipe>()

        if (material.hasPart(Part.ORE) && material.hasPart(Part.INGOT)) {
            val ore = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.ORE)}"
            val ingot = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.INGOT)}"

            recipes.add(SmeltingRecipe(
                unlocalizedName = "smelting/${material.unlocalizedName(Part.INGOT)}_from_${material.unlocalizedName(Part.ORE)}",
                ingredient = ore,
                result = ingot,
                group = material.unlocalizedName(Part.INGOT),
                category = "blocks"
            ))

            recipes.add(BlastingRecipe(
                unlocalizedName = "blasting/${material.unlocalizedName(Part.INGOT)}_from_${material.unlocalizedName(Part.ORE)}",
                ingredient = ore,
                result = ingot,
                group = material.unlocalizedName(Part.INGOT),
                category = "blocks"
            ))
        }

        if (material.hasPart(Part.ORE_DEEPSLATE) && material.hasPart(Part.INGOT)) {
            val ore = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.ORE_DEEPSLATE)}"
            val ingot = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.INGOT)}"

            recipes.add(SmeltingRecipe(
                unlocalizedName = "smelting/${material.unlocalizedName(Part.INGOT)}_from_${material.unlocalizedName(Part.ORE_DEEPSLATE)}",
                ingredient = ore,
                result = ingot,
                group = material.unlocalizedName(Part.INGOT),
                category = "blocks"
            ))

            recipes.add(BlastingRecipe(
                unlocalizedName = "blasting/${material.unlocalizedName(Part.INGOT)}_from_${material.unlocalizedName(Part.ORE_DEEPSLATE)}",
                ingredient = ore,
                result = ingot,
                group = material.unlocalizedName(Part.INGOT),
                category = "blocks"
            ))
        }

        return recipes
    }

    private fun gemRecipes(material: Material): List<GeneratedRecipe> {
        val recipes = mutableListOf<GeneratedRecipe>()

        if (material.hasPart(Part.ORE) && material.hasPart(Part.GEM)) {
            val ore = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.ORE)}"
            val gem = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.GEM)}"

            recipes.add(SmeltingRecipe(
                unlocalizedName = "smelting/${material.unlocalizedName(Part.GEM)}_from_${material.unlocalizedName(Part.ORE)}",
                ingredient = ore,
                result = gem,
                group = material.unlocalizedName(Part.GEM),
                category = "misc"
            ))
        }

        if (material.hasPart(Part.ORE_DEEPSLATE) && material.hasPart(Part.GEM)) {
            val ore = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.ORE_DEEPSLATE)}"
            val gem = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.GEM)}"

            recipes.add(SmeltingRecipe(
                unlocalizedName = "smelting/${material.unlocalizedName(Part.GEM)}_from_${material.unlocalizedName(Part.ORE_DEEPSLATE)}",
                ingredient = ore,
                result = gem,
                group = material.unlocalizedName(Part.GEM),
                category = "misc"
            ))
        }

        return recipes
    }

    private fun Material.hasPart(part: Part) = this.parts.contains(part)
}
