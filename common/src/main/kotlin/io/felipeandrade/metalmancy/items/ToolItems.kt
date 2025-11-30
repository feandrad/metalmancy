package io.felipeandrade.metalmancy.items

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.Metalmancy.resourceKey
import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item
import net.minecraft.world.item.ToolMaterial
import tools.toolgen.ToolMaterials
import tools.toolgen.ToolStats

object ToolItems {

    val BRASS = registerAllTools(ToolStats(Materials.BRASS, ToolMaterial.IRON, 190, 6.5F, 1.8F, 18))
    val BRONZE = registerAllTools(ToolStats(Materials.BRONZE, ToolMaterial.IRON, 200, 6.0F, 2.2F, 14))
    val ALUMINUM = registerAllTools(ToolStats(Materials.ALUMINUM, ToolMaterial.IRON, 180, 8.5F, 1.0F, 20))
    val STEEL = registerAllTools(ToolStats(Materials.STEEL, ToolMaterial.IRON, 500, 7.5F, 3.0F, 16))
    val INVAR = registerAllTools(ToolStats(Materials.INVAR, ToolMaterial.IRON, 750, 7.0F, 3.5F, 12))

    val SILVER = registerAllTools(ToolStats(Materials.SILVER, ToolMaterial.GOLD, 40, 10.5F, 0.5F, 22))
    val ELECTRUM = registerAllTools(ToolStats(Materials.ELECTRUM, ToolMaterial.GOLD, 100, 12.0F, 0.8F, 30))
    val COBALT = registerAllTools(ToolStats(Materials.COBALT, ToolMaterial.DIAMOND, 1800, 8.5F, 4.0F, 25))

    val PLATINUM = registerAllTools(ToolStats(Materials.PLATINUM, ToolMaterial.DIAMOND, 1300, 7.0F, 4.0F, 18))
    val TITANIUM = registerAllTools(ToolStats(Materials.TITANIUM, ToolMaterial.DIAMOND, 1600, 9.5F, 2.8F, 10))
    val TOPAZ = registerAllTools(ToolStats(Materials.TOPAZ, ToolMaterial.DIAMOND, 1600, 8.5F, 3.0F, 14))
    val RUBY = registerAllTools(ToolStats(Materials.RUBY, ToolMaterial.DIAMOND, 1600, 7.5F, 4.5F, 14))
    val SAPPHIRE = registerAllTools(ToolStats(Materials.SAPPHIRE, ToolMaterial.DIAMOND, 2000, 8.0F, 3.0F, 14))

    val MITHRIL = registerAllTools(ToolStats(Materials.MITHRIL, ToolMaterial.NETHERITE, 2800, 11.0F, 4.0F, 25))
    val ORICHALCUM = registerAllTools(ToolStats(Materials.ORICHALCUM, ToolMaterial.NETHERITE, 3000, 8.5F, 6.0F, 18))


    fun registerAllTools(stats: ToolStats): Map<ToolType, Item> {
        val result: MutableMap<ToolType, Item> = mutableMapOf()

        ToolType.entries.forEach { type ->
            result[type] = registerTool(type, stats)
        }

        return result
    }

    fun registerTool(type: ToolType, stats: ToolStats): Item {
        val unlocalizedName = type.getUnlocalizedName(stats.material.name)
        val key = resourceKey(unlocalizedName, Registries.ITEM)

        val properties: Item.Properties = Item.Properties().setId(key)

        val toolProperties: Item.Properties = when (type) {
            ToolType.SWORD -> properties.sword(
                stats.tier,
                type.attackDamage,
                type.attackSpeed
            )

            ToolType.AXE -> properties.axe(
                stats.tier,
                type.attackDamage,
                type.attackSpeed
            )

            ToolType.PICKAXE -> properties.pickaxe(
                stats.tier,
                type.attackDamage,
                type.attackSpeed
            )

            ToolType.SHOVEL -> properties.shovel(
                stats.tier,
                type.attackDamage,
                type.attackSpeed
            )

            ToolType.HOE -> properties.hoe(
                stats.tier,
                type.attackDamage,
                type.attackSpeed
            )
        }

        return Registry.register(BuiltInRegistries.ITEM, key, toolProperties)
    }


    /**
     * Validates tool-enabled materials and logs warnings for invalid configurations.
     * This should be called during mod initialization to catch configuration errors early.
     */
    fun validateToolMaterials() {
        ToolMaterials.TOOL_ENABLED.forEach { material ->
            // Validate material has required parts
            if (!ToolMaterials.hasRequiredParts(material)) {
                Metalmancy.LOG.warn("Material ${material.name} is in tool-enabled list but lacks INGOT/GEM parts. Tools will not be generated.")
                return@forEach
            }

            // Validate material has tier mapping
            val tier = try {
                ToolMaterials.getTier(material)
            } catch (e: IllegalArgumentException) {
                Metalmancy.LOG.warn("Material ${material.name} does not have a tier mapping. Tools will not be generated.")
                return@forEach
            }

            // Validate material has repair ingredient
            val materialItems = getMaterialItems(material)
            val repairIngredient = materialItems[Part.INGOT] ?: materialItems[Part.GEM]

            if (repairIngredient == null) {
                Metalmancy.LOG.warn("Material ${material.name} has no repair ingredient (INGOT or GEM). Tools will not be generated.")
                return@forEach
            }
        }

        val validMaterials = ToolMaterials.TOOL_ENABLED.filter {
            ToolMaterials.hasRequiredParts(it) &&
                    ToolMaterials.getTierWithValidation(it) != null
        }

        Metalmancy.LOG.info("Tool validation complete: ${validMaterials.size}/${ToolMaterials.TOOL_ENABLED.size} materials are valid for tool generation")
    }

    /**
     * Gets the material items map for a given material.
     * This maps to the appropriate property in MaterialItems based on material type.
     */
    private fun getMaterialItems(material: Material): Map<Part, net.minecraft.world.item.Item> {
        return when {
            material in MaterialItems.GEMS.keys -> MaterialItems.GEMS[material] ?: emptyMap()
            material in MaterialItems.METALS.keys -> MaterialItems.METALS[material] ?: emptyMap()
            material in MaterialItems.ALLOYS.keys -> MaterialItems.ALLOYS[material] ?: emptyMap()
            material in MaterialItems.SALTS.keys -> MaterialItems.SALTS[material] ?: emptyMap()
            else -> emptyMap()
        }
    }

    /**
     * Placeholder for registerAll - actual registration happens in platform-specific code.
     * This method performs validation only.
     */
    fun registerAll() {
        validateToolMaterials()
    }
}
