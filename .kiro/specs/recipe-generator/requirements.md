# Requirements Document - Recipe Generator Datapack Structure Compliance

## Introduction

The Recipe Generator is a build-time tool that automatically creates Minecraft recipe JSON files for the Metalmancy mod. The generator must strictly follow the datapack folder structure defined in `common/src/tools/datapack-structure-1.21.10.md` to ensure Minecraft 1.21.10 can properly load all generated files. This spec ensures the generator uses the correct paths for all output files according to the authoritative datapack structure reference.

## Glossary

- **Recipe Generator**: Build-time tool that creates recipe JSON files (RecipeGen.kt)
- **Datapack Structure**: The folder organization defined for Minecraft 1.21.10 (pack format 88.0)
- **Output Path**: The directory where generated recipe files are written
- **Namespace**: The mod identifier used in paths (e.g., "metalmancy")

## Requirements

### Requirement 1: Follow Datapack Structure Reference for All Paths

**User Story:** As a mod developer, I want the recipe generator to always follow the datapack structure reference document, so that all generated files are placed in the correct locations for Minecraft 1.21.10.

#### Acceptance Criteria

1. WHEN determining output paths THEN the system SHALL consult `common/src/tools/datapack-structure-1.21.10.md` as the authoritative source
2. WHEN the datapack structure shows `recipe/` THEN the system SHALL use `"recipe"` not `"recipes"` in the path
3. WHEN the datapack structure shows any folder name THEN the system SHALL use that exact name (including singular vs plural)
4. WHEN generating files THEN the system SHALL construct paths as `<base>/data/<namespace>/<folder>/` where `<folder>` matches the datapack structure exactly
5. WHEN the datapack structure reference is updated for future Minecraft versions THEN the system SHALL use the updated folder names

### Requirement 2: Clean Up Old Generated Recipes

**User Story:** As a mod developer, I want old incorrectly-placed recipes to be deleted before generation, so that only correctly-placed recipes exist in the resources folder.

#### Acceptance Criteria

1. WHEN the generator starts THEN the system SHALL delete existing recipe files from the old `recipes/` folder
2. WHEN the generator starts THEN the system SHALL delete existing recipe files from the resources folder
3. WHEN old files are deleted THEN the system SHALL log which files were removed
4. WHEN deletion fails THEN the system SHALL continue with generation and log a warning
5. WHEN deletion is complete THEN the system SHALL proceed with generating new recipes

### Requirement 3: Copy Generated Files to Resources

**User Story:** As a mod developer, I want generated recipe files to be automatically copied to the resources folder, so that they're included in the mod build.

#### Acceptance Criteria

1. WHEN recipes are generated THEN the system SHALL copy all files from `build/generated/data/metalmancy/recipe/` to `common/src/main/resources/data/metalmancy/recipe/`
2. WHEN copying files THEN the system SHALL preserve the subdirectory structure (e.g., `smelting/`, `blasting/`)
3. WHEN copying files THEN the system SHALL create parent directories if they don't exist
4. WHEN copying files THEN the system SHALL log each file that is copied
5. WHEN copying is complete THEN the system SHALL report the total number of files copied

### Requirement 4: Copy Hardcoded Recipe Overrides

**User Story:** As a mod developer, I want to manually override specific generated recipes with hardcoded JSON files, so that I can customize recipes that need special handling.

#### Acceptance Criteria

1. WHEN hardcoded recipes exist in `common/src/tools/recipegen/recipe/` THEN the system SHALL copy them to the resources folder AFTER generated recipes
2. WHEN a hardcoded recipe has the same filename as a generated recipe THEN the system SHALL override the generated recipe with the hardcoded one
3. WHEN a hardcoded recipe overrides a generated recipe THEN the system SHALL log a message indicating the override
4. WHEN hardcoded recipes are copied THEN the system SHALL preserve subdirectory structure
5. WHEN no hardcoded recipes exist THEN the system SHALL skip this step and continue normally

### Requirement 5: Logging and Reporting

**User Story:** As a mod developer, I want detailed logging of the generation process, so that I can verify what files were generated, copied, and overridden.

#### Acceptance Criteria

1. WHEN old recipes are deleted THEN the system SHALL log the number of files deleted
2. WHEN recipes are generated THEN the system SHALL log each recipe file written to `build/generated/`
3. WHEN recipes are copied to resources THEN the system SHALL log each file copied
4. WHEN a hardcoded recipe overrides a generated recipe THEN the system SHALL log a warning message with both filenames
5. WHEN the generator completes THEN the system SHALL print a summary with total files generated, copied, and overridden
