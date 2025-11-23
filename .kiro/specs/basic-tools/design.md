# Design Document - Basic Tools System

## Overview

The Basic Tools System extends the Metalmancy mod to provide craftable tools (sword, axe, pickaxe, shovel, and hoe) for selected metallic materials. The system integrates with the existing build-time generator architecture, following the same patterns as ItemGen, RecipeGen, and BlockGen. Tools will be generated at build time with appropriate properties based on material tiers, including mining levels, durability, efficiency, attack damage, and enchantability.

The design follows Minecraft's tool tier system while allowing customization per material. A dedicated ToolGen generator will create tool item registrations, crafting recipes, recycling recipes, recipe advancements, and item models. The system maintains separation between tool-enabled materials and general materials, allowing explicit control over which metals can be crafted into tools.

## Architecture

### Component Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    Build-Time Generation                     │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────────┐      ┌──────────────┐                    │
│  │  Materials   │─────▶│  ToolGen     │                    │
│  │  Registry    │      │  Generator   │                    │
│  └──────────────┘      └──────┬───────┘                    │
│                               │                             │
│                               ├─────────────┐               │
│                               ▼             ▼               │
│                        ┌─────────────┐  ┌─────────────┐    │
│                        │ Tool Items  │  │  Recipes    │    │
│                        │ & Models    │  │ & Advmts    │    │
│                        └─────────────┘  └─────────────┘    │
└─────────────────────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                      Runtime System                          │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────────┐      ┌──────────────┐                    │
│  │  Tool Items  │─────▶│  Minecraft   │                    │
│  │  Registry    │      │  Tool System │                    │
│  └──────────────┘      └──────────────┘                    │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### Design Rationale

**Build-Time Generation:** Following the existing pattern of ItemGen, RecipeGen, and BlockGen, tools are generated at build time rather than runtime. This approach:
- Maintains consistency with existing mod architecture
- Allows easy inspection and debugging of generated files
- Reduces runtime overhead
- Enables manual overrides when needed

**Explicit Tool-Enabled Materials:** Rather than generating tools for all materials, we maintain a separate list of tool-enabled materials. This design:
- Provides explicit control over which materials have tools
- Prevents accidental tool generation for inappropriate materials (e.g., salts, mercury)
- Allows future expansion without breaking existing content
- Validates that materials have required parts (INGOT) before generation

**Tier-Based Properties with Overrides:** Materials are assigned to tier categories (copper-like, iron-like, etc.) with default properties, but can override specific values. This design:
- Simplifies configuration for most materials
- Provides flexibility for special cases
- Maintains balance across similar materials
- Follows Minecraft's existing tier system

## Components and Interfaces

### 1. Tool Tier System

**ToolTier Data Class**
```kotlin
data class ToolTier(
    val miningLevel: Int,
    val durability: Int,
    val efficiency: Float,
    val attackDamageBonus: Float,
    val enchantability: Int
)
```

**Tier Categories**
- **COPPER_LIKE**: Mining level 1, 200 durability, 4.0 efficiency, 14 enchantability
- **IRON_LIKE**: Mining level 2, 250 durability, 6.0 efficiency, 14 enchantability
- **GOLD_LIKE**: Mining level 0, 32 durability, 12.0 efficiency, 22 enchantability
- **DIAMOND_LIKE**: Mining level 3, 1561 durability, 8.0 efficiency, 10 enchantability
- **MYSTIC**: Mining level 4, 2031 durability, 9.0 efficiency, 15 enchantability

**Design Rationale:** These tiers mirror Minecraft's vanilla progression (wood → stone → iron → diamond → netherite) while providing appropriate slots for mod materials. Copper-like materials fill the gap between stone and iron, while mystic materials match or exceed netherite.

### 2. Tool-Enabled Materials Configuration

**ToolMaterials Object**
```kotlin
object ToolMaterials {
    val TOOL_ENABLED = listOf(
        Materials.BRASS,
        Materials.BRONZE,
        Materials.SILVER,
        Materials.COBALT,
        Materials.ORICHALCUM,
        Materials.MITHRIL,
        Materials.PLATINUM,
        Materials.TITANIUM,
        Materials.ELECTRUM,
        Materials.TOPAZ,
        Materials.RUBY,
        Materials.SAPPHIRE,
        Materials.ALUMINUM,
        Materials.STEEL
    )
    
    fun getTier(material: Material): ToolTier {
        // Returns tier based on material's property group
        // Allows per-material overrides
    }
}
```

**Design Rationale:** Separating tool-enabled materials from the general materials list provides explicit control and prevents accidental generation. The configuration validates that materials have INGOT parts (or GEM for gem materials) before allowing tool generation.

### 3. Tool Types

**ToolType Enum**
```kotlin
enum class ToolType(
    val unlocalizedSuffix: String,
    val attackDamage: Float,
    val attackSpeed: Float,
    val ingotCount: Int
) {
    SWORD("sword", 3.0f, -2.4f, 2),
    AXE("axe", 6.0f, -3.0f, 3),      // Varies by tier
    PICKAXE("pickaxe", 1.0f, -2.8f, 3),
    SHOVEL("shovel", 1.5f, -3.0f, 1),
    HOE("hoe", 0.0f, -3.0f, 2)        // Speed varies by tier
}
```

**Design Rationale:** Encapsulating tool properties in an enum provides type safety and ensures consistency. The ingotCount field is used for both crafting recipes and recycling recipes (determining nugget output).

### 4. ToolGen Generator

**Main Generator Class**
```kotlin
object ToolGen {
    fun main(args: Array<String>) {
        // Parse command-line arguments for output directory
        // Generate tool items and models
        // Generate crafting recipes
        // Generate recycling recipes (smelting/blasting)
        // Generate recipe advancements
        // Copy files to resources
        // Report generation summary
    }
}
```

**Generation Flow:**
1. Parse command-line arguments (--out for output directory)
2. Iterate through tool-enabled materials
3. For each material and tool type combination:
   - Generate tool item registration code
   - Generate item model JSON
   - Generate crafting recipe JSON
   - Generate recycling recipes (smelting and blasting)
   - Generate recipe advancement JSON
4. Write all files to build/generated/
5. Copy files to src/main/resources/
6. Report generation statistics

**Design Rationale:** Following the existing generator pattern (ItemGen, RecipeGen) ensures consistency and maintainability. The generator produces all necessary files in one pass, reducing build complexity.

## Data Models

### Tool Item Model JSON

**Structure:**
```json
{
  "parent": "minecraft:item/handheld",
  "textures": {
    "layer0": "metalmancy:item/wooden_handle",
    "layer1": "metalmancy:item/brass_pickaxe"
  }
}
```

**Handle Texture Mapping:**
- Axes, Pickaxes, Hoes: `wooden_handle`
- Swords: `wooden_sword_handle`
- Shovels: `wooden_shovel_handle`

**Design Rationale:** Using a two-layer texture system allows reusing handle textures across all tools while only requiring unique head textures per material. This reduces texture asset requirements significantly.

### Crafting Recipe JSON

**Sword Pattern:**
```json
{
  "type": "minecraft:crafting_shaped",
  "category": "equipment",
  "pattern": [
    "#",
    "#",
    "S"
  ],
  "key": {
    "#": "metalmancy:brass_ingot",
    "S": "minecraft:stick"
  },
  "result": {
    "id": "metalmancy:brass_sword",
    "count": 1
  }
}
```

**Axe Pattern (with mirrored variant):**
```json
{
  "pattern": [
    "##",
    "#S",
    " S"
  ]
}
// Mirrored:
{
  "pattern": [
    "##",
    "S#",
    "S "
  ]
}
```

**Pickaxe Pattern:**
```json
{
  "pattern": [
    "###",
    " S ",
    " S "
  ]
}
```

**Shovel Pattern:**
```json
{
  "pattern": [
    "#",
    "S",
    "S"
  ]
}
```

**Hoe Pattern (with mirrored variant):**
```json
{
  "pattern": [
    "##",
    " S",
    " S"
  ]
}
// Mirrored:
{
  "pattern": [
    "##",
    "S ",
    "S "
  ]
}
```

**Design Rationale:** Following Minecraft's standard tool crafting patterns ensures familiarity for players. Axes and hoes include mirrored variants to allow both left-handed and right-handed crafting orientations. Using shaped recipes rather than shapeless maintains the traditional crafting experience.

### Recycling Recipe JSON

**Smelting:**
```json
{
  "type": "minecraft:smelting",
  "category": "misc",
  "ingredient": "metalmancy:brass_sword",
  "result": {
    "id": "metalmancy:brass_nugget",
    "count": 2
  },
  "experience": 0.2,
  "cookingtime": 200
}
```

**Blasting:**
```json
{
  "type": "minecraft:blasting",
  "category": "misc",
  "ingredient": "metalmancy:brass_sword",
  "result": {
    "id": "metalmancy:brass_nugget",
    "count": 2
  },
  "experience": 0.2,
  "cookingtime": 100
}
```

**Nugget Yields:**
- Sword: 2 nuggets (2 ingots × 1 nugget/ingot)
- Axe: 3 nuggets (3 ingots × 1 nugget/ingot)
- Pickaxe: 3 nuggets (3 ingots × 1 nugget/ingot)
- Shovel: 1 nugget (1 ingot × 1 nugget/ingot)
- Hoe: 2 nuggets (2 ingots × 1 nugget/ingot)

**Experience:** 0.1 per nugget

**Design Rationale:** Recycling recipes provide a way to recover materials from broken or unwanted tools. The nugget yield matches the ingot cost, providing fair material recovery. Both smelting and blasting options give players flexibility in processing speed.

### Recipe Advancement JSON

**Structure:**
```json
{
  "parent": "minecraft:recipes/root",
  "criteria": {
    "has_the_recipe": {
      "trigger": "minecraft:recipe_unlocked",
      "conditions": {
        "recipe": "metalmancy:brass_sword"
      }
    },
    "has_brass_ingot": {
      "trigger": "minecraft:inventory_changed",
      "conditions": {
        "items": [
          {
            "items": ["metalmancy:brass_ingot"]
          }
        ]
      }
    },
    "has_stick": {
      "trigger": "minecraft:inventory_changed",
      "conditions": {
        "items": [
          {
            "items": ["minecraft:stick"]
          }
        ]
      }
    }
  },
  "requirements": [
    ["has_the_recipe"],
    ["has_brass_ingot", "has_stick"]
  ],
  "rewards": {
    "recipes": ["metalmancy:brass_sword"]
  }
}
```

**Design Rationale:** Recipe advancements unlock recipes in the player's recipe book when they obtain the required materials. The requirements array uses OR logic within sub-arrays and AND logic between them, meaning players need either the recipe unlocked OR (the ingot AND stick).


## Correctness Properties

*A property is a characteristic or behavior that should hold true across all valid executions of a system—essentially, a formal statement about what the system should do. Properties serve as the bridge between human-readable specifications and machine-verifiable correctness guarantees.*


### Property Reflection

After analyzing all acceptance criteria, I've identified the following consolidations to eliminate redundancy:

**Consolidations:**
1. Properties 1.3, 1.4, and 1.5 can be combined into a single property about override behavior
2. Properties 4.1-4.5 (recipe patterns) can be combined into one property that validates recipe structure for all tool types
3. Properties 4.1.3 and 4.1.4 (advancement triggers) can be combined into one property about required triggers
4. Properties 4.2.3-4.2.7 (specific nugget yields) are examples, not properties - they validate specific constants
5. Properties 5.6, 5.7, and 5.8 (handle textures) can be combined into one property about handle texture mapping
6. Properties 6.1, 6.3, and 6.4 are examples for specific tool types
7. Properties 8.3-8.6 are examples for specific tiers
8. Properties 11.2-11.6 are examples for specific tiers

**Redundancies to Remove:**
- Property 2.1 is implied by 2.4 (if non-tool-enabled materials don't generate tools, the lists must be separate)
- Property 3.2 is implied by the tier system design - if tools use tiers, they must use tier properties
- Property 4.6 is implied by 4.1-4.5 (recipe patterns already specify ingot usage)
- Property 5.2 is too vague and is covered by more specific properties
- Property 7.1, 7.2, 7.3, 7.4 are Minecraft vanilla behaviors, not mod-specific
- Properties 8.1, 8.2, 11.1 are Minecraft vanilla behaviors

After reflection, we have approximately 35 unique testable properties and 15 example tests.

### Correctness Properties

Property 1: Tool tier configuration accepts all required properties
*For any* tool tier configuration with specified mining level, durability, efficiency, attack damage bonus, and enchantability values, the system should correctly store and retrieve all specified properties
**Validates: Requirements 1.1**

Property 2: Tier categories accept base values and overrides
*For any* material assigned to a tier category with optional property overrides, the final tier should use override values where specified and tier defaults otherwise
**Validates: Requirements 1.2, 1.3, 1.4, 1.5**

Property 3: Tool-enabled materials require INGOT parts
*For any* material marked as tool-enabled, the system should verify the material has an INGOT part (or GEM for gem materials) and reject materials without the required part
**Validates: Requirements 2.2**

Property 4: Non-tool-enabled materials generate no tools
*For any* material not in the tool-enabled list, the system should not generate any tool items, recipes, or models
**Validates: Requirements 2.4**

Property 5: Tool-enabled materials generate all five tool types
*For any* material in the tool-enabled list with valid parts, the system should generate exactly five tool items (sword, axe, pickaxe, shovel, hoe)
**Validates: Requirements 3.1**

Property 6: Tool unlocalized names follow naming convention
*For any* generated tool, the unlocalized name should follow the pattern "{material_name}_{tool_type}" (e.g., "brass_sword", "titanium_pickaxe")
**Validates: Requirements 3.3**

Property 7: Tools are assigned to correct creative tab
*For any* generated tool item, it should be assigned to the Metalmancy creative tab
**Validates: Requirements 3.4, 10.5**

Property 8: Invalid materials log warnings and skip generation
*For any* material in the tool-enabled list without required parts, the system should log a warning and skip tool generation for that material
**Validates: Requirements 3.5**

Property 9: Tool recipes follow standard crafting patterns
*For any* generated tool crafting recipe, it should use the correct pattern for that tool type (sword: 2 ingots + 1 stick vertical, axe: 3 ingots + 2 sticks in axe pattern with mirrored variant, pickaxe: 3 ingots + 2 sticks in pickaxe pattern, shovel: 1 ingot + 2 sticks vertical, hoe: 2 ingots + 2 sticks in hoe pattern with mirrored variant)
**Validates: Requirements 4.1, 4.2, 4.3, 4.4, 4.5, 4.6**

Property 10: Tool recipes use minecraft:stick as handle
*For any* generated tool crafting recipe, the handle ingredient should be "minecraft:stick"
**Validates: Requirements 4.7**

Property 11: Recipe advancements generated for all tool recipes
*For any* tool crafting recipe, there should be a corresponding recipe advancement JSON file
**Validates: Requirements 4.1.1**

Property 12: Recipe advancements have correct parent
*For any* recipe advancement, the parent field should be "minecraft:recipes/root"
**Validates: Requirements 4.1.2**

Property 13: Recipe advancements include required triggers
*For any* recipe advancement, it should include inventory_changed triggers for both the material's ingot and minecraft:stick, plus a recipe_unlocked trigger
**Validates: Requirements 4.1.3, 4.1.4, 4.1.5**

Property 14: Recipe advancements have correct requirements structure
*For any* recipe advancement, the requirements array should contain both "has_the_recipe" and material criteria
**Validates: Requirements 4.1.6**

Property 15: Recipe advancements unlock corresponding recipes
*For any* recipe advancement, the rewards should unlock the corresponding recipe
**Validates: Requirements 4.1.7**

Property 16: Tool smelting recipes produce nuggets
*For any* tool smelting recipe, the input should be the tool item and the output should be the material's nuggets
**Validates: Requirements 4.2.1**

Property 17: Tool blasting recipes are faster than smelting
*For any* tool, the blasting recipe should have the same input/output as smelting but with half the cooking time (100 ticks vs 200 ticks)
**Validates: Requirements 4.2.2**

Property 18: Recycling nugget yield matches ingot cost
*For any* tool recycling recipe, the nugget output count should equal the ingot count used in the crafting recipe
**Validates: Requirements 4.2.3, 4.2.4, 4.2.5, 4.2.6, 4.2.7**

Property 19: Recycling experience scales with nugget yield
*For any* tool recycling recipe, the experience value should be 0.1 multiplied by the nugget count
**Validates: Requirements 4.2.8**

Property 20: Tool generator produces registration code
*For any* execution of the tool generator with tool-enabled materials, it should generate Kotlin code for tool item registration
**Validates: Requirements 5.1, 9.1**

Property 21: Tool item models use handheld parent
*For any* generated tool item model JSON, the parent should be "minecraft:item/handheld"
**Validates: Requirements 5.4**

Property 22: Tool models use two-layer texture structure
*For any* generated tool item model JSON, it should have exactly two texture layers: layer0 for handle and layer1 for tool head
**Validates: Requirements 5.5**

Property 23: Tool handle textures map correctly by type
*For any* generated tool model, the layer0 texture should be "wooden_handle" for axes/pickaxes/hoes, "wooden_sword_handle" for swords, and "wooden_shovel_handle" for shovels
**Validates: Requirements 5.6, 5.7, 5.8**

Property 24: Tool head textures use material-specific paths
*For any* generated tool model, the layer1 texture should be "metalmancy:item/{material}_{tool}" (e.g., "metalmancy:item/brass_pickaxe")
**Validates: Requirements 5.9**

Property 25: Tool models are in correct directory
*For any* generated tool item model, the JSON file should be located in assets/metalmancy/models/item/
**Validates: Requirements 5.3**

Property 26: Tool inventory icons are generated
*For any* generated tool, there should be a corresponding JSON file in assets/metalmancy/items/ for inventory rendering
**Validates: Requirements 5.10**

Property 27: Tool language entries are generated
*For any* generated tool, there should be a localized name entry in en_us.json
**Validates: Requirements 5.11**

Property 28: Generator reports generation statistics
*For any* execution of the tool generator, it should report the count of generated tool items, models, recipes, and language entries
**Validates: Requirements 5.12**

Property 29: Axe attack properties vary by tier
*For any* generated axe, the attack damage should be in the range appropriate for its tier and attack speed should be between -3.0 and -3.2
**Validates: Requirements 6.2**

Property 30: Hoe attack properties vary by tier
*For any* generated hoe, the attack damage should be 0 and attack speed should vary based on the material's tier
**Validates: Requirements 6.5**

Property 31: Recipe JSONs are generated for all tools
*For any* execution of the tool generator, it should generate recipe JSON files for all tool crafting recipes
**Validates: Requirements 9.2**

Property 32: Model JSONs are generated for all tools
*For any* execution of the tool generator, it should generate item model JSON files for all tools
**Validates: Requirements 9.3**

Property 33: Generated files are copied to resources
*For any* execution of the tool generator, generated files should be copied to the appropriate resource directories (src/main/resources/)
**Validates: Requirements 9.5**

Property 34: Generated files follow datapack structure
*For any* generated JSON file, it should follow the structure specified in the Minecraft 1.21.10 datapack specification (recipes in data/{namespace}/recipe/, models in assets/{namespace}/models/item/)
**Validates: Requirements 9.6**


## Error Handling

### Build-Time Errors

**Missing INGOT Part:**
- **Scenario:** A material in the tool-enabled list doesn't have an INGOT part (or GEM for gem materials)
- **Handling:** Log a warning message and skip tool generation for that material
- **Rationale:** Failing silently could lead to confusion, but failing loudly would break the build for a configuration issue

**Invalid Tier Configuration:**
- **Scenario:** A material specifies an invalid tier category or property values
- **Handling:** Throw an exception with a clear error message during generator execution
- **Rationale:** Invalid configuration should fail fast to prevent generating incorrect tools

**File System Errors:**
- **Scenario:** Unable to write generated files to disk (permissions, disk full, etc.)
- **Handling:** Throw an exception and halt generation
- **Rationale:** Partial generation could lead to inconsistent state

### Runtime Errors

**Missing Tool Tier:**
- **Scenario:** A tool item is registered but its material doesn't have a tier defined
- **Handling:** Use a default tier (iron-like) and log a warning
- **Rationale:** Allows the mod to load but alerts developers to the configuration issue

**Missing Repair Material:**
- **Scenario:** A tool's repair material (ingot) doesn't exist
- **Handling:** Tool cannot be repaired in anvil (Minecraft's default behavior)
- **Rationale:** This is a configuration issue that should be caught during testing

## Testing Strategy

### Unit Testing

The Basic Tools System will use unit tests for specific examples and edge cases:

**Configuration Tests:**
- Test that tier defaults are correctly defined (copper-like, iron-like, etc.)
- Test that specific materials are in the tool-enabled list (BRASS, BRONZE, etc.)
- Test that tier overrides work correctly for edge cases

**Generation Tests:**
- Test that specific tool types generate correct recipe patterns (sword, axe, etc.)
- Test that specific nugget yields are correct (sword: 2, axe: 3, etc.)
- Test that specific handle textures are used (sword: wooden_sword_handle, etc.)

**Validation Tests:**
- Test that materials without INGOT parts are rejected
- Test that invalid tier configurations throw exceptions
- Test that file system errors are handled correctly

### Property-Based Testing

The system will use property-based testing to verify universal properties across all inputs. We'll use **Kotest Property Testing** as the PBT library for Kotlin.

**Configuration:**
- Each property-based test will run a minimum of 100 iterations
- Tests will use custom generators for materials, tool types, and tier configurations
- Each test will be tagged with a comment referencing the design document property

**Test Organization:**
- Property tests will be in `common/src/test/kotlin/io/felipeandrade/metalmancy/tools/`
- Tests will be organized by component (TierSystemTest, GeneratorTest, RecipeTest, etc.)
- Each test file will contain multiple property tests

**Generators:**
- `Arb.material()`: Generates random materials with various part configurations
- `Arb.toolType()`: Generates random tool types (sword, axe, pickaxe, shovel, hoe)
- `Arb.toolTier()`: Generates random tool tier configurations
- `Arb.toolEnabledMaterial()`: Generates random materials that are valid for tools (have INGOT parts)

**Example Property Test Structure:**
```kotlin
class RecipeGenerationTest : StringSpec({
    "Property 9: Tool recipes follow standard crafting patterns" {
        // Feature: basic-tools, Property 9: Tool recipes follow standard crafting patterns
        checkAll(Arb.toolEnabledMaterial(), Arb.toolType()) { material, toolType ->
            val recipe = generateRecipe(material, toolType)
            recipe.pattern shouldBe toolType.expectedPattern
            recipe.ingredientCount shouldBe toolType.expectedIngotCount
        }
    }
})
```

**Property Test Coverage:**
- All 34 correctness properties will be implemented as property-based tests
- Properties will be tested with diverse inputs (different materials, tool types, tiers)
- Edge cases (empty lists, null values, boundary conditions) will be covered by generators

**Test Execution:**
- Property tests will run as part of the standard Gradle test task
- Tests will run on both NeoForge and Fabric platforms
- Failed tests will report the failing input for debugging

### Integration Testing

**Build Integration:**
- Test that ToolGen integrates with Gradle build system
- Test that generated files are correctly copied to resources
- Test that the mod loads successfully with generated tools

**Minecraft Integration:**
- Test that tools can be crafted in-game
- Test that tools have correct mining levels
- Test that tools can be repaired with correct materials
- Test that recycling recipes work correctly

**Note:** Integration tests are not part of the core property-based testing strategy but should be performed manually or through automated gameplay testing.

## Implementation Notes

### Gradle Integration

The ToolGen generator will be integrated into the Gradle build system following the existing pattern:

**Build Script (common/gradle/toolgen.gradle):**
```gradle
task toolGen(type: JavaExec) {
    classpath = sourceSets.tools.runtimeClasspath
    mainClass = 'tools.toolgen.ToolGenKt'
    args '--out', 'build/generated'
}

processResources.dependsOn toolGen
```

**Execution Order:**
1. ToolGen runs before processResources
2. Generated files are written to build/generated/
3. Files are copied to src/main/resources/
4. processResources includes the generated files in the final JAR

### File Generation Strategy

**Incremental Generation:**
- Generator always regenerates all tool files (no incremental updates)
- This ensures consistency and prevents stale files
- Old files are not deleted (allows manual overrides to coexist)

**Override Support:**
- Manual recipe files in src/tools/toolgen/recipe/ can override generated recipes
- Manual model files in src/tools/toolgen/models/ can override generated models
- Overrides are copied after generation, replacing generated files

### Performance Considerations

**Generation Time:**
- Expected generation time: < 1 second for 14 materials × 5 tools = 70 tool items
- File I/O is the primary bottleneck
- Parallel generation not necessary for this scale

**Runtime Performance:**
- Tool registration happens once at mod initialization
- No runtime generation or dynamic tool creation
- Tool properties are cached by Minecraft's item system

### Future Extensibility

**Adding New Tool Types:**
- Add new entry to ToolType enum
- Add recipe pattern to recipe generation logic
- Add handle texture mapping to model generation logic
- No changes needed to tier system or material configuration

**Adding New Materials:**
- Add material to Materials object
- Add material to ToolMaterials.TOOL_ENABLED list
- Assign tier category or custom tier
- Generator automatically creates all tools

**Custom Tool Properties:**
- Extend ToolTier data class with new properties
- Update tier defaults and override logic
- Update tool item creation to use new properties

## Dependencies

### External Dependencies

**Minecraft/NeoForge:**
- Minecraft 1.21.10 (pack format 88)
- NeoForge API for tool item registration
- Fabric API for Fabric platform support

**Architectury:**
- Architectury API for cross-platform compatibility
- Architectury Transformer for runtime class transformation

**Kotlin:**
- Kotlin standard library
- Kotlin reflection (for generator)

**Gson:**
- JSON serialization for recipe and model generation
- Already used by existing generators

**Kotest:**
- Kotest Property Testing for property-based tests
- Kotest assertions for test validation

### Internal Dependencies

**Materials System:**
- Materials object for material definitions
- Material class for material properties
- Part enum for material parts (INGOT, GEM, etc.)

**Existing Generators:**
- ItemGen pattern for item model generation
- RecipeGen pattern for recipe generation
- BlockGen pattern for block model generation

**Build System:**
- Gradle for build orchestration
- Existing generator tasks for integration pattern

## Glossary Updates

The following terms are specific to the Basic Tools System:

- **ToolGen**: Build-time generator that creates tool items, recipes, and models
- **Tool Tier**: Configuration object defining tool properties (mining level, durability, etc.)
- **Tool-Enabled Material**: A material that has been explicitly configured to have tools generated
- **Tier Category**: Predefined property group (copper-like, iron-like, etc.) for materials
- **Handle Texture**: The layer0 texture in tool models representing the wooden handle
- **Head Texture**: The layer1 texture in tool models representing the metal tool head
- **Recycling Recipe**: Smelting or blasting recipe that converts tools back to nuggets
- **Recipe Advancement**: JSON file that unlocks recipes in the player's recipe book

