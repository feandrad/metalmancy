#!/bin/bash

# Test script to verify the cleanup function works correctly

echo "Setting up test environment..."

# Create test directories
mkdir -p common/src/main/resources/data/metalmancy/recipes
mkdir -p common/src/main/resources/data/metalmancy/recipe

# Create test files
echo '{"type": "minecraft:smelting"}' > common/src/main/resources/data/metalmancy/recipes/test1.json
echo '{"type": "minecraft:blasting"}' > common/src/main/resources/data/metalmancy/recipes/test2.json
echo '{"type": "minecraft:crafting_shaped"}' > common/src/main/resources/data/metalmancy/recipe/test3.json
echo '{"type": "minecraft:crafting_shapeless"}' > common/src/main/resources/data/metalmancy/recipe/test4.json

echo "Test files created:"
ls -la common/src/main/resources/data/metalmancy/recipes/
ls -la common/src/main/resources/data/metalmancy/recipe/

echo ""
echo "Running RecipeGen (which will call deleteOldRecipes)..."
./gradlew :common:runRecipeGen

echo ""
echo "Checking if files were deleted..."
echo "Old recipes/ directory:"
ls -la common/src/main/resources/data/metalmancy/recipes/ 2>&1 || echo "Directory empty or doesn't exist"
echo ""
echo "New recipe/ directory:"
ls -la common/src/main/resources/data/metalmancy/recipe/ 2>&1 || echo "Directory empty or doesn't exist"
