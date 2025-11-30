package io.felipeandrade.metalmancy.neoforge

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.Metalmancy.resourceKey
import io.felipeandrade.metalmancy.items.MaterialItems
import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Part
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.Item
import net.minecraft.world.item.SwordItem
import net.minecraft.world.item.Tier
import net.minecraft.world.item.crafting.Ingredient
import tools.toolgen.ToolMaterials
import tools.toolgen.ToolType

/**
 * NeoForge-specific tool item registration.
 * 
 * This object handles the registration of tool items for tool-enabled materials
 * on the NeoForge platform. Tools are registered with appropriate tier properties
 * based on their material configuration.
 */
object NeoForgeToolItems {
    
    /**
     * Registers all tool items for tool-enabled materials.
     * This is called during the NeoForge item registration event.
     */
    fun registerAll() {
        Metalmancy.LOG.info("Registering NeoForge tool items...")
        
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
        
        Metalmancy.LOG.info("Registered $registeredCount tool items on NeoForge")
    }
    
    /**
     * Creates a Minecraft Tier from a ToolTier configuration.
     */
    private fun createMinecraftTier(material: Material, toolTier: tools.toolgen.ToolTier): net.minecraft.world.item.Tier {
        return object : net.minecraft.world.item.Tier {
            override fun getUses(): Int = toolTier.durability
            
            override fun getSpeed(): Float = toolTier.efficiency
            
            override fun getAttackDamageBonus(): Float = toolTier.attackDamageBonus
            
            override fun getEnchantmentValue(): Int = toolTier.enchantability
            
            override fun getTag() = when (toolTier.miningLevel) {
                0 -> BlockTags.INCORRECT_FOR_WOODEN_TOOL
                1 -> BlockTags.INCORRECT_FOR_STONE_TOOL
                2 -> BlockTags.INCORRECT_FOR_IRON_TOOL
                3 -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL
                4 -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL
                else -> BlockTags.INCORRECT_FOR_IRON_TOOL
            }
            
            override fun getRepairIngredient(): Ingredient {
                // Get repair material from MaterialItems
                val materialItems = getMaterialItems(material)
                val repairItem = materialItems[Part.INGOT] ?: materialItems[Part.GEM]
                
                return if (repairItem != null) {
                    Ingredient.of(repairItem)
                } else {
                    Ingredient.of()
                }
            }
        }
    }
    
    /**
     * Registers a single tool item for a material and tool type.
     */
    private fun registerTool(material: Material, toolType: ToolType, tier: net.minecraft.world.item.Tier) {
        val unlocalizedName = toolType.getUnlocalizedName(material.name)
        val key = resourceKey(unlocalizedName, Registries.ITEM)
        
        val properties = Item.Properties()
            .setId(key)
        
        val toolItem = when (toolType) {
            ToolType.SWORD -> net.minecraft.world.item.SwordItem(
                tier,
                toolType.attackDamage,
                toolType.attackSpeed,
                properties
            )
            ToolType.AXE -> net.minecraft.world.item.AxeItem(
                tier,
                toolType.attackDamage,
                toolType.attackSpeed,
                properties
            )
            ToolType.PICKAXE -> net.minecraft.world.item.DiggerItem(
                tier,
                BlockTags.MINEABLE_WITH_PICKAXE,
                toolType.attackDamage,
                toolType.attackSpeed,
                properties
            )
            ToolType.SHOVEL -> net.minecraft.world.item.ShovelItem(
                tier,
                toolType.attackDamage,
                toolType.attackSpeed,
                properties
            )
            ToolType.HOE -> net.minecraft.world.item.HoeItem(
                tier,
                toolType.attackDamage,
                toolType.attackSpeed,
                properties
            )
        }
        
        Registry.register(BuiltInRegistries.ITEM, key, toolItem)
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
