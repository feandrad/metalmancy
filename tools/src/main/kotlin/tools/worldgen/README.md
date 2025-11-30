# Worldgen JSON Datagen — Guide

This document explains how the `generateJson` and `syncGeneratedWorldgen` Gradle tasks work and how the Kotlin JSON generator creates Minecraft 1.21.x **configured_feature** and **placed_feature** files for ores.

---

## Overview

- You edit a **single Kotlin list** of entries describing ores (names, sizes, Y range, etc.).
- Run a Gradle task that executes a small Kotlin program on the module’s **runtimeClasspath** (Minecraft + Loom + your code).
- The program emits **JSON** into `common/build/generated/...` and (optionally) copies them into `src/main/resources`.

Key benefits:
- No dependency on live Minecraft registries (headless, safe during builds).
- One source of truth for ore names via `UnlocalizedNames` (pure strings).

---

## Project Layout (module `common`)

```
common/
  build.gradle (Groovy)
  src/
    main/
      kotlin/... (game code)
      resources/... (runtime resources)
    tools/
      datagen/                (added to main source set)
        OreGen.kt             (data classes)
        OreGenEntries.kt      (your list of ores)
        WorldgenJsonGen.kt    (the generator)
```

> The `src/tools/datagen` directory is added to the **main** source set so you don’t need a separate module. It compiles with the rest of the code.

---

## Gradle Tasks

### `generateJson` (JavaExec)

- **Purpose:** Run the Kotlin `main()` in `WorldgenJsonGen.kt` with the module’s **runtimeClasspath** so MC/Loom classes are available (but we don’t touch registries).
- **Where it writes:** `common/build/generated/data/<modid>/worldgen/{configured_feature|placed_feature}/...`
- **Invocation:**

```bash
./gradlew :common:generateJson
```

**Groovy DSL example (in `common/build.gradle`):**
```groovy
tasks.register('generateJson', JavaExec) {
    group = 'datagen'
    description = 'Generate worldgen JSON from OreGenEntries'
    mainClass = 'tools.WorldgenJsonGenKt' // package of WorldgenJsonGen.kt
    classpath = sourceSets.main.runtimeClasspath
    args '--out', "$buildDir/generated"
    doFirst { println "→ Generating worldgen JSON in $buildDir/generated …" }
}
```

**Kotlin DSL example (in `common/build.gradle.kts`):**
```kotlin
tasks.register<JavaExec>("generateJson") {
    group = "datagen"
    description = "Generate worldgen JSON from OreGenEntries"
    mainClass.set("tools.WorldgenJsonGenKt")
    classpath = sourceSets["main"].runtimeClasspath
    args("--out", "$buildDir/generated")
    doFirst { println("→ Generating worldgen JSON in $buildDir/generated …") }
}
```

### `syncGeneratedWorldgen` (Copy)

- **Purpose:** Copy generated JSONs into your runtime resources for quick testing.
- **Invocation:**

```bash
./gradlew :common:syncGeneratedWorldgen
```

**Groovy DSL:**
```groovy
tasks.register('syncGeneratedWorldgen', Copy) {
    group = 'datagen'
    description = 'Copy generated worldgen JSONs to resources'
    from "$buildDir/generated/data"
    into 'src/main/resources/data'
    dependsOn 'generateJson'
}
```

**Kotlin DSL:**
```kotlin
tasks.register<Copy>("syncGeneratedWorldgen") {
    group = "datagen"
    description = "Copy generated worldgen JSONs to resources"
    from("$buildDir/generated/data")
    into("src/main/resources/data")
    dependsOn("generateJson")
}
```

### Source Set wiring

Add the **datagen** directory to the main source set so the generator compiles:

**Groovy DSL:**
```groovy
sourceSets {
    main {
        java.srcDir 'src/tools/datagen'
        resources.srcDir 'src/tools/datagen/resources'
    }
}
```

**Kotlin DSL:**
```kotlin
sourceSets {
    named("main") {
        java.srcDir("src/tools/datagen")
        resources.srcDir("src/tools/datagen/resources")
    }
}
```

---

## Kotlin/JVM Setup

Use **JDK 21** for Gradle and compilation:

```groovy
plugins { id 'org.jetbrains.kotlin.jvm' }
kotlin { jvmToolchain(21) }
```

If needed, pin Gradle JVM via `gradle.properties`:
```
org.gradle.java.home=/path/to/jdk-21
```

---

## The JSON Generator Logic

### 1) Single Source of Truth for Names

Use `UnlocalizedNames` to centralize unnamespaced IDs as strings:

```kotlin
object UnlocalizedNames {
    const val RUBY_ORE = "ruby_ore"
    const val RUBY_DEEPSLATE_ORE = "ruby_deepslate_ore"
    // ... others
}
```

### 2) Edit-only List (`OreGenEntries.kt`)

```kotlin
data class OreGen(
    val stone: String,
    val deepslate: String? = null,
    val yRange: IntRange = -80..80,
    val heightType: OreGenHeightType = OreGenHeightType.TRAPEZOID,
    val veinSize: Int = 4,
    val countPerChunk: Int = 7,
    val suffix: String? = null,
)

object OreGenEntries {
    val overworld = listOf(
        OreGen(
            UnlocalizedNames.RUBY_ORE,
            UnlocalizedNames.RUBY_DEEPSLATE_ORE,
            -4..-64,
            OreGenHeightType.UNIFORM,
            4,
            2,
        ),
        // ...
    )
}
```

### 3) Namespacing

The generator **always prefixes** with your `MOD_ID` when writing block state names and feature references:

```kotlin
"Name": "metalmancy:ruby_ore"
"feature": "metalmancy:ruby_ore"
```

You can build those strings inline:
```kotlin
stoneStateId = "$MOD_ID:${e.stone}"
deepslateStateId = e.deepslate?.let { "$MOD_ID:$it" }
```

### 4) Files Emitted

For each entry, the generator writes:

- **Configured Feature**: `data/<modid>/worldgen/configured_feature/<base>.json`
  ```json
  {
    "type": "minecraft:ore",
    "config": {
      "size": 9,
      "discard_chance_on_air_exposure": 0.0,
      "targets": [
        {
          "target": {"predicate_type":"minecraft:tag_match","tag":"minecraft:stone_ore_replaceables"},
          "state": {"Name":"metalmancy:ruby_ore"}
        },
        {
          "target": {"predicate_type":"minecraft:tag_match","tag":"minecraft:deepslate_ore_replaceables"},
          "state": {"Name":"metalmancy:ruby_deepslate_ore"}
        }
      ]
    }
  }
  ```

- **Placed Feature**: `data/<modid>/worldgen/placed_feature/oregen_<base>.json`
  ```json
  {
    "feature": "metalmancy:ruby_ore",
    "placement": [
      {"type":"minecraft:count","count":10},
      {"type":"minecraft:in_square"},
      {"type":"minecraft:height_range","height":{
        "type":"minecraft:trapezoid",
        "min_inclusive":{"absolute":-80},
        "max_inclusive":{"absolute":80}
      }},
      {"type":"minecraft:biome"}
    ]
  }
  ```

> If your loader requires an explicit `IntProvider`, use:
> ```json
> {"type":"minecraft:count","count":{"type":"minecraft:constant","value":10}}
> ```

### 5) Naming & Uniqueness

- The base name comes from `e.stone` (e.g., `ruby_ore`).
- If you repeat the same stone, the generator appends `_2`, `_3`, … to filenames and IDs (`ruby_ore.json`, `ruby_ore_2.json`, etc.).
- The placed features mirror the name with `oregen_` prefix (`oregen_ruby_ore.json`, `oregen_ruby_ore_2.json`).

---

## Common Errors & Quick Fixes

- **Failed to parse … configured_feature …**  
  JSON shape invalid. Check:
  - `state.Name` values are fully qualified: `"metalmancy:<id>"`.
  - `type: "minecraft:ore"` and `targets` use `minecraft:stone_ore_replaceables` / `minecraft:deepslate_ore_replaceables`.
- **Unbound values in registry …**  
  The corresponding configured feature didn’t load → check JSON and namespacing.
- **JavaExec can’t find main class**  
  `mainClass` must match the Kotlin file package: e.g., `tools.WorldgenJsonGenKt`.
- **Gradle running with Java 17**  
  Ensure Gradle JVM is 21 (`gradle.properties` or IDE’s Gradle JVM).

---

## Usage

1. Edit `OreGenEntries.overworld`.
2. Run:
   ```bash
   ./gradlew :common:generateJson
   ./gradlew :common:syncGeneratedWorldgen   # optional copy to resources
   ```
3. Launch the game.

---

## Extending

- The generator now supports `TRAPEZOID`, `TRIANGLE`, and `UNIFORM` distributions via the `OreGenHeightType` enum.
- Per-biome filters can be done with additional placements (`minecraft:environment_scan`, `minecraft:rarity_filter`, etc.).
- Add Nether/End lists (e.g., `OreGenEntries.nether`) and write to their own files with different tags.

---

*Last updated: 2025-10-02.*