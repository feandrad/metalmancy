# Property-Based Testing Guide for Metalmancy Material System

## Overview

This directory contains property-based tests (PBT) for the Metalmancy material system using Kotest. Property-based testing validates universal properties that should hold across all inputs, providing stronger guarantees than example-based unit tests.

## Framework

- **Framework**: Kotest 5.8.0
- **Test Style**: StringSpec
- **Minimum Iterations**: 100 per property (configurable)

## Running Tests

### Run All Property Tests

```bash
gradle :common:test
```

### Run Specific Test Class

```bash
gradle :common:test --tests "MaterialPropertyTest"
gradle :common:test --tests "MaterialGroupingPropertyTest"
gradle :common:test --tests "MaterialBlocksPropertyTest"
```

### Run with Verbose Output

```bash
gradle :common:test --info
```

## Test Structure

### Custom Generators (Arb)

Located in `MaterialGenerators.kt`, these provide random but valid test data:

- `Arb.part()` - Generates random Part enum values
- `Arb.family()` - Generates random Family enum values
- `Arb.materialName()` - Generates valid material names (lowercase, alphanumeric with underscores)
- `Arb.partSet()` - Generates non-empty sets of Parts
- `Arb.material()` - Generates random valid Material instances
- `Arb.materialWithFamily(family)` - Generates Materials with specific family
- `Arb.materialWithPart(part)` - Generates Materials that have a specific part

### Test Files

#### MaterialPropertyTest.kt
Tests core Material data class properties:
- **Property 1**: Material preserves all fields (Requirements 1.1, 1.5)
- **Property 2**: Correct unlocalized name generation (Requirements 1.2)

#### MaterialGroupingPropertyTest.kt
Tests material grouping and categorization:
- **Property 3**: Grouping by family (Requirements 1.4, 10.1, 10.2)
- **Property 4**: Completeness of ALL list (Requirements 10.5)
- **Property 31**: Metal grouping by level (Requirements 10.3)

#### MaterialBlocksPropertyTest.kt
Tests block registration logic:
- **Property 5**: Block creation for block parts (Requirements 2.1)
- **Property 6**: Correct namespace in ResourceLocations (Requirements 2.6)
- **Property 32**: Category-based property application (Requirements 2.5, 10.4)

## Property Testing Best Practices

### 1. Input Domain Validation

Always filter out invalid inputs before testing:

```kotlin
checkAll<String, Family, Set<Part>>(100) { name, family, parts ->
    // Skip invalid inputs
    if (name.isBlank() || parts.isEmpty()) return@checkAll
    
    val material = Material(name, family, parts)
    // ... test logic
}
```

### 2. Universal Quantification

Properties should be stated with "for all" quantification:

```kotlin
// Good: For any Material and Part, unlocalizedName should contain both
"Property 2: unlocalizedName generates correct names" {
    checkAll(100, Arb.material()) { material ->
        material.parts.forEach { part ->
            val name = material.unlocalizedName(part)
            name shouldContain material.name
            name shouldContain part.suffix()
        }
    }
}
```

### 3. Test Static Data with checkAll

Even for static data, use `checkAll` for consistency:

```kotlin
"Property 3: GEMS contains only GEM family materials" {
    checkAll<Unit>(1) { _ ->
        Materials.GEMS.forEach { material ->
            material.family shouldBe Family.GEM
        }
    }
}
```

### 4. Property Annotations

Each property test must reference the design document:

```kotlin
// Feature: material-system, Property 1: Preservação de campos do Material
// Validates: Requirements 1.1, 1.5
"Property 1: Material preserves all fields" {
    // ... test implementation
}
```

## Configuration

### Kotest Dependencies (build.gradle)

```groovy
testImplementation "io.kotest:kotest-runner-junit5:5.8.0"
testImplementation "io.kotest:kotest-assertions-core:5.8.0"
testImplementation "io.kotest:kotest-property:5.8.0"
```

### JUnit 5 Configuration

```groovy
tasks.withType(Test).configureEach {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
    }
}
```

## Troubleshooting

### Tests Not Running

Ensure JUnit 5 is configured:
```groovy
tasks.withType(Test).configureEach {
    useJUnitPlatform()
}
```

### Compilation Errors with Arb

Import the Arb class:
```kotlin
import io.kotest.property.Arb
```

### Type Inference Issues with checkAll

Specify the type explicitly:
```kotlin
checkAll<Unit>(1) { _ ->
    // test logic
}
```

## Coverage

Current property test coverage:

- ✅ Material data class (Properties 1-2)
- ✅ Material grouping (Properties 3-4, 31)
- ✅ Block registration logic (Properties 5-6, 32)
- ⏳ Item registration (Properties 7-10) - Pending
- ⏳ Block Generator (Properties 11-14) - Pending
- ⏳ Item Generator (Properties 15-17) - Pending
- ⏳ Recipe Generator (Properties 18-19) - Pending
- ⏳ Worldgen Generator (Properties 20-26) - Pending
- ⏳ Platform abstraction (Properties 28-30) - Pending

## References

- [Kotest Documentation](https://kotest.io/)
- [Kotest Property Testing](https://kotest.io/docs/proptest/property-based-testing.html)
- Design Document: `.kiro/specs/material-system/design.md`
- Requirements Document: `.kiro/specs/material-system/requirements.md`
