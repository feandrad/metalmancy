# Gemini Steering File for Metalmancy

This file provides context and instructions for AI agents working on the Metalmancy project.

## 1. Project Overview
**Metalmancy** is a Minecraft mod that adds an extensible material system with new metals, gems, and alchemical materials.
- **Type:** Minecraft Mod
- **Platforms:** Fabric (Primary/Stable), NeoForge (WIP)
- **Framework:** Architectury API (Multi-platform)
- **Language:** Kotlin (JDK 21)
- **Build System:** Gradle 8.x

## 2. Architecture & Structure
The project follows a multi-module Gradle structure designed for strict separation of concerns:

- **`common/`**: The core logic and assets.
    - `src/main/kotlin/io/felipeandrade/metalmancy/`: **Runtime code** (Materials, Blocks, Items).
    - `src/tools/`: **Build-time generators** (BlockGen, ItemGen, RecipeGen, etc.).
    - `src/main/resources/`: Assets (textures, lang) and Data (recipes, tags).
- **`fabric/`**: Fabric-specific implementation and entry points.
- **`neoforge/`**: NeoForge-specific implementation (In Development).

### Architectural Constraints
**These rules are critical for the project's long-term maintainability:**
1.  **Platform Independence**: The `fabric` and `neoforge` modules must **never** depend on each other. They are sibling modules that both depend on `common`.
2.  **Tool Isolation**: No runtime module (`common/src/main`, `fabric`, `neoforge`) is allowed to depend on `common/src/tools`. The `tools` package is strictly for build-time asset generation and must remain isolated from the game logic.
    - *Note:* Ideally, `tools` would be a separate Gradle subproject. Currently, it resides in `common/src/tools`, but the logical separation must be enforced.

### Key Files
- **Material Definitions**: `common/src/main/kotlin/io/felipeandrade/metalmancy/material/Materials.kt` - The single source of truth for all materials.
- **Ore Generation**: `common/src/tools/worldgen/OreGenEntries.kt` - Configuration for world generation.

## 3. Development Workflow

### Adding a New Material
1.  **Define**: Add entry to `Materials.kt`.
2.  **Generate**: Run Gradle tasks to generate JSON assets/data.
3.  **Assets**: Add textures (`.png`) in `common/src/main/resources/assets/metalmancy/textures/`.
4.  **Lang**: Update `en_us.json` and `pt_br.json`.
5.  **Worldgen**: Configure `OreGenEntries.kt` (if it has ore).

### Generators
The project relies heavily on code generation to maintain consistency and reduce boilerplate. Always run generators after modifying material definitions.

```bash
# Generate everything
./gradlew :common:generateBlocks
./gradlew :common:generateItems
./gradlew :common:generateRecipes
./gradlew :common:generateLoot
./gradlew :common:generateJson
./gradlew :common:syncGeneratedWorldgen
```

## 4. Testing Guidelines
**Strict Adherence Required:**
- **Unit Tests**: Run via `./gradlew test`.
- **Property Tests**: Critical for validating the material system. Run via `./gradlew :common:test --tests "*PropertyTest"`.
- **Run Configurations**: **DO NOT** modify files in `.idea/runConfigurations/`. Use existing Gradle tasks or IntelliJ configurations.
- **Client/Server**: Use `:fabric:runClient` or `:fabric:runServer` for in-game testing.

## 5. Coding Conventions
- **Kotlin**: Follow standard Kotlin idioms. Use `val` over `var`, data classes for structures, and object singletons where appropriate.
- **Style**: Match existing code style (indentation, naming).
- **Documentation**: Write clear KDoc for public APIs.
- **Commits**: Follow Conventional Commits (`feat:`, `fix:`, `docs:`, etc.).

## 6. Important Notes
- **NeoForge Status**: The NeoForge module is currently in development. Focus on `common` and `fabric` unless specifically asked to work on NeoForge porting.
- **Data-Driven**: The project prioritizes data-driven design. Logic should often derive from the `Material` objects defined in `Materials.kt`.
