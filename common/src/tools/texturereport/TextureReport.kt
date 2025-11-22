package texturereport

import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part
import java.io.File

/**
 * Generates a report of missing textures for the material system.
 * 
 * This tool scans the Materials catalog and checks which textures exist
 * in the assets directory, then generates a report of missing textures.
 */
object TextureReport {
    
    private val TEXTURE_BASE_PATH = "common/src/main/resources/assets/metalmancy/textures"
    
    data class MissingTexture(
        val materialName: String,
        val part: Part,
        val expectedPath: String,
        val textureType: String // "block" or "item"
    )
    
    @JvmStatic
    fun main(args: Array<String>) {
        val outputPath = args.find { it.startsWith("--out=") }?.substringAfter("--out=") 
            ?: "aseprite/missing_textures_report.md"
        
        println("Generating texture report...")
        println("Checking textures in: $TEXTURE_BASE_PATH")
        
        val missingTextures = findMissingTextures()
        
        val report = generateReport(missingTextures)
        
        val outputFile = File(outputPath)
        outputFile.parentFile?.mkdirs()
        outputFile.writeText(report)
        
        println("Report generated: $outputPath")
        println("Total missing textures: ${missingTextures.size}")
    }
    
    private fun findMissingTextures(): List<MissingTexture> {
        val missing = mutableListOf<MissingTexture>()
        
        for (material in Materials.ALL) {
            for (part in material.parts) {
                val unlocalizedName = material.unlocalizedName(part)
                
                if (part.isBlock) {
                    // Check block texture
                    val blockTexturePath = "$TEXTURE_BASE_PATH/block/$unlocalizedName.png"
                    if (!File(blockTexturePath).exists()) {
                        missing.add(MissingTexture(
                            materialName = material.name,
                            part = part,
                            expectedPath = "block/$unlocalizedName.png",
                            textureType = "block"
                        ))
                    }
                } else {
                    // Check item texture
                    val itemTexturePath = "$TEXTURE_BASE_PATH/item/$unlocalizedName.png"
                    if (!File(itemTexturePath).exists()) {
                        missing.add(MissingTexture(
                            materialName = material.name,
                            part = part,
                            expectedPath = "item/$unlocalizedName.png",
                            textureType = "item"
                        ))
                    }
                }
            }
        }
        
        return missing.sortedWith(compareBy({ it.materialName }, { it.part.name }))
    }
    
    private fun generateReport(missingTextures: List<MissingTexture>): String {
        val sb = StringBuilder()
        
        sb.appendLine("# Missing Textures Report")
        sb.appendLine()
        sb.appendLine("Generated: ${java.time.LocalDateTime.now()}")
        sb.appendLine()
        sb.appendLine("## Summary")
        sb.appendLine()
        sb.appendLine("Total missing textures: **${missingTextures.size}**")
        sb.appendLine()
        
        // Group by material
        val byMaterial = missingTextures.groupBy { it.materialName }
        sb.appendLine("Materials with missing textures: **${byMaterial.size}**")
        sb.appendLine()
        
        // Statistics by type
        val blockCount = missingTextures.count { it.textureType == "block" }
        val itemCount = missingTextures.count { it.textureType == "item" }
        sb.appendLine("- Block textures missing: $blockCount")
        sb.appendLine("- Item textures missing: $itemCount")
        sb.appendLine()
        
        sb.appendLine("## Missing Textures by Material")
        sb.appendLine()
        
        for ((materialName, textures) in byMaterial) {
            sb.appendLine("### $materialName (${textures.size} missing)")
            sb.appendLine()
            
            for (texture in textures) {
                sb.appendLine("- [ ] `${texture.expectedPath}` (${texture.part.name})")
            }
            sb.appendLine()
        }
        
        sb.appendLine("## Missing Textures by Type")
        sb.appendLine()
        
        sb.appendLine("### Block Textures")
        sb.appendLine()
        val blockTextures = missingTextures.filter { it.textureType == "block" }
        if (blockTextures.isEmpty()) {
            sb.appendLine("✅ All block textures present!")
        } else {
            for (texture in blockTextures) {
                sb.appendLine("- [ ] `${texture.expectedPath}` (${texture.materialName})")
            }
        }
        sb.appendLine()
        
        sb.appendLine("### Item Textures")
        sb.appendLine()
        val itemTextures = missingTextures.filter { it.textureType == "item" }
        if (itemTextures.isEmpty()) {
            sb.appendLine("✅ All item textures present!")
        } else {
            for (texture in itemTextures) {
                sb.appendLine("- [ ] `${texture.expectedPath}` (${texture.materialName})")
            }
        }
        sb.appendLine()
        
        sb.appendLine("## Full List (Copy-Paste Friendly)")
        sb.appendLine()
        sb.appendLine("```")
        for (texture in missingTextures) {
            sb.appendLine("${texture.expectedPath}")
        }
        sb.appendLine("```")
        
        return sb.toString()
    }
}
