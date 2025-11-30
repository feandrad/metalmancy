package io.felipeandrade.metalmancy.fabric

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.items.MaterialItems
import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Part
import net.minecraft.world.item.Item
import tools.toolgen.ToolMaterials
import tools.toolgen.ToolType

/**
 * Fabric-specific tool item registration.
 * 
 * This object handles the registration of tool items for tool-enabled materials
 * on the Fabric platform. Tools are registered with appropriate tier properties
 * based on their material configuration.
 */
object FabricToolItems {
    
    /**
     * Registers all tool items for tool-enabled materials.
     * This is called during Fabric mod initialization.
     */
    fun registerAll() {
        Metalmancy.LOG.info("Registering Fabric tool items...")
        
        var registeredCount = 0
        
        ToolMaterials.TOOL_ENABLED.forEach { material ->
            // Validate material has required parts
            if (!ToolMaterials.hasRequiredParts(material)) {
                Metalmancy.LOG.warn("Material ${material.name} lacks required parts for tools. Skipping.")
                return@forEach
            }
            
            // Get tier for material
            val toolTier = try {
                ToolMaterials.getTier(material)
            } catch (e: IllegalArgumentException) {
                Metalmancy.LOG.warn("Material ${material.name} has no tier mapping. Skipping.")
                return@forEach
            }
            
            // Create Minecraft Tier from ToolTier
            val tier = createMinecraftTier(material, toolTier)
            
            // Register all tool types for this material
            ToolType.entries.forEach { toolType ->
                try {
                    registerTool(material, toolType, tier)
                    registeredCount++
                } catch (e: Exception) {
                    Metalmancy.LOG.error("Failed to register ${toolType.unlocalizedSuffix} for ${material.name}: ${e.message}")
                }
            }
        }
        
        Metalmancy.LOG.info("Registered $registeredCount tool items on Fabric")
    }

    
    /**
     * Gets the material items map for a given material.
     */
    private fun getMaterialItems(material: Material): Map<Part, Item> {
        return when {
            material in MaterialItems.GEMS.keys -> MaterialItems.GEMS[material] ?: emptyMap()
            material in MaterialItems.METALS.keys -> MaterialItems.METALS[material] ?: emptyMap()
            material in MaterialItems.ALLOYS.keys -> MaterialItems.ALLOYS[material] ?: emptyMap()
            material in MaterialItems.SALTS.keys -> MaterialItems.SALTS[material] ?: emptyMap()
            else -> emptyMap()
        }
    }
}
