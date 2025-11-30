package tools.recipegen

import com.google.gson.GsonBuilder
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import java.io.File
import java.io.FileWriter

fun main(args: Array<String>) {
    var outDir = "build/generated/data"
    var i = 0
    while (i < args.size) {
        when (args[i]) {
            "--out" -> {
                outDir = args.getOrNull(i + 1) ?: outDir
                i++
            }
        }
        i++
    }

    // Phase 1: Cleanup old recipes
    deleteOldRecipes()

    // Phase 2: Generation
    val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    // Path follows datapack structure defined in common/src/tools/datapack-structure-1.21.10.md
    val recipesDir = dir(outDir, MOD_ID, "recipe")

    for (recipe in RecipeEntries.recipes) {
        val recipeJson = recipe.generateRecipe()
        gson.writeJson(recipesDir, "${recipe.unlocalizedName}.json", recipeJson)
    }

    // Phase 3: Copy to Resources
    val resourcesRecipeDir = File("src/main/resources/data/$MOD_ID/recipe")
    val copiedCount = copyToResources(recipesDir, resourcesRecipeDir)
    
    // Phase 4: Apply Overrides
    val hardcodedRecipeDir = File("src/tools/recipegen/recipe")
    val overrideCount = if (hardcodedRecipeDir.exists()) {
        copyHardcodedRecipes(hardcodedRecipeDir, resourcesRecipeDir)
    } else {
        0
    }
    
    // Final summary
    println("[RecipeGen] Complete: ${RecipeEntries.recipes.size} generated, $copiedCount copied, $overrideCount overridden")
}

private fun dir(base: String, vararg parts: String): File {
    var f = File(base)
    for (p in parts) f = File(f, p)
    if (!f.exists()) f.mkdirs()
    return f
}

private fun com.google.gson.Gson.writeJson(dir: File, fileName: String, json: com.google.gson.JsonObject) {
    // Ensure flat structure - filename must not contain path separators
    require(!fileName.contains('/') && !fileName.contains('\\')) {
        "Recipe filename must not contain path separators (found: $fileName). All recipes must be in a flat structure."
    }
    
    val out = File(dir, fileName)
    // No need to create parent directories - we're writing directly to dir
    FileWriter(out).use { w -> toJson(json, w) }
    println("[RecipeGen] wrote ${out.path}")
}

/**
 * Deletes old recipe files from both the old plural location and the new singular location.
 * Handles missing directories gracefully and logs all deletion operations.
 * 
 * Requirements: 2.1, 2.2, 2.3, 2.4, 5.1
 */
private fun deleteOldRecipes(): Int {
    val resourcesBase = File("src/main/resources/data/$MOD_ID")
    val oldPluralDir = File(resourcesBase, "recipes")
    val newSingularDir = File(resourcesBase, "recipe")
    
    var totalDeleted = 0
    
    // Delete from old plural location (recipes/)
    if (oldPluralDir.exists() && oldPluralDir.isDirectory) {
        val deleted = deleteRecipesFromDirectory(oldPluralDir, "old plural location (recipes/)")
        totalDeleted += deleted
    }
    
    // Delete from new singular location (recipe/)
    if (newSingularDir.exists() && newSingularDir.isDirectory) {
        val deleted = deleteRecipesFromDirectory(newSingularDir, "new singular location (recipe/)")
        totalDeleted += deleted
    }
    
    println("[RecipeGen] Cleanup: $totalDeleted files deleted")
    return totalDeleted
}

/**
 * Deletes all JSON files from the specified directory.
 * Logs each file deleted and handles deletion failures gracefully.
 */
private fun deleteRecipesFromDirectory(dir: File, locationDescription: String): Int {
    var count = 0
    val files = dir.listFiles { file -> file.isFile && file.extension == "json" } ?: emptyArray()
    
    for (file in files) {
        try {
            if (file.delete()) {
                println("[RecipeGen] Deleted: ${file.path} ($locationDescription)")
                count++
            } else {
                println("[RecipeGen] WARNING: Failed to delete ${file.path} ($locationDescription)")
            }
        } catch (e: Exception) {
            println("[RecipeGen] WARNING: Exception deleting ${file.path}: ${e.message}")
        }
    }
    
    return count
}

/**
 * Copies all files from source directory to target directory in a flat structure.
 * Creates target directory if it doesn't exist.
 * Fails if source contains subdirectories (datapack structure requires flat recipe folder).
 * Logs each file copied.
 * 
 * Requirements: 3.1, 3.3, 3.4, 5.3
 * 
 * @param sourceDir The source directory containing recipe files
 * @param targetDir The target directory where files should be copied
 * @return The number of files successfully copied
 */
private fun copyToResources(sourceDir: File, targetDir: File): Int {
    // Verify source directory exists
    if (!sourceDir.exists() || !sourceDir.isDirectory) {
        println("[RecipeGen] WARNING: Source directory does not exist: ${sourceDir.path}")
        return 0
    }
    
    // Create target directory if it doesn't exist
    if (!targetDir.exists()) {
        targetDir.mkdirs()
        println("[RecipeGen] Created target directory: ${targetDir.path}")
    }
    
    // Get all files from source directory
    val files = sourceDir.listFiles() ?: emptyArray()
    
    // Check for subdirectories (not allowed in flat structure)
    val subdirectories = files.filter { it.isDirectory }
    if (subdirectories.isNotEmpty()) {
        val subdirNames = subdirectories.joinToString(", ") { it.name }
        error("Source directory contains subdirectories: $subdirNames. Datapack structure requires flat recipe/ folder with no subdirectories.")
    }
    
    // Copy all files
    var copiedCount = 0
    val jsonFiles = files.filter { it.isFile && it.extension == "json" }
    
    for (file in jsonFiles) {
        try {
            val targetFile = File(targetDir, file.name)
            file.copyTo(targetFile, overwrite = true)
            println("[RecipeGen] Copied: ${file.path} → ${targetFile.path}")
            copiedCount++
        } catch (e: Exception) {
            println("[RecipeGen] WARNING: Failed to copy ${file.path}: ${e.message}")
        }
    }
    
    println("[RecipeGen] Copy complete: $copiedCount files copied")
    return copiedCount
}

/**
 * Copies hardcoded recipe files from source directory to target directory, overriding any existing files.
 * This allows manual customization of specific recipes that need special handling.
 * Creates target directory if it doesn't exist.
 * Handles missing source directory gracefully (skips if doesn't exist).
 * Fails if source contains subdirectories (datapack structure requires flat recipe folder).
 * Logs warnings when hardcoded recipes override generated ones.
 * 
 * Requirements: 4.1, 4.2, 4.3, 4.5, 5.4
 * 
 * @param sourceDir The source directory containing hardcoded recipe files (common/src/tools/recipegen/recipe/)
 * @param targetDir The target directory where files should be copied (resources)
 * @return The number of files successfully overridden
 */
private fun copyHardcodedRecipes(sourceDir: File, targetDir: File): Int {
    // Handle missing source directory gracefully - skip if doesn't exist
    if (!sourceDir.exists() || !sourceDir.isDirectory) {
        println("[RecipeGen] No hardcoded recipes directory found at: ${sourceDir.path} (skipping)")
        return 0
    }
    
    // Create target directory if it doesn't exist
    if (!targetDir.exists()) {
        targetDir.mkdirs()
        println("[RecipeGen] Created target directory: ${targetDir.path}")
    }
    
    // Get all files from source directory
    val files = sourceDir.listFiles() ?: emptyArray()
    
    // Check for subdirectories (not allowed in flat structure)
    val subdirectories = files.filter { it.isDirectory }
    if (subdirectories.isNotEmpty()) {
        val subdirNames = subdirectories.joinToString(", ") { it.name }
        error("Hardcoded recipe directory contains subdirectories: $subdirNames. Datapack structure requires flat recipe/ folder with no subdirectories.")
    }
    
    // Copy all files, overwriting existing ones
    var overriddenCount = 0
    val jsonFiles = files.filter { it.isFile && it.extension == "json" }
    
    for (file in jsonFiles) {
        try {
            val targetFile = File(targetDir, file.name)
            val isOverride = targetFile.exists()
            
            file.copyTo(targetFile, overwrite = true)
            
            if (isOverride) {
                println("[RecipeGen] WARNING: Hardcoded recipe overrides generated recipe: ${file.name} (${file.path} → ${targetFile.path})")
            } else {
                println("[RecipeGen] Copied hardcoded recipe: ${file.path} → ${targetFile.path}")
            }
            
            overriddenCount++
        } catch (e: Exception) {
            println("[RecipeGen] WARNING: Failed to copy hardcoded recipe ${file.path}: ${e.message}")
        }
    }
    
    println("[RecipeGen] Hardcoded recipes: $overriddenCount files copied/overridden")
    return overriddenCount
}
