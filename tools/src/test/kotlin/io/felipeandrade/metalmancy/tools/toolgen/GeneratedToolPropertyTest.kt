//package io.felipeandrade.metalmancy.tools.toolgen
//
//import io.felipeandrade.metalmancy.items.ToolType
//import io.kotest.core.spec.style.StringSpec
//import io.kotest.matchers.shouldBe
//import io.kotest.matchers.shouldNotBe
//import io.kotest.matchers.string.shouldContain
//import io.kotest.matchers.string.shouldNotContain
//import io.kotest.property.Arb
//import io.kotest.property.checkAll
//import tools.toolgen.GeneratedTool
//
///**
// * Property-based tests for GeneratedTool.
// *
// * These tests validate that tool generation produces correct outputs for all
// * tool-enabled materials and tool types.
// */
//class GeneratedToolPropertyTest : StringSpec({
//
//    /**
//     * Feature: basic-tools, Property 5: Tool-enabled materials generate all five tool types
//     *
//     * For any material in the tool-enabled list with valid parts, the system should
//     * generate exactly five tool items (sword, axe, pickaxe, shovel, hoe).
//     *
//     * Validates: Requirements 3.1
//     */
//    "Property 5: Tool-enabled materials generate all five tool types".config(invocations = 100) {
//        checkAll(Arb.actualToolEnabledMaterial()) { material ->
//            // Generate all five tool types for this material
//            val tools = ToolType.entries.map { toolType ->
//                GeneratedTool(material, toolType)
//            }
//
//            // Should have exactly 5 tools
//            tools.size shouldBe 5
//
//            // Should have one of each type
//            val toolTypes = tools.map { it.toolType }.toSet()
//            toolTypes shouldBe ToolType.entries.toSet()
//
//            // All tools should be for the same material
//            tools.all { it.material == material } shouldBe true
//
//            // All tools should have valid unlocalized names
//            tools.all { it.unlocalizedName.isNotEmpty() } shouldBe true
//        }
//    }
//
//    /**
//     * Feature: basic-tools, Property 6: Tool unlocalized names follow naming convention
//     *
//     * For any generated tool, the unlocalized name should follow the pattern
//     * "{material_name}_{tool_type}" (e.g., "brass_sword", "titanium_pickaxe").
//     *
//     * Validates: Requirements 3.3
//     */
//    "Property 6: Tool unlocalized names follow naming convention".config(invocations = 100) {
//        checkAll(Arb.materialAndToolType()) { pair ->
//            val (material, toolType) = pair
//            val tool = GeneratedTool(material, toolType)
//
//            // Unlocalized name should follow the pattern "{material}_{tool}"
//            val expectedName = "${material.name}_${toolType.unlocalizedSuffix}"
//            tool.unlocalizedName shouldBe expectedName
//
//            // Should contain the material name
//            tool.unlocalizedName shouldContain material.name
//
//            // Should contain the tool type suffix
//            tool.unlocalizedName shouldContain toolType.unlocalizedSuffix
//
//            // Should have exactly one underscore separating material and tool type
//            tool.unlocalizedName.count { it == '_' } shouldBe 1
//        }
//    }
//
//    /**
//     * Property 6 (validation): Unlocalized names are unique per material-tool combination
//     *
//     * This test ensures that different material-tool combinations produce different names.
//     */
//    "Property 6 (validation): Unlocalized names are unique per material-tool combination".config(invocations = 100) {
//        checkAll(Arb.materialAndToolType()) { pair ->
//            val (material, currentToolType) = pair
//            val tool1 = GeneratedTool(material, currentToolType)
//
//            // Same material and tool type should produce same name
//            val tool2 = GeneratedTool(material, currentToolType)
//            tool1.unlocalizedName shouldBe tool2.unlocalizedName
//
//            // Different tool types should produce different names
//            val otherToolTypes = ToolType.entries.filter { it != currentToolType }
//            for (otherToolType in otherToolTypes) {
//                val otherTool = GeneratedTool(material, otherToolType)
//                // Names should be different (not just checking suffix since "pickaxe" contains "axe")
//                tool1.unlocalizedName shouldNotBe otherTool.unlocalizedName
//            }
//        }
//    }
//})
//
///**
// * Property-based tests for tool model generation.
// *
// * These tests validate that generated tool models have the correct structure
// * and texture mappings.
// */
//class GeneratedToolModelPropertyTest : StringSpec({
//
//    /**
//     * Property: Tool item models use handheld parent
//     *
//     * For any generated tool item model JSON, the parent should be "minecraft:item/handheld".
//     */
//    "Tool item models use handheld parent".config(invocations = 100) {
//        checkAll(Arb.materialAndToolType()) { pair ->
//            val (material, toolType) = pair
//            val tool = GeneratedTool(material, toolType)
//            val model = tool.generateItemModel()
//
//            // Should have parent property
//            model.has("parent") shouldBe true
//            model.get("parent").asString shouldBe "minecraft:item/handheld"
//        }
//    }
//
//    /**
//     * Property: Tool models use two-layer texture structure
//     *
//     * For any generated tool item model JSON, it should have exactly two texture
//     * layers: layer0 for handle and layer1 for tool head.
//     */
//    "Tool models use two-layer texture structure".config(invocations = 100) {
//        checkAll(Arb.materialAndToolType()) { pair ->
//            val (material, toolType) = pair
//            val tool = GeneratedTool(material, toolType)
//            val model = tool.generateItemModel()
//
//            // Should have textures object
//            model.has("textures") shouldBe true
//            val textures = model.getAsJsonObject("textures")
//
//            // Should have exactly two layers
//            textures.has("layer0") shouldBe true
//            textures.has("layer1") shouldBe true
//            textures.size() shouldBe 2
//        }
//    }
//
//    /**
//     * Property: Tool handle textures map correctly by type
//     *
//     * For any generated tool model, the layer0 texture should be "wooden_handle"
//     * for axes/pickaxes/hoes, "wooden_sword_handle" for swords, and
//     * "wooden_shovel_handle" for shovels.
//     */
//    "Tool handle textures map correctly by type".config(invocations = 100) {
//        checkAll(Arb.materialAndToolType()) { pair ->
//            val (material, toolType) = pair
//            val tool = GeneratedTool(material, toolType)
//            val model = tool.generateItemModel()
//            val textures = model.getAsJsonObject("textures")
//            val handleTexture = textures.get("layer0").asString
//
//            when (toolType) {
//                ToolType.SWORD -> {
//                    handleTexture shouldContain "wooden_sword_handle"
//                }
//                ToolType.SHOVEL -> {
//                    handleTexture shouldContain "wooden_shovel_handle"
//                }
//                ToolType.AXE, ToolType.PICKAXE, ToolType.HOE -> {
//                    handleTexture shouldContain "wooden_handle"
//                    handleTexture shouldNotContain "sword"
//                    handleTexture shouldNotContain "shovel"
//                }
//            }
//        }
//    }
//
//    /**
//     * Property: Tool head textures use material-specific paths
//     *
//     * For any generated tool model, the layer1 texture should be
//     * "metalmancy:item/{material}_{tool}" (e.g., "metalmancy:item/brass_pickaxe").
//     */
//    "Tool head textures use material-specific paths".config(invocations = 100) {
//        checkAll(Arb.materialAndToolType()) { pair ->
//            val (material, toolType) = pair
//            val tool = GeneratedTool(material, toolType)
//            val model = tool.generateItemModel()
//            val textures = model.getAsJsonObject("textures")
//            val headTexture = textures.get("layer1").asString
//
//            // Should contain the mod ID
//            headTexture shouldContain "metalmancy:item/"
//
//            // Should contain the material name
//            headTexture shouldContain material.name
//
//            // Should contain the tool type
//            headTexture shouldContain toolType.unlocalizedSuffix
//
//            // Should match the unlocalized name pattern
//            headTexture shouldContain tool.unlocalizedName
//        }
//    }
//})
//
///**
// * Property-based tests for tool registration code generation.
// *
// * These tests validate that generated registration code is syntactically correct
// * and contains the necessary components.
// */
//class GeneratedToolRegistrationPropertyTest : StringSpec({
//
//    /**
//     * Property: Tool registration code contains required elements
//     *
//     * For any generated tool, the registration code should contain the tool name,
//     * tier reference, attack damage, and attack speed.
//     */
//    "Tool registration code contains required elements".config(invocations = 100) {
//        checkAll(Arb.materialAndToolType()) { pair ->
//            val (material, toolType) = pair
//            val tool = GeneratedTool(material, toolType)
//            val registrationCode = tool.generateRegistrationCode()
//
//            // Should contain the unlocalized name
//            registrationCode shouldContain tool.unlocalizedName
//
//            // Should contain the material tier reference
//            registrationCode shouldContain "${material.name.uppercase()}_TIER"
//
//            // Should contain attack damage
//            val attackDamageStr = "${toolType.attackDamage}f"
//            registrationCode shouldContain attackDamageStr
//
//            // Should contain attack speed
//            val attackSpeedStr = "${toolType.attackSpeed}f"
//            registrationCode shouldContain attackSpeedStr
//
//            // Should contain the appropriate item class
//            val expectedClass = when (toolType) {
//                ToolType.SWORD -> "SwordItem"
//                ToolType.AXE -> "AxeItem"
//                ToolType.PICKAXE -> "PickaxeItem"
//                ToolType.SHOVEL -> "ShovelItem"
//                ToolType.HOE -> "HoeItem"
//            }
//            registrationCode shouldContain expectedClass
//        }
//    }
//
//    /**
//     * Property: Tool registration code uses correct item class
//     *
//     * For any generated tool, the registration code should use the correct
//     * Minecraft item class based on the tool type.
//     */
//    "Tool registration code uses correct item class".config(invocations = 100) {
//        checkAll(Arb.materialAndToolType()) { pair ->
//            val (material, toolType) = pair
//            val tool = GeneratedTool(material, toolType)
//            val registrationCode = tool.generateRegistrationCode()
//
//            val expectedClass = when (toolType) {
//                ToolType.SWORD -> "SwordItem"
//                ToolType.AXE -> "AxeItem"
//                ToolType.PICKAXE -> "PickaxeItem"
//                ToolType.SHOVEL -> "ShovelItem"
//                ToolType.HOE -> "HoeItem"
//            }
//
//            registrationCode shouldContain expectedClass
//
//            // Should not contain other item classes
//            val otherClasses = listOf("SwordItem", "AxeItem", "PickaxeItem", "ShovelItem", "HoeItem")
//                .filter { it != expectedClass }
//
//            for (otherClass in otherClasses) {
//                registrationCode shouldNotContain otherClass
//            }
//        }
//    }
//})
//
//
///**
// * Property-based tests for invalid material handling.
// *
// * These tests validate that materials without required parts are properly
// * detected and handled.
// */
//class InvalidMaterialHandlingPropertyTest : StringSpec({
//
//    /**
//     * Feature: basic-tools, Property 8: Invalid materials log warnings and skip generation
//     *
//     * For any material in the tool-enabled list without required parts, the system
//     * should log a warning and skip tool generation for that material.
//     *
//     * Validates: Requirements 3.5
//     *
//     * Note: This test validates the validation logic. The actual warning logging
//     * will be tested when the ToolGen generator is implemented.
//     */
//    "Property 8: Invalid materials are detected by validation".config(invocations = 100) {
//        checkAll(Arb.materialWithoutRequiredParts()) { material ->
//            // Materials without INGOT or GEM parts should fail validation
//            val hasRequiredParts = material.parts.contains(io.felipeandrade.metalmancy.registry.material.Part.INGOT) ||
//                                   material.parts.contains(io.felipeandrade.metalmancy.registry.material.Part.GEM)
//            hasRequiredParts shouldBe false
//
//            // ToolMaterials validation should also detect this
//            tools.toolgen.ToolMaterials.hasRequiredParts(material) shouldBe false
//        }
//    }
//
//    /**
//     * Property 8 (validation): Tool-enabled materials all have required parts
//     *
//     * This test ensures that all materials in the TOOL_ENABLED list have the
//     * required parts, so no warnings should be logged during normal operation.
//     */
//    "Property 8 (validation): All tool-enabled materials have required parts" {
//        checkAll(Arb.actualToolEnabledMaterial()) { material ->
//            // All tool-enabled materials should have required parts
//            tools.toolgen.ToolMaterials.hasRequiredParts(material) shouldBe true
//
//            // Should be able to get a tier for the material
//            val tier = tools.toolgen.ToolMaterials.getTierWithValidation(material)
//            tier shouldNotBe null
//        }
//    }
//})
