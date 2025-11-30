package io.felipeandrade.metalmancy.tools.toolgen

import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part
import tools.toolgen.ToolMaterials

/**
 * Manual verification test for ToolMaterials functionality.
 * This test can be run independently to verify the implementation works correctly.
 */
fun main() {
    println("=== ToolMaterials Manual Verification ===\n")
    
    // Test 1: Verify all tool-enabled materials have required parts
    println("Test 1: Verifying all tool-enabled materials have INGOT or GEM parts...")
    var allValid = true
    for (material in ToolMaterials.TOOL_ENABLED) {
        val hasRequired = material.parts.contains(Part.INGOT) || material.parts.contains(Part.GEM)
        if (!hasRequired) {
            println("  ❌ FAIL: ${material.name} does not have INGOT or GEM part")
            allValid = false
        } else {
            println("  ✓ ${material.name} has required parts")
        }
    }
    if (allValid) {
        println("✅ Test 1 PASSED: All tool-enabled materials have required parts\n")
    } else {
        println("❌ Test 1 FAILED\n")
    }
    
    // Test 2: Verify the correct materials are in the tool-enabled list
    println("Test 2: Verifying tool-enabled list contains exactly the specified materials...")
    val expectedMaterials = setOf(
        "brass", "bronze", "silver", "cobalt", "orichalcum", "mithril",
        "platinum", "titanium", "electrum", "topaz", "ruby", "sapphire",
        "aluminum", "steel"
    )
    val actualMaterials = ToolMaterials.TOOL_ENABLED.map { it.name }.toSet()
    
    if (actualMaterials == expectedMaterials) {
        println("✅ Test 2 PASSED: Tool-enabled list contains exactly the expected materials\n")
    } else {
        println("❌ Test 2 FAILED")
        println("  Expected: $expectedMaterials")
        println("  Actual: $actualMaterials")
        println("  Missing: ${expectedMaterials - actualMaterials}")
        println("  Extra: ${actualMaterials - expectedMaterials}\n")
    }
    
    // Test 3: Verify tier mapping works for all tool-enabled materials
    println("Test 3: Verifying tier mapping for all tool-enabled materials...")
    var allMapped = true
    for (material in ToolMaterials.TOOL_ENABLED) {
        try {
            val tier = ToolMaterials.getTier(material)
            println("  ✓ ${material.name} -> mining level ${tier.miningLevel}, durability ${tier.durability}")
        } catch (e: Exception) {
            println("  ❌ FAIL: ${material.name} - ${e.message}")
            allMapped = false
        }
    }
    if (allMapped) {
        println("✅ Test 3 PASSED: All tool-enabled materials have tier mappings\n")
    } else {
        println("❌ Test 3 FAILED\n")
    }
    
    // Test 4: Verify validation function works
    println("Test 4: Verifying validation function...")
    val invalidMaterials = ToolMaterials.validateToolEnabledMaterials()
    if (invalidMaterials.isEmpty()) {
        println("✅ Test 4 PASSED: All tool-enabled materials pass validation\n")
    } else {
        println("❌ Test 4 FAILED: Found invalid materials:")
        for (material in invalidMaterials) {
            println("  - ${material.name}")
        }
        println()
    }
    
    // Test 5: Verify non-tool-enabled materials are excluded
    println("Test 5: Verifying non-tool-enabled materials are excluded...")
    val nonToolMaterials = listOf(Materials.ZINC, Materials.TIN, Materials.LEAD, Materials.NICKEL)
    var allExcluded = true
    for (material in nonToolMaterials) {
        if (material in ToolMaterials.TOOL_ENABLED) {
            println("  ❌ FAIL: ${material.name} should not be in tool-enabled list")
            allExcluded = false
        } else {
            println("  ✓ ${material.name} correctly excluded")
        }
    }
    if (allExcluded) {
        println("✅ Test 5 PASSED: Non-tool-enabled materials are correctly excluded\n")
    } else {
        println("❌ Test 5 FAILED\n")
    }
    
    println("=== Manual Verification Complete ===")
}
