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

        // Ore to ingot (smelting/blasting)
        if (material.hasPart(Part.ORE) && material.hasPart(Part.INGOT)) {
            val ore = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.ORE)}"
            val ingot = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.INGOT)}"

            recipes.add(SmeltingRecipe(
                unlocalizedName = "smelting_${material.unlocalizedName(Part.INGOT)}_from_${material.unlocalizedName(Part.ORE)}",
                ingredient = ore,
                result = ingot,
                group = material.unlocalizedName(Part.INGOT),
                category = "blocks"
            ))

            recipes.add(BlastingRecipe(
                unlocalizedName = "blasting_${material.unlocalizedName(Part.INGOT)}_from_${material.unlocalizedName(Part.ORE)}",
                ingredient = ore,
                result = ingot,
                group = material.unlocalizedName(Part.INGOT),
                category = "blocks"
            ))
        }

        // Deepslate ore to ingot (smelting/blasting)
        if (material.hasPart(Part.ORE_DEEPSLATE) && material.hasPart(Part.INGOT)) {
            val ore = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.ORE_DEEPSLATE)}"
            val ingot = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.INGOT)}"

            recipes.add(SmeltingRecipe(
                unlocalizedName = "smelting_${material.unlocalizedName(Part.INGOT)}_from_${material.unlocalizedName(Part.ORE_DEEPSLATE)}",
                ingredient = ore,
                result = ingot,
                group = material.unlocalizedName(Part.INGOT),
                category = "blocks"
            ))

            recipes.add(BlastingRecipe(
                unlocalizedName = "blasting_${material.unlocalizedName(Part.INGOT)}_from_${material.unlocalizedName(Part.ORE_DEEPSLATE)}",
                ingredient = ore,
                result = ingot,
                group = material.unlocalizedName(Part.INGOT),
                category = "blocks"
            ))
        }

        // Raw item to ingot (smelting/blasting)
        if (material.hasPart(Part.RAW_ITEM) && material.hasPart(Part.INGOT)) {
            val raw = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.RAW_ITEM)}"
            val ingot = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.INGOT)}"

            recipes.add(SmeltingRecipe(
                unlocalizedName = "smelting_${material.unlocalizedName(Part.INGOT)}_from_${material.unlocalizedName(Part.RAW_ITEM)}",
                ingredient = raw,
                result = ingot,
                group = material.unlocalizedName(Part.INGOT),
                category = "misc"
            ))

            recipes.add(BlastingRecipe(
                unlocalizedName = "blasting_${material.unlocalizedName(Part.INGOT)}_from_${material.unlocalizedName(Part.RAW_ITEM)}",
                ingredient = raw,
                result = ingot,
                group = material.unlocalizedName(Part.INGOT),
                category = "misc"
            ))
        }

        // Raw item to raw block (shaped 3x3)
        if (material.hasPart(Part.RAW_ITEM) && material.hasPart(Part.RAW_BLOCK)) {
            val raw = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.RAW_ITEM)}"
            val rawBlock = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.RAW_BLOCK)}"

            recipes.add(ShapedRecipe(
                unlocalizedName = "${material.unlocalizedName(Part.RAW_BLOCK)}",
                pattern = listOf("###", "###", "###"),
                key = mapOf('#' to raw),
                result = rawBlock,
                count = 1,
                group = material.unlocalizedName(Part.RAW_BLOCK),
                category = "building"
            ))
        }

        // Raw block to raw item (shapeless)
        if (material.hasPart(Part.RAW_BLOCK) && material.hasPart(Part.RAW_ITEM)) {
            val rawBlock = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.RAW_BLOCK)}"
            val raw = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.RAW_ITEM)}"

            recipes.add(ShapelessRecipe(
                unlocalizedName = "${material.unlocalizedName(Part.RAW_ITEM)}_from_${material.unlocalizedName(Part.RAW_BLOCK)}",
                ingredients = listOf(rawBlock),
                result = raw,
                count = 9,
                group = material.unlocalizedName(Part.RAW_ITEM),
                category = "misc"
            ))
        }

        // Nugget to ingot (shaped 3x3)
        if (material.hasPart(Part.NUGGET) && material.hasPart(Part.INGOT)) {
            val nugget = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.NUGGET)}"
            val ingot = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.INGOT)}"

            recipes.add(ShapedRecipe(
                unlocalizedName = "${material.unlocalizedName(Part.INGOT)}_from_nuggets",
                pattern = listOf("###", "###", "###"),
                key = mapOf('#' to nugget),
                result = ingot,
                count = 1,
                group = material.unlocalizedName(Part.INGOT),
                category = "misc"
            ))
        }

        // Ingot to nugget (shapeless)
        if (material.hasPart(Part.INGOT) && material.hasPart(Part.NUGGET)) {
            val ingot = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.INGOT)}"
            val nugget = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.NUGGET)}"

            recipes.add(ShapelessRecipe(
                unlocalizedName = "${material.unlocalizedName(Part.NUGGET)}",
                ingredients = listOf(ingot),
                result = nugget,
                count = 9,
                group = material.unlocalizedName(Part.NUGGET),
                category = "misc"
            ))
        }

        // Ingot to block (shaped 3x3)
        if (material.hasPart(Part.INGOT) && material.hasPart(Part.BLOCK)) {
            val ingot = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.INGOT)}"
            val block = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.BLOCK)}"

            recipes.add(ShapedRecipe(
                unlocalizedName = "${material.unlocalizedName(Part.BLOCK)}",
                pattern = listOf("###", "###", "###"),
                key = mapOf('#' to ingot),
                result = block,
                count = 1,
                group = material.unlocalizedName(Part.BLOCK),
                category = "building"
            ))
        }

        // Block to ingot (shapeless)
        if (material.hasPart(Part.BLOCK) && material.hasPart(Part.INGOT)) {
            val block = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.BLOCK)}"
            val ingot = "${Metalmancy.MOD_ID}:${material.unlocalizedName(Part.INGOT)}"

            recipes.add(ShapelessRecipe(
                unlocalizedName = "${material.unlocalizedName(Part.INGOT)}_from_${material.unlocalizedName(Part.BLOCK)}",
                ingredients = listOf(block),
                result = ingot,
                count = 9,
                group = material.unlocalizedName(Part.INGOT),
                category = "misc"
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
                unlocalizedName = "smelting_${material.unlocalizedName(Part.GEM)}_from_${material.unlocalizedName(Part.ORE)}",
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
                unlocalizedName = "smelting_${material.unlocalizedName(Part.GEM)}_from_${material.unlocalizedName(Part.ORE_DEEPSLATE)}",
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
