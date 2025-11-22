package io.felipeandrade.metalmancy.material

/**
 * Simple verification that MaterialItems is structured correctly.
 * This verifies the implementation without requiring Minecraft runtime.
 */
fun main() {
    println("=== MaterialItems Verification ===\n")
    
    // Verify that MaterialItems has the expected structure
    println("1. Verifying MaterialItems structure:")
    println("   MaterialItems.GEMS exists: ${MaterialItems.GEMS != null}")
    println("   MaterialItems.SALTS exists: ${MaterialItems.SALTS != null}")
    println("   MaterialItems.METALS exists: ${MaterialItems.METALS != null}")
    println("   MaterialItems.ALLOYS exists: ${MaterialItems.ALLOYS != null}")
    
    // Verify that items are organized by category
    println("\n2. Verifying item organization:")
    println("   Number of gem materials with items: ${MaterialItems.GEMS.size}")
    println("   Number of salt materials with items: ${MaterialItems.SALTS.size}")
    println("   Number of metal materials with items: ${MaterialItems.METALS.size}")
    println("   Number of alloy materials with items: ${MaterialItems.ALLOYS.size}")
    
    // Verify that items are created for all parts
    println("\n3. Verifying items are created for all parts:")
    Materials.GEMS.forEach { material ->
        val items = MaterialItems.GEMS[material] ?: emptyMap()
        val allParts = material.parts
        println("   ${material.name}: ${items.size} items for ${allParts.size} parts")
        
        // Verify all parts have items
        allParts.forEach { part ->
            val hasItem = items.containsKey(part)
            if (!hasItem) {
                println("     WARNING: Missing item for ${material.name} $part")
            }
        }
    }
    
    // Verify that BlockItems are created for block parts
    println("\n4. Verifying BlockItems for block parts:")
    Materials.METALS.take(2).forEach { material ->
        val items = MaterialItems.METALS[material] ?: emptyMap()
        val blockParts = material.parts.filter { it.isBlock }
        println("   ${material.name}:")
        blockParts.forEach { part ->
            val item = items[part]
            val isBlockItem = item is net.minecraft.world.item.BlockItem
            println("     - $part: ${if (isBlockItem) "BlockItem ✓" else "Regular Item ✗"}")
        }
    }
    
    // Verify category-specific organization
    println("\n5. Verifying category organization:")
    println("   Gems in MaterialItems.GEMS:")
    MaterialItems.GEMS.keys.forEach { material ->
        val itemCount = MaterialItems.GEMS[material]?.size ?: 0
        println("     - ${material.name} (family: ${material.family}, items: $itemCount)")
    }
    
    println("   Salts in MaterialItems.SALTS:")
    MaterialItems.SALTS.keys.forEach { material ->
        val itemCount = MaterialItems.SALTS[material]?.size ?: 0
        println("     - ${material.name} (family: ${material.family}, items: $itemCount)")
    }
    
    println("   Metals in MaterialItems.METALS:")
    MaterialItems.METALS.keys.take(5).forEach { material ->
        val itemCount = MaterialItems.METALS[material]?.size ?: 0
        val category = when {
            material in Materials.COPPER_LIKE_METALS -> "copper-like"
            material in Materials.IRON_LIKE_METALS -> "iron-like"
            material in Materials.GOLD_LIKE_METALS -> "gold-like"
            material in Materials.DIAMOND_LIKE_METALS -> "diamond-like"
            else -> "special"
        }
        println("     - ${material.name} (family: ${material.family}, category: $category, items: $itemCount)")
    }
    println("   ... and ${MaterialItems.METALS.size - 5} more metals")
    
    println("   Alloys in MaterialItems.ALLOYS:")
    MaterialItems.ALLOYS.keys.forEach { material ->
        val itemCount = MaterialItems.ALLOYS[material]?.size ?: 0
        println("     - ${material.name} (family: ${material.family}, items: $itemCount)")
    }
    
    // Verify convenience accessors
    println("\n6. Verifying convenience accessors:")
    val accessors = listOf(
        "RUBY" to MaterialItems.RUBY,
        "ZINC" to MaterialItems.ZINC,
        "PEWTER" to MaterialItems.PEWTER
    )
    accessors.forEach { (name, items) ->
        println("   MaterialItems.$name: ${items.size} items")
    }
    
    println("\n=== Verification Complete ===")
}
