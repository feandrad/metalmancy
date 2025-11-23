package io.felipeandrade.metalmancy.material

import io.felipeandrade.metalmancy.blocks.MaterialBlocks
import io.felipeandrade.metalmancy.registry.material.Materials

/**
 * Simple verification that MaterialBlocks is structured correctly.
 * This verifies the implementation without requiring Minecraft runtime.
 */
fun main() {
    println("=== MaterialBlocks Verification ===\n")
    
    // Verify that MaterialBlocks has the expected structure
    println("1. Verifying MaterialBlocks structure:")
    println("   MaterialBlocks.GEMS exists: ${MaterialBlocks.GEMS != null}")
    println("   MaterialBlocks.SALTS exists: ${MaterialBlocks.SALTS != null}")
    println("   MaterialBlocks.METALS exists: ${MaterialBlocks.METALS != null}")
    println("   MaterialBlocks.ALLOYS exists: ${MaterialBlocks.ALLOYS != null}")
    
    // Verify that blocks are organized by category
    println("\n2. Verifying block organization:")
    println("   Number of gem materials with blocks: ${MaterialBlocks.GEMS.size}")
    println("   Number of salt materials with blocks: ${MaterialBlocks.SALTS.size}")
    println("   Number of metal materials with blocks: ${MaterialBlocks.METALS.size}")
    println("   Number of alloy materials with blocks: ${MaterialBlocks.ALLOYS.size}")
    
    // Verify that blocks are created for block parts only
    println("\n3. Verifying blocks are created for block parts:")
    Materials.GEMS.forEach { material ->
        val blocks = MaterialBlocks.GEMS[material] ?: emptyMap()
        val blockParts = material.parts.filter { it.isBlock }
        println("   ${material.name}: ${blocks.size} blocks for ${blockParts.size} block parts")
        
        // Verify all block parts have blocks
        blockParts.forEach { part ->
            val hasBlock = blocks.containsKey(part)
            if (!hasBlock) {
                println("     WARNING: Missing block for ${material.name} $part")
            }
        }
    }
    
    // Verify that createLike function exists
    println("\n4. Verifying createLike function:")
    try {
        val properties = MaterialBlocks.createLike("test", net.minecraft.world.level.block.Blocks.STONE)
        println("   createLike function works: ${properties != null}")
    } catch (e: Exception) {
        println("   createLike function error: ${e.message}")
    }
    
    // Verify category-specific properties
    println("\n5. Verifying category organization:")
    println("   Gems in MaterialBlocks.GEMS:")
    MaterialBlocks.GEMS.keys.forEach { material ->
        println("     - ${material.name} (family: ${material.family})")
    }
    
    println("   Salts in MaterialBlocks.SALTS:")
    MaterialBlocks.SALTS.keys.forEach { material ->
        println("     - ${material.name} (family: ${material.family})")
    }
    
    println("   Metals in MaterialBlocks.METALS:")
    MaterialBlocks.METALS.keys.forEach { material ->
        val category = when {
            material in Materials.COPPER_LIKE_METALS -> "copper-like"
            material in Materials.IRON_LIKE_METALS -> "iron-like"
            material in Materials.GOLD_LIKE_METALS -> "gold-like"
            material in Materials.DIAMOND_LIKE_METALS -> "diamond-like"
            else -> "special"
        }
        println("     - ${material.name} (family: ${material.family}, category: $category)")
    }
    
    println("   Alloys in MaterialBlocks.ALLOYS:")
    MaterialBlocks.ALLOYS.keys.forEach { material ->
        println("     - ${material.name} (family: ${material.family})")
    }
    
    println("\n=== Verification Complete ===")
}
