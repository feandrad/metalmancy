package tools.toolgen

import com.google.gson.JsonObject
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import io.felipeandrade.metalmancy.registry.material.Material

/**
 * Represents a generated tool item with its associated metadata and generation logic.
 * 
 * This class encapsulates the data needed to generate tool items, including:
 * - Item registration code
 * - Item model JSON
 * - Item render JSON
 * 
 * @param material The material the tool is made from
 * @param toolType The type of tool (sword, axe, pickaxe, shovel, hoe)
 */
data class GeneratedTool(
    val material: Material,
    val toolType: ToolType
) {
    /**
     * The unlocalized name for this tool, following the pattern "{material_name}_{tool_type}".
     * Example: "brass_sword", "titanium_pickaxe"
     */
    val unlocalizedName: String = toolType.getUnlocalizedName(material.name)
    
    /**
     * Generates the item model JSON for this tool.
     * 
     * Tool models use the "minecraft:item/handheld" parent and a two-layer texture structure:
     * - layer0: Handle texture (varies by tool type)
     * - layer1: Tool head texture (material-specific)
     * 
     * @return JsonObject representing the item model
     */
    fun generateItemModel(): JsonObject {
        val json = JsonObject()
        json.addProperty("parent", "minecraft:item/handheld")
        
        val textures = JsonObject()
        
        // Handle texture mapping by tool type
        val handleTexture = when (toolType) {
            ToolType.SWORD -> "$MOD_ID:item/wooden_sword_handle"
            ToolType.SHOVEL -> "$MOD_ID:item/wooden_shovel_handle"
            ToolType.AXE, ToolType.PICKAXE, ToolType.HOE -> "$MOD_ID:item/wooden_handle"
        }
        textures.addProperty("layer0", handleTexture)
        
        // Tool head texture (material-specific)
        textures.addProperty("layer1", "$MOD_ID:item/$unlocalizedName")
        
        json.add("textures", textures)
        return json
    }
    
    /**
     * Generates the item render JSON for this tool.
     * 
     * This JSON is used for rendering the tool in inventories and the player's hand.
     * 
     * @return JsonObject representing the item render configuration
     */
    fun generateItemRender(): JsonObject {
        val json = JsonObject()
        val modelObject = JsonObject()
        modelObject.addProperty("type", "minecraft:model")
        modelObject.addProperty("model", "$MOD_ID:item/$unlocalizedName")
        json.add("model", modelObject)
        return json
    }
    
    /**
     * Generates Kotlin code for registering this tool item.
     * 
     * This generates the registration code that will be used at runtime to create
     * the tool item with the correct tier properties.
     * 
     * @return String containing the Kotlin registration code
     */
    fun generateRegistrationCode(): String {
        val tier = ToolMaterials.getTier(material)
        val className = when (toolType) {
            ToolType.SWORD -> "SwordItem"
            ToolType.AXE -> "AxeItem"
            ToolType.PICKAXE -> "PickaxeItem"
            ToolType.SHOVEL -> "ShovelItem"
            ToolType.HOE -> "HoeItem"
        }
        
        return """
            // ${material.name} ${toolType.unlocalizedSuffix}
            val ${unlocalizedName.uppercase()} = register(
                "$unlocalizedName",
                $className(
                    ${material.name.uppercase()}_TIER,
                    ${toolType.attackDamage}f,
                    ${toolType.attackSpeed}f,
                    Item.Properties()
                )
            )
        """.trimIndent()
    }
}
