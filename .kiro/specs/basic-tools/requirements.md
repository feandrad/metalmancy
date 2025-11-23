# Requirements Document - Basic Tools System

## Introduction

The Basic Tools System extends the Metalmancy mod to include craftable tools (sword, axe, pickaxe, shovel, and hoe) for all metallic materials. This system integrates with the existing material system and build-time generators to automatically create tool items, recipes, and assets for each metal tier. Tools will have appropriate mining levels, durability, and efficiency based on their material properties.

## Glossary

- **Tool**: A craftable item used for combat or resource gathering (sword, axe, pickaxe, shovel, hoe)
- **Tool Material**: The metal or alloy used to craft a tool, determining its properties
- **Mining Level**: The hardness tier a tool can mine (wood=0, stone=1, iron=2, diamond=3, netherite=4)
- **Durability**: The number of uses before a tool breaks
- **Efficiency**: The speed multiplier for mining/digging operations
- **Attack Damage**: The base damage dealt by a weapon tool
- **Attack Speed**: The cooldown between attacks for a weapon tool
- **Tool Generator**: Build-time tool that generates tool item registrations and recipes
- **Tier**: Minecraft's tool material system defining mining level, durability, speed, damage, and enchantability

## Requirements

### Requirement 1: Tool Material Tiers

**User Story:** As a mod developer, I want to define tool material tiers with custom properties for each metal, so that tools have appropriate properties based on their material.

#### Acceptance Criteria

1. WHEN a tool-enabled material is defined THEN the system SHALL allow specification of mining level, durability, efficiency, attack damage bonus, and enchantability
2. WHEN a material's tier is configured THEN the system SHALL accept a base tier category (copper-like, iron-like, gold-like, diamond-like, mystic) with optional property overrides
3. WHEN durability is specified for a material THEN the system SHALL use that value instead of the tier default
4. WHEN efficiency is specified for a material THEN the system SHALL use that value instead of the tier default
5. WHEN a material does not specify custom properties THEN the system SHALL use the default values for its tier category
6. WHEN tier defaults are needed THEN the system SHALL use: copper-like (level 1, 200 durability, 4.0 efficiency), iron-like (level 2, 250 durability, 6.0 efficiency), gold-like (level 0, 32 durability, 12.0 efficiency), diamond-like (level 3, 1561 durability, 8.0 efficiency), mystic (level 4, 2031 durability, 9.0 efficiency)

### Requirement 2: Tool-Enabled Materials Configuration

**User Story:** As a mod developer, I want to explicitly configure which materials should have tools, so that I can control which metals are suitable for tool crafting.

#### Acceptance Criteria

1. WHEN materials are defined THEN the system SHALL maintain a list of tool-enabled materials separate from the general material list
2. WHEN a material is marked as tool-enabled THEN the system SHALL verify the material has an INGOT part
3. WHEN tool-enabled materials are queried THEN the system SHALL provide a dedicated collection (e.g., Materials.TOOL_MATERIALS)
4. WHEN a material is not in the tool-enabled list THEN the system SHALL NOT generate tools for that material
5. WHEN the tool-enabled list is modified THEN the system SHALL regenerate only the affected tool items and recipes

### Requirement 3: Tool Item Registration

**User Story:** As a mod developer, I want to register tool items for tool-enabled materials, so that players can craft and use tools made from selected mod metals.

#### Acceptance Criteria

1. WHEN a material is in the tool-enabled list THEN the system SHALL create sword, axe, pickaxe, shovel, and hoe items for that material
2. WHEN a tool item is created THEN the system SHALL use the material's tool tier for properties
3. WHEN a tool item is registered THEN the system SHALL use the correct unlocalized name format (e.g., "zinc_sword", "brass_pickaxe")
4. WHEN tool items are registered THEN the system SHALL add them to the appropriate creative mode tab
5. WHEN a material without an INGOT part is in the tool-enabled list THEN the system SHALL log a warning and skip tool generation

### Requirement 4: Tool Crafting Recipes

**User Story:** As a player, I want to craft tools using material ingots and sticks, so that I can create tools from the mod's metals.

#### Acceptance Criteria

1. WHEN a sword recipe is generated THEN the system SHALL require 2 ingots and 1 stick in a vertical pattern
2. WHEN an axe recipe is generated THEN the system SHALL require 3 ingots and 2 sticks in the standard axe pattern
3. WHEN a pickaxe recipe is generated THEN the system SHALL require 3 ingots and 2 sticks in the standard pickaxe pattern
4. WHEN a shovel recipe is generated THEN the system SHALL require 1 ingot and 2 sticks in a vertical pattern
5. WHEN a hoe recipe is generated THEN the system SHALL require 2 ingots and 2 sticks in the standard hoe pattern
6. WHEN tool recipes are generated THEN the system SHALL use the material's ingot as the ingredient
7. WHEN tool recipes are generated THEN the system SHALL use minecraft:stick as the handle ingredient

### Requirement 4.1: Recipe Advancements

**User Story:** As a player, I want tool recipes to unlock in my recipe book when I obtain the required materials, so that I can discover crafting recipes naturally.

#### Acceptance Criteria

1. WHEN a tool crafting recipe is generated THEN the system SHALL generate a corresponding recipe advancement JSON file
2. WHEN a recipe advancement is created THEN the system SHALL set "minecraft:recipes/root" as the parent
3. WHEN a recipe advancement is created THEN the system SHALL include an "inventory_changed" trigger for having the material's ingot
4. WHEN a recipe advancement is created THEN the system SHALL include an "inventory_changed" trigger for having minecraft:stick
5. WHEN a recipe advancement is created THEN the system SHALL include a "recipe_unlocked" trigger for the specific recipe
6. WHEN a recipe advancement is created THEN the system SHALL set requirements as an array containing both "has_the_recipe" and the material criteria
7. WHEN a recipe advancement is created THEN the system SHALL set rewards to unlock the corresponding recipe

### Requirement 4.2: Tool Recycling Recipes

**User Story:** As a player, I want to recycle broken or unwanted tools back into nuggets, so that I can recover some of the material.

#### Acceptance Criteria

1. WHEN a tool smelting recipe is generated THEN the system SHALL accept the tool as input and produce nuggets as output
2. WHEN a tool blasting recipe is generated THEN the system SHALL accept the tool as input and produce nuggets as output with faster cooking time
3. WHEN a sword is smelted THEN the system SHALL yield 2 nuggets (2 ingots worth)
4. WHEN an axe is smelted THEN the system SHALL yield 3 nuggets (3 ingots worth)
5. WHEN a pickaxe is smelted THEN the system SHALL yield 3 nuggets (3 ingots worth)
6. WHEN a shovel is smelted THEN the system SHALL yield 1 nugget (1 ingot worth)
7. WHEN a hoe is smelted THEN the system SHALL yield 2 nuggets (2 ingots worth)
8. WHEN tool recycling recipes are generated THEN the system SHALL set appropriate experience values (0.1 per nugget)

### Requirement 5: Tool Item and Model Generation

**User Story:** As a mod developer, I want to automatically generate tool items and their models, so that tools appear correctly in-game without manual JSON creation.

#### Acceptance Criteria

1. WHEN the tool generator runs THEN the system SHALL generate item registration code for each tool (sword, axe, pickaxe, shovel, hoe)
2. WHEN tool items are generated THEN the system SHALL create proper Minecraft tool item instances with correct properties
3. WHEN tool item models are generated THEN the system SHALL create JSON files in assets/metalmancy/models/item/
4. WHEN tool item models are generated THEN the system SHALL reference the correct texture path (e.g., "metalmancy:item/zinc_sword")
5. WHEN tool item models are generated THEN the system SHALL use the "minecraft:item/handheld" parent model for all tools
6. WHEN tool item render files are generated THEN the system SHALL create JSON files in assets/metalmancy/items/
7. WHEN the generation completes THEN the system SHALL report the number of tool items, models, and recipes generated

### Requirement 6: Tool Properties by Type

**User Story:** As a player, I want tools to have appropriate attack damage and speed values, so that different tool types feel distinct.

#### Acceptance Criteria

1. WHEN a sword is created THEN the system SHALL apply +3 attack damage and -2.4 attack speed modifiers
2. WHEN an axe is created THEN the system SHALL apply appropriate attack damage based on tier (+6 to +9) and -3.0 to -3.2 attack speed
3. WHEN a pickaxe is created THEN the system SHALL apply +1 attack damage and -2.8 attack speed modifiers
4. WHEN a shovel is created THEN the system SHALL apply +1.5 attack damage and -3.0 attack speed modifiers
5. WHEN a hoe is created THEN the system SHALL apply +0 attack damage and variable attack speed based on tier

### Requirement 7: Tool Durability and Repair

**User Story:** As a player, I want tools to have durability that can be repaired, so that I can maintain my tools over time.

#### Acceptance Criteria

1. WHEN a tool is used THEN the system SHALL decrease its durability by 1 per use
2. WHEN a tool reaches 0 durability THEN the system SHALL break the tool and play the break sound
3. WHEN a tool is repaired in an anvil THEN the system SHALL accept the material's ingot as repair material
4. WHEN a tool is repaired THEN the system SHALL restore durability based on the ingot's repair value
5. WHEN tools are enchanted with Unbreaking THEN the system SHALL reduce durability loss according to the enchantment level

### Requirement 8: Mining Level Enforcement

**User Story:** As a player, I want tools to only mine blocks appropriate for their tier, so that progression feels meaningful.

#### Acceptance Criteria

1. WHEN a tool mines a block THEN the system SHALL check if the tool's mining level is sufficient for that block
2. WHEN a tool's mining level is insufficient THEN the system SHALL prevent the block from dropping items
3. WHEN copper-like metal tools are used THEN the system SHALL mine stone-tier blocks (iron ore, lapis ore, etc.)
4. WHEN iron-like metal tools are used THEN the system SHALL mine iron-tier blocks (gold ore, diamond ore, redstone ore, etc.)
5. WHEN diamond-like metal tools are used THEN the system SHALL mine diamond-tier blocks (obsidian, ancient debris, etc.)
6. WHEN mystic metal tools are used THEN the system SHALL mine all blocks including netherite-tier blocks

### Requirement 9: Tool Generator Integration

**User Story:** As a mod developer, I want a build-time tool generator, so that tool registration and recipes are generated automatically.

#### Acceptance Criteria

1. WHEN the tool generator is executed THEN the system SHALL generate Kotlin code for tool item registration
2. WHEN the tool generator is executed THEN the system SHALL generate recipe JSON files for all tool crafting recipes
3. WHEN the tool generator is executed THEN the system SHALL generate item model JSON files for all tools
4. WHEN the tool generator is executed THEN the system SHALL integrate with the existing Gradle build system
5. WHEN the tool generator completes THEN the system SHALL copy generated files to the appropriate resource directories

### Requirement 10: Creative Mode Tab Integration

**User Story:** As a player, I want to find tools in the creative mode inventory, so that I can easily access them in creative mode.

#### Acceptance Criteria

1. WHEN the creative mode tab is opened THEN the system SHALL display all tool items organized by material
2. WHEN tools are displayed THEN the system SHALL group them by tool type (all swords together, all pickaxes together, etc.)
3. WHEN tools are displayed THEN the system SHALL order materials by tier (copper-like, iron-like, gold-like, diamond-like, mystic)
4. WHEN the mod's creative tab is populated THEN the system SHALL include tools after material items (ores, ingots, etc.)
5. WHEN tools are added to creative tabs THEN the system SHALL use the existing Metalmancy creative tab

### Requirement 11: Enchantability

**User Story:** As a player, I want to enchant tools at enchanting tables, so that I can improve their capabilities.

#### Acceptance Criteria

1. WHEN a tool is placed in an enchanting table THEN the system SHALL allow enchantments based on the material's enchantability
2. WHEN copper-like metal tools are enchanted THEN the system SHALL use enchantability value of 14
3. WHEN iron-like metal tools are enchanted THEN the system SHALL use enchantability value of 14
4. WHEN gold-like metal tools are enchanted THEN the system SHALL use enchantability value of 22
5. WHEN diamond-like metal tools are enchanted THEN the system SHALL use enchantability value of 10
6. WHEN mystic metal tools are enchanted THEN the system SHALL use enchantability value of 15
