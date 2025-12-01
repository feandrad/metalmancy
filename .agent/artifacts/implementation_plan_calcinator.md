# Implementation Plan - Port Calcinator to 3.3.1 (Architectury 1.21)

## Goal
Port the Calcinator block, block entity, screen, and recipe logic from the `main` branch (Fabric 1.20.x) to the `3.3.1` branch (Architectury 1.21.x).

## User Review Required
> [!IMPORTANT]
> **Version Compatibility**: The user has specified that code below 1.21.8 is not guaranteed to be compatible with 1.21.10.
> - I will verify all Architectury and Minecraft API calls against 1.21.10 standards.
> - I will avoid using deprecated methods from early 1.21 versions.
> - Please confirm if there are specific "breaking changes" I should be aware of (e.g., Data Component changes, Registry freezing, etc.).

## Constraints & Standards
- **Target Version**: Minecraft 1.21.10
- **Framework**: Architectury API
- **Module Structure**:
    - **Common**: Contains all game logic, blocks, items, block entities, menus, and screens.
    - **Fabric**: Only Fabric-specific registration entry points.
    - **NeoForge**: Only NeoForge-specific registration entry points.
- **Data Gen**: Ensure recipes and tags are datagen'd if possible.

## Proposed Changes

### 1. Port `CalcinatingRecipe`
- **Location**: `common/src/main/kotlin/io/felipeandrade/metalmancy/recipe/CalcinatingRecipe.kt`
- **Changes**:
    - **Codec**: Use `MapCodec` and `StreamCodec` (MC 1.21 standard) for network serialization.
    - **Serializer**: Implement `RecipeSerializer<CalcinatingRecipe>`.
    - **Registration**: Register via `ModRecipes` (Architectury `DeferredRegister`).

### 2. Port `CalcinatorBlock`
- **Location**: `common/src/main/kotlin/io/felipeandrade/metalmancy/blocks/CalcinatorBlock.kt`
- **Changes**:
    - **Inheritance**: `AbstractFurnaceBlock` is good, but ensure `MapCodec` is implemented correctly for 1.21.
    - **Menu Opening**: Use `MenuRegistry.openMenu(player, menuProvider)` (Architectury) instead of `player.openHandledScreen`.
    - **Ticker**: Use `validateTicker` helper if available, or standard check.

### 3. Port `CalcinatorBlockEntity`
- **Location**: `common/src/main/kotlin/io/felipeandrade/metalmancy/blocks/entity/CalcinatorBlockEntity.kt`
- **Changes**:
    - **Fluids**:
        - **Do NOT** use `SingleFluidStorage` (Fabric API).
        - Use Architectury's `FluidStack` hooks or a platform-agnostic `FluidTank` implementation (often provided by libraries like `Botarium` or custom `SimpleFluidTank` in `common`).
        - *Decision*: Implement a simple `FluidTank` class in `common` that saves to NBT and syncs via `PropertyDelegate` or custom packet.
    - **Inventory**: Use standard `Container` or `SimpleContainer`.
    - **Syncing**:
        - Use `NetworkManager` (Architectury) for fluid syncing packets.
        - Create a `FluidSyncPacket` class in `common/network`.

### 4. Port `CalcinatorMenu` (Renamed from ScreenHandler)
- **Location**: `common/src/main/kotlin/io/felipeandrade/metalmancy/menu/CalcinatorMenu.kt`
- **Changes**:
    - Rename `CalcinatorScreenHandler` to `CalcinatorMenu` to match project convention.
    - Extend `AbstractContainerMenu`.
    - **Slots**: Ensure `Slot` and `FurnaceOutputSlot` usage is correct for 1.21.
    - **Syncing**: Sync fluid data via `addDataSlot` or custom packet handling if `PropertyDelegate` isn't enough (Ints are too small for some fluid amounts, but fine for buckets usually. 1.21 might allow Longs in data slots? No, still Ints. Split Longs into two Ints or use Packet).

### 5. Port `CalcinatorScreen`
- **Location**: `common/src/main/kotlin/io/felipeandrade/metalmancy/client/screen/CalcinatorScreen.kt`
- **Changes**:
    - **Rendering**: Use `DrawContext` (MC 1.20+).
    - **Registration**: Use `MenuRegistry.registerScreenFactory` in `ClientSetup` (or equivalent common client init).

### 6. Registration & Boilerplate
- **Registry Classes**:
    - `ModBlocks`: Register `CALCINATOR`.
    - `ModBlockEntities`: Register `CALCINATOR_BLOCK_ENTITY`.
    - `ModMenus`: Register `CALCINATOR_MENU`.
    - `ModRecipes`: Register `CALCINATING` type and serializer.
