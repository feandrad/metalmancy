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
