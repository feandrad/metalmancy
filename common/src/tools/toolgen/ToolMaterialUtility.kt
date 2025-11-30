package tools.toolgen

import io.felipeandrade.metalmancy.registry.material.Material
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ToolMaterial
import net.minecraft.world.level.block.Block

object ToolMaterialUtility {

    // --- Placeholder Tag Creation ---
    // These are dummy implementations to allow the code to compile.
    // Replace with your actual TagKey creation logic.
    private fun createBlockTag(name: String): TagKey<Block> =
        TagKey.create(Registries.BLOCK, ResourceLocation("metalmancy", name))

    private fun createItemTag(name: String): TagKey<Item> =
        TagKey.create(Registries.ITEM, ResourceLocation("metalmancy", name))

    // You should dynamically get the correct tag keys based on the material's properties
    // For simplicity, we'll use tier-based tags here, similar to the previous answer:
    private fun getIncorrectBlocksTag(tier: ToolTier): TagKey<Block> {
        return when (tier) {
            TierCategories.COPPER_LIKE -> createBlockTag("incorrect_for_copper_like_tool")
            TierCategories.IRON_LIKE -> createBlockTag("incorrect_for_iron_like_tool")
            TierCategories.GOLD_LIKE -> createBlockTag("incorrect_for_gold_like_tool")
            TierCategories.DIAMOND_LIKE -> createBlockTag("incorrect_for_diamond_like_tool")
            // Add more tiers if needed
            else -> throw IllegalArgumentException("Unknown tool tier: $tier")
        }
    }

    // In a real mod, you might generate a specific repair tag for each material 
    // (e.g., BRASS_INGOT_REPAIR_TAG) or keep it tier-based.
    // For this example, we'll keep it tier-based for the repair *tag*.
    private fun getRepairItemsTag(tier: ToolTier): TagKey<Item> {
        return when (tier) {
            TierCategories.COPPER_LIKE -> createItemTag("copper_like_tool_materials")
            TierCategories.IRON_LIKE -> createItemTag("iron_like_tool_materials")
            TierCategories.GOLD_LIKE -> createItemTag("gold_like_tool_materials")
            TierCategories.DIAMOND_LIKE -> createItemTag("diamond_like_tool_materials")
            else -> throw IllegalArgumentException("Unknown tool tier: $tier")
        }
    }
    // ----------------------------------------------------------------------------------

    /**
     * Maps a custom Material to a Minecraft ToolMaterial record using its assigned ToolTier properties.
     * This acts as your "ExpandedMaterial" implementation by injecting the custom material's context
     * into the standard ToolMaterial structure.
     * @param material The custom Material object.
     * @return A ToolMaterial record with the properties corresponding to the material's tier.
     */
    fun createToolMaterial(material: Material): ToolMaterial {
        val tier = ToolMaterials.getTier(material)

        // Define the stats based on the tier, mirroring the vanilla ToolMaterial stat block
        val (durability, speed, attackDamageBonus, enchantmentValue) = when (tier) {
            TierCategories.COPPER_LIKE -> listOf(190, 5.0F, 1.0F, 13) // COPPER stats
            TierCategories.IRON_LIKE -> listOf(250, 6.0F, 2.0F, 14)   // IRON stats
            TierCategories.GOLD_LIKE -> listOf(32, 12.0F, 0.0F, 22)   // GOLD stats
            TierCategories.DIAMOND_LIKE -> listOf(1561, 8.0F, 3.0F, 10) // DIAMOND stats
            // Add other tier mappings here
            else -> throw IllegalArgumentException("Missing stats for tool tier: $tier")
        }

        // Create the final ToolMaterial record
        return ToolMaterial(
            getIncorrectBlocksTag(tier), // e.g., INCORRECT_FOR_IRON_LIKE_TOOL
            durability as Int,
            (speed as Number).toFloat(),
            (attackDamageBonus as Number).toFloat(),
            enchantmentValue as Int,
            // Use the repair items tag. If you want material-specific repair items, 
            // you'd generate a tag key here based on 'material' instead of 'tier'.
            getRepairItemsTag(tier)
        )
    }
}
