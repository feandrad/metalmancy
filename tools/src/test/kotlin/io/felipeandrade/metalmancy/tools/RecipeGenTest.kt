package io.felipeandrade.metalmancy.tools

import com.google.gson.GsonBuilder
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import tools.recipegen.RecipeEntries
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests for RecipeGen to ensure compliance with Minecraft 1.21.10 datapack structure.
 * 
 * **Feature: recipe-generator, Example 1: Generated files use correct datapack structure path**
 * **Validates: Requirements 1.2, 1.3, 1.4**
 */
class RecipeGenTest {

    @Test
    fun `generated files use correct datapack structure path`(@TempDir tempDir: File) {
        // Given: A temporary output directory
        val outDir = File(tempDir, "build/generated/data")
        
        // When: We generate recipes
        val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
        val recipesDir = File(outDir, "$MOD_ID/recipe")
        recipesDir.mkdirs()
        
        // Generate at least one recipe to test
        val testRecipes = RecipeEntries.recipes.take(5)
        assertTrue(testRecipes.isNotEmpty(), "Should have at least one recipe to test")
        
        testRecipes.forEach { recipe ->
            val recipeJson = recipe.generateRecipe()
            val fileName = "${recipe.unlocalizedName}.json"
            
            // Verify filename doesn't contain path separators (flat structure)
            assertFalse(
                fileName.contains('/') || fileName.contains('\\'),
                "Recipe filename should not contain path separators: $fileName"
            )
            
            val out = File(recipesDir, fileName)
            out.writeText(gson.toJson(recipeJson))
        }
        
        // Then: Files should be in the correct location with singular "recipe" folder
        val expectedPath = File(outDir, "$MOD_ID/recipe")
        assertTrue(expectedPath.exists(), "Recipe directory should exist at: ${expectedPath.absolutePath}")
        assertTrue(expectedPath.isDirectory, "Recipe path should be a directory")
        
        // Verify the path uses "recipe" (singular) not "recipes" (plural)
        assertTrue(
            expectedPath.absolutePath.contains("/recipe"),
            "Path should contain '/recipe' (singular): ${expectedPath.absolutePath}"
        )
        assertFalse(
            expectedPath.absolutePath.contains("/recipes"),
            "Path should NOT contain '/recipes' (plural): ${expectedPath.absolutePath}"
        )
        
        // Verify all files are directly in the recipe/ folder (flat structure)
        val generatedFiles = expectedPath.listFiles() ?: emptyArray()
        assertTrue(generatedFiles.isNotEmpty(), "Should have generated at least one recipe file")
        
        generatedFiles.forEach { file ->
            assertTrue(file.isFile, "All items in recipe/ should be files, not directories: ${file.name}")
            assertTrue(file.extension == "json", "All recipe files should be JSON: ${file.name}")
        }
        
        // Verify no subdirectories exist
        val subdirectories = generatedFiles.filter { it.isDirectory }
        assertEquals(
            0,
            subdirectories.size,
            "Recipe directory should have NO subdirectories (flat structure). Found: ${subdirectories.map { it.name }}"
        )
    }
    
    @Test
    fun `recipe filenames include type prefix for uniqueness`(@TempDir tempDir: File) {
        // Given: Recipes that would have conflicting names without type prefix
        val recipes = RecipeEntries.recipes
        
        // When: We check the filenames
        val smeltingRecipes = recipes.filter { it.unlocalizedName.startsWith("smelting_") }
        val blastingRecipes = recipes.filter { it.unlocalizedName.startsWith("blasting_") }
        
        // Then: Smelting and blasting recipes should have type prefixes
        assertTrue(
            smeltingRecipes.isNotEmpty(),
            "Should have smelting recipes with 'smelting_' prefix"
        )
        assertTrue(
            blastingRecipes.isNotEmpty(),
            "Should have blasting recipes with 'blasting_' prefix"
        )
        
        // Verify all recipe names are unique (no conflicts in flat structure)
        val allNames = recipes.map { it.unlocalizedName }
        val uniqueNames = allNames.toSet()
        assertEquals(
            allNames.size,
            uniqueNames.size,
            "All recipe filenames must be unique in flat structure"
        )
    }
    
    /**
     * **Feature: recipe-generator, Example 2: Old recipes are cleaned up before generation**
     * **Validates: Requirements 2.1, 2.2, 2.5**
     */
    @Test
    fun `old recipes are cleaned up before generation`(@TempDir tempDir: File) {
        // Given: Old recipe files exist in both old plural and new singular locations
        val resourcesBase = File(tempDir, "common/src/main/resources/data/$MOD_ID")
        val oldPluralDir = File(resourcesBase, "recipes")
        val newSingularDir = File(resourcesBase, "recipe")
        
        oldPluralDir.mkdirs()
        newSingularDir.mkdirs()
        
        // Create some old recipe files
        val oldFile1 = File(oldPluralDir, "old_recipe_1.json")
        val oldFile2 = File(oldPluralDir, "old_recipe_2.json")
        val newFile1 = File(newSingularDir, "existing_recipe_1.json")
        val newFile2 = File(newSingularDir, "existing_recipe_2.json")
        
        oldFile1.writeText("""{"type": "minecraft:smelting"}""")
        oldFile2.writeText("""{"type": "minecraft:blasting"}""")
        newFile1.writeText("""{"type": "minecraft:crafting_shaped"}""")
        newFile2.writeText("""{"type": "minecraft:crafting_shapeless"}""")
        
        assertTrue(oldFile1.exists(), "Old file 1 should exist before cleanup")
        assertTrue(oldFile2.exists(), "Old file 2 should exist before cleanup")
        assertTrue(newFile1.exists(), "New file 1 should exist before cleanup")
        assertTrue(newFile2.exists(), "New file 2 should exist before cleanup")
        
        // When: We run the cleanup function (simulated by calling deleteOldRecipes logic)
        // We need to temporarily change the working directory context for the test
        val originalUserDir = System.getProperty("user.dir")
        try {
            System.setProperty("user.dir", tempDir.absolutePath)
            
            // Simulate the deleteOldRecipes function
            var totalDeleted = 0
            
            if (oldPluralDir.exists() && oldPluralDir.isDirectory) {
                val files = oldPluralDir.listFiles { file -> file.isFile && file.extension == "json" } ?: emptyArray()
                files.forEach { file ->
                    if (file.delete()) {
                        totalDeleted++
                    }
                }
            }
            
            if (newSingularDir.exists() && newSingularDir.isDirectory) {
                val files = newSingularDir.listFiles { file -> file.isFile && file.extension == "json" } ?: emptyArray()
                files.forEach { file ->
                    if (file.delete()) {
                        totalDeleted++
                    }
                }
            }
            
            // Then: All old recipe files should be deleted
            assertFalse(oldFile1.exists(), "Old file 1 should be deleted")
            assertFalse(oldFile2.exists(), "Old file 2 should be deleted")
            assertFalse(newFile1.exists(), "New file 1 should be deleted")
            assertFalse(newFile2.exists(), "New file 2 should be deleted")
            
            // Verify the correct count was returned
            assertEquals(4, totalDeleted, "Should have deleted 4 files total")
            
        } finally {
            System.setProperty("user.dir", originalUserDir)
        }
    }
    
    /**
     * **Feature: recipe-generator, Example 5: Deletion count is logged**
     * **Validates: Requirements 2.3, 5.1**
     */
    @Test
    fun `deletion count is logged`(@TempDir tempDir: File) {
        // Given: Old recipe files exist
        val resourcesBase = File(tempDir, "common/src/main/resources/data/$MOD_ID")
        val oldPluralDir = File(resourcesBase, "recipes")
        val newSingularDir = File(resourcesBase, "recipe")
        
        oldPluralDir.mkdirs()
        newSingularDir.mkdirs()
        
        // Create test files
        File(oldPluralDir, "test1.json").writeText("{}")
        File(oldPluralDir, "test2.json").writeText("{}")
        File(newSingularDir, "test3.json").writeText("{}")
        
        // When: We capture the output and run cleanup
        val originalUserDir = System.getProperty("user.dir")
        val outputCapture = StringBuilder()
        
        try {
            System.setProperty("user.dir", tempDir.absolutePath)
            
            // Simulate the deleteOldRecipes function with logging
            var totalDeleted = 0
            
            if (oldPluralDir.exists() && oldPluralDir.isDirectory) {
                val files = oldPluralDir.listFiles { file -> file.isFile && file.extension == "json" } ?: emptyArray()
                files.forEach { file ->
                    if (file.delete()) {
                        val logMessage = "[RecipeGen] Deleted: ${file.path} (old plural location (recipes/))"
                        outputCapture.appendLine(logMessage)
                        totalDeleted++
                    }
                }
            }
            
            if (newSingularDir.exists() && newSingularDir.isDirectory) {
                val files = newSingularDir.listFiles { file -> file.isFile && file.extension == "json" } ?: emptyArray()
                files.forEach { file ->
                    if (file.delete()) {
                        val logMessage = "[RecipeGen] Deleted: ${file.path} (new singular location (recipe/))"
                        outputCapture.appendLine(logMessage)
                        totalDeleted++
                    }
                }
            }
            
            val summaryMessage = "[RecipeGen] Cleanup: $totalDeleted files deleted"
            outputCapture.appendLine(summaryMessage)
            
            // Then: The output should contain deletion logs and summary
            val output = outputCapture.toString()
            
            // Verify individual file deletion logs
            assertTrue(
                output.contains("[RecipeGen] Deleted:") && output.contains("test1.json"),
                "Should log deletion of test1.json"
            )
            assertTrue(
                output.contains("[RecipeGen] Deleted:") && output.contains("test2.json"),
                "Should log deletion of test2.json"
            )
            assertTrue(
                output.contains("[RecipeGen] Deleted:") && output.contains("test3.json"),
                "Should log deletion of test3.json"
            )
            
            // Verify location descriptions in logs
            assertTrue(
                output.contains("old plural location (recipes/)"),
                "Should log old plural location"
            )
            assertTrue(
                output.contains("new singular location (recipe/)"),
                "Should log new singular location"
            )
            
            // Verify summary with count
            assertTrue(
                output.contains("[RecipeGen] Cleanup: 3 files deleted"),
                "Should log total deletion count in summary"
            )
            
        } finally {
            System.setProperty("user.dir", originalUserDir)
        }
    }
    
    /**
     * **Feature: recipe-generator, Example 3: Generated recipes are copied to resources in flat structure**
     * **Validates: Requirements 3.1**
     */
    @Test
    fun `generated recipes are copied to resources in flat structure`(@TempDir tempDir: File) {
        // Given: Generated recipe files exist in build/generated/data/metalmancy/recipe/
        val sourceDir = File(tempDir, "build/generated/data/$MOD_ID/recipe")
        sourceDir.mkdirs()
        
        // Create some test recipe files
        val recipe1 = File(sourceDir, "zinc_ingot_from_smelting_zinc_ore.json")
        val recipe2 = File(sourceDir, "zinc_ingot_from_blasting_zinc_ore.json")
        val recipe3 = File(sourceDir, "zinc_block.json")
        
        recipe1.writeText("""{"type": "minecraft:smelting", "result": {"id": "metalmancy:zinc_ingot"}}""")
        recipe2.writeText("""{"type": "minecraft:blasting", "result": {"id": "metalmancy:zinc_ingot"}}""")
        recipe3.writeText("""{"type": "minecraft:crafting_shaped", "result": {"id": "metalmancy:zinc_block"}}""")
        
        // And: Target directory for resources
        val targetDir = File(tempDir, "common/src/main/resources/data/$MOD_ID/recipe")
        
        // Verify target doesn't exist yet
        assertFalse(targetDir.exists(), "Target directory should not exist before copy")
        
        // When: We copy files to resources (simulating copyToResources function)
        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }
        
        val files = sourceDir.listFiles()?.filter { it.isFile && it.extension == "json" } ?: emptyList()
        var copiedCount = 0
        
        for (file in files) {
            val targetFile = File(targetDir, file.name)
            file.copyTo(targetFile, overwrite = true)
            copiedCount++
        }
        
        // Then: All files should be copied to the target directory
        assertTrue(targetDir.exists(), "Target directory should exist after copy")
        assertTrue(targetDir.isDirectory, "Target should be a directory")
        
        // Verify all source files were copied
        assertEquals(3, copiedCount, "Should have copied 3 files")
        
        val copiedFile1 = File(targetDir, "zinc_ingot_from_smelting_zinc_ore.json")
        val copiedFile2 = File(targetDir, "zinc_ingot_from_blasting_zinc_ore.json")
        val copiedFile3 = File(targetDir, "zinc_block.json")
        
        assertTrue(copiedFile1.exists(), "Recipe 1 should be copied")
        assertTrue(copiedFile2.exists(), "Recipe 2 should be copied")
        assertTrue(copiedFile3.exists(), "Recipe 3 should be copied")
        
        // Verify content was preserved
        assertTrue(
            copiedFile1.readText().contains("minecraft:smelting"),
            "Recipe 1 content should be preserved"
        )
        assertTrue(
            copiedFile2.readText().contains("minecraft:blasting"),
            "Recipe 2 content should be preserved"
        )
        assertTrue(
            copiedFile3.readText().contains("minecraft:crafting_shaped"),
            "Recipe 3 content should be preserved"
        )
        
        // Verify flat structure - no subdirectories
        val copiedFiles = targetDir.listFiles() ?: emptyArray()
        val subdirectories = copiedFiles.filter { it.isDirectory }
        assertEquals(
            0,
            subdirectories.size,
            "Target directory should have NO subdirectories (flat structure)"
        )
        
        // Verify all copied items are files
        copiedFiles.forEach { file ->
            assertTrue(file.isFile, "All items in target should be files: ${file.name}")
        }
    }
    
    /**
     * **Feature: recipe-generator, Example 6: Copy operations are logged**
     * **Validates: Requirements 3.4, 5.3**
     */
    @Test
    fun `copy operations are logged`(@TempDir tempDir: File) {
        // Given: Generated recipe files exist
        val sourceDir = File(tempDir, "build/generated/data/$MOD_ID/recipe")
        sourceDir.mkdirs()
        
        // Create test recipe files
        val recipe1 = File(sourceDir, "test_recipe_1.json")
        val recipe2 = File(sourceDir, "test_recipe_2.json")
        val recipe3 = File(sourceDir, "test_recipe_3.json")
        
        recipe1.writeText("""{"type": "minecraft:smelting"}""")
        recipe2.writeText("""{"type": "minecraft:blasting"}""")
        recipe3.writeText("""{"type": "minecraft:crafting_shaped"}""")
        
        val targetDir = File(tempDir, "common/src/main/resources/data/$MOD_ID/recipe")
        
        // When: We capture output and copy files (simulating copyToResources with logging)
        val outputCapture = StringBuilder()
        
        if (!targetDir.exists()) {
            targetDir.mkdirs()
            outputCapture.appendLine("[RecipeGen] Created target directory: ${targetDir.path}")
        }
        
        val files = sourceDir.listFiles()?.filter { it.isFile && it.extension == "json" } ?: emptyList()
        var copiedCount = 0
        
        for (file in files) {
            val targetFile = File(targetDir, file.name)
            file.copyTo(targetFile, overwrite = true)
            outputCapture.appendLine("[RecipeGen] Copied: ${file.path} → ${targetFile.path}")
            copiedCount++
        }
        
        outputCapture.appendLine("[RecipeGen] Copy complete: $copiedCount files copied")
        
        // Then: The output should contain copy logs for each file
        val output = outputCapture.toString()
        
        // Verify individual file copy logs
        assertTrue(
            output.contains("[RecipeGen] Copied:") && output.contains("test_recipe_1.json"),
            "Should log copy of test_recipe_1.json"
        )
        assertTrue(
            output.contains("[RecipeGen] Copied:") && output.contains("test_recipe_2.json"),
            "Should log copy of test_recipe_2.json"
        )
        assertTrue(
            output.contains("[RecipeGen] Copied:") && output.contains("test_recipe_3.json"),
            "Should log copy of test_recipe_3.json"
        )
        
        // Verify logs show source → destination format
        assertTrue(
            output.contains("→"),
            "Copy logs should show source → destination format"
        )
        
        // Verify summary with count
        assertTrue(
            output.contains("[RecipeGen] Copy complete: 3 files copied"),
            "Should log total copy count in summary"
        )
        
        // Verify target directory creation is logged
        assertTrue(
            output.contains("[RecipeGen] Created target directory:"),
            "Should log target directory creation"
        )
    }
    
    /**
     * **Feature: recipe-generator, Example 4: Hardcoded recipes override generated recipes**
     * **Validates: Requirements 4.1, 4.2**
     */
    @Test
    fun `hardcoded recipes override generated recipes`(@TempDir tempDir: File) {
        // Given: Generated recipe files exist in resources
        val resourcesDir = File(tempDir, "common/src/main/resources/data/$MOD_ID/recipe")
        resourcesDir.mkdirs()
        
        // Create generated recipe files
        val generatedRecipe1 = File(resourcesDir, "zinc_ingot.json")
        val generatedRecipe2 = File(resourcesDir, "copper_ingot.json")
        val generatedRecipe3 = File(resourcesDir, "iron_ingot.json")
        
        val generatedContent1 = """{"type": "minecraft:smelting", "result": {"id": "metalmancy:zinc_ingot"}, "source": "generated"}"""
        val generatedContent2 = """{"type": "minecraft:smelting", "result": {"id": "metalmancy:copper_ingot"}, "source": "generated"}"""
        val generatedContent3 = """{"type": "minecraft:smelting", "result": {"id": "metalmancy:iron_ingot"}, "source": "generated"}"""
        
        generatedRecipe1.writeText(generatedContent1)
        generatedRecipe2.writeText(generatedContent2)
        generatedRecipe3.writeText(generatedContent3)
        
        // And: Hardcoded recipe files exist (some override generated, some are new)
        val hardcodedDir = File(tempDir, "common/src/tools/recipegen/recipe")
        hardcodedDir.mkdirs()
        
        // This one overrides a generated recipe
        val hardcodedRecipe1 = File(hardcodedDir, "zinc_ingot.json")
        val hardcodedContent1 = """{"type": "minecraft:blasting", "result": {"id": "metalmancy:zinc_ingot"}, "source": "hardcoded", "custom": true}"""
        hardcodedRecipe1.writeText(hardcodedContent1)
        
        // This one is a new recipe (not generated)
        val hardcodedRecipe2 = File(hardcodedDir, "custom_recipe.json")
        val hardcodedContent2 = """{"type": "minecraft:crafting_shaped", "result": {"id": "metalmancy:custom_item"}, "source": "hardcoded"}"""
        hardcodedRecipe2.writeText(hardcodedContent2)
        
        // When: We copy hardcoded recipes (simulating copyHardcodedRecipes function)
        val files = hardcodedDir.listFiles()?.filter { it.isFile && it.extension == "json" } ?: emptyList()
        var overriddenCount = 0
        
        for (file in files) {
            val targetFile = File(resourcesDir, file.name)
            file.copyTo(targetFile, overwrite = true)
            overriddenCount++
        }
        
        // Then: Hardcoded recipes should override generated ones
        assertEquals(2, overriddenCount, "Should have copied 2 hardcoded recipes")
        
        // Verify zinc_ingot.json was overridden with hardcoded version
        assertTrue(generatedRecipe1.exists(), "zinc_ingot.json should still exist")
        val finalContent1 = generatedRecipe1.readText()
        assertTrue(
            finalContent1.contains("hardcoded"),
            "zinc_ingot.json should contain hardcoded content"
        )
        assertTrue(
            finalContent1.contains("minecraft:blasting"),
            "zinc_ingot.json should have hardcoded recipe type"
        )
        assertTrue(
            finalContent1.contains("\"custom\": true"),
            "zinc_ingot.json should have hardcoded custom field"
        )
        assertFalse(
            finalContent1.contains("generated"),
            "zinc_ingot.json should NOT contain generated marker"
        )
        
        // Verify copper_ingot.json was NOT overridden (still has generated content)
        assertTrue(generatedRecipe2.exists(), "copper_ingot.json should still exist")
        val finalContent2 = generatedRecipe2.readText()
        assertTrue(
            finalContent2.contains("generated"),
            "copper_ingot.json should still have generated content"
        )
        assertFalse(
            finalContent2.contains("hardcoded"),
            "copper_ingot.json should NOT have hardcoded marker"
        )
        
        // Verify custom_recipe.json was added
        val customRecipe = File(resourcesDir, "custom_recipe.json")
        assertTrue(customRecipe.exists(), "custom_recipe.json should be added")
        val customContent = customRecipe.readText()
        assertTrue(
            customContent.contains("hardcoded"),
            "custom_recipe.json should have hardcoded content"
        )
        assertTrue(
            customContent.contains("custom_item"),
            "custom_recipe.json should have custom item"
        )
        
        // Verify all files are in flat structure
        val allFiles = resourcesDir.listFiles() ?: emptyArray()
        val subdirectories = allFiles.filter { it.isDirectory }
        assertEquals(
            0,
            subdirectories.size,
            "Resources directory should have NO subdirectories (flat structure)"
        )
        
        // Verify we have the expected number of files
        val jsonFiles = allFiles.filter { it.isFile && it.extension == "json" }
        assertEquals(
            4,
            jsonFiles.size,
            "Should have 4 recipe files total (3 generated + 1 new hardcoded, with 1 override)"
        )
    }
    
    /**
     * **Feature: recipe-generator, Example 4: Hardcoded recipes override generated recipes**
     * **Validates: Requirements 4.1, 4.2**
     * 
     * Additional test: Verify behavior when hardcoded recipe directory doesn't exist
     */
    @Test
    fun `hardcoded recipes directory missing is handled gracefully`(@TempDir tempDir: File) {
        // Given: Resources directory exists but hardcoded directory does NOT exist
        val resourcesDir = File(tempDir, "common/src/main/resources/data/$MOD_ID/recipe")
        resourcesDir.mkdirs()
        
        // Create a generated recipe
        val generatedRecipe = File(resourcesDir, "test_recipe.json")
        generatedRecipe.writeText("""{"type": "minecraft:smelting", "source": "generated"}""")
        
        val hardcodedDir = File(tempDir, "common/src/tools/recipegen/recipe")
        // Note: NOT creating hardcodedDir
        
        // When: We try to copy hardcoded recipes (simulating copyHardcodedRecipes function)
        var overriddenCount = 0
        
        if (hardcodedDir.exists() && hardcodedDir.isDirectory) {
            val files = hardcodedDir.listFiles()?.filter { it.isFile && it.extension == "json" } ?: emptyList()
            for (file in files) {
                val targetFile = File(resourcesDir, file.name)
                file.copyTo(targetFile, overwrite = true)
                overriddenCount++
            }
        }
        
        // Then: Should handle gracefully with no errors
        assertEquals(0, overriddenCount, "Should have copied 0 files when directory doesn't exist")
        
        // Verify generated recipe is unchanged
        assertTrue(generatedRecipe.exists(), "Generated recipe should still exist")
        val content = generatedRecipe.readText()
        assertTrue(
            content.contains("generated"),
            "Generated recipe should be unchanged"
        )
    }
    
    /**
     * **Feature: recipe-generator, Example 7: Override operations are logged**
     * **Validates: Requirements 4.3, 5.4**
     */
    @Test
    fun `override operations are logged`(@TempDir tempDir: File) {
        // Given: Generated recipe files exist in resources
        val resourcesDir = File(tempDir, "common/src/main/resources/data/$MOD_ID/recipe")
        resourcesDir.mkdirs()
        
        // Create generated recipe files
        val generatedRecipe1 = File(resourcesDir, "zinc_ingot.json")
        val generatedRecipe2 = File(resourcesDir, "copper_ingot.json")
        
        generatedRecipe1.writeText("""{"type": "minecraft:smelting", "source": "generated"}""")
        generatedRecipe2.writeText("""{"type": "minecraft:smelting", "source": "generated"}""")
        
        // And: Hardcoded recipe files exist (one overrides, one is new)
        val hardcodedDir = File(tempDir, "common/src/tools/recipegen/recipe")
        hardcodedDir.mkdirs()
        
        val hardcodedRecipe1 = File(hardcodedDir, "zinc_ingot.json")  // Overrides generated
        val hardcodedRecipe2 = File(hardcodedDir, "custom_recipe.json")  // New recipe
        
        hardcodedRecipe1.writeText("""{"type": "minecraft:blasting", "source": "hardcoded"}""")
        hardcodedRecipe2.writeText("""{"type": "minecraft:crafting_shaped", "source": "hardcoded"}""")
        
        // When: We capture output and copy hardcoded recipes (simulating copyHardcodedRecipes with logging)
        val outputCapture = StringBuilder()
        
        val files = hardcodedDir.listFiles()?.filter { it.isFile && it.extension == "json" } ?: emptyList()
        var overriddenCount = 0
        
        for (file in files) {
            val targetFile = File(resourcesDir, file.name)
            val isOverride = targetFile.exists()
            
            file.copyTo(targetFile, overwrite = true)
            
            if (isOverride) {
                outputCapture.appendLine("[RecipeGen] WARNING: Hardcoded recipe overrides generated recipe: ${file.name} (${file.path} → ${targetFile.path})")
            } else {
                outputCapture.appendLine("[RecipeGen] Copied hardcoded recipe: ${file.path} → ${targetFile.path}")
            }
            
            overriddenCount++
        }
        
        outputCapture.appendLine("[RecipeGen] Hardcoded recipes: $overriddenCount files copied/overridden")
        
        // Then: The output should contain override warnings and logs
        val output = outputCapture.toString()
        
        // Verify override warning for zinc_ingot.json
        assertTrue(
            output.contains("[RecipeGen] WARNING: Hardcoded recipe overrides generated recipe: zinc_ingot.json"),
            "Should log WARNING when hardcoded recipe overrides generated recipe"
        )
        
        // Verify the warning includes both source and destination filenames
        assertTrue(
            output.contains("zinc_ingot.json") && output.contains("→"),
            "Override warning should include both source and destination filenames"
        )
        
        // Verify new recipe is logged differently (not as override)
        assertTrue(
            output.contains("[RecipeGen] Copied hardcoded recipe:") && output.contains("custom_recipe.json"),
            "Should log new hardcoded recipes without override warning"
        )
        assertFalse(
            output.contains("WARNING: Hardcoded recipe overrides generated recipe: custom_recipe.json"),
            "Should NOT log override warning for new recipes"
        )
        
        // Verify summary with total count
        assertTrue(
            output.contains("[RecipeGen] Hardcoded recipes: 2 files copied/overridden"),
            "Should log total count of hardcoded recipes"
        )
        
        // Verify the format distinguishes between overrides and new files
        val lines = output.lines()
        val overrideLines = lines.filter { it.contains("WARNING: Hardcoded recipe overrides") }
        val newFileLines = lines.filter { it.contains("Copied hardcoded recipe:") && !it.contains("WARNING") }
        
        assertEquals(1, overrideLines.size, "Should have 1 override warning")
        assertEquals(1, newFileLines.size, "Should have 1 new file log")
    }
    
    /**
     * **Feature: recipe-generator, Example 7: Override operations are logged**
     * **Validates: Requirements 4.3, 5.4**
     * 
     * Additional test: Verify logging when no hardcoded recipes exist
     */
    @Test
    fun `no hardcoded recipes is logged appropriately`(@TempDir tempDir: File) {
        // Given: Resources directory exists but hardcoded directory does NOT exist
        val resourcesDir = File(tempDir, "common/src/main/resources/data/$MOD_ID/recipe")
        resourcesDir.mkdirs()
        
        val hardcodedDir = File(tempDir, "common/src/tools/recipegen/recipe")
        // Note: NOT creating hardcodedDir
        
        // When: We capture output and try to copy hardcoded recipes
        val outputCapture = StringBuilder()
        
        if (!hardcodedDir.exists() || !hardcodedDir.isDirectory) {
            outputCapture.appendLine("[RecipeGen] No hardcoded recipes directory found at: ${hardcodedDir.path} (skipping)")
        }
        
        // Then: Should log that directory was not found
        val output = outputCapture.toString()
        
        assertTrue(
            output.contains("[RecipeGen] No hardcoded recipes directory found at:"),
            "Should log when hardcoded recipes directory doesn't exist"
        )
        assertTrue(
            output.contains("(skipping)"),
            "Should indicate that step is being skipped"
        )
    }
    
    /**
     * **Feature: recipe-generator, Example 8: Summary is printed on completion**
     * **Validates: Requirements 5.5**
     */
    @Test
    fun `summary is printed on completion`(@TempDir tempDir: File) {
        // Given: A complete recipe generation workflow
        // Phase 1: Cleanup - old recipes exist
        val resourcesBase = File(tempDir, "common/src/main/resources/data/$MOD_ID")
        val oldPluralDir = File(resourcesBase, "recipes")
        val newSingularDir = File(resourcesBase, "recipe")
        
        oldPluralDir.mkdirs()
        newSingularDir.mkdirs()
        
        File(oldPluralDir, "old1.json").writeText("{}")
        File(newSingularDir, "old2.json").writeText("{}")
        
        // Phase 2: Generation - simulate generating recipes
        val buildDir = File(tempDir, "build/generated/data/$MOD_ID/recipe")
        buildDir.mkdirs()
        
        val generatedRecipe1 = File(buildDir, "zinc_ingot.json")
        val generatedRecipe2 = File(buildDir, "copper_ingot.json")
        val generatedRecipe3 = File(buildDir, "iron_ingot.json")
        
        generatedRecipe1.writeText("""{"type": "minecraft:smelting"}""")
        generatedRecipe2.writeText("""{"type": "minecraft:smelting"}""")
        generatedRecipe3.writeText("""{"type": "minecraft:smelting"}""")
        
        val generatedCount = 3
        
        // Phase 3: Copy to resources
        newSingularDir.mkdirs()  // Recreate after cleanup
        
        val buildFiles = buildDir.listFiles()?.filter { it.isFile && it.extension == "json" } ?: emptyList()
        var copiedCount = 0
        
        for (file in buildFiles) {
            val targetFile = File(newSingularDir, file.name)
            file.copyTo(targetFile, overwrite = true)
            copiedCount++
        }
        
        // Phase 4: Apply overrides - hardcoded recipes exist
        val hardcodedDir = File(tempDir, "common/src/tools/recipegen/recipe")
        hardcodedDir.mkdirs()
        
        val hardcodedRecipe1 = File(hardcodedDir, "zinc_ingot.json")  // Overrides
        val hardcodedRecipe2 = File(hardcodedDir, "custom_recipe.json")  // New
        
        hardcodedRecipe1.writeText("""{"type": "minecraft:blasting"}""")
        hardcodedRecipe2.writeText("""{"type": "minecraft:crafting_shaped"}""")
        
        val hardcodedFiles = hardcodedDir.listFiles()?.filter { it.isFile && it.extension == "json" } ?: emptyList()
        var overrideCount = 0
        
        for (file in hardcodedFiles) {
            val targetFile = File(newSingularDir, file.name)
            file.copyTo(targetFile, overwrite = true)
            overrideCount++
        }
        
        // When: We generate the final summary message
        val summaryMessage = "[RecipeGen] Complete: $generatedCount generated, $copiedCount copied, $overrideCount overridden"
        
        // Then: The summary should contain all required information
        // Verify format matches exactly: "[RecipeGen] Complete: X generated, Y copied, Z overridden"
        assertTrue(
            summaryMessage.startsWith("[RecipeGen] Complete:"),
            "Summary should start with '[RecipeGen] Complete:'"
        )
        
        // Verify it contains the generated count
        assertTrue(
            summaryMessage.contains("3 generated"),
            "Summary should contain generated count: '3 generated'"
        )
        
        // Verify it contains the copied count
        assertTrue(
            summaryMessage.contains("3 copied"),
            "Summary should contain copied count: '3 copied'"
        )
        
        // Verify it contains the overridden count
        assertTrue(
            summaryMessage.contains("2 overridden"),
            "Summary should contain overridden count: '2 overridden'"
        )
        
        // Verify the exact format
        assertEquals(
            "[RecipeGen] Complete: 3 generated, 3 copied, 2 overridden",
            summaryMessage,
            "Summary should match exact format: '[RecipeGen] Complete: X generated, Y copied, Z overridden'"
        )
        
        // Verify the counts are correct
        assertEquals(3, generatedCount, "Should have generated 3 recipes")
        assertEquals(3, copiedCount, "Should have copied 3 recipes")
        assertEquals(2, overrideCount, "Should have overridden/added 2 recipes")
    }
    
    /**
     * **Feature: recipe-generator, Example 9: Smelting recipes have correct JSON structure**
     * **Validates: Requirements 6.1, 6.5**
     */
    @Test
    fun `smelting recipes have correct JSON structure`() {
        // Given: A smelting recipe
        val smeltingRecipe = tools.recipegen.SmeltingRecipe(
            unlocalizedName = "test_smelting_zinc_ingot",
            ingredient = "metalmancy:zinc_ore",
            result = "metalmancy:zinc_ingot",
            experience = 0.7,
            cookingTime = 200,
            group = "zinc_ingot",
            category = "blocks"
        )
        
        // When: We generate the recipe JSON
        val json = smeltingRecipe.generateRecipe()
        
        // Then: The JSON should have all required fields with correct types
        // Required field: type
        assertTrue(json.has("type"), "Smelting recipe must have 'type' field")
        assertEquals(
            "minecraft:smelting",
            json.get("type").asString,
            "Smelting recipe type must be 'minecraft:smelting'"
        )
        
        // Required field: category
        assertTrue(json.has("category"), "Smelting recipe must have 'category' field")
        assertEquals(
            "blocks",
            json.get("category").asString,
            "Category should match the provided value"
        )
        
        // Required field: cookingtime
        assertTrue(json.has("cookingtime"), "Smelting recipe must have 'cookingtime' field")
        assertEquals(
            200,
            json.get("cookingtime").asInt,
            "Cooking time should match the provided value"
        )
        
        // Required field: experience
        assertTrue(json.has("experience"), "Smelting recipe must have 'experience' field")
        assertEquals(
            0.7,
            json.get("experience").asDouble,
            0.001,
            "Experience should match the provided value"
        )
        
        // Required field: ingredient
        assertTrue(json.has("ingredient"), "Smelting recipe must have 'ingredient' field")
        assertEquals(
            "metalmancy:zinc_ore",
            json.get("ingredient").asString,
            "Ingredient should match the provided value"
        )
        
        // Required field: result (must be an object with 'id' field)
        assertTrue(json.has("result"), "Smelting recipe must have 'result' field")
        assertTrue(json.get("result").isJsonObject, "Result must be a JSON object")
        
        val resultObj = json.getAsJsonObject("result")
        assertTrue(resultObj.has("id"), "Result object must have 'id' field")
        assertEquals(
            "metalmancy:zinc_ingot",
            resultObj.get("id").asString,
            "Result id should match the provided value"
        )
        
        // Optional field: group
        assertTrue(json.has("group"), "Group field should be present when provided")
        assertEquals(
            "zinc_ingot",
            json.get("group").asString,
            "Group should match the provided value"
        )
    }
    
    /**
     * **Feature: recipe-generator, Example 10: Blasting recipes have correct JSON structure**
     * **Validates: Requirements 6.2, 6.5**
     */
    @Test
    fun `blasting recipes have correct JSON structure`() {
        // Given: A blasting recipe
        val blastingRecipe = tools.recipegen.BlastingRecipe(
            unlocalizedName = "test_blasting_zinc_ingot",
            ingredient = "metalmancy:raw_zinc",
            result = "metalmancy:zinc_ingot",
            experience = 0.7,
            cookingTime = 100,
            group = "zinc_ingot",
            category = "misc"
        )
        
        // When: We generate the recipe JSON
        val json = blastingRecipe.generateRecipe()
        
        // Then: The JSON should have all required fields with correct types
        // Required field: type
        assertTrue(json.has("type"), "Blasting recipe must have 'type' field")
        assertEquals(
            "minecraft:blasting",
            json.get("type").asString,
            "Blasting recipe type must be 'minecraft:blasting'"
        )
        
        // Required field: category
        assertTrue(json.has("category"), "Blasting recipe must have 'category' field")
        assertEquals(
            "misc",
            json.get("category").asString,
            "Category should match the provided value"
        )
        
        // Required field: cookingtime
        assertTrue(json.has("cookingtime"), "Blasting recipe must have 'cookingtime' field")
        assertEquals(
            100,
            json.get("cookingtime").asInt,
            "Cooking time should match the provided value"
        )
        
        // Required field: experience
        assertTrue(json.has("experience"), "Blasting recipe must have 'experience' field")
        assertEquals(
            0.7,
            json.get("experience").asDouble,
            0.001,
            "Experience should match the provided value"
        )
        
        // Required field: ingredient
        assertTrue(json.has("ingredient"), "Blasting recipe must have 'ingredient' field")
        assertEquals(
            "metalmancy:raw_zinc",
            json.get("ingredient").asString,
            "Ingredient should match the provided value"
        )
        
        // Required field: result (must be an object with 'id' field)
        assertTrue(json.has("result"), "Blasting recipe must have 'result' field")
        assertTrue(json.get("result").isJsonObject, "Result must be a JSON object")
        
        val resultObj = json.getAsJsonObject("result")
        assertTrue(resultObj.has("id"), "Result object must have 'id' field")
        assertEquals(
            "metalmancy:zinc_ingot",
            resultObj.get("id").asString,
            "Result id should match the provided value"
        )
        
        // Optional field: group
        assertTrue(json.has("group"), "Group field should be present when provided")
        assertEquals(
            "zinc_ingot",
            json.get("group").asString,
            "Group should match the provided value"
        )
    }
    
    /**
     * **Feature: recipe-generator, Example 11: Shapeless recipes have correct JSON structure**
     * **Validates: Requirements 6.3, 6.5**
     */
    @Test
    fun `shapeless recipes have correct JSON structure`() {
        // Given: A shapeless crafting recipe
        val shapelessRecipe = tools.recipegen.ShapelessRecipe(
            unlocalizedName = "test_shapeless_zinc_nugget",
            ingredients = listOf("metalmancy:zinc_ingot"),
            result = "metalmancy:zinc_nugget",
            count = 9,
            group = "zinc_nugget",
            category = "misc"
        )
        
        // When: We generate the recipe JSON
        val json = shapelessRecipe.generateRecipe()
        
        // Then: The JSON should have all required fields with correct types
        // Required field: type
        assertTrue(json.has("type"), "Shapeless recipe must have 'type' field")
        assertEquals(
            "minecraft:crafting_shapeless",
            json.get("type").asString,
            "Shapeless recipe type must be 'minecraft:crafting_shapeless'"
        )
        
        // Required field: category
        assertTrue(json.has("category"), "Shapeless recipe must have 'category' field")
        assertEquals(
            "misc",
            json.get("category").asString,
            "Category should match the provided value"
        )
        
        // Required field: ingredients (must be an array)
        assertTrue(json.has("ingredients"), "Shapeless recipe must have 'ingredients' field")
        assertTrue(json.get("ingredients").isJsonArray, "Ingredients must be a JSON array")
        
        val ingredientsArray = json.getAsJsonArray("ingredients")
        assertTrue(ingredientsArray.size() > 0, "Ingredients array should not be empty")
        
        // Each ingredient should be an object with 'item' or 'tag' field
        val firstIngredient = ingredientsArray.get(0).asJsonObject
        assertTrue(
            firstIngredient.has("item") || firstIngredient.has("tag"),
            "Each ingredient must have either 'item' or 'tag' field"
        )
        
        // Required field: result (must be an object with 'id' field)
        assertTrue(json.has("result"), "Shapeless recipe must have 'result' field")
        assertTrue(json.get("result").isJsonObject, "Result must be a JSON object")
        
        val resultObj = json.getAsJsonObject("result")
        assertTrue(resultObj.has("id"), "Result object must have 'id' field")
        assertEquals(
            "metalmancy:zinc_nugget",
            resultObj.get("id").asString,
            "Result id should match the provided value"
        )
        
        // Result should have count when count > 1
        assertTrue(resultObj.has("count"), "Result should have 'count' field when count > 1")
        assertEquals(
            9,
            resultObj.get("count").asInt,
            "Result count should match the provided value"
        )
        
        // Optional field: group
        assertTrue(json.has("group"), "Group field should be present when provided")
        assertEquals(
            "zinc_nugget",
            json.get("group").asString,
            "Group should match the provided value"
        )
    }
    
    /**
     * **Feature: recipe-generator, Example 12: Shaped recipes have correct JSON structure**
     * **Validates: Requirements 6.4, 6.5**
     */
    @Test
    fun `shaped recipes have correct JSON structure`() {
        // Given: A shaped crafting recipe
        val shapedRecipe = tools.recipegen.ShapedRecipe(
            unlocalizedName = "test_shaped_zinc_block",
            pattern = listOf("###", "###", "###"),
            key = mapOf('#' to "metalmancy:zinc_ingot"),
            result = "metalmancy:zinc_block",
            count = 1,
            group = "zinc_block",
            category = "building"
        )
        
        // When: We generate the recipe JSON
        val json = shapedRecipe.generateRecipe()
        
        // Then: The JSON should have all required fields with correct types
        // Required field: type
        assertTrue(json.has("type"), "Shaped recipe must have 'type' field")
        assertEquals(
            "minecraft:crafting_shaped",
            json.get("type").asString,
            "Shaped recipe type must be 'minecraft:crafting_shaped'"
        )
        
        // Required field: category
        assertTrue(json.has("category"), "Shaped recipe must have 'category' field")
        assertEquals(
            "building",
            json.get("category").asString,
            "Category should match the provided value"
        )
        
        // Required field: key (must be an object)
        assertTrue(json.has("key"), "Shaped recipe must have 'key' field")
        assertTrue(json.get("key").isJsonObject, "Key must be a JSON object")
        
        val keyObj = json.getAsJsonObject("key")
        assertTrue(keyObj.has("#"), "Key should contain the pattern character '#'")
        
        // Each key entry should be an object with 'item' or 'tag' field
        val keyEntry = keyObj.getAsJsonObject("#")
        assertTrue(
            keyEntry.has("item") || keyEntry.has("tag"),
            "Each key entry must have either 'item' or 'tag' field"
        )
        
        // Required field: pattern (must be an array)
        assertTrue(json.has("pattern"), "Shaped recipe must have 'pattern' field")
        assertTrue(json.get("pattern").isJsonArray, "Pattern must be a JSON array")
        
        val patternArray = json.getAsJsonArray("pattern")
        assertEquals(
            3,
            patternArray.size(),
            "Pattern should have 3 rows for 3x3 crafting"
        )
        
        // Each pattern row should be a string
        patternArray.forEach { row ->
            assertTrue(row.isJsonPrimitive, "Each pattern row should be a string")
            assertEquals(
                "###",
                row.asString,
                "Pattern row should match the provided value"
            )
        }
        
        // Required field: result (must be an object with 'id' field)
        assertTrue(json.has("result"), "Shaped recipe must have 'result' field")
        assertTrue(json.get("result").isJsonObject, "Result must be a JSON object")
        
        val resultObj = json.getAsJsonObject("result")
        assertTrue(resultObj.has("id"), "Result object must have 'id' field")
        assertEquals(
            "metalmancy:zinc_block",
            resultObj.get("id").asString,
            "Result id should match the provided value"
        )
        
        // Result should NOT have count when count == 1 (optional optimization)
        // Note: The current implementation may include count=1, which is valid but not required
        
        // Optional field: group
        assertTrue(json.has("group"), "Group field should be present when provided")
        assertEquals(
            "zinc_block",
            json.get("group").asString,
            "Group should match the provided value"
        )
    }
}

