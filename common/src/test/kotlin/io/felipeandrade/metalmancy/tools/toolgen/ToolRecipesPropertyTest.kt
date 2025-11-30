package io.felipeandrade.metalmancy.tools.toolgen

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.property.Arb
import io.kotest.property.checkAll
import tools.toolgen.ToolRecipes
import tools.toolgen.ToolType

/**
 * Property-based tests for ToolRecipes.
 * 
 * These tests validate that tool crafting recipes follow the correct patterns
 * and use the correct ingredients.
 */
class ToolRecipesPropertyTest : StringSpec({
    
    /**
     * Feature: basic-tools, Property 9: Tool recipes follow standard crafting patterns
     * 
     * For any generated tool crafting recipe, it should use the correct pattern for 
     * that tool type:
     * - Sword: 2 ingots + 1 stick in vertical pattern
     * - Axe: 3 ingots + 2 sticks in axe pattern (with mirrored variant)
     * - Pickaxe: 3 ingots + 2 sticks in pickaxe pattern
     * - Shovel: 1 ingot + 2 sticks in vertical pattern
     * - Hoe: 2 ingots + 2 sticks in hoe pattern (with mirrored variant)
     * 
     * Validates: Requirements 4.1, 4.2, 4.3, 4.4, 4.5, 4.6
     */
    "Property 9: Tool recipes follow standard crafting patterns".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val recipe = ToolRecipes.generateCraftingRecipe(material, toolType)
            
            // All recipes should be shaped crafting recipes
            recipe.has("type") shouldBe true
            recipe.get("type").asString shouldBe "minecraft:crafting_shaped"
            
            // All recipes should have equipment category
            recipe.has("category") shouldBe true
            recipe.get("category").asString shouldBe "equipment"
            
            // All recipes should have a pattern
            recipe.has("pattern") shouldBe true
            val pattern = recipe.getAsJsonArray("pattern")
            pattern shouldNotBe null
            
            // All recipes should have a key
            recipe.has("key") shouldBe true
            val key = recipe.getAsJsonObject("key")
            key shouldNotBe null
            
            // All recipes should have a result
            recipe.has("result") shouldBe true
            val result = recipe.getAsJsonObject("result")
            result shouldNotBe null
            result.has("id") shouldBe true
            result.has("count") shouldBe true
            result.get("count").asInt shouldBe 1
            
            // Validate pattern based on tool type
            when (toolType) {
                ToolType.SWORD -> {
                    // Sword: vertical pattern with 2 ingots + 1 stick
                    pattern.size() shouldBe 3
                    pattern.get(0).asString shouldBe "#"
                    pattern.get(1).asString shouldBe "#"
                    pattern.get(2).asString shouldBe "S"
                    
                    // Should use 2 ingots (2 '#' symbols)
                    val patternStr = pattern.joinToString("") { it.asString }
                    patternStr.count { it == '#' } shouldBe 2
                    patternStr.count { it == 'S' } shouldBe 1
                }
                ToolType.AXE -> {
                    // Axe: 3 ingots + 2 sticks in axe pattern
                    pattern.size() shouldBe 3
                    pattern.get(0).asString shouldBe "##"
                    pattern.get(1).asString shouldBe "#S"
                    pattern.get(2).asString shouldBe " S"
                    
                    // Should use 3 ingots (3 '#' symbols)
                    val patternStr = pattern.joinToString("") { it.asString }
                    patternStr.count { it == '#' } shouldBe 3
                    patternStr.count { it == 'S' } shouldBe 2
                }
                ToolType.PICKAXE -> {
                    // Pickaxe: 3 ingots + 2 sticks in pickaxe pattern
                    pattern.size() shouldBe 3
                    pattern.get(0).asString shouldBe "###"
                    pattern.get(1).asString shouldBe " S "
                    pattern.get(2).asString shouldBe " S "
                    
                    // Should use 3 ingots (3 '#' symbols)
                    val patternStr = pattern.joinToString("") { it.asString }
                    patternStr.count { it == '#' } shouldBe 3
                    patternStr.count { it == 'S' } shouldBe 2
                }
                ToolType.SHOVEL -> {
                    // Shovel: 1 ingot + 2 sticks in vertical pattern
                    pattern.size() shouldBe 3
                    pattern.get(0).asString shouldBe "#"
                    pattern.get(1).asString shouldBe "S"
                    pattern.get(2).asString shouldBe "S"
                    
                    // Should use 1 ingot (1 '#' symbol)
                    val patternStr = pattern.joinToString("") { it.asString }
                    patternStr.count { it == '#' } shouldBe 1
                    patternStr.count { it == 'S' } shouldBe 2
                }
                ToolType.HOE -> {
                    // Hoe: 2 ingots + 2 sticks in hoe pattern
                    pattern.size() shouldBe 3
                    pattern.get(0).asString shouldBe "##"
                    pattern.get(1).asString shouldBe " S"
                    pattern.get(2).asString shouldBe " S"
                    
                    // Should use 2 ingots (2 '#' symbols)
                    val patternStr = pattern.joinToString("") { it.asString }
                    patternStr.count { it == '#' } shouldBe 2
                    patternStr.count { it == 'S' } shouldBe 2
                }
            }
            
            // Validate ingot count matches tool type
            val patternStr = pattern.joinToString("") { it.asString }
            val ingotCount = patternStr.count { it == '#' }
            ingotCount shouldBe toolType.ingotCount
        }
    }
    
    /**
     * Feature: basic-tools, Property 10: Tool recipes use minecraft:stick as handle
     * 
     * For any generated tool crafting recipe, the handle ingredient should be 
     * "minecraft:stick".
     * 
     * Validates: Requirements 4.7
     */
    "Property 10: Tool recipes use minecraft:stick as handle".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val recipe = ToolRecipes.generateCraftingRecipe(material, toolType)
            
            // Get the key object
            val key = recipe.getAsJsonObject("key")
            
            // Should have 'S' key for stick
            key.has("S") shouldBe true
            key.get("S").asString shouldBe "minecraft:stick"
            
            // Should have '#' key for ingot/gem
            key.has("#") shouldBe true
            val ingredientItem = key.get("#").asString
            
            // Ingredient should be from metalmancy mod
            ingredientItem shouldContain "metalmancy:"
            
            // Ingredient should contain material name
            ingredientItem shouldContain material.name
            
            // Ingredient should be either ingot or gem
            val isIngotOrGem = ingredientItem.contains("_ingot") || ingredientItem.contains("_gem")
            isIngotOrGem shouldBe true
        }
    }
    
    /**
     * Property: Mirrored recipes are generated for axes and hoes
     * 
     * For axes and hoes, mirrored recipe variants should be generated with
     * the same result but mirrored pattern.
     */
    "Mirrored recipes are generated for axes and hoes".config(invocations = 100) {
        checkAll(Arb.actualToolEnabledMaterial()) { material ->
            // Test axe mirrored recipe
            val axeRecipe = ToolRecipes.generateCraftingRecipe(material, ToolType.AXE)
            val axeMirrored = ToolRecipes.generateMirroredRecipe(material, ToolType.AXE)
            
            // Both should produce the same result
            val axeResult = axeRecipe.getAsJsonObject("result").get("id").asString
            val axeMirroredResult = axeMirrored.getAsJsonObject("result").get("id").asString
            axeResult shouldBe axeMirroredResult
            
            // Patterns should be different (mirrored)
            val axePattern = axeRecipe.getAsJsonArray("pattern")
            val axeMirroredPattern = axeMirrored.getAsJsonArray("pattern")
            axePattern.get(0).asString shouldBe "##"
            axePattern.get(1).asString shouldBe "#S"
            axePattern.get(2).asString shouldBe " S"
            axeMirroredPattern.get(0).asString shouldBe "##"
            axeMirroredPattern.get(1).asString shouldBe "S#"
            axeMirroredPattern.get(2).asString shouldBe "S "
            
            // Test hoe mirrored recipe
            val hoeRecipe = ToolRecipes.generateCraftingRecipe(material, ToolType.HOE)
            val hoeMirrored = ToolRecipes.generateMirroredRecipe(material, ToolType.HOE)
            
            // Both should produce the same result
            val hoeResult = hoeRecipe.getAsJsonObject("result").get("id").asString
            val hoeMirroredResult = hoeMirrored.getAsJsonObject("result").get("id").asString
            hoeResult shouldBe hoeMirroredResult
            
            // Patterns should be different (mirrored)
            val hoePattern = hoeRecipe.getAsJsonArray("pattern")
            val hoeMirroredPattern = hoeMirrored.getAsJsonArray("pattern")
            hoePattern.get(0).asString shouldBe "##"
            hoePattern.get(1).asString shouldBe " S"
            hoePattern.get(2).asString shouldBe " S"
            hoeMirroredPattern.get(0).asString shouldBe "##"
            hoeMirroredPattern.get(1).asString shouldBe "S "
            hoeMirroredPattern.get(2).asString shouldBe "S "
        }
    }
    
    /**
     * Property: Recipe result IDs match tool unlocalized names
     * 
     * For any generated recipe, the result ID should match the tool's
     * unlocalized name pattern.
     */
    "Recipe result IDs match tool unlocalized names".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val recipe = ToolRecipes.generateCraftingRecipe(material, toolType)
            
            val result = recipe.getAsJsonObject("result")
            val resultId = result.get("id").asString
            
            // Should contain mod ID
            resultId shouldContain "metalmancy:"
            
            // Should contain material name
            resultId shouldContain material.name
            
            // Should contain tool type suffix
            resultId shouldContain toolType.unlocalizedSuffix
            
            // Should match the expected pattern
            val expectedName = toolType.getUnlocalizedName(material.name)
            resultId shouldBe "metalmancy:$expectedName"
        }
    }
    
    /**
     * Property: All recipes have exactly 2 keys (# for ingot, S for stick)
     * 
     * For any generated recipe, the key object should have exactly 2 entries.
     */
    "All recipes have exactly 2 keys".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val recipe = ToolRecipes.generateCraftingRecipe(material, toolType)
            
            val key = recipe.getAsJsonObject("key")
            key.size() shouldBe 2
            key.has("#") shouldBe true
            key.has("S") shouldBe true
        }
    }
    
    /**
     * Feature: basic-tools, Property 11: Recipe advancements generated for all tool recipes
     * 
     * For any tool crafting recipe, there should be a corresponding recipe advancement
     * JSON file that can be generated.
     * 
     * Validates: Requirements 4.1.1
     */
    "Property 11: Recipe advancements generated for all tool recipes".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            
            // Should be able to generate advancement without error
            val advancement = ToolRecipes.generateRecipeAdvancement(material, toolType)
            
            // Advancement should be a valid JSON object
            advancement shouldNotBe null
            
            // Should have all required top-level fields
            advancement.has("parent") shouldBe true
            advancement.has("criteria") shouldBe true
            advancement.has("requirements") shouldBe true
            advancement.has("rewards") shouldBe true
        }
    }
    
    /**
     * Feature: basic-tools, Property 12: Recipe advancements have correct parent
     * 
     * For any recipe advancement, the parent field should be "minecraft:recipes/root".
     * 
     * Validates: Requirements 4.1.2
     */
    "Property 12: Recipe advancements have correct parent".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val advancement = ToolRecipes.generateRecipeAdvancement(material, toolType)
            
            advancement.has("parent") shouldBe true
            advancement.get("parent").asString shouldBe "minecraft:recipes/root"
        }
    }
    
    /**
     * Feature: basic-tools, Property 13: Recipe advancements include required triggers
     * 
     * For any recipe advancement, it should include inventory_changed triggers for both
     * the material's ingot/gem and minecraft:stick, plus a recipe_unlocked trigger.
     * 
     * Validates: Requirements 4.1.3, 4.1.4, 4.1.5
     */
    "Property 13: Recipe advancements include required triggers".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val advancement = ToolRecipes.generateRecipeAdvancement(material, toolType)
            
            // Get criteria object
            advancement.has("criteria") shouldBe true
            val criteria = advancement.getAsJsonObject("criteria")
            
            // Should have exactly 3 criteria
            criteria.size() shouldBe 3
            
            // Should have recipe_unlocked trigger
            criteria.has("has_the_recipe") shouldBe true
            val hasTheRecipe = criteria.getAsJsonObject("has_the_recipe")
            hasTheRecipe.has("trigger") shouldBe true
            hasTheRecipe.get("trigger").asString shouldBe "minecraft:recipe_unlocked"
            hasTheRecipe.has("conditions") shouldBe true
            val recipeConditions = hasTheRecipe.getAsJsonObject("conditions")
            recipeConditions.has("recipe") shouldBe true
            recipeConditions.get("recipe").asString shouldContain "metalmancy:"
            recipeConditions.get("recipe").asString shouldContain material.name
            recipeConditions.get("recipe").asString shouldContain toolType.unlocalizedSuffix
            
            // Should have inventory_changed trigger for stick
            criteria.has("has_stick") shouldBe true
            val hasStick = criteria.getAsJsonObject("has_stick")
            hasStick.has("trigger") shouldBe true
            hasStick.get("trigger").asString shouldBe "minecraft:inventory_changed"
            hasStick.has("conditions") shouldBe true
            val stickConditions = hasStick.getAsJsonObject("conditions")
            stickConditions.has("items") shouldBe true
            val stickItems = stickConditions.getAsJsonArray("items")
            stickItems.size() shouldBe 1
            val stickItemObj = stickItems.get(0).asJsonObject
            stickItemObj.has("items") shouldBe true
            val stickItemArray = stickItemObj.getAsJsonArray("items")
            stickItemArray.size() shouldBe 1
            stickItemArray.get(0).asString shouldBe "minecraft:stick"
            
            // Should have inventory_changed trigger for ingot/gem
            val hasIngotOrGem = if (material.parts.contains(io.felipeandrade.metalmancy.registry.material.Part.INGOT)) {
                criteria.has("has_${material.name}_ingot") shouldBe true
                criteria.getAsJsonObject("has_${material.name}_ingot")
            } else {
                criteria.has("has_${material.name}_gem") shouldBe true
                criteria.getAsJsonObject("has_${material.name}_gem")
            }
            hasIngotOrGem.has("trigger") shouldBe true
            hasIngotOrGem.get("trigger").asString shouldBe "minecraft:inventory_changed"
            hasIngotOrGem.has("conditions") shouldBe true
            val ingotConditions = hasIngotOrGem.getAsJsonObject("conditions")
            ingotConditions.has("items") shouldBe true
            val ingotItems = ingotConditions.getAsJsonArray("items")
            ingotItems.size() shouldBe 1
            val ingotItemObj = ingotItems.get(0).asJsonObject
            ingotItemObj.has("items") shouldBe true
            val ingotItemArray = ingotItemObj.getAsJsonArray("items")
            ingotItemArray.size() shouldBe 1
            ingotItemArray.get(0).asString shouldContain "metalmancy:"
            ingotItemArray.get(0).asString shouldContain material.name
        }
    }
    
    /**
     * Feature: basic-tools, Property 14: Recipe advancements have correct requirements structure
     * 
     * For any recipe advancement, the requirements array should contain both
     * "has_the_recipe" and material criteria with proper OR/AND logic.
     * 
     * Validates: Requirements 4.1.6
     */
    "Property 14: Recipe advancements have correct requirements structure".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val advancement = ToolRecipes.generateRecipeAdvancement(material, toolType)
            
            // Get requirements array
            advancement.has("requirements") shouldBe true
            val requirements = advancement.getAsJsonArray("requirements")
            
            // Should have exactly 2 requirement groups (OR logic between them)
            requirements.size() shouldBe 2
            
            // First group should be ["has_the_recipe"]
            val firstGroup = requirements.get(0).asJsonArray
            firstGroup.size() shouldBe 1
            firstGroup.get(0).asString shouldBe "has_the_recipe"
            
            // Second group should be [material_criteria, "has_stick"] (AND logic within)
            val secondGroup = requirements.get(1).asJsonArray
            secondGroup.size() shouldBe 2
            
            // One should be the material criteria
            val criteriaNames = setOf(secondGroup.get(0).asString, secondGroup.get(1).asString)
            ("has_stick" in criteriaNames) shouldBe true
            
            // The other should be the ingot/gem criteria
            val expectedMaterialCriteria = if (material.parts.contains(io.felipeandrade.metalmancy.registry.material.Part.INGOT)) {
                "has_${material.name}_ingot"
            } else {
                "has_${material.name}_gem"
            }
            (expectedMaterialCriteria in criteriaNames) shouldBe true
        }
    }
    
    /**
     * Feature: basic-tools, Property 15: Recipe advancements unlock corresponding recipes
     * 
     * For any recipe advancement, the rewards should unlock the corresponding recipe.
     * 
     * Validates: Requirements 4.1.7
     */
    "Property 15: Recipe advancements unlock corresponding recipes".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val advancement = ToolRecipes.generateRecipeAdvancement(material, toolType)
            
            // Get rewards object
            advancement.has("rewards") shouldBe true
            val rewards = advancement.getAsJsonObject("rewards")
            
            // Should have recipes array
            rewards.has("recipes") shouldBe true
            val recipes = rewards.getAsJsonArray("recipes")
            
            // Should have exactly 1 recipe
            recipes.size() shouldBe 1
            
            // Recipe ID should match the tool's recipe ID
            val recipeId = recipes.get(0).asString
            recipeId shouldContain "metalmancy:"
            recipeId shouldContain material.name
            recipeId shouldContain toolType.unlocalizedSuffix
            
            // Should match the expected recipe ID
            val expectedRecipeId = "metalmancy:${toolType.getUnlocalizedName(material.name)}"
            recipeId shouldBe expectedRecipeId
        }
    }
    
    /**
     * Feature: basic-tools, Property 16: Tool smelting recipes produce nuggets
     * 
     * For any tool smelting recipe, the input should be the tool item and the
     * output should be the material's nuggets.
     * 
     * Validates: Requirements 4.2.1
     */
    "Property 16: Tool smelting recipes produce nuggets".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val recipe = ToolRecipes.generateSmeltingRecipe(material, toolType)
            
            // Should be a smelting recipe
            recipe.has("type") shouldBe true
            recipe.get("type").asString shouldBe "minecraft:smelting"
            
            // Should have misc category
            recipe.has("category") shouldBe true
            recipe.get("category").asString shouldBe "misc"
            
            // Should have ingredient (the tool)
            recipe.has("ingredient") shouldBe true
            val ingredient = recipe.get("ingredient").asString
            ingredient shouldContain "metalmancy:"
            ingredient shouldContain material.name
            ingredient shouldContain toolType.unlocalizedSuffix
            
            // Should have result (nuggets)
            recipe.has("result") shouldBe true
            val result = recipe.getAsJsonObject("result")
            result.has("id") shouldBe true
            result.has("count") shouldBe true
            
            val resultId = result.get("id").asString
            resultId shouldContain "metalmancy:"
            resultId shouldContain material.name
            
            // Result should be nugget or gem_shard
            val isNuggetOrShard = resultId.contains("_nugget") || resultId.contains("_gem_shard")
            isNuggetOrShard shouldBe true
            
            // Should have cooking time
            recipe.has("cookingtime") shouldBe true
            recipe.get("cookingtime").asInt shouldBe 200
            
            // Should have experience
            recipe.has("experience") shouldBe true
        }
    }
    
    /**
     * Feature: basic-tools, Property 17: Tool blasting recipes are faster than smelting
     * 
     * For any tool, the blasting recipe should have the same input/output as smelting
     * but with half the cooking time (100 ticks vs 200 ticks).
     * 
     * Validates: Requirements 4.2.2
     */
    "Property 17: Tool blasting recipes are faster than smelting".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val smeltingRecipe = ToolRecipes.generateSmeltingRecipe(material, toolType)
            val blastingRecipe = ToolRecipes.generateBlastingRecipe(material, toolType)
            
            // Blasting should be a blasting recipe
            blastingRecipe.has("type") shouldBe true
            blastingRecipe.get("type").asString shouldBe "minecraft:blasting"
            
            // Should have same ingredient
            smeltingRecipe.get("ingredient").asString shouldBe blastingRecipe.get("ingredient").asString
            
            // Should have same result
            val smeltingResult = smeltingRecipe.getAsJsonObject("result")
            val blastingResult = blastingRecipe.getAsJsonObject("result")
            smeltingResult.get("id").asString shouldBe blastingResult.get("id").asString
            smeltingResult.get("count").asInt shouldBe blastingResult.get("count").asInt
            
            // Should have same experience
            smeltingRecipe.get("experience").asFloat shouldBe blastingRecipe.get("experience").asFloat
            
            // Blasting should be faster (100 ticks vs 200 ticks)
            smeltingRecipe.get("cookingtime").asInt shouldBe 200
            blastingRecipe.get("cookingtime").asInt shouldBe 100
            blastingRecipe.get("cookingtime").asInt shouldBe (smeltingRecipe.get("cookingtime").asInt / 2)
        }
    }
    
    /**
     * Feature: basic-tools, Property 18: Recycling nugget yield matches ingot cost
     * 
     * For any tool recycling recipe, the nugget output count should equal the
     * ingot count used in the crafting recipe.
     * 
     * Validates: Requirements 4.2.3, 4.2.4, 4.2.5, 4.2.6, 4.2.7
     */
    "Property 18: Recycling nugget yield matches ingot cost".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val smeltingRecipe = ToolRecipes.generateSmeltingRecipe(material, toolType)
            val blastingRecipe = ToolRecipes.generateBlastingRecipe(material, toolType)
            
            // Get nugget count from result
            val smeltingResult = smeltingRecipe.getAsJsonObject("result")
            val blastingResult = blastingRecipe.getAsJsonObject("result")
            val smeltingNuggetCount = smeltingResult.get("count").asInt
            val blastingNuggetCount = blastingResult.get("count").asInt
            
            // Both should have same count
            smeltingNuggetCount shouldBe blastingNuggetCount
            
            // Nugget count should match tool's ingot count
            smeltingNuggetCount shouldBe toolType.ingotCount
            blastingNuggetCount shouldBe toolType.ingotCount
            
            // Verify specific tool types have correct counts
            when (toolType) {
                ToolType.SWORD -> smeltingNuggetCount shouldBe 2
                ToolType.AXE -> smeltingNuggetCount shouldBe 3
                ToolType.PICKAXE -> smeltingNuggetCount shouldBe 3
                ToolType.SHOVEL -> smeltingNuggetCount shouldBe 1
                ToolType.HOE -> smeltingNuggetCount shouldBe 2
            }
        }
    }
    
    /**
     * Feature: basic-tools, Property 19: Recycling experience scales with nugget yield
     * 
     * For any tool recycling recipe, the experience value should be 0.1 multiplied
     * by the nugget count.
     * 
     * Validates: Requirements 4.2.8
     */
    "Property 19: Recycling experience scales with nugget yield".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val smeltingRecipe = ToolRecipes.generateSmeltingRecipe(material, toolType)
            val blastingRecipe = ToolRecipes.generateBlastingRecipe(material, toolType)
            
            // Get nugget count and experience
            val smeltingResult = smeltingRecipe.getAsJsonObject("result")
            val blastingResult = blastingRecipe.getAsJsonObject("result")
            val nuggetCount = smeltingResult.get("count").asInt
            
            val smeltingExperience = smeltingRecipe.get("experience").asFloat
            val blastingExperience = blastingRecipe.get("experience").asFloat
            
            // Both should have same experience
            smeltingExperience shouldBe blastingExperience
            
            // Experience should be 0.1 per nugget
            val expectedExperience = nuggetCount * 0.1f
            smeltingExperience shouldBe expectedExperience
            blastingExperience shouldBe expectedExperience
            
            // Verify specific tool types have correct experience
            when (toolType) {
                ToolType.SWORD -> smeltingExperience shouldBe 0.2f
                ToolType.AXE -> smeltingExperience shouldBe 0.3f
                ToolType.PICKAXE -> smeltingExperience shouldBe 0.3f
                ToolType.SHOVEL -> smeltingExperience shouldBe 0.1f
                ToolType.HOE -> smeltingExperience shouldBe 0.2f
            }
        }
    }
})

