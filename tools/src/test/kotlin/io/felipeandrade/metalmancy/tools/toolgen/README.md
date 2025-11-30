# Tool Generation Test Infrastructure

This directory contains the property-based testing infrastructure for the Basic Tools System.

## Overview

The test infrastructure uses **Kotest Property Testing** to validate universal properties that should hold across all tool configurations, materials, and types. Each test runs with a minimum of 100 iterations to ensure comprehensive coverage.

## Test Configuration

### KotestConfig.kt

Global configuration for all property-based tests:
- **Property Test Iterations**: 100 (minimum)
- **Isolation Mode**: InstancePerTest (fresh instance per test)
- **Parallelism**: 4 threads for faster execution

### Custom Generators (ToolTierGenerators.kt)

The following custom generators (Arb) are available for property-based testing:

#### Tool Tier Generators

- **`Arb.toolTier()`**: Generates random valid ToolTier instances with reasonable property values
  - Mining level: 0-4
  - Durability: 32-3000
  - Efficiency: 1.0-15.0
  - Attack damage bonus: 0.0-10.0
  - Enchantability: 1-30

- **`Arb.tierCategory()`**: Generates one of the predefined tier categories
  - COPPER_LIKE, IRON_LIKE, GOLD_LIKE, DIAMOND_LIKE, MYSTIC

- **`Arb.tierOverride()`**: Generates TierOverride instances with random overrides
  - Ensures at least one property is overridden
  - Uses valid tier categories as base

#### Material Generators

- **`Arb.material()`**: Generates random materials with various part configurations
  - Random name (5-15 characters)
  - Random family
  - Random non-empty set of parts

- **`Arb.toolEnabledMaterial()`**: Generates materials valid for tool generation
  - Guaranteed to have INGOT or GEM part
  - May have additional parts

- **`Arb.actualToolEnabledMaterial()`**: Generates from the actual tool-enabled list
  - Uses real materials from ToolMaterials.TOOL_ENABLED

- **`Arb.nonToolEnabledMaterial()`**: Generates materials NOT in the tool-enabled list
  - Useful for testing exclusion logic

- **`Arb.materialWithoutRequiredParts()`**: Generates invalid materials for tools
  - No INGOT or GEM parts
  - Useful for testing validation logic

#### Tool Type Generators

- **`Arb.toolType()`**: Generates random ToolType values
  - SWORD, AXE, PICKAXE, SHOVEL, HOE

- **`Arb.materialAndToolType()`**: Generates pairs of (Material, ToolType)
  - Uses actual tool-enabled materials
  - Useful for testing tool generation

## Test Files

### Property-Based Tests

- **ToolTierPropertyTest.kt**: Tests for tier configuration and overrides
  - Property 1: Tool tier configuration accepts all required properties
  - Property 2: Tier categories accept base values and overrides

- **ToolMaterialsPropertyTest.kt**: Tests for tool-enabled materials
  - Property 3: Tool-enabled materials require INGOT parts
  - Property 4: Non-tool-enabled materials generate no tools

- **GeneratedToolPropertyTest.kt**: Tests for tool generation
  - Property 5: Tool-enabled materials generate all five tool types
  - Property 6: Tool unlocalized names follow naming convention
  - Property 8: Invalid materials log warnings and skip generation
  - Property 21-24: Model generation properties

- **ToolRecipesPropertyTest.kt**: Tests for recipe generation
  - Property 9-10: Crafting recipe patterns
  - Property 11-15: Recipe advancement properties
  - Property 16-19: Recycling recipe properties

- **ToolGenPropertyTest.kt**: Tests for the main generator
  - Property 20: Tool generator produces registration code
  - Property 28: Generator reports generation statistics
  - Property 31-34: File generation and structure properties

- **ToolItemsPropertyTest.kt**: Tests for runtime tool registration
  - Property 7: Tools are assigned to correct creative tab
  - Property 29-30: Attack properties by tool type

### Manual Tests

- **ToolMaterialsManualTest.kt**: Manual verification tests for specific materials
  - Validates specific materials are in tool-enabled list
  - Checks tier assignments for known materials

## Running Tests

### Run All Tests
```bash
./gradlew :common:test
```

### Run Specific Test Class
```bash
./gradlew :common:test --tests "io.felipeandrade.metalmancy.tools.toolgen.ToolTierPropertyTest"
```

### Run Specific Test
```bash
./gradlew :common:test --tests "io.felipeandrade.metalmancy.tools.toolgen.ToolTierPropertyTest.Property 1*"
```

### Run with Verbose Output
```bash
./gradlew :common:test --info
```

## Writing New Property Tests

### Example Structure

```kotlin
class MyPropertyTest : StringSpec({
    // Feature: basic-tools, Property X: Description
    // Validates: Requirements X.Y
    "Property X: Description" {
        checkAll(100, Arb.toolEnabledMaterial(), Arb.toolType()) { material, toolType ->
            // Test logic here
            result shouldBe expected
        }
    }
})
```

### Best Practices

1. **Always tag tests with property number and requirements**
   - Use comments to reference design document properties
   - Include requirement numbers for traceability

2. **Use appropriate generators**
   - Choose generators that match your input domain
   - Use `actualToolEnabledMaterial()` for real materials
   - Use `toolEnabledMaterial()` for random valid materials

3. **Run at least 100 iterations**
   - Default is configured globally
   - Can override with `checkAll(200, ...)` for more thorough testing

4. **Test universal properties, not examples**
   - Properties should hold for ALL valid inputs
   - Use manual tests for specific examples

5. **Keep tests focused**
   - One property per test
   - Clear assertion messages
   - Minimal setup code

## Troubleshooting

### Test Failures

When a property test fails, Kotest will report:
- The failing input values
- The assertion that failed
- The iteration number

Use this information to:
1. Verify the test is correct
2. Check if the implementation has a bug
3. Determine if the specification needs clarification

### Performance Issues

If tests are slow:
- Reduce parallelism in KotestConfig
- Reduce iteration count for specific tests
- Profile generator performance

### Generator Issues

If generators produce invalid inputs:
- Check generator constraints
- Add validation to generators
- Use filters to exclude invalid cases

## References

- [Kotest Documentation](https://kotest.io/)
- [Property-Based Testing Guide](https://kotest.io/docs/proptest/property-based-testing.html)
- [Design Document](../../../../../../.kiro/specs/basic-tools/design.md)
- [Requirements Document](../../../../../../.kiro/specs/basic-tools/requirements.md)
