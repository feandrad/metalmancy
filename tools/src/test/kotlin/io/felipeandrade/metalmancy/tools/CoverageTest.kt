package io.felipeandrade.metalmancy.tools

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Materials
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.io.File

/**
 * This test checks if all material parts have corresponding generated files.
 *
 * NOTE: You must run `./gradlew :common:syncGeneratedAll` before running this test
 * to ensure the generated files are present in the resources directory.
 */
class CoverageTest {

    private val resourcesDir = "src/main/resources"
    private val root = "/Users/feandrad/Develop/Minecraft/metalmancy/common"

    @TestFactory
    fun `all material parts should have corresponding files`(): List<DynamicTest> {
        val tests = mutableListOf<DynamicTest>()
        Materials.ALL.forEach { material ->
            material.parts.forEach { part ->
                val unlocalizedName = Material.unlocalizedName(material.name, part)
                when {
                    part.isBlock -> {
                        val blockstatePath = "$root/$resourcesDir/assets/${Metalmancy.MOD_ID}/blockstates/$unlocalizedName.json"
                        tests.add(
                            DynamicTest.dynamicTest("Blockstate for $unlocalizedName") {
                                println("  Checking blockstate: $blockstatePath")
                                val file = File(blockstatePath)
                                assertTrue(file.exists(), "Blockstate file not found for $unlocalizedName at $blockstatePath")
                            }
                        )

                        val blockModelPath = "$root/$resourcesDir/assets/${Metalmancy.MOD_ID}/models/block/$unlocalizedName.json"
                        tests.add(
                            DynamicTest.dynamicTest("Block model for $unlocalizedName") {
                                println("  Checking block model: $blockModelPath")
                                val file = File(blockModelPath)
                                assertTrue(file.exists(), "Block model file not found for $unlocalizedName at $blockModelPath")
                            }
                        )

                        val itemModelPath = "$root/$resourcesDir/assets/${Metalmancy.MOD_ID}/models/item/$unlocalizedName.json"
                        tests.add(
                            DynamicTest.dynamicTest("Item model for block $unlocalizedName") {
                                println("  Checking item model for block: $itemModelPath")
                                val file = File(itemModelPath)
                                assertTrue(file.exists(), "Item model file not found for block $unlocalizedName at $itemModelPath")
                            }
                        )
                    }
                    else -> {
                        val itemModelPath = "$root/$resourcesDir/assets/${Metalmancy.MOD_ID}/models/item/$unlocalizedName.json"
                        tests.add(
                            DynamicTest.dynamicTest("Item model for $unlocalizedName") {
                                println("  Checking item model: $itemModelPath")
                                val file = File(itemModelPath)
                                assertTrue(file.exists(), "Item model file not found for $unlocalizedName at $itemModelPath")
                            }
                        )
                    }
                }
            }
        }
        return tests
    }
}