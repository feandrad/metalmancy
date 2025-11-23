# Design Document - Recipe Generator Datapack Structure Compliance

## Overview

The Recipe Generator currently outputs files to `data/<namespace>/recipes/` but Minecraft 1.21.10 (pack format 88.0) requires `data/<namespace>/recipe/` (singular). This design addresses:

1. **Path Correction**: Fix the output path to use `recipe/` (singular) as defined in `common/src/tools/datapack-structure-1.21.10.md`
2. **Cleanup**: Delete old incorrectly-placed recipes from both the old `recipes/` folder and the current resources folder
3. **Resource Integration**: Automatically copy generated recipes to `common/src/main/resources/data/metalmancy/recipe/`
4. **Override System**: Support hardcoded recipe files in `common/src/tools/recipegen/recipe/` that override generated recipes
5. **Logging**: Provide detailed logging of all operations for verification

This transforms the generator from a simple file writer into a complete recipe management system that handles the full lifecycle: cleanup → generation → copying → overrides.

## Architecture

The Recipe Generator follows an enhanced pipeline with four phases:

```
Phase 1: Cleanup
    Delete old recipes from resources/data/metalmancy/recipes/ (old location)
    Delete old recipes from resources/data/metalmancy/recipe/ (new location)
    ↓
Phase 2: Generation
    Materials.ALL → RecipeEntries → RecipeGen.main()
    Write JSON files FLAT to build/generated/data/metalmancy/recipe/
    NO subdirectories - all files directly in recipe/
    ↓
Phase 3: Copy to Resources
    Copy all generated files to common/src/main/resources/data/metalmancy/recipe/
    Flat structure - no subdirectories
    ↓
Phase 4: Apply Overrides
    Copy hardcoded recipes from common/src/tools/recipegen/recipe/
    Override any generated recipes with same filename
    Log all overrides
```

### Current Implementation (Incorrect)

```kotlin
val recipesDir = dir(outDir, MOD_ID, "recipes")  // ❌ Wrong: plural
// No cleanup, no copying to resources, no override support
```

Output: `build/generated/data/metalmancy/recipes/zinc_ingot.json` (wrong location, not copied to resources)

### Fixed Implementation (Correct)

```kotlin
fun main(args: Array<String>) {
    var outDir = "build/generated/data"
    // ... parse args ...
    
    // Phase 1: Cleanup
    deleteOldRecipes()
    
    // Phase 2: Generation
    val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    val recipesDir = dir(outDir, MOD_ID, "recipe")  // ✅ Correct: singular
    
    for (recipe in RecipeEntries.recipes) {
        val recipeJson = recipe.generateRecipe()
        gson.writeJson(recipesDir, "${recipe.unlocalizedName}.json", recipeJson)
    }
    
    // Phase 3: Copy to Resources
    val resourcesRecipeDir = File("common/src/main/resources/data/$MOD_ID/recipe")
    val copiedCount = copyToResources(recipesDir, resourcesRecipeDir)
    
    // Phase 4: Apply Overrides
    val hardcodedRecipeDir = File("common/src/tools/recipegen/recipe")
    val overrideCount = if (hardcodedRecipeDir.exists()) {
        copyHardcodedRecipes(hardcodedRecipeDir, resourcesRecipeDir)
    } else 0
    
    println("[RecipeGen] Complete: ${RecipeEntries.recipes.size} generated, $copiedCount copied, $overrideCount overridden")
}
```

Output examples (all flat, no subdirectories):
- Generated: `build/generated/data/metalmancy/recipe/zinc_ingot_from_smelting_zinc_ore.json`
- Copied to: `common/src/main/resources/data/metalmancy/recipe/zinc_ingot_from_smelting_zinc_ore.json`
- Override (if exists): `common/src/tools/recipegen/recipe/zinc_ingot_from_smelting_zinc_ore.json` → resources

**Critical**: The datapack structure does NOT allow subdirectories within `recipe/`. All recipe files must be directly in the `recipe/` folder with no nesting.

## Components and Interfaces

### RecipeGen.kt

The main entry point that orchestrates the complete recipe generation lifecycle.

**New Functions Required:**

1. **`deleteOldRecipes()`** - Cleanup phase
   - Deletes files from `common/src/main/resources/data/metalmancy/recipes/` (old plural location)
   - Deletes files from `common/src/main/resources/data/metalmancy/recipe/` (new singular location)
   - Logs number of files deleted
   - Continues on failure with warning

2. **`copyToResources(sourceDir: File, targetDir: File): Int`** - Copy phase
   - Copies all files from `build/generated/data/metalmancy/recipe/` to resources
   - Flat copy - all files go directly into target directory
   - NO subdirectories - fails if source has subdirectories
   - Creates target directory if needed
   - Logs each file copied
   - Returns count of files copied

3. **`copyHardcodedRecipes(sourceDir: File, targetDir: File): Int`** - Override phase
   - Copies from `common/src/tools/recipegen/recipe/` to resources
   - Flat copy - all files go directly into target directory
   - NO subdirectories - fails if source has subdirectories
   - Overwrites any existing files (generated or previous overrides)
   - Logs when a file overrides a generated recipe
   - Returns count of files overridden

**Updated main() function:**
```kotlin
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

    // Phase 1: Cleanup
    deleteOldRecipes()
    
    // Phase 2: Generation
    val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    // Path follows datapack structure defined in common/src/tools/datapack-structure-1.21.10.md
    val recipesDir = dir(outDir, MOD_ID, "recipe")  // Fixed: "recipe" not "recipes"
    
    for (recipe in RecipeEntries.recipes) {
        val recipeJson = recipe.generateRecipe()
        gson.writeJson(recipesDir, "${recipe.unlocalizedName}.json", recipeJson)
    }
    
    // Phase 3: Copy to Resources
    val resourcesRecipeDir = File("common/src/main/resources/data/$MOD_ID/recipe")
    val copiedCount = copyToResources(recipesDir, resourcesRecipeDir)
    
    // Phase 4: Apply Overrides
    val hardcodedRecipeDir = File("common/src/tools/recipegen/recipe")
    val overrideCount = if (hardcodedRecipeDir.exists()) {
        copyHardcodedRecipes(hardcodedRecipeDir, resourcesRecipeDir)
    } else 0
    
    println("[RecipeGen] Complete: ${RecipeEntries.recipes.size} generated, $copiedCount copied, $overrideCount overridden")
}
```

### Path Construction

The existing `dir()` helper function remains unchanged:

```kotlin
private fun dir(base: String, vararg parts: String): File {
    var f = File(base)
    for (p in parts) f = File(f, p)
    if (!f.exists()) f.mkdirs()
    return f
}
```

With the fix:
- `base` = `"build/generated/data"`
- `parts` = `["metalmancy", "recipe"]`
- Result = `build/generated/data/metalmancy/recipe/`

### File Operations

The existing `writeJson()` extension function remains unchanged:

```kotlin
private fun Gson.writeJson(dir: File, fileName: String, json: JsonObject) {
    val out = File(dir, fileName)
    out.parentFile?.mkdirs()  // Create parent directories if needed
    FileWriter(out).use { w -> toJson(json, w) }
    println("[RecipeGen] wrote ${out.path}")
}
```

## Data Models

The existing recipe classes must generate JSON that matches the datapack structure specification:

### GeneratedRecipe (Abstract Base)
- Defines common interface for all recipe types
- `generateRecipe()` returns JsonObject

### SmeltingRecipe
Must generate JSON with these fields:
```json
{
  "type": "minecraft:smelting",
  "category": "misc",
  "cookingtime": 200,
  "experience": 0.7,
  "group": "optional_group_name",
  "ingredient": "namespace:item_id",
  "result": {
    "id": "namespace:item_id"
  }
}
```

### BlastingRecipe
Must generate JSON with these fields:
```json
{
  "type": "minecraft:blasting",
  "category": "misc",
  "cookingtime": 100,
  "experience": 0.7,
  "group": "optional_group_name",
  "ingredient": "namespace:item_id",
  "result": {
    "id": "namespace:item_id"
  }
}
```

### ShapelessRecipe
Must generate JSON with these fields:
```json
{
  "type": "minecraft:crafting_shapeless",
  "category": "misc",
  "group": "optional_group_name",
  "ingredients": ["namespace:item_id"],
  "result": {
    "count": 9,
    "id": "namespace:item_id"
  }
}
```

### ShapedRecipe
Must generate JSON with these fields:
```json
{
  "type": "minecraft:crafting_shaped",
  "category": "misc",
  "group": "optional_group_name",
  "key": {
    "#": "namespace:item_id"
  },
  "pattern": ["###", "###", "###"],
  "result": {
    "count": 1,
    "id": "namespace:item_id"
  }
}
```

**Validation Requirements:**
- All recipe types must include the correct `type` field
- All recipes should include `category` (defaults to `misc`)
- Result objects must have `id` field
- Result objects may have optional `count` field
- Ingredient fields can reference tags using `#` prefix

## Correctness Properties

*A property is a characteristic or behavior that should hold true across all valid executions of a system-essentially, a formal statement about what the system should do. Properties serve as the bridge between human-readable specifications and machine-verifiable correctness guarantees.*

### Acceptance Criteria Testing Prework

1.1 WHEN determining output paths THEN the system SHALL consult `common/src/tools/datapack-structure-1.21.10.md` as the authoritative source
Thoughts: This is about the development process and documentation, not a runtime behavior we can test automatically.
Testable: no

1.2 WHEN the datapack structure shows `recipe/` THEN the system SHALL use `"recipe"` not `"recipes"` in the path
Thoughts: This is a specific example we can verify - check that the code uses the correct string literal.
Testable: yes - example

1.3 WHEN the datapack structure shows any folder name THEN the system SHALL use that exact name (including singular vs plural)
Thoughts: This is about following a general principle across all folder names. We can test this by checking the generated path matches the expected pattern.
Testable: yes - example

1.4 WHEN generating files THEN the system SHALL construct paths as `<base>/data/<namespace>/<folder>/` where `<folder>` matches the datapack structure exactly
Thoughts: This is verifying the path construction logic produces the correct format. We can test with a specific example.
Testable: yes - example

1.5 WHEN the datapack structure reference is updated for future Minecraft versions THEN the system SHALL use the updated folder names
Thoughts: This is about future maintenance, not current testable behavior.
Testable: no

2.1 WHEN the generator starts THEN the system SHALL delete existing recipe files from the old `recipes/` folder
Thoughts: This is a specific behavior we can test - run the generator and verify the old folder is cleaned up.
Testable: yes - example

2.2 WHEN the generator starts THEN the system SHALL delete existing recipe files from the resources folder
Thoughts: This is a specific behavior we can test - verify the resources folder is cleaned before generation.
Testable: yes - example

2.3 WHEN old files are deleted THEN the system SHALL log which files were removed
Thoughts: This is about logging behavior. We can verify the log output contains the expected messages.
Testable: yes - example

2.4 WHEN deletion fails THEN the system SHALL continue with generation and log a warning
Thoughts: This is an error handling edge case. We can test by making a file read-only and verifying the generator continues.
Testable: edge-case

2.5 WHEN deletion is complete THEN the system SHALL proceed with generating new recipes
Thoughts: This is about the control flow - deletion happens before generation. We can verify by checking timestamps.
Testable: yes - example

3.1 WHEN recipes are generated THEN the system SHALL copy all files from `build/generated/data/metalmancy/recipe/` to `common/src/main/resources/data/metalmancy/recipe/`
Thoughts: This is a specific behavior we can test - verify files exist in both locations after generation.
Testable: yes - example

3.2 WHEN copying files THEN the system SHALL preserve the subdirectory structure (e.g., `smelting/`, `blasting/`)
Thoughts: WAIT - this requirement is WRONG. The datapack structure does NOT allow subdirectories in recipe/. This requirement conflicts with the datapack structure. We should NOT preserve subdirectories - all files must be flat.
Testable: no - requirement is incorrect

3.3 WHEN copying files THEN the system SHALL create parent directories if they don't exist
Thoughts: This is an edge case - what happens when the target directory doesn't exist. We can test by deleting the target first.
Testable: edge-case

3.4 WHEN copying files THEN the system SHALL log each file that is copied
Thoughts: This is about logging behavior. We can verify the log contains expected file paths.
Testable: yes - example

3.5 WHEN copying is complete THEN the system SHALL report the total number of files copied
Thoughts: This is about the summary output. We can verify the final message contains the correct count.
Testable: yes - example

4.1 WHEN hardcoded recipes exist in `common/src/tools/recipegen/recipe/` THEN the system SHALL copy them to the resources folder AFTER generated recipes
Thoughts: This is about the order of operations. We can test by having both a generated and hardcoded version and verifying the hardcoded one wins.
Testable: yes - example

4.2 WHEN a hardcoded recipe has the same filename as a generated recipe THEN the system SHALL override the generated recipe with the hardcoded one
Thoughts: This is the core override behavior. We can test by comparing file contents after generation.
Testable: yes - example

4.3 WHEN a hardcoded recipe overrides a generated recipe THEN the system SHALL log a message indicating the override
Thoughts: This is about logging behavior. We can verify the log contains override messages.
Testable: yes - example

4.4 WHEN hardcoded recipes are copied THEN the system SHALL preserve subdirectory structure
Thoughts: WAIT - this requirement is WRONG. The datapack structure does NOT allow subdirectories in recipe/. This requirement conflicts with the datapack structure. We should NOT preserve subdirectories - all files must be flat.
Testable: no - requirement is incorrect

4.5 WHEN no hardcoded recipes exist THEN the system SHALL skip this step and continue normally
Thoughts: This is an edge case - what happens when the override directory doesn't exist. We can test by deleting it.
Testable: edge-case

5.1 WHEN old recipes are deleted THEN the system SHALL log the number of files deleted
Thoughts: This is about logging behavior. We can verify the log contains the deletion count.
Testable: yes - example

5.2 WHEN recipes are generated THEN the system SHALL log each recipe file written to `build/generated/`
Thoughts: This is about logging behavior. Already covered by existing writeJson logging.
Testable: yes - example

5.3 WHEN recipes are copied to resources THEN the system SHALL log each file copied
Thoughts: This is about logging behavior. Same as 3.4.
Testable: yes - example

5.4 WHEN a hardcoded recipe overrides a generated recipe THEN the system SHALL log a warning message with both filenames
Thoughts: This is about logging behavior. Same as 4.3 but more specific about the message format.
Testable: yes - example

5.5 WHEN the generator completes THEN the system SHALL print a summary with total files generated, copied, and overridden
Thoughts: This is about the final summary output. We can verify the message format and counts.
Testable: yes - example

6.1 WHEN generating a smelting recipe THEN the system SHALL include `type`, `category`, `cookingtime`, `experience`, `ingredient`, and `result` fields as defined in the datapack structure reference
Thoughts: This is about validating the JSON structure of generated smelting recipes. We can test by generating a smelting recipe and verifying all required fields are present with correct types.
Testable: yes - example

6.2 WHEN generating a blasting recipe THEN the system SHALL include `type`, `category`, `cookingtime`, `experience`, `ingredient`, and `result` fields as defined in the datapack structure reference
Thoughts: This is about validating the JSON structure of generated blasting recipes. We can test by generating a blasting recipe and verifying all required fields are present with correct types.
Testable: yes - example

6.3 WHEN generating a shapeless crafting recipe THEN the system SHALL include `type`, `category`, `ingredients`, and `result` fields as defined in the datapack structure reference
Thoughts: This is about validating the JSON structure of generated shapeless recipes. We can test by generating a shapeless recipe and verifying all required fields are present with correct types.
Testable: yes - example

6.4 WHEN generating a shaped crafting recipe THEN the system SHALL include `type`, `category`, `key`, `pattern`, and `result` fields as defined in the datapack structure reference
Thoughts: This is about validating the JSON structure of generated shaped recipes. We can test by generating a shaped recipe and verifying all required fields are present with correct types.
Testable: yes - example

6.5 WHEN generating any recipe THEN the system SHALL use the correct recipe type identifier (e.g., `minecraft:smelting`, `minecraft:blasting`, `minecraft:crafting_shaped`, `minecraft:crafting_shapeless`)
Thoughts: This is about validating that the `type` field has the correct value for each recipe type. We can test by checking the type field in generated recipes.
Testable: yes - example

### Property Reflection

After reviewing all testable criteria, most are specific examples of expected behavior rather than universal properties. The criteria fall into these categories:

1. **Path correctness** (1.2, 1.3, 1.4) - Can be combined into one example
2. **Cleanup behavior** (2.1, 2.2, 2.5) - Can be combined into one example
3. **Copy behavior** (3.1, 3.2) - Can be combined into one example
4. **Override behavior** (4.1, 4.2) - Can be combined into one example
5. **Logging behavior** (2.3, 3.4, 4.3, 5.1-5.5) - Multiple examples for different log messages
6. **Edge cases** (2.4, 3.3, 4.5) - Handled by generators in property tests

Since this is a file I/O and build tool, we'll focus on example-based tests that verify the complete workflow rather than universal properties.

### Correctness Properties

**Example 1: Generated files use correct datapack structure path**
*For the specific case* of running RecipeGen with default settings, generated files should be written to `build/generated/data/metalmancy/recipe/` (singular) not `build/generated/data/metalmancy/recipes/` (plural)
**Validates: Requirements 1.2, 1.3, 1.4**

**Example 2: Old recipes are cleaned up before generation**
*For the specific case* of running RecipeGen when old recipe files exist in `common/src/main/resources/data/metalmancy/recipes/` or `common/src/main/resources/data/metalmancy/recipe/`, those files should be deleted before new recipes are generated
**Validates: Requirements 2.1, 2.2, 2.5**

**Example 3: Generated recipes are copied to resources in flat structure**
*For the specific case* of running RecipeGen, all generated files from `build/generated/data/metalmancy/recipe/` should be copied to `common/src/main/resources/data/metalmancy/recipe/` with NO subdirectories - all files directly in the recipe/ folder
**Validates: Requirements 3.1**
**Note**: Requirement 3.2 about preserving subdirectories is incorrect and conflicts with datapack structure

**Example 4: Hardcoded recipes override generated recipes**
*For the specific case* of running RecipeGen when a hardcoded recipe exists in `common/src/tools/recipegen/recipe/zinc_ingot.json` and a generated recipe with the same name exists, the final file in resources should contain the hardcoded version's content
**Validates: Requirements 4.1, 4.2**

**Example 5: Deletion count is logged**
*For the specific case* of running RecipeGen when old recipe files exist, the log output should contain a message indicating how many files were deleted
**Validates: Requirements 2.3, 5.1**

**Example 6: Copy operations are logged**
*For the specific case* of running RecipeGen, the log output should contain messages for each file copied to resources
**Validates: Requirements 3.4, 5.3**

**Example 7: Override operations are logged**
*For the specific case* of running RecipeGen when hardcoded recipes override generated ones, the log output should contain warning messages indicating which files were overridden
**Validates: Requirements 4.3, 5.4**

**Example 8: Summary is printed on completion**
*For the specific case* of running RecipeGen, the final log message should contain a summary with counts of files generated, copied, and overridden
**Validates: Requirements 5.5**

**Example 9: Smelting recipes have correct JSON structure**
*For the specific case* of generating a smelting recipe, the output JSON should contain all required fields (`type`, `category`, `cookingtime`, `experience`, `ingredient`, `result`) with correct types and the type field should be `minecraft:smelting`
**Validates: Requirements 6.1, 6.5**

**Example 10: Blasting recipes have correct JSON structure**
*For the specific case* of generating a blasting recipe, the output JSON should contain all required fields (`type`, `category`, `cookingtime`, `experience`, `ingredient`, `result`) with correct types and the type field should be `minecraft:blasting`
**Validates: Requirements 6.2, 6.5**

**Example 11: Shapeless recipes have correct JSON structure**
*For the specific case* of generating a shapeless crafting recipe, the output JSON should contain all required fields (`type`, `category`, `ingredients`, `result`) with correct types and the type field should be `minecraft:crafting_shapeless`
**Validates: Requirements 6.3, 6.5**

**Example 12: Shaped recipes have correct JSON structure**
*For the specific case* of generating a shaped crafting recipe, the output JSON should contain all required fields (`type`, `category`, `key`, `pattern`, `result`) with correct types and the type field should be `minecraft:crafting_shaped`
**Validates: Requirements 6.4, 6.5**

## Error Handling

No changes to error handling. The existing error handling remains:

- **Missing output directory**: The `dir()` function creates directories automatically with `mkdirs()`
- **File write failures**: Handled by `FileWriter` which throws `IOException`
- **Invalid JSON**: Prevented by using Gson's type-safe API

## Testing Strategy

### Unit Tests

Unit tests will verify individual functions and specific behaviors:

1. **Path Construction Test**: Verify `dir("build/generated/data", "metalmancy", "recipe")` produces correct path
2. **Cleanup Test**: Verify `deleteOldRecipes()` removes files from both old and new locations
3. **Flat Copy Test**: Verify `copyToResources()` copies all files directly to target with NO subdirectories
4. **Flat Override Test**: Verify `copyHardcodedRecipes()` overwrites existing files with NO subdirectories
5. **Subdirectory Rejection Test**: Verify copy functions fail/warn if source contains subdirectories
6. **Logging Test**: Verify all operations produce expected log messages
7. **Edge Case Tests**:
   - Missing target directories are created
   - Non-existent hardcoded recipe directory is handled gracefully
   - File deletion failures don't stop generation

### Integration Tests

Integration tests will verify the complete workflow:

1. **Full Pipeline Test**: 
   - Create old recipe files in wrong locations
   - Run RecipeGen
   - Verify cleanup, generation, copying, and overrides all work together
   - Verify final state has correct files in correct locations

2. **Override Integration Test**:
   - Generate recipes
   - Add hardcoded override
   - Run RecipeGen again
   - Verify hardcoded version is in resources

3. **Minecraft Loading Test**: 
   - Run RecipeGen
   - Copy resources to test world datapack
   - Verify Minecraft 1.21.10 loads recipes
   - Verify recipes appear in recipe book
   - Verify recipes work in-game

### Manual Verification

1. Run `./gradlew :common:runRecipeGen`
2. Verify log output shows:
   - Deletion count for old recipes
   - Generation messages for each recipe
   - Copy messages for each file
   - Override messages (if any)
   - Final summary with counts
3. Check file locations:
   - Generated: `common/build/generated/data/metalmancy/recipe/`
   - Resources: `common/src/main/resources/data/metalmancy/recipe/`
   - Old location empty: `common/src/main/resources/data/metalmancy/recipes/`
4. Test in Minecraft 1.21.10

### Testing Framework

Use Kotlin's built-in testing support:
- **JUnit 5** for test structure
- **Kotlin Test** for assertions
- **Temporary directories** for file I/O tests
- **Log capture** for verifying log messages

## Implementation Notes

### The Fix

**File**: `common/src/tools/recipegen/RecipeGen.kt`  
**Line**: 18  
**Change**: 
```kotlin
// Before
val recipesDir = dir(outDir, MOD_ID, "recipes")

// After
// Path follows datapack structure defined in common/src/tools/datapack-structure-1.21.10.md
val recipesDir = dir(outDir, MOD_ID, "recipe")
```

### Recipe File Organization

**Current (Incorrect)**: The generator currently creates subdirectories:
```
build/generated/data/metalmancy/recipes/  ❌ Wrong: plural AND has subdirectories
├── smelting/
│   └── zinc_ingot_from_raw_zinc.json
├── blasting/
│   └── ...
└── zinc_block.json
```

**Fixed (Correct)**: The generator must create a FLAT structure with NO subdirectories:
```
build/generated/data/metalmancy/recipe/  ✅ Correct: singular AND flat
├── zinc_ingot_from_smelting_zinc_ore.json
├── zinc_ingot_from_smelting_raw_zinc.json
├── zinc_ingot_from_blasting_zinc_ore.json
├── zinc_ingot_from_blasting_raw_zinc.json
└── zinc_block.json
```

All files must be directly in the `recipe/` folder. Recipe names must be unique and descriptive to avoid conflicts.

**Implementation Impact**: The `writeJson()` function currently creates parent directories with `out.parentFile?.mkdirs()`. This must be removed or modified to ensure no subdirectories are created. Recipe filenames must encode the recipe type (e.g., `zinc_ingot_from_smelting_zinc_ore.json` instead of `smelting/zinc_ingot_from_zinc_ore.json`).

### Required Changes in RecipeGen.kt

1. **Change folder name**: `"recipes"` → `"recipe"` (Line 18)
2. **Add cleanup function**: Delete old recipes from resources before generation
3. **Add copy function**: Copy generated recipes to resources (flat structure only)
4. **Add hardcoded recipe copy**: Copy hardcoded recipes from `common/src/tools/recipegen/recipe/` (overriding generated ones)
5. **Ensure flat structure**: All files written directly to `recipe/` with no subdirectories

```kotlin
fun main(args: Array<String>) {
    var outDir = "build/generated/data"
    // ... parse args ...
    
    // 1. Delete old recipes from resources
    deleteOldRecipes()
    
    // 2. Generate recipes to build/generated/data (FLAT - no subdirectories)
    val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    val recipesDir = dir(outDir, MOD_ID, "recipe")  // Fixed: "recipe" not "recipes"
    
    for (recipe in RecipeEntries.recipes) {
        val recipeJson = recipe.generateRecipe()
        // Write directly to recipe/ folder - NO subdirectories
        gson.writeJson(recipesDir, "${recipe.unlocalizedName}.json", recipeJson)
    }
    
    // 3. Copy generated recipes to resources (flat copy)
    val resourcesRecipeDir = File("common/src/main/resources/data/$MOD_ID/recipe")
    copyToResources(recipesDir, resourcesRecipeDir)
    
    // 4. Copy hardcoded recipes (overrides generated ones, flat copy)
    val hardcodedRecipeDir = File("common/src/tools/recipegen/recipe")
    if (hardcodedRecipeDir.exists()) {
        copyHardcodedRecipes(hardcodedRecipeDir, resourcesRecipeDir)
    }
    
    println("[RecipeGen] OK → $outDir")
}

private fun deleteOldRecipes() {
    // Delete from common/src/main/resources/data/metalmancy/recipes/ (old location)
    // Delete from common/src/main/resources/data/metalmancy/recipe/ (new location)
}

private fun copyToResources(sourceDir: File, targetDir: File) {
    // Copy from build/generated/data/metalmancy/recipe/
    // To common/src/main/resources/data/metalmancy/recipe/
    // FLAT copy - all files directly in target, NO subdirectories
    // Fail if source contains subdirectories
}

private fun copyHardcodedRecipes(sourceDir: File, targetDir: File) {
    // Copy from common/src/tools/recipegen/recipe/
    // To common/src/main/resources/data/metalmancy/recipe/
    // FLAT copy - all files directly in target, NO subdirectories
    // Overriding any generated files with the same name
    // Log when a hardcoded recipe overrides a generated one
}
```

### Hardcoded Recipe Override System

The generator supports hardcoded recipe files in `common/src/tools/recipegen/recipe/`. These files:
1. Are copied AFTER generated recipes
2. Override any generated recipe with the same filename
3. Allow manual customization of specific recipes
4. Are logged when they override a generated recipe

Example:
- Generated: `build/generated/data/metalmancy/recipe/zinc_ingot.json`
- Hardcoded: `common/src/tools/recipegen/recipe/zinc_ingot.json`
- Result: Hardcoded version is used in resources, logged as override

### Why This Matters

Minecraft 1.21 (pack format 48+) changed datapack folder names from plural to singular:
- `recipes/` → `recipe/`
- `loot_tables/` → `loot_table/`
- `advancements/` → `advancement/`
- `functions/` → `function/`

Using the old plural names causes Minecraft to ignore the files entirely.

### Reference Document

The authoritative source for folder names is `common/src/tools/datapack-structure-1.21.10.md`, which documents the structure for Minecraft 1.21.10 (pack format 88.0).

## Future Considerations

1. **Other Generators**: Check if BlockGen, ItemGen, WorldgenGen, etc. also need similar fixes
2. **Validation Tool**: Consider creating a tool that validates generator output paths against the datapack structure reference
3. **Version Updates**: When updating to Minecraft 1.22+, consult the updated datapack structure reference
4. **Automated Testing**: Add CI tests that verify generated files are in correct locations
