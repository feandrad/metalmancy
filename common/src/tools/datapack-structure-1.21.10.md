# Minecraft 1.21.10 Datapack Structure Reference

**Pack Format:** 88.0  
**Minecraft Versions:** 1.21.9 - 1.21.10  
**Extracted:** November 22, 2025  
**Source:** https://minecraft.wiki/w/Data_pack

> **Note:** This is a static reference document for Minecraft 1.21.10. It will not be updated when the wiki changes for future versions.

## Pack Format 88.0 Changes

Pack format now includes minor versions, which are incremented instead of the major version when non-breaking changes are made.
- `minecraft:profile` components now resolve differently
- World borders are now dimension-specific

## Datapack Folder Structure

```
<datapack_name>/
├── pack.mcmeta (required)
├── pack.png (optional)
└── data/
    └── <namespace>/  (e.g., "metalmancy", "minecraft")
        ├── advancement/          - Advancement definitions (.json)
        ├── banner_pattern/ *     - Banner pattern definitions (.json)
        ├── cat_variant/ *        - Cat variant definitions (.json)
        ├── chat_type/ *          - Chat message formatting (.json)
        ├── chicken_variant/ *    - Chicken variant definitions (.json)
        ├── cow_variant/ *        - Cow variant definitions (.json)
        ├── damage_type/ *        - Damage types and death messages (.json)
        ├── dialog/ *             - Dialog definitions (.json)
        ├── dimension/ *          - Dimension biome layout and terrain (.json)
        ├── dimension_type/ *     - Dimension properties (.json)
        ├── enchantment/ *        - Enchantment definitions (.json)
        ├── enchantment_provider/ - Enchantment selection (.json)
        ├── frog_variant/ *       - Frog variant definitions (.json)
        ├── function/             - Command functions (.mcfunction)
        ├── instrument/ *         - Goat horn instruments (.json)
        ├── item_modifier/        - Loot functions for items (.json)
        ├── jukebox_song/ *       - Jukebox song definitions (.json)
        ├── loot_table/           - Loot tables (.json)
        ├── painting_variant/ *   - Painting definitions (.json)
        ├── pig_variant/ *        - Pig variant definitions (.json)
        ├── predicate/            - Condition tests (.json)
        ├── recipe/               - Crafting/smelting recipes (.json)
        ├── structure/            - Structure templates (.nbt)
        ├── tags/                 - Tag collections
        │   ├── banner_pattern/   - Banner pattern tags (.json)
        │   ├── biome/            - Biome tags (.json)
        │   ├── block/            - Block tags (.json)
        │   ├── damage_type/      - Damage type tags (.json)
        │   ├── dialog/           - Dialog tags (.json)
        │   ├── enchantment/      - Enchantment tags (.json)
        │   ├── entity_type/      - Entity type tags (.json)
        │   ├── flat_level_generator_preset/ - Flat preset tags (.json)
        │   ├── fluid/            - Fluid tags (.json)
        │   ├── function/         - Function tags (.json)
        │   ├── game_event/       - Game event tags (.json)
        │   ├── instrument/       - Instrument tags (.json)
        │   ├── item/             - Item tags (.json)
        │   ├── painting_variant/ - Painting variant tags (.json)
        │   ├── point_of_interest_type/ - POI type tags (.json)
        │   ├── structure/        - Structure tags (.json)
        │   └── world_preset/     - World preset tags (.json)
        ├── test_environment/ *   - GameTest environments (.json)
        ├── test_instance/ *      - GameTest instances (.json)
        ├── trial_spawner/ *      - Trial spawner configs (.json)
        ├── trim_material/ *      - Armor trim materials (.json)
        ├── trim_pattern/ *       - Armor trim patterns (.json)
        ├── wolf_sound_variant/ * - Wolf sound variants (.json)
        ├── wolf_variant/ *       - Wolf variant definitions (.json)
        └── worldgen/ *           - World generation configs
            ├── biome/            - Biome definitions (.json)
            ├── configured_carver/ - Carver configurations (.json)
            ├── configured_feature/ - Feature configurations (.json)
            ├── density_function/  - Density functions (.json)
            ├── flat_level_generator_preset/ - Flat presets (.json)
            ├── noise/            - Noise configurations (.json)
            ├── noise_settings/   - Noise settings (.json)
            ├── placed_feature/   - Placed features (.json)
            ├── processor_list/   - Structure processors (.json)
            ├── structure/        - Structure definitions (.json)
            ├── structure_set/    - Structure sets (.json)
            ├── template_pool/    - Template pools (.json)
            └── world_preset/     - World presets (.json)
```

**Legend:**
- `*` = Experimental feature (requires world restart, cannot use `/reload`, not compatible with Realms)

## Key Information for Generators

### Recipe Generator
- **Output Path:** `data/<namespace>/recipe/`
- **File Extension:** `.json`
- **Reloadable:** Yes (use `/reload` command)
- **Experimental:** No
- **Structure:** Flat - all recipe files directly in `recipe/` folder, NO subdirectories

#### Recipe Types

**1. Smelting Recipe (`minecraft:smelting`)**
```json
{
  "type": "minecraft:smelting",
  "category": "misc",
  "cookingtime": 200,
  "experience": 0.7,
  "group": "copper_ingot",
  "ingredient": "minecraft:copper_ore",
  "result": {
    "id": "minecraft:copper_ingot"
  }
}
```
- `cookingtime`: Ticks to smelt (200 = 10 seconds)
- `experience`: XP awarded per item
- `group`: Recipe book grouping (optional)
- `ingredient`: Input item (can be tag with `#` prefix)

**2. Blasting Recipe (`minecraft:blasting`)**
```json
{
  "type": "minecraft:blasting",
  "category": "misc",
  "cookingtime": 100,
  "experience": 0.7,
  "group": "copper_ingot",
  "ingredient": "minecraft:raw_copper",
  "result": {
    "id": "minecraft:copper_ingot"
  }
}
```
- Same structure as smelting
- `cookingtime`: Typically half of smelting time (100 = 5 seconds)

**3. Shapeless Crafting (`minecraft:crafting_shapeless`)**
```json
{
  "type": "minecraft:crafting_shapeless",
  "category": "misc",
  "group": "copper_ingot",
  "ingredients": [
    "minecraft:copper_block"
  ],
  "result": {
    "count": 9,
    "id": "minecraft:copper_ingot"
  }
}
```
- `ingredients`: Array of items (can be tags with `#` prefix)
- `result.count`: Number of items produced

**4. Shaped Crafting (`minecraft:crafting_shaped`)**
```json
{
  "type": "minecraft:crafting_shaped",
  "category": "misc",
  "group": "copper_ingot",
  "key": {
    "#": "minecraft:copper_nugget"
  },
  "pattern": [
    "###",
    "###",
    "###"
  ],
  "result": {
    "count": 1,
    "id": "minecraft:copper_ingot"
  }
}
```
- `pattern`: 1-3 strings representing crafting grid rows
- `key`: Maps pattern characters to items/tags
- `show_notification`: Optional, defaults to true

**5. Shaped Crafting with Tags**
```json
{
  "type": "minecraft:crafting_shaped",
  "category": "misc",
  "key": {
    "#": "#minecraft:planks"
  },
  "pattern": [
    "##",
    "##"
  ],
  "result": {
    "count": 1,
    "id": "minecraft:crafting_table"
  },
  "show_notification": false
}
```
- Tags use `#` prefix in key values
- `show_notification`: Hides recipe unlock notification

#### Recipe Categories
- `misc` - Miscellaneous items
- `building` - Building blocks
- `redstone` - Redstone components
- `equipment` - Tools and armor
- `food` - Food items

#### Common Fields
- `type`: Recipe type (required)
- `category`: Recipe book category (optional, defaults to `misc`)
- `group`: Groups similar recipes in recipe book (optional)
- `result`: Output item with optional count (required)

### Loot Table Generator
- **Output Path:** `data/<namespace>/loot_table/`
- **File Extension:** `.json`
- **Reloadable:** Yes (use `/reload` command)
- **Experimental:** No

### Worldgen Generator
- **Output Path:** `data/<namespace>/worldgen/`
- **Subdirectories:**
  - `configured_feature/` - Feature configurations
  - `placed_feature/` - Feature placements
- **File Extension:** `.json`
- **Reloadable:** No (requires world restart)
- **Experimental:** Yes

### Advancement Generator
- **Output Path:** `data/<namespace>/advancement/`
- **File Extension:** `.json`
- **Reloadable:** Yes (use `/reload` command)
- **Experimental:** No

### Tag Generator
- **Output Path:** `data/<namespace>/tags/<type>/`
- **Types:** block, item, entity_type, fluid, game_event, biome, etc.
- **File Extension:** `.json`
- **Reloadable:** Yes (use `/reload` command)
- **Experimental:** No
- **Special Behavior:** Tags merge unless `"replace": true` is set

## Namespace Guidelines

- **minecraft:** Reserved for vanilla content, can be overridden
- **metalmancy:** Use for all mod-specific content
- Multiple namespaces can coexist in a single datapack
- Files load based on datapack priority order

## pack.mcmeta Format

```json
{
  "pack": {
    "pack_format": 88,
    "description": "Metalmancy Data"
  }
}
```

## Important Notes

1. **Folder Naming:** As of 1.21, folders use singular names (e.g., `recipe` not `recipes`, `loot_table` not `loot_tables`)
2. **Experimental Features:** Folders marked with `*` require world restart to reload changes
3. **Tag Merging:** Tag files merge by default unless `"replace": true` is specified
4. **Namespace Separation:** Keep mod content in the `metalmancy` namespace, not `minecraft`
