# Testing and Run Configuration Guidelines

## Project Context

This is a Minecraft mod project (Metalmancy) built using:
- **Architectury API** - Multi-platform mod development
- **NeoForge** - Primary mod loader platform
- **Fabric** - Secondary mod loader platform
- **Gradle** - Build system with multi-module setup

## Critical Testing Rules

### 1. Always Check Run Configurations First

Before running any tests or making changes that affect the build:

1. **Check `.idea/runConfigurations/`** for existing IntelliJ run configurations
2. **Never modify these configurations** without explicit user approval
3. **Understand the setup** before attempting to run or test the mod

### 2. Available Run Configurations

The project has four pre-configured run configurations:

- `Minecraft Client (:neoforge)` - NeoForge client for testing in-game
- `Minecraft Server (:neoforge)` - NeoForge server for multiplayer testing
- `Minecraft Client (:fabric)` - Fabric client for testing in-game
- `Minecraft Server (:fabric)` - Fabric server for multiplayer testing

### 3. Testing Approach

**DO:**
- Use Gradle tasks for building and testing: `./gradlew :neoforge:build`, `./gradlew :neoforge:test`
- Run unit tests through Gradle
- Build the project before attempting to run the game client
- Test changes incrementally

**DON'T:**
- Try to launch the Minecraft client directly for unit testing
- Modify JVM parameters or environment variables without checking existing configs
- Run the game client to test code changes (use unit tests instead)
- Break existing run configurations

### 4. NeoForge Configuration Details

The NeoForge run configurations use:
- **Main class:** `dev.architectury.transformer.TransformerRuntime`
- **Module:** `metalmancy.neoforge.main`
- **Working directory:** `$PROJECT_DIR$/neoforge/run/`
- **Environment variable:** `MOD_CLASSES` with specific build output paths
- **JVM parameters:** Extensive Architectury and NeoForge-specific parameters

### 5. Build System Structure

```
metalmancy/
├── common/          # Shared code across platforms
├── neoforge/        # NeoForge-specific implementation
├── fabric/          # Fabric-specific implementation
└── build.gradle     # Root build configuration
```

When making changes:
- Common code goes in `common/src/`
- Platform-specific code goes in `neoforge/src/` or `fabric/src/`
- Always build the appropriate subproject

### 6. Testing Workflow

1. Make code changes
2. Run `./gradlew :neoforge:build` (or `:fabric:build`)
3. Run unit tests if available: `./gradlew :neoforge:test`
4. Only launch the game client if you need to test in-game behavior
5. Use the pre-configured run configurations from IntelliJ

## Previous Issues to Avoid

- **Breaking changes to run configurations** - Always preserve existing setup
- **Attempting to run Minecraft client for unit tests** - Use Gradle test tasks instead
- **Modifying build paths or environment variables** - These are carefully configured for Architectury

## When in Doubt

- Check `.idea/runConfigurations/` first
- Use Gradle tasks for building and testing
- Ask the user before modifying any run configurations
- Test with the smallest scope possible (unit tests > integration tests > full game launch)
