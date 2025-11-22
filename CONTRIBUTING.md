# Contributing to Metalmancy

Thank you for your interest in contributing to Metalmancy! This guide will help you get started.

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Project Structure](#project-structure)
- [Making Changes](#making-changes)
- [Testing Guidelines](#testing-guidelines)
- [Submitting Changes](#submitting-changes)
- [Style Guidelines](#style-guidelines)
- [Common Tasks](#common-tasks)

## 🤝 Code of Conduct

We are committed to providing a welcoming and inclusive environment. Please:

- Be respectful and considerate
- Welcome newcomers and help them learn
- Focus on constructive feedback
- Respect differing viewpoints and experiences
- Accept responsibility and apologize for mistakes

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have:

- **JDK 21** installed
- **Git** for version control
- **IntelliJ IDEA** (recommended) or another Kotlin-compatible IDE
- Basic knowledge of Kotlin and Minecraft modding

### Fork and Clone

1. Fork the repository on GitHub
2. Clone your fork locally:

```bash
git clone https://github.com/YOUR_USERNAME/metalmancy.git
cd metalmancy
```

3. Add the upstream repository:

```bash
git remote add upstream https://github.com/ORIGINAL_OWNER/metalmancy.git
```

4. Create a feature branch:

```bash
git checkout -b feature/your-feature-name
```

## 🛠️ Development Setup

### Initial Build

```bash
# Build the project
./gradlew build

# Run tests to ensure everything works
./gradlew test
```

### IDE Setup (IntelliJ IDEA)

1. Open the project in IntelliJ IDEA
2. Wait for Gradle sync to complete
3. Ensure the project SDK is set to JDK 21
4. Install the Kotlin plugin if not already installed

### Running the Mod

```bash
# Run Fabric client
./gradlew :fabric:runClient

# Run Fabric server
./gradlew :fabric:runServer
```

## 📁 Project Structure

```
metalmancy/
├── common/                          # Shared code
│   ├── src/main/kotlin/            # Runtime code
│   │   └── io/felipeandrade/metalmancy/
│   │       ├── material/           # Material system
│   │       ├── blocks/             # Block definitions
│   │       └── items/              # Item definitions
│   ├── src/main/resources/         # Assets and data
│   │   ├── assets/metalmancy/      # Client-side assets
│   │   └── data/metalmancy/        # Server-side data
│   ├── src/test/kotlin/            # Tests
│   └── src/tools/                  # Build-time generators
│       ├── blockgen/               # Block generator
│       ├── itemgen/                # Item generator
│       ├── recipegen/              # Recipe generator
│       ├── lootgen/                # Loot table generator
│       └── worldgen/               # Worldgen generator
├── fabric/                          # Fabric implementation
│   └── src/main/java/              # Fabric-specific code
├── neoforge/                        # NeoForge implementation (WIP)
│   └── src/main/java/              # NeoForge-specific code
└── .kiro/specs/material-system/    # Specification documents
    ├── requirements.md             # Requirements
    ├── design.md                   # Design document
    └── tasks.md                    # Task list
```

## 🔧 Making Changes

### Types of Contributions

We welcome various types of contributions:

1. **Bug Fixes** - Fix issues in existing code
2. **New Materials** - Add new metals, gems, or alloys
3. **Features** - Add new functionality
4. **Documentation** - Improve or add documentation
5. **Tests** - Add or improve test coverage
6. **Performance** - Optimize existing code

### Adding a New Material

This is the most common contribution. Follow these steps:

#### 1. Define the Material

Edit `common/src/main/kotlin/io/felipeandrade/metalmancy/material/Materials.kt`:

```kotlin
object Materials {
    // Add your material
    val ADAMANTIUM = Material(
        name = "adamantium",
        family = Family.METAL,
        parts = setOf(
            Part.ORE,
            Part.ORE_DEEPSLATE,
            Part.RAW_ITEM,
            Part.RAW_BLOCK,
            Part.INGOT,
            Part.NUGGET,
            Part.DUST,
            Part.BLOCK
        )
    )
    
    // Add to appropriate category
    val MYSTICAL_METALS = listOf(MITHRIL, ORICHALCUM, ADAMANTIUM)
    
    // Update METALS list
    val METALS = COPPER_LIKE_METALS + IRON_LIKE_METALS + 
                 DIAMOND_LIKE_METALS + MYSTICAL_METALS
}
```

#### 2. Generate Assets

```bash
./gradlew :common:generateBlocks
./gradlew :common:generateItems
./gradlew :common:generateRecipes
./gradlew :common:generateLoot
./gradlew :common:generateJson
./gradlew :common:syncGeneratedWorldgen
```

#### 3. Add Textures

Create 16x16 PNG textures in `common/src/main/resources/assets/metalmancy/textures/`:

- `block/adamantium_ore.png`
- `block/adamantium_deepslate_ore.png`
- `block/adamantium_raw_block.png`
- `block/adamantium_block.png`
- `item/adamantium_raw_item.png`
- `item/adamantium_ingot.png`
- `item/adamantium_nugget.png`
- `item/adamantium_dust.png`

**Texture Guidelines:**
- Use 16x16 pixel resolution
- Follow Minecraft's art style
- Ensure textures are distinct and recognizable
- Consider color palette and contrast

#### 4. Add Translations

Update both language files:

**common/src/main/resources/assets/metalmancy/lang/en_us.json:**
```json
{
  "block.metalmancy.adamantium_ore": "Adamantium Ore",
  "block.metalmancy.adamantium_deepslate_ore": "Deepslate Adamantium Ore",
  "block.metalmancy.adamantium_raw_block": "Block of Raw Adamantium",
  "block.metalmancy.adamantium_block": "Block of Adamantium",
  "item.metalmancy.adamantium_raw_item": "Raw Adamantium",
  "item.metalmancy.adamantium_ingot": "Adamantium Ingot",
  "item.metalmancy.adamantium_nugget": "Adamantium Nugget",
  "item.metalmancy.adamantium_dust": "Adamantium Dust"
}
```

**common/src/main/resources/assets/metalmancy/lang/pt_br.json:**
```json
{
  "block.metalmancy.adamantium_ore": "Minério de Adamantium",
  "block.metalmancy.adamantium_deepslate_ore": "Minério de Adamantium de Ardósia Profunda",
  "block.metalmancy.adamantium_raw_block": "Bloco de Adamantium Bruto",
  "block.metalmancy.adamantium_block": "Bloco de Adamantium",
  "item.metalmancy.adamantium_raw_item": "Adamantium Bruto",
  "item.metalmancy.adamantium_ingot": "Lingote de Adamantium",
  "item.metalmancy.adamantium_nugget": "Pepita de Adamantium",
  "item.metalmancy.adamantium_dust": "Pó de Adamantium"
}
```

#### 5. Configure World Generation (Optional)

Edit `common/src/tools/worldgen/OreGenEntries.kt`:

```kotlin
object OreGenEntries {
    val overworld = listOf(
        // ... existing entries
        OreGen(
            stone = "adamantium_ore",
            deepslate = "adamantium_deepslate_ore",
            yRange = -64..-32,
            heightType = OreGenHeightType.TRIANGLE,
            veinSize = 2,
            countPerChunk = 1
        )
    )
}
```

**World Generation Guidelines:**
- Consider rarity (countPerChunk: 1-10)
- Choose appropriate Y-range
- Use TRIANGLE for rare ores, TRAPEZOID for common ones
- Balance vein size (2-9 blocks)

#### 6. Test Your Changes

```bash
# Run all tests
./gradlew test

# Run property-based tests
./gradlew :common:test --tests "*PropertyTest"

# Test in-game
./gradlew :fabric:runClient
```

### Adding a New Feature

For larger features:

1. **Discuss First** - Open an issue to discuss the feature
2. **Update Specs** - Update requirements and design documents in `.kiro/specs/`
3. **Implement** - Write the code following existing patterns
4. **Test** - Add both unit tests and property-based tests
5. **Document** - Update relevant documentation

## 🧪 Testing Guidelines

### Writing Tests

We use two types of tests:

#### Unit Tests

Test specific functionality:

```kotlin
@Test
fun `material generates correct unlocalized name`() {
    val material = Material("ruby", Family.GEM, setOf(Part.ORE))
    assertEquals("ruby_ore", material.unlocalizedName(Part.ORE))
}
```

#### Property-Based Tests

Test universal properties using Kotest:

```kotlin
class MaterialPropertyTest : StringSpec({
    "Material preserves all fields" {
        checkAll(100, Arb.material()) { material ->
            material.name shouldNotBe ""
            material.parts shouldNotBe emptySet()
        }
    }
})
```

### Test Requirements

- All new code must have tests
- Property-based tests for universal properties
- Unit tests for specific examples
- Tests must pass before submitting PR
- Aim for >80% code coverage

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "MaterialTest"

# Run property tests only
./gradlew :common:test --tests "*PropertyTest"

# Run with verbose output
./gradlew test --info
```

## 📤 Submitting Changes

### Before Submitting

Ensure your changes:

- [ ] Build successfully (`./gradlew build`)
- [ ] Pass all tests (`./gradlew test`)
- [ ] Follow code style guidelines
- [ ] Include appropriate documentation
- [ ] Have descriptive commit messages

### Commit Messages

Follow conventional commit format:

```
type(scope): brief description

Detailed explanation of what changed and why.

Fixes #123
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `test`: Test additions or changes
- `refactor`: Code refactoring
- `style`: Code style changes
- `chore`: Build or tooling changes

**Examples:**
```
feat(materials): add adamantium metal

Add new mystical metal with ore generation in the End dimension.
Includes textures, translations, and worldgen configuration.

Closes #45

---

fix(worldgen): correct Y-range for deep ores

Deep ores were generating above Y=0. Updated range to -64..-16.

Fixes #67

---

docs(readme): add generator usage examples

Added detailed examples for running each generator with command-line
arguments and expected output.
```

### Pull Request Process

1. **Update your branch** with latest upstream:

```bash
git fetch upstream
git rebase upstream/main
```

2. **Push to your fork**:

```bash
git push origin feature/your-feature-name
```

3. **Create Pull Request** on GitHub:
   - Use a clear, descriptive title
   - Reference related issues
   - Describe what changed and why
   - Include screenshots for visual changes
   - List any breaking changes

4. **Respond to feedback**:
   - Address review comments
   - Push additional commits if needed
   - Be open to suggestions

5. **Merge**:
   - Maintainers will merge once approved
   - Your branch will be deleted after merge

## 🎨 Style Guidelines

### Kotlin Code Style

Follow Kotlin coding conventions:

```kotlin
// Use descriptive names
val copperLikeMetals = listOf(ZINC, TIN, LEAD, NICKEL)

// Prefer immutability
val material = Material("ruby", Family.GEM, setOf(Part.ORE))

// Use data classes for data structures
data class Material(
    val name: String,
    val family: Family,
    val parts: Set<Part>
)

// Use object for singletons
object Materials {
    val RUBY = Material("ruby", Family.GEM, setOf(...))
}

// Document public APIs
/**
 * Generates the unlocalized name for a material part.
 * 
 * @param part The part to generate a name for
 * @return The unlocalized name (e.g., "ruby_ore")
 */
fun unlocalizedName(part: Part): String = "${name}_${part.id}"
```

### JSON Style

Generated JSON should be:
- Pretty-printed with 2-space indentation
- Use double quotes for strings
- No trailing commas
- Alphabetically sorted keys (where order doesn't matter)

### Documentation Style

- Use clear, concise language
- Include code examples
- Add comments for complex logic
- Keep README files up to date
- Use proper Markdown formatting

## 🔨 Common Tasks

### Regenerate All Assets

```bash
./gradlew :common:generateBlocks
./gradlew :common:generateItems
./gradlew :common:generateRecipes
./gradlew :common:generateLoot
./gradlew :common:generateJson
./gradlew :common:syncGeneratedWorldgen
```

### Clean Build

```bash
./gradlew clean build
```

### Update Dependencies

Edit `gradle.properties` and `build.gradle`, then:

```bash
./gradlew --refresh-dependencies
```

### Debug in IDE

1. Create a run configuration for `:fabric:runClient`
2. Set breakpoints in your code
3. Run in debug mode

### Check for Issues

```bash
# Run all tests
./gradlew test

# Check for compilation errors
./gradlew compileKotlin

# Verify generated assets
./gradlew :common:generateBlocks --args="--out build/generated/assets"
```

## 📚 Additional Resources

- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Architectury Documentation](https://docs.architectury.dev/)
- [Fabric Wiki](https://fabricmc.net/wiki/)
- [Minecraft Wiki](https://minecraft.wiki/)
- [Kotest Documentation](https://kotest.io/)

## ❓ Getting Help

If you need help:

1. Check existing documentation
2. Search existing issues
3. Ask in GitHub Discussions
4. Join our Discord (if applicable)

## 🙏 Thank You

Thank you for contributing to Metalmancy! Your efforts help make this mod better for everyone.

---

**Questions?** Open an issue or start a discussion on GitHub.
