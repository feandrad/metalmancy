# Implementation Plan - Basic Tools System

## Overview
This plan implements a build-time tool generation system for the Metalmancy mod, creating craftable tools (sword, axe, pickaxe, shovel, hoe) for selected metallic materials. The implementation follows the existing generator pattern (ItemGen, RecipeGen, BlockGen) and integrates with the current material system.

---

## Phase 1: Core Tool System Foundation

- [x] 1. Create tool tier system and configuration
  - Create `common/src/tools/toolgen/ToolTier.kt` with tier data class and tier categories
  - Define tier constants: COPPER_LIKE, IRON_LIKE, GOLD_LIKE, DIAMOND_LIKE, MYSTIC
  - Implement tier property system with override support
  - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5, 1.6_

- [x] 1.1 Write property test for tier configuration
  - **Property 1: Tool tier configuration accepts all required properties**
  - **Validates: Requirements 1.1**

- [x] 1.2 Write property test for tier overrides
  - **Property 2: Tier categories accept base values and overrides**
  - **Validates: Requirements 1.2, 1.3, 1.4, 1.5**

- [x] 2. Create tool-enabled materials configuration
  - Create `common/src/tools/toolgen/ToolMaterials.kt` with tool-enabled materials list
  - Add materials: BRASS, BRONZE, SILVER, COBALT, ORICHALCUM, MITHRIL, PLATINUM, TITANIUM, ELECTRUM, TOPAZ, RUBY, SAPPHIRE, ALUMINUM, STEEL
  - Implement material-to-tier mapping function
  - Add validation for INGOT/GEM part requirements
  - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 2.6_

- [x] 2.1 Write property test for tool-enabled material validation
  - **Property 3: Tool-enabled materials require INGOT parts**
  - **Validates: Requirements 2.2**

- [x] 2.2 Write property test for non-tool-enabled materials
  - **Property 4: Non-tool-enabled materials generate no tools**
  - **Validates: Requirements 2.4**

- [x] 3. Create tool type enumeration
  - Create `common/src/tools/toolgen/ToolType.kt` enum with all five tool types
  - Define attack damage, attack speed, and ingot count for each type
  - Include unlocalized name suffix for each tool type
  - _Requirements: 3.1, 3.3, 6.1, 6.2, 6.3, 6.4, 6.5_

---

## Phase 2: Tool Generation Core

- [x] 4. Implement tool item generation
  - Create `common/src/tools/toolgen/GeneratedTool.kt` data class
  - Implement tool item registration code generation
  - Generate proper unlocalized names (e.g., "brass_sword")
  - _Requirements: 3.1, 3.3, 5.1_

- [x] 4.1 Write property test for tool generation
  - **Property 5: Tool-enabled materials generate all five tool types**
  - **Validates: Requirements 3.1**

- [x] 4.2 Write property test for tool naming
  - **Property 6: Tool unlocalized names follow naming convention**
  - **Validates: Requirements 3.3**

- [x] 4.3 Write property test for invalid material handling
  - **Property 8: Invalid materials log warnings and skip generation**
  - **Validates: Requirements 3.5**

- [x] 5. Implement tool model generation
  - Add item model JSON generation to GeneratedTool
  - Implement two-layer texture system (handle + head)
  - Map handle textures by tool type (wooden_handle, wooden_sword_handle, wooden_shovel_handle)
  - Generate material-specific head texture paths
  - _Requirements: 5.3, 5.4, 5.5, 5.6, 5.7, 5.8, 5.9_

- [x] 5.1 Write property test for model structure
  - **Property 21: Tool item models use handheld parent**
  - **Property 22: Tool models use two-layer texture structure**
  - **Validates: Requirements 5.4, 5.5**

- [x] 5.2 Write property test for handle texture mapping
  - **Property 23: Tool handle textures map correctly by type**
  - **Validates: Requirements 5.6, 5.7, 5.8**

- [x] 5.3 Write property test for head texture paths
  - **Property 24: Tool head textures use material-specific paths**
  - **Validates: Requirements 5.9**

---

## Phase 3: Recipe Generation

- [x] 6. Implement crafting recipe generation
  - Create `common/src/tools/toolgen/ToolRecipes.kt` with recipe generation functions
  - Implement sword pattern (2 ingots + 1 stick vertical)
  - Implement axe pattern with mirrored variant (3 ingots + 2 sticks)
  - Implement pickaxe pattern (3 ingots + 2 sticks)
  - Implement shovel pattern (1 ingot + 2 sticks vertical)
  - Implement hoe pattern with mirrored variant (2 ingots + 2 sticks)
  - Use minecraft:stick as handle ingredient
  - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 4.7_

- [x] 6.1 Write property test for recipe patterns
  - **Property 9: Tool recipes follow standard crafting patterns**
  - **Property 10: Tool recipes use minecraft:stick as handle**
  - **Validates: Requirements 4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 4.7**

- [x] 7. Implement recipe advancement generation
  - Add recipe advancement JSON generation to ToolRecipes
  - Set parent to "minecraft:recipes/root"
  - Add inventory_changed triggers for ingot and stick
  - Add recipe_unlocked trigger
  - Configure requirements array with proper OR/AND logic
  - Set rewards to unlock corresponding recipe
  - _Requirements: 4.1.1, 4.1.2, 4.1.3, 4.1.4, 4.1.5, 4.1.6, 4.1.7_

- [x] 7.1 Write property test for advancement generation
  - **Property 11: Recipe advancements generated for all tool recipes**
  - **Property 12: Recipe advancements have correct parent**
  - **Property 13: Recipe advancements include required triggers**
  - **Property 14: Recipe advancements have correct requirements structure**
  - **Property 15: Recipe advancements unlock corresponding recipes**
  - **Validates: Requirements 4.1.1, 4.1.2, 4.1.3, 4.1.4, 4.1.5, 4.1.6, 4.1.7**

- [x] 8. Implement tool recycling recipes
  - Add smelting recipe generation (200 ticks cooking time)
  - Add blasting recipe generation (100 ticks cooking time)
  - Calculate nugget yield based on ingot count (sword: 2, axe: 3, pickaxe: 3, shovel: 1, hoe: 2)
  - Set experience to 0.1 per nugget
  - _Requirements: 4.2.1, 4.2.2, 4.2.3, 4.2.4, 4.2.5, 4.2.6, 4.2.7, 4.2.8_

- [x] 8.1 Write property test for recycling recipes
  - **Property 16: Tool smelting recipes produce nuggets**
  - **Property 17: Tool blasting recipes are faster than smelting**
  - **Property 18: Recycling nugget yield matches ingot cost**
  - **Property 19: Recycling experience scales with nugget yield**
  - **Validates: Requirements 4.2.1, 4.2.2, 4.2.3, 4.2.4, 4.2.5, 4.2.6, 4.2.7, 4.2.8**

---

## Phase 4: Main Generator Implementation

- [x] 9. Create ToolGen main generator
  - Create `common/src/tools/toolgen/ToolGen.kt` with main function
  - Parse command-line arguments (--out for output directory)
  - Iterate through tool-enabled materials and tool types
  - Generate all tool items, models, recipes, advancements, and recycling recipes
  - Write files to build/generated/ directory
  - Copy files to src/main/resources/ following datapack structure
  - Report generation statistics (items, models, recipes, language entries)
  - _Requirements: 5.1, 5.12, 9.1, 9.2, 9.3, 9.5, 9.6_

- [x] 9.1 Write property test for generator output
  - **Property 20: Tool generator produces registration code**
  - **Property 31: Recipe JSONs are generated for all tools**
  - **Property 32: Model JSONs are generated for all tools**
  - **Property 33: Generated files are copied to resources**
  - **Property 34: Generated files follow datapack structure**
  - **Validates: Requirements 5.1, 9.1, 9.2, 9.3, 9.5, 9.6**

- [x] 9.2 Write property test for generation statistics
  - **Property 28: Generator reports generation statistics**
  - **Validates: Requirements 5.12**

- [x] 10. Implement language file generation
  - Add en_us.json generation for tool localized names
  - Generate entries for all tools (e.g., "item.metalmancy.brass_sword": "Brass Sword")
  - Merge with existing language file if present
  - _Requirements: 5.11_

- [x] 10.1 Write property test for language generation
  - **Property 27: Tool language entries are generated**
  - **Validates: Requirements 5.11**

---

## Phase 5: Build System Integration

- [x] 11. Create Gradle build integration
  - Create `common/gradle/toolgen.gradle` following existing generator pattern
  - Add generateTools task with JavaExec configuration
  - Add syncGeneratedTools task for copying to resources
  - Configure task dependencies (cleanGeneratedAssets → generateTools → syncGeneratedTools)
  - Add toolgen to generateAll and syncGeneratedAll tasks
  - _Requirements: 9.4_

- [x] 12. Update build.gradle to include toolgen
  - Add toolgen source directory to sourceSets.main.kotlin
  - Apply toolgen.gradle script
  - Update generateAll task to depend on generateTools
  - Update syncGeneratedAll task to depend on syncGeneratedTools
  - _Requirements: 9.4_

- [x] 13. Test build integration
  - Run `./gradlew :common:generateTools` to verify generation
  - Run `./gradlew :common:syncGeneratedTools` to verify file copying
  - Run `./gradlew :common:generateAll` to verify full integration
  - Verify generated files are in correct locations
  - _Requirements: 9.4, 9.5_

---

## Phase 6: Runtime Tool Registration

- [x] 14. Create tool items registry
  - Create `common/src/main/kotlin/io/felipeandrade/metalmancy/items/ToolItems.kt`
  - Implement tool tier creation from ToolTier configuration
  - Register all tool items using platform-specific registration
  - Add tools to Metalmancy creative tab
  - Set repair materials to material ingots
  - _Requirements: 3.1, 3.2, 3.4, 7.3, 7.4, 10.1, 10.4, 10.5_

- [x] 14.1 Write property test for creative tab assignment
  - **Property 7: Tools are assigned to correct creative tab**
  - **Validates: Requirements 3.4, 10.5**

- [x] 15. Implement platform-specific tool registration
  - Add NeoForge tool registration in `neoforge/src/main/java/io/felipeandrade/metalmancy/neoforge/MetalmancyNeoForge.java`
  - Add Fabric tool registration in `fabric/src/main/java/io/felipeandrade/metalmancy/fabric/MetalmancyFabric.java`
  - Ensure tools use correct tier properties on both platforms
  - _Requirements: 3.1, 3.2, 6.1, 6.2, 6.3, 6.4, 6.5, 8.1, 8.2, 8.3, 8.4, 8.5, 8.6_

- [x] 15.1 Write property test for axe attack properties
  - **Property 29: Axe attack properties vary by tier**
  - **Validates: Requirements 6.2**

- [x] 15.2 Write property test for hoe attack properties
  - **Property 30: Hoe attack properties vary by tier**
  - **Validates: Requirements 6.5**

---

## Phase 7: Testing and Validation

- [x] 16. Create test infrastructure
  - Create `common/src/test/kotlin/io/felipeandrade/metalmancy/tools/toolgen/` directory
  - Create custom Kotest generators for ToolTier, ToolType, and tool-enabled materials
  - Set up test configuration for 100+ iterations per property test
  - _Design: Testing Strategy_

- [-] 17. Run all property tests
  - Execute `./gradlew :common:test` to run all property tests
  - Verify all 30+ properties pass with diverse inputs
  - Fix any failing tests by correcting implementation
  - _Design: Testing Strategy_

- [ ] 18. Checkpoint - Ensure all tests pass
  - Ensure all tests pass, ask the user if questions arise.

---

## Phase 8: Final Integration and Verification

- [ ] 19. Generate all tool assets
  - Run `./gradlew :common:generateAll` to generate all tools
  - Verify tool item models in `assets/metalmancy/models/item/`
  - Verify tool recipes in `data/metalmancy/recipe/`
  - Verify recipe advancements in `data/metalmancy/advancement/recipe/`
  - Verify language entries in `assets/metalmancy/lang/en_us.json`
  - _Requirements: 5.3, 5.10, 5.11, 9.2, 9.3, 9.6_

- [ ] 20. Build and test the mod
  - Run `./gradlew :neoforge:build` to build NeoForge version
  - Run `./gradlew :fabric:build` to build Fabric version
  - Verify no compilation errors
  - Verify tools are registered at runtime
  - _Requirements: 3.1, 3.4, 10.1, 10.5_

- [ ] 21. Final checkpoint - Verify complete implementation
  - Ensure all tests pass, ask the user if questions arise.

---

## Notes

- **Testing Approach**: Property-based tests use Kotest with 100+ iterations per property
- **Generator Pattern**: Follows existing ItemGen/RecipeGen/BlockGen patterns
- **Datapack Structure**: All JSON files follow Minecraft 1.21.10 datapack specification
- **Platform Support**: Tools work on both NeoForge and Fabric platforms
- **Comprehensive Testing**: All property-based tests are required for correctness validation
