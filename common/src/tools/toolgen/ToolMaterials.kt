package tools.toolgen

import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part

/**
 * Configuration for tool-enabled materials. This object maintains the list of materials
 * that should have tools generated and provides tier mapping functionality.
 */
object ToolMaterials {
    /**
     * List of materials that are enabled for tool generation.
     * These materials must have INGOT or GEM parts to be valid for tool crafting.
     */
    val TOOL_ENABLED = listOf(
        Materials.BRASS,
        Materials.BRONZE,
        Materials.SILVER,
        Materials.COBALT,
        Materials.ORICHALCUM,
        Materials.MITHRIL,
        Materials.PLATINUM,
        Materials.TITANIUM,
        Materials.ELECTRUM,
        Materials.TOPAZ,
        Materials.RUBY,
        Materials.SAPPHIRE,
        Materials.ALUMINUM,
        Materials.STEEL
    )

    /**
     * Validates that a material has the required parts (INGOT or GEM) for tool generation.
     * 
     * @param material The material to validate
     * @return true if the material has INGOT or GEM parts, false otherwise
     */
    fun hasRequiredParts(material: Material): Boolean {
        return material.parts.contains(Part.INGOT) || material.parts.contains(Part.GEM)
    }

    /**
     * Gets the tool tier for a material based on its property group.
     * Materials are assigned to tier categories based on their classification in Materials.kt.
     * 
     * @param material The material to get the tier for
     * @return The ToolTier for the material
     * @throws IllegalArgumentException if the material is not in any known property group
     */
    fun getTier(material: Material): ToolTier {
        return when (material) {
            // Copper-like metals
            in Materials.COPPER_LIKE_METALS -> TierCategories.COPPER_LIKE
            
            // Iron-like metals (includes BRASS, BRONZE, STEEL, ALUMINUM)
            in Materials.IRON_LIKE_METALS -> TierCategories.IRON_LIKE
            
            // Gold-like metals (includes SILVER, COBALT)
            in Materials.GOLD_LIKE_METALS -> TierCategories.GOLD_LIKE
            
            // Diamond-like metals (includes PLATINUM, TITANIUM, MITHRIL, ORICHALCUM)
            in Materials.DIAMOND_LIKE_METALS -> TierCategories.DIAMOND_LIKE
            
            // Gems (TOPAZ, RUBY, SAPPHIRE) - treated as diamond-like
            in Materials.GEMS -> TierCategories.DIAMOND_LIKE
            
            else -> throw IllegalArgumentException("Material ${material.name} is not in any known tier category")
        }
    }

    /**
     * Gets the tool tier for a material with validation.
     * Checks if the material has required parts before returning the tier.
     * 
     * @param material The material to get the tier for
     * @return The ToolTier for the material, or null if validation fails
     */
    fun getTierWithValidation(material: Material): ToolTier? {
        if (!hasRequiredParts(material)) {
            return null
        }
        return try {
            getTier(material)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    /**
     * Validates all tool-enabled materials and returns a list of materials that
     * fail validation (missing required parts or no tier mapping).
     * 
     * @return List of materials that fail validation
     */
    fun validateToolEnabledMaterials(): List<Material> {
        return TOOL_ENABLED.filter { material ->
            !hasRequiredParts(material) || getTierWithValidation(material) == null
        }
    }
}
