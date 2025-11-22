package io.felipeandrade.metalmancy.material

/**
 * Simple verification that the material system is working correctly.
 * This can be run to manually verify the implementation.
 */
fun main() {
    println("=== Material System Verification ===\n")
    
    // Verify Material creation and unlocalized names
    println("1. Testing Material creation and unlocalized names:")
    val ruby = Materials.RUBY
    println("   Ruby ore: ${ruby.unlocalizedName(Part.ORE)}")
    println("   Ruby deepslate ore: ${ruby.unlocalizedName(Part.ORE_DEEPSLATE)}")
    println("   Ruby gem: ${ruby.unlocalizedName(Part.GEM)}")
    println("   Ruby block: ${ruby.unlocalizedName(Part.BLOCK)}")
    
    val zinc = Materials.ZINC
    println("   Zinc ore: ${zinc.unlocalizedName(Part.ORE)}")
    println("   Zinc ingot: ${zinc.unlocalizedName(Part.INGOT)}")
    println("   Zinc nugget: ${zinc.unlocalizedName(Part.NUGGET)}")
    
    // Verify groupings
    println("\n2. Testing material groupings:")
    println("   Total materials: ${Materials.ALL.size}")
    println("   Gems: ${Materials.GEMS.size} - ${Materials.GEMS.map { it.name }}")
    println("   Salts: ${Materials.SALTS.size} - ${Materials.SALTS.map { it.name }}")
    println("   Copper-like metals: ${Materials.COPPER_LIKE_METALS.size} - ${Materials.COPPER_LIKE_METALS.map { it.name }}")
    println("   Iron-like metals: ${Materials.IRON_LIKE_METALS.size} - ${Materials.IRON_LIKE_METALS.map { it.name }}")
    println("   Gold-like metals: ${Materials.GOLD_LIKE_METALS.size} - ${Materials.GOLD_LIKE_METALS.map { it.name }}")
    println("   Diamond-like metals: ${Materials.DIAMOND_LIKE_METALS.size} - ${Materials.DIAMOND_LIKE_METALS.map { it.name }}")
    println("   Special metals: ${Materials.SPECIAL_METALS.size} - ${Materials.SPECIAL_METALS.map { it.name }}")
    println("   Alloys: ${Materials.ALLOYS.size} - ${Materials.ALLOYS.map { it.name }}")
    
    // Verify family grouping correctness
    println("\n3. Verifying family grouping correctness:")
    val allGemsAreGems = Materials.GEMS.all { it.family == Family.GEM }
    val allSaltsAreSalts = Materials.SALTS.all { it.family == Family.SALT }
    val allMetalsAreMetals = Materials.METALS.all { it.family == Family.METAL }
    val allAlloysAreAlloys = Materials.ALLOYS.all { it.family == Family.ALLOY }
    
    println("   All gems are GEM family: $allGemsAreGems")
    println("   All salts are SALT family: $allSaltsAreSalts")
    println("   All metals are METAL family: $allMetalsAreMetals")
    println("   All alloys are ALLOY family: $allAlloysAreAlloys")
    
    // Verify ALL is complete
    println("\n4. Verifying ALL list completeness:")
    val expectedSize = Materials.GEMS.size + Materials.SALTS.size + Materials.METALS.size + Materials.ALLOYS.size
    val actualSize = Materials.ALL.size
    println("   Expected size: $expectedSize")
    println("   Actual size: $actualSize")
    println("   ALL is complete: ${expectedSize == actualSize}")
    
    // Verify Part.isBlock
    println("\n5. Verifying Part.isBlock property:")
    println("   ORE is block: ${Part.ORE.isBlock}")
    println("   ORE_DEEPSLATE is block: ${Part.ORE_DEEPSLATE.isBlock}")
    println("   RAW_BLOCK is block: ${Part.RAW_BLOCK.isBlock}")
    println("   BLOCK is block: ${Part.BLOCK.isBlock}")
    println("   INGOT is block: ${Part.INGOT.isBlock}")
    println("   GEM is block: ${Part.GEM.isBlock}")
    
    println("\n=== Verification Complete ===")
}
