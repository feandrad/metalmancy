# Implementation Plan - Recipe Generator Datapack Structure Compliance

- [x] 1. Fix recipe output path from plural to singular
  - Change `"recipes"` to `"recipe"` in RecipeGen.kt line 18
  - Add comment referencing datapack structure document
  - _Requirements: 1.2, 1.3, 1.4_

- [x] 2. Ensure flat file structure (no subdirectories)
  - Review how recipe filenames are generated in RecipeEntries
  - Ensure filenames include recipe type prefix (e.g., `smelting_`, `blasting_`)
  - Modify `writeJson()` to prevent subdirectory creation
  - Remove or modify `out.parentFile?.mkdirs()` logic
  - _Requirements: 1.2, 1.4_

- [x] 2.1 Write unit test for flat structure
  - **Example 1: Generated files use correct datapack structure path**
  - **Validates: Requirements 1.2, 1.3, 1.4**

- [x] 3. Implement cleanup function
- [x] 3.1 Create `deleteOldRecipes()` function
  - Delete files from `common/src/main/resources/data/metalmancy/recipes/` (old plural location)
  - Delete files from `common/src/main/resources/data/metalmancy/recipe/` (new singular location)
  - Handle missing directories gracefully
  - Count deleted files
  - _Requirements: 2.1, 2.2_

- [x] 3.2 Add deletion logging
  - Log each file deleted
  - Log total count of deleted files
  - Log warnings if deletion fails but continue execution
  - _Requirements: 2.3, 2.4, 5.1_

- [x] 3.3 Write unit test for cleanup
  - **Example 2: Old recipes are cleaned up before generation**
  - **Validates: Requirements 2.1, 2.2, 2.5**

- [x] 3.4 Write unit test for deletion logging
  - **Example 5: Deletion count is logged**
  - **Validates: Requirements 2.3, 5.1**

- [x] 4. Implement copy to resources function
- [x] 4.1 Create `copyToResources(sourceDir: File, targetDir: File): Int` function
  - Copy all files from source to target (flat structure only)
  - Create target directory if it doesn't exist
  - Fail or warn if source contains subdirectories
  - Count copied files and return count
  - _Requirements: 3.1, 3.3_

- [x] 4.2 Add copy logging
  - Log each file copied with source and destination paths
  - Log total count of copied files
  - _Requirements: 3.4, 5.3_

- [x] 4.3 Write unit test for copy function
  - **Example 3: Generated recipes are copied to resources in flat structure**
  - **Validates: Requirements 3.1**

- [x] 4.4 Write unit test for copy logging
  - **Example 6: Copy operations are logged**
  - **Validates: Requirements 3.4, 5.3**

- [x] 5. Implement hardcoded recipe override function
- [x] 5.1 Create `copyHardcodedRecipes(sourceDir: File, targetDir: File): Int` function
  - Copy files from `common/src/tools/recipegen/recipe/` to resources
  - Overwrite any existing files with same name
  - Handle missing source directory gracefully (skip if doesn't exist)
  - Fail or warn if source contains subdirectories
  - Count overridden files and return count
  - _Requirements: 4.1, 4.2, 4.5_

- [x] 5.2 Add override logging
  - Log warning when a hardcoded recipe overrides a generated one
  - Include both source and destination filenames in log
  - Log total count of overridden files
  - _Requirements: 4.3, 5.4_

- [x] 5.3 Write unit test for override function
  - **Example 4: Hardcoded recipes override generated recipes**
  - **Validates: Requirements 4.1, 4.2**

- [x] 5.4 Write unit test for override logging
  - **Example 7: Override operations are logged**
  - **Validates: Requirements 4.3, 5.4**

- [x] 6. Update main() function to orchestrate all phases
- [x] 6.1 Add cleanup phase call
  - Call `deleteOldRecipes()` before generation
  - _Requirements: 2.5_

- [x] 6.2 Add copy phase call
  - Call `copyToResources()` after generation
  - Pass correct source and target directories
  - Store returned count
  - _Requirements: 3.5_

- [x] 6.3 Add override phase call
  - Check if hardcoded recipe directory exists
  - Call `copyHardcodedRecipes()` if it exists
  - Store returned count
  - _Requirements: 4.5_

- [x] 6.4 Update final summary message
  - Print total files generated (from RecipeEntries.recipes.size)
  - Print total files copied (from copyToResources return value)
  - Print total files overridden (from copyHardcodedRecipes return value)
  - Format: `[RecipeGen] Complete: X generated, Y copied, Z overridden`
  - _Requirements: 5.5_

- [x] 6.5 Write unit test for summary message
  - **Example 8: Summary is printed on completion**
  - **Validates: Requirements 5.5**

- [-] 7. Validate recipe JSON structure compliance
- [x] 7.1 Review existing recipe classes for JSON structure
  - Check SmeltingRecipe, BlastingRecipe, ShapedRecipe, ShapelessRecipe
  - Verify they generate JSON matching datapack structure reference
  - Identify any missing or incorrect fields
  - _Requirements: 6.1, 6.2, 6.3, 6.4, 6.5_

- [x] 7.2 Write unit test for smelting recipe structure
  - **Example 9: Smelting recipes have correct JSON structure**
  - **Validates: Requirements 6.1, 6.5**

- [x] 7.3 Write unit test for blasting recipe structure
  - **Example 10: Blasting recipes have correct JSON structure**
  - **Validates: Requirements 6.2, 6.5**

- [x] 7.4 Write unit test for shapeless recipe structure
  - **Example 11: Shapeless recipes have correct JSON structure**
  - **Validates: Requirements 6.3, 6.5**

- [x] 7.5 Write unit test for shaped recipe structure
  - **Example 12: Shaped recipes have correct JSON structure**
  - **Validates: Requirements 6.4, 6.5**

- [x] 7.6 Fix any recipe structure issues found
  - Update recipe classes to match datapack structure
  - Ensure all required fields are present
  - Ensure correct field types and values
  - _Requirements: 6.1, 6.2, 6.3, 6.4, 6.5_

- [x] 8. Checkpoint - Ensure all tests pass
  - Ensure all tests pass, ask the user if questions arise.
