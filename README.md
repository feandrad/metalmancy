# Metalmancy - Material System

A Minecraft mod that adds an extensible material system with new metals, gems, and alchemical materials. Built with Architectury for multi-platform support (Fabric and NeoForge).

## 🌟 Features

- **26 New Materials**: 3 gems, 3 alchemical materials, 14 metals, and 6 alloys
- **Automatic Asset Generation**: Build-time generators create all JSON files from Kotlin definitions
- **Multi-Platform**: Compatible with Fabric (NeoForge support in development)
- **Extensible Design**: Add new materials by editing a single Kotlin file
- **Property-Based Testing**: Comprehensive test suite ensures correctness

## 📦 Materials

### Gems (3)
- **Ruby** - Red gemstone found in the overworld
- **Sapphire** - Blue gemstone found in the overworld
- **Topaz** - Yellow gemstone found in the overworld

### Alchemical Materials (3)
- **Rock Salt** - Essential for alchemical processes
- **Potash** - Potassium-based alchemical material
- **Mercury** - Derived from cinnabar ore

### Metals

#### Base Metals (Copper-tier, 4)
- **Zinc** - Lightweight metal for alloys
- **Tin** - Essential for bronze production
- **Lead** - Dense metal with unique properties
- **Nickel** - Corrosion-resistant metal

#### Intermediate Metals (Iron-tier, 4)
- **Aluminum** - Lightweight and strong
- **Manganese** - Steel hardening agent
- **Silver** - Precious metal with magical properties
- **Cobalt** - Blue-tinted metal

#### Advanced Metals (Diamond-tier, 4)
- **Platinum** - Rare precious metal
- **Titanium** - Extremely strong and light
- **Lithium** - Reactive metal for advanced crafting
- **Uranium** - Radioactive metal for power generation

#### Mystical Metals (Netherite-tier, 2)
- **Mithril** - Legendary lightweight metal (found in Nether)
- **Orichalcum** - Ancient powerful metal (found in End)

### Alloys (6)
- **Pewter** - Tin and lead alloy
- **Brass** - Copper and zinc alloy
- **Bronze** - Copper and tin alloy
- **Steel** - Iron and carbon alloy
- **Electrum** - Gold and silver alloy
- **Invar** - Iron and nickel alloy

## 🚀 Quick Start

### For Players

1. Download the mod from [releases page]
2. Install Fabric Loader (or NeoForge when available)
3. Place the mod JAR in your `mods` folder
4. Launch Minecraft 1.21.x

### For Developers

#### Prerequisites
- JDK 21
- Gradle 8.x
- IntelliJ IDEA (recommended)

#### Building from Source

```bash
# Clone the repository
git clone https://github.com/yourusername/metalmancy.git
cd metalmancy

# Build the mod
./gradlew build

# The compiled JARs will be in:
# - fabric/build/libs/metalmancy-fabric-*.jar
# - neoforge/build/libs/metalmancy-neoforge-*.jar (when available)
```

#### Running in Development

```bash
# Run Fabric client
./gradlew :fabric:runClient

# Run Fabric server
./gradlew :fabric:runServer

# Run NeoForge client (when available)
./gradlew :neoforge:runClient
```

## 🛠️ Adding New Materials

The material system is designed to be easily extensible. Here's how to add a new material:

### 1. Define the Material

Edit `common/src/main/kotlin/io/felipeandrade/metalmancy/material/Materials.kt`:

```kotlin
object Materials {
    // Add your material definition
    val MYTHRIL = Material(
        name = "mythril",
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
    
    // Add to appropriate category list
    val MYSTICAL_METALS = listOf(MITHRIL, ORICHALCUM, MYTHRIL)
    
    // Update METALS list
    val METALS = COPPER_LIKE_METALS + IRON_LIKE_METALS + 
                 DIAMOND_LIKE_METALS + MYSTICAL_METALS
}
```

### 2. Generate Assets

Run the asset generators to create all necessary JSON files:

```bash
# Generate all assets
./gradlew :common:generateBlocks
./gradlew :common:generateItems
./gradlew :common:generateRecipes
./gradlew :common:generateLoot
./gradlew :common:generateJson

# Copy generated worldgen to resources
./gradlew :common:syncGeneratedWorldgen
```

### 3. Add Textures

Create texture files in `common/src/main/resources/assets/metalmancy/textures/`:

```
block/
  mythril_ore.png
  mythril_deepslate_ore.png
  mythril_raw_block.png
  mythril_block.png
item/
  mythril_raw_item.png
  mythril_ingot.png
  mythril_nugget.png
  mythril_dust.png
```

### 4. Add Translations

Edit language files in `common/src/main/resources/assets/metalmancy/lang/`:

**en_us.json:**
```json
{
  "block.metalmancy.mythril_ore": "Mythril Ore",
  "block.metalmancy.mythril_deepslate_ore": "Deepslate Mythril Ore",
  "item.metalmancy.mythril_ingot": "Mythril Ingot"
}
```

**pt_br.json:**
```json
{
  "block.metalmancy.mythril_ore": "Minério de Mithril",
  "block.metalmancy.mythril_deepslate_ore": "Minério de Mithril de Ardósia Profunda",
  "item.metalmancy.mythril_ingot": "Lingote de Mithril"
}
```

### 5. Configure World Generation (Optional)

Edit `common/src/tools/worldgen/OreGenEntries.kt` to configure ore generation:

```kotlin
object OreGenEntries {
    val overworld = listOf(
        // ... existing entries
        OreGen(
            stone = "mythril_ore",
            deepslate = "mythril_deepslate_ore",
            yRange = -64..-16,
            heightType = OreGenHeightType.TRIANGLE,
            veinSize = 3,
            countPerChunk = 2
        )
    )
}
```

### 6. Test Your Changes

```bash
# Run tests
./gradlew test

# Run property-based tests
./gradlew :common:test --tests "*PropertyTest"

# Launch the game
./gradlew :fabric:runClient
```

That's it! Your new material is now fully integrated into the game.

## 🏗️ Architecture

The mod uses a data-driven architecture with clear separation between runtime and build-time code:

### Runtime Components

- **Material System** (`common/src/main/kotlin/io/felipeandrade/metalmancy/material/`)
  - `Material.kt` - Core material data structure
  - `Materials.kt` - Catalog of all materials
  - `MaterialBlocks.kt` - Automatic block registration
  - `MaterialItems.kt` - Automatic item registration

### Build-Time Generators

- **Block Generator** (`common/src/tools/blockgen/`) - Generates blockstates and block models
- **Item Generator** (`common/src/tools/itemgen/`) - Generates item models
- **Recipe Generator** (`common/src/tools/recipegen/`) - Generates crafting recipes
- **Loot Generator** (`common/src/tools/lootgen/`) - Generates loot tables
- **Worldgen Generator** (`common/src/tools/worldgen/`) - Generates ore placement

### Platform Modules

- **Common** - Shared code and assets
- **Fabric** - Fabric-specific implementation (✅ Fully functional)
- **NeoForge** - NeoForge-specific implementation (⏳ In development)

## 📝 Generator Usage

### Block Generator

Generates blockstate and block model JSON files:

```bash
./gradlew :common:generateBlocks --args="--out build/generated/assets"
```

Output:
- `assets/metalmancy/blockstates/*.json`
- `assets/metalmancy/models/block/*.json`

### Item Generator

Generates item model JSON files:

```bash
./gradlew :common:generateItems --args="--out build/generated/assets"
```

Output:
- `assets/metalmancy/models/item/*.json`
- `assets/metalmancy/items/*.json`

### Recipe Generator

Generates smelting and blasting recipes:

```bash
./gradlew :common:generateRecipes --args="--out build/generated/data"
```

Output:
- `data/metalmancy/recipes/*.json`

### Loot Generator

Generates loot tables for ore blocks:

```bash
./gradlew :common:generateLoot --args="--out build/generated/data"
```

Output:
- `data/metalmancy/loot_tables/blocks/*.json`

### Worldgen Generator

Generates ore placement configurations:

```bash
./gradlew :common:generateJson --args="--out build/generated"
./gradlew :common:syncGeneratedWorldgen
```

Output:
- `data/metalmancy/worldgen/configured_feature/*.json`
- `data/metalmancy/worldgen/placed_feature/*.json`

See [Worldgen README](common/src/tools/worldgen/README.md) for detailed documentation.

## 🧪 Testing

The mod includes comprehensive testing:

### Unit Tests

Test individual components and logic:

```bash
./gradlew test
```

### Property-Based Tests

Test universal properties across all materials using Kotest:

```bash
./gradlew :common:test --tests "*PropertyTest"
```

The test suite includes 40+ property-based tests covering:
- Material field preservation
- Name generation correctness
- Block and item registration
- JSON generation validity
- Worldgen configuration correctness

See [Property Testing README](common/src/test/kotlin/io/felipeandrade/metalmancy/material/PROPERTY_TESTING_README.md) for details.

### Test Coverage

Current test statistics:
- **381 total tests** (40 property tests + 341 unit tests)
- **100% pass rate**
- All correctness properties validated

## 📚 Documentation

- [Requirements Document](.kiro/specs/material-system/requirements.md) - Formal requirements specification
- [Design Document](.kiro/specs/material-system/design.md) - Detailed system design
- [Task List](.kiro/specs/material-system/tasks.md) - Implementation progress
- [Worldgen Guide](common/src/tools/worldgen/README.md) - Ore generation documentation
- [Contributing Guide](CONTRIBUTING.md) - How to contribute

## 🤝 Contributing

We welcome contributions! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

### Quick Contribution Checklist

- [ ] Fork the repository
- [ ] Create a feature branch
- [ ] Add your changes
- [ ] Run tests (`./gradlew test`)
- [ ] Run generators if you added materials
- [ ] Update documentation
- [ ] Submit a pull request

## 📄 License

This project is licensed under the terms specified in [LICENSE.txt](LICENSE.txt).

## 🙏 Acknowledgments

- Built with [Architectury](https://github.com/architectury/architectury-api)
- Uses [Kotest](https://kotest.io/) for property-based testing
- Inspired by classic tech mods and metallurgy systems

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/yourusername/metalmancy/issues)
- **Discussions**: [GitHub Discussions](https://github.com/yourusername/metalmancy/discussions)
- **Discord**: [Join our Discord](#) (if applicable)

## 🗺️ Roadmap

- [x] Core material system
- [x] Fabric support
- [x] Asset generators
- [x] Property-based testing
- [ ] NeoForge support
- [ ] Tool and armor sets
- [ ] Advanced crafting mechanics
- [ ] Alchemical processing
- [ ] Magic system integration

---

**Made with ❤️ for the Minecraft modding community**
