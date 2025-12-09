package tools.toolgen

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import io.felipeandrade.metalmancy.items.ToolType
import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Part

/**
 * Generates crafting recipes for tools.
 * 
 * This object provides functions to generate shaped crafting recipes for all five tool types:
 * - Sword: 2 ingots + 1 stick in vertical pattern
 * - Axe: 3 ingots + 2 sticks in axe pattern (with mirrored variant)
 * - Pickaxe: 3 ingots + 2 sticks in pickaxe pattern
 * - Shovel: 1 ingot + 2 sticks in vertical pattern
 * - Hoe: 2 ingots + 2 sticks in hoe pattern (with mirrored variant)
 * 
 * All recipes use minecraft:stick as the handle ingredient.
 * 
 * Also provides functions to generate recipe advancements that unlock recipes
 * in the player's recipe book when they obtain the required materials.
 */
object ToolRecipes {
    
    /**
     * Generates a crafting recipe for a tool.
     * 
     * @param material The material the tool is made from
     * @param toolType The type of tool to generate a recipe for
     * @return JsonObject representing the crafting recipe
     */
    fun generateCraftingRecipe(material: Material, toolType: ToolType): JsonObject {
        val unlocalizedName = toolType.getUnlocalizedName(material.name)
        val ingredientItem = getIngredientItem(material)
        
        return when (toolType) {
            ToolType.SWORD -> generateSwordRecipe(unlocalizedName, ingredientItem)
            ToolType.AXE -> generateAxeRecipe(unlocalizedName, ingredientItem)
            ToolType.PICKAXE -> generatePickaxeRecipe(unlocalizedName, ingredientItem)
            ToolType.SHOVEL -> generateShovelRecipe(unlocalizedName, ingredientItem)
            ToolType.HOE -> generateHoeRecipe(unlocalizedName, ingredientItem)
        }
    }
    
    /**
     * Generates a mirrored variant of a crafting recipe (for axes and hoes).
     * 
     * @param material The material the tool is made from
     * @param toolType The type of tool to generate a recipe for (must be AXE or HOE)
     * @return JsonObject representing the mirrored crafting recipe
     * @throws IllegalArgumentException if toolType is not AXE or HOE
     */
    fun generateMirroredRecipe(material: Material, toolType: ToolType): JsonObject {
        require(toolType == ToolType.AXE || toolType == ToolType.HOE) {
            "Only axes and hoes have mirrored recipes"
        }
        
        val unlocalizedName = "${toolType.getUnlocalizedName(material.name)}_mirrored"
        val ingredientItem = getIngredientItem(material)
        
        return when (toolType) {
            ToolType.AXE -> generateAxeRecipeMirrored(unlocalizedName, ingredientItem, material)
            ToolType.HOE -> generateHoeRecipeMirrored(unlocalizedName, ingredientItem, material)
            else -> throw IllegalArgumentException("Unsupported tool type for mirrored recipe")
        }
    }
    
    /**
     * Gets the ingredient item ID for a material (ingot or gem).
     */
    private fun getIngredientItem(material: Material): String {
        return if (material.parts.contains(Part.INGOT)) {
            "$MOD_ID:${material.name}_ingot"
        } else if (material.parts.contains(Part.GEM)) {
            "$MOD_ID:${material.name}_gem"
        } else {
            throw IllegalArgumentException("Material ${material.name} has no INGOT or GEM part")
        }
    }
    
    /**
     * Generates a sword recipe: 2 ingots + 1 stick in vertical pattern
     * Pattern:
     *   #
     *   #
     *   S
     */
    private fun generateSwordRecipe(unlocalizedName: String, ingredientItem: String): JsonObject {
        val json = JsonObject()
        json.addProperty("type", "minecraft:crafting_shaped")
        json.addProperty("category", "equipment")
        
        val pattern = JsonArray()
        pattern.add("#")
        pattern.add("#")
        pattern.add("S")
        json.add("pattern", pattern)
        
        val key = JsonObject()
        key.addProperty("#", ingredientItem)
        key.addProperty("S", "minecraft:stick")
        json.add("key", key)
        
        val result = JsonObject()
        result.addProperty("id", "$MOD_ID:$unlocalizedName")
        result.addProperty("count", 1)
        json.add("result", result)
        
        return json
    }
    
    /**
     * Generates an axe recipe: 3 ingots + 2 sticks
     * Pattern:
     *   ##
     *   #S
     *    S
     */
    private fun generateAxeRecipe(unlocalizedName: String, ingredientItem: String): JsonObject {
        val json = JsonObject()
        json.addProperty("type", "minecraft:crafting_shaped")
        json.addProperty("category", "equipment")
        
        val pattern = JsonArray()
        pattern.add("##")
        pattern.add("#S")
        pattern.add(" S")
        json.add("pattern", pattern)
        
        val key = JsonObject()
        key.addProperty("#", ingredientItem)
        key.addProperty("S", "minecraft:stick")
        json.add("key", key)
        
        val result = JsonObject()
        result.addProperty("id", "$MOD_ID:$unlocalizedName")
        result.addProperty("count", 1)
        json.add("result", result)
        
        return json
    }
    
    /**
     * Generates a mirrored axe recipe: 3 ingots + 2 sticks
     * Pattern:
     *   ##
     *   S#
     *   S
     */
    private fun generateAxeRecipeMirrored(unlocalizedName: String, ingredientItem: String, material: Material): JsonObject {
        val json = JsonObject()
        json.addProperty("type", "minecraft:crafting_shaped")
        json.addProperty("category", "equipment")
        json.addProperty("show_notification", false)
        
        val pattern = JsonArray()
        pattern.add("##")
        pattern.add("S#")
        pattern.add("S ")
        json.add("pattern", pattern)
        
        val key = JsonObject()
        key.addProperty("#", ingredientItem)
        key.addProperty("S", "minecraft:stick")
        json.add("key", key)
        
        val result = JsonObject()
        result.addProperty("id", "$MOD_ID:${ToolType.AXE.getUnlocalizedName(material.name)}")
        result.addProperty("count", 1)
        json.add("result", result)
        
        return json
    }
    
    /**
     * Generates a pickaxe recipe: 3 ingots + 2 sticks
     * Pattern:
     *   ###
     *    S
     *    S
     */
    private fun generatePickaxeRecipe(unlocalizedName: String, ingredientItem: String): JsonObject {
        val json = JsonObject()
        json.addProperty("type", "minecraft:crafting_shaped")
        json.addProperty("category", "equipment")
        
        val pattern = JsonArray()
        pattern.add("###")
        pattern.add(" S ")
        pattern.add(" S ")
        json.add("pattern", pattern)
        
        val key = JsonObject()
        key.addProperty("#", ingredientItem)
        key.addProperty("S", "minecraft:stick")
        json.add("key", key)
        
        val result = JsonObject()
        result.addProperty("id", "$MOD_ID:$unlocalizedName")
        result.addProperty("count", 1)
        json.add("result", result)
        
        return json
    }
    
    /**
     * Generates a shovel recipe: 1 ingot + 2 sticks in vertical pattern
     * Pattern:
     *   #
     *   S
     *   S
     */
    private fun generateShovelRecipe(unlocalizedName: String, ingredientItem: String): JsonObject {
        val json = JsonObject()
        json.addProperty("type", "minecraft:crafting_shaped")
        json.addProperty("category", "equipment")
        
        val pattern = JsonArray()
        pattern.add("#")
        pattern.add("S")
        pattern.add("S")
        json.add("pattern", pattern)
        
        val key = JsonObject()
        key.addProperty("#", ingredientItem)
        key.addProperty("S", "minecraft:stick")
        json.add("key", key)
        
        val result = JsonObject()
        result.addProperty("id", "$MOD_ID:$unlocalizedName")
        result.addProperty("count", 1)
        json.add("result", result)
        
        return json
    }
    
    /**
     * Generates a hoe recipe: 2 ingots + 2 sticks
     * Pattern:
     *   ##
     *    S
     *    S
     */
    private fun generateHoeRecipe(unlocalizedName: String, ingredientItem: String): JsonObject {
        val json = JsonObject()
        json.addProperty("type", "minecraft:crafting_shaped")
        json.addProperty("category", "equipment")
        
        val pattern = JsonArray()
        pattern.add("##")
        pattern.add(" S")
        pattern.add(" S")
        json.add("pattern", pattern)
        
        val key = JsonObject()
        key.addProperty("#", ingredientItem)
        key.addProperty("S", "minecraft:stick")
        json.add("key", key)
        
        val result = JsonObject()
        result.addProperty("id", "$MOD_ID:$unlocalizedName")
        result.addProperty("count", 1)
        json.add("result", result)
        
        return json
    }
    
    /**
     * Generates a mirrored hoe recipe: 2 ingots + 2 sticks
     * Pattern:
     *   ##
     *   S
     *   S
     */
    private fun generateHoeRecipeMirrored(unlocalizedName: String, ingredientItem: String, material: Material): JsonObject {
        val json = JsonObject()
        json.addProperty("type", "minecraft:crafting_shaped")
        json.addProperty("category", "equipment")
        json.addProperty("show_notification", false)
        
        val pattern = JsonArray()
        pattern.add("##")
        pattern.add("S ")
        pattern.add("S ")
        json.add("pattern", pattern)
        
        val key = JsonObject()
        key.addProperty("#", ingredientItem)
        key.addProperty("S", "minecraft:stick")
        json.add("key", key)
        
        val result = JsonObject()
        result.addProperty("id", "$MOD_ID:${ToolType.HOE.getUnlocalizedName(material.name)}")
        result.addProperty("count", 1)
        json.add("result", result)
        
        return json
    }
    
    /**
     * Generates a smelting recipe for recycling a tool back into nuggets.
     * 
     * Smelting recipes convert tools into nuggets based on their ingot cost:
     * - Sword: 2 nuggets (2 ingots)
     * - Axe: 3 nuggets (3 ingots)
     * - Pickaxe: 3 nuggets (3 ingots)
     * - Shovel: 1 nugget (1 ingot)
     * - Hoe: 2 nuggets (2 ingots)
     * 
     * Cooking time: 200 ticks
     * Experience: 0.1 per nugget
     * 
     * @param material The material the tool is made from
     * @param toolType The type of tool to recycle
     * @return JsonObject representing the smelting recipe
     */
    fun generateSmeltingRecipe(material: Material, toolType: ToolType): JsonObject {
        val unlocalizedName = toolType.getUnlocalizedName(material.name)
        val nuggetItem = getNuggetItem(material)
        val nuggetCount = toolType.ingotCount
        val experience = nuggetCount * 0.1f
        
        val json = JsonObject()
        json.addProperty("type", "minecraft:smelting")
        json.addProperty("category", "misc")
        json.addProperty("ingredient", "$MOD_ID:$unlocalizedName")
        
        val result = JsonObject()
        result.addProperty("id", nuggetItem)
        result.addProperty("count", nuggetCount)
        json.add("result", result)
        
        json.addProperty("experience", experience)
        json.addProperty("cookingtime", 200)
        
        return json
    }
    
    /**
     * Generates a blasting recipe for recycling a tool back into nuggets.
     * 
     * Blasting recipes are faster than smelting (100 ticks vs 200 ticks)
     * but produce the same output.
     * 
     * @param material The material the tool is made from
     * @param toolType The type of tool to recycle
     * @return JsonObject representing the blasting recipe
     */
    fun generateBlastingRecipe(material: Material, toolType: ToolType): JsonObject {
        val unlocalizedName = toolType.getUnlocalizedName(material.name)
        val nuggetItem = getNuggetItem(material)
        val nuggetCount = toolType.ingotCount
        val experience = nuggetCount * 0.1f
        
        val json = JsonObject()
        json.addProperty("type", "minecraft:blasting")
        json.addProperty("category", "misc")
        json.addProperty("ingredient", "$MOD_ID:$unlocalizedName")
        
        val result = JsonObject()
        result.addProperty("id", nuggetItem)
        result.addProperty("count", nuggetCount)
        json.add("result", result)
        
        json.addProperty("experience", experience)
        json.addProperty("cookingtime", 100)
        
        return json
    }
    
    /**
     * Gets the nugget item ID for a material.
     */
    private fun getNuggetItem(material: Material): String {
        return if (material.parts.contains(Part.INGOT)) {
            "$MOD_ID:${material.name}_nugget"
        } else if (material.parts.contains(Part.GEM)) {
            "$MOD_ID:${material.name}_gem_shard"
        } else {
            throw IllegalArgumentException("Material ${material.name} has no INGOT or GEM part")
        }
    }
    
    /**
     * Generates a recipe advancement for a tool crafting recipe.
     * 
     * Recipe advancements unlock recipes in the player's recipe book when they
     * obtain the required materials (ingot/gem and stick).
     * 
     * @param material The material the tool is made from
     * @param toolType The type of tool to generate an advancement for
     * @return JsonObject representing the recipe advancement
     */
    fun generateRecipeAdvancement(material: Material, toolType: ToolType): JsonObject {
        val unlocalizedName = toolType.getUnlocalizedName(material.name)
        val recipeId = "$MOD_ID:$unlocalizedName"
        val ingredientItem = getIngredientItem(material)
        
        val json = JsonObject()
        
        // Set parent to minecraft:recipes/root
        json.addProperty("parent", "minecraft:recipes/root")
        
        // Create criteria object
        val criteria = JsonObject()
        
        // Add recipe_unlocked trigger
        val hasTheRecipe = JsonObject()
        hasTheRecipe.addProperty("trigger", "minecraft:recipe_unlocked")
        val recipeConditions = JsonObject()
        recipeConditions.addProperty("recipe", recipeId)
        hasTheRecipe.add("conditions", recipeConditions)
        criteria.add("has_the_recipe", hasTheRecipe)
        
        // Add inventory_changed trigger for ingot/gem
        val hasIngot = JsonObject()
        hasIngot.addProperty("trigger", "minecraft:inventory_changed")
        val ingotConditions = JsonObject()
        val ingotItems = JsonArray()
        val ingotItemObj = JsonObject()
        val ingotItemArray = JsonArray()
        ingotItemArray.add(ingredientItem)
        ingotItemObj.add("items", ingotItemArray)
        ingotItems.add(ingotItemObj)
        ingotConditions.add("items", ingotItems)
        hasIngot.add("conditions", ingotConditions)
        
        // Create criteria name based on material part type
        val ingredientCriteriaName = if (material.parts.contains(Part.INGOT)) {
            "has_${material.name}_ingot"
        } else {
            "has_${material.name}_gem"
        }
        criteria.add(ingredientCriteriaName, hasIngot)
        
        // Add inventory_changed trigger for stick
        val hasStick = JsonObject()
        hasStick.addProperty("trigger", "minecraft:inventory_changed")
        val stickConditions = JsonObject()
        val stickItems = JsonArray()
        val stickItemObj = JsonObject()
        val stickItemArray = JsonArray()
        stickItemArray.add("minecraft:stick")
        stickItemObj.add("items", stickItemArray)
        stickItems.add(stickItemObj)
        stickConditions.add("items", stickItems)
        hasStick.add("conditions", stickConditions)
        criteria.add("has_stick", hasStick)
        
        json.add("criteria", criteria)
        
        // Create requirements array with OR/AND logic
        // Requirements: [["has_the_recipe"], ["has_ingot", "has_stick"]]
        // This means: has_the_recipe OR (has_ingot AND has_stick)
        val requirements = JsonArray()
        
        val recipeRequirement = JsonArray()
        recipeRequirement.add("has_the_recipe")
        requirements.add(recipeRequirement)
        
        val materialRequirement = JsonArray()
        materialRequirement.add(ingredientCriteriaName)
        materialRequirement.add("has_stick")
        requirements.add(materialRequirement)
        
        json.add("requirements", requirements)
        
        // Set rewards to unlock the recipe
        val rewards = JsonObject()
        val recipes = JsonArray()
        recipes.add(recipeId)
        rewards.add("recipes", recipes)
        json.add("rewards", rewards)
        
        return json
    }
}
