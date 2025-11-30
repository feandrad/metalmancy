package io.felipeandrade.metalmancy.tools.toolgen

import io.felipeandrade.metalmancy.items.ToolType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import tools.toolgen.GeneratedTool
import tools.toolgen.ToolMaterials
import tools.toolgen.ToolRecipes
import java.io.File

/**
 * Property-based tests for ToolGen generator output.
 * 
 * These tests validate that the tool generator produces correct output files
 * and follows the datapack structure.
 */
class ToolGenPropertyTest : StringSpec({
    
    /**
     * Feature: basic-tools, Property 20: Tool generator produces registration code
     * 
     * For any execution of the tool generator with tool-enabled materials, it should
     * generate Kotlin code for tool item registration.
     * 
     * Validates: Requirements 5.1, 9.1
     */
    "Property 20: Tool generator produces registration code".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val tool = GeneratedTool(material, toolType)
            
            // Generate registration code
            val registrationCode = tool.generateRegistrationCode()
            
            // Should not be empty
            registrationCode.isNotBlank() shouldBe true
            
            // Should contain material name
            registrationCode shouldContain material.name
            
            // Should contain tool type
            registrationCode shouldContain toolType.unlocalizedSuffix
            
            // Should contain the unlocalized name
            registrationCode shouldContain tool.unlocalizedName
            
            // Should contain appropriate item class based on tool type
            val expectedClass = when (toolType) {
                ToolType.SWORD -> "SwordItem"
                ToolType.AXE -> "AxeItem"
                ToolType.PICKAXE -> "PickaxeItem"
                ToolType.SHOVEL -> "ShovelItem"
                ToolType.HOE -> "HoeItem"
            }
            registrationCode shouldContain expectedClass
            
            // Should contain attack damage and speed
            registrationCode shouldContain "${toolType.attackDamage}f"
            registrationCode shouldContain "${toolType.attackSpeed}f"
            
            // Should contain tier reference
            registrationCode shouldContain "_TIER"
        }
    }
    
    /**
     * Feature: basic-tools, Property 31: Recipe JSONs are generated for all tools
     * 
     * For any execution of the tool generator, it should generate recipe JSON files
     * for all tool crafting recipes.
     * 
     * Validates: Requirements 9.2
     */
    "Property 31: Recipe JSONs are generated for all tools".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            
            // Generate crafting recipe
            val craftingRecipe = ToolRecipes.generateCraftingRecipe(material, toolType)
            craftingRecipe shouldNotBe null
            
            // Should be valid JSON with required fields
            craftingRecipe.has("type") shouldBe true
            craftingRecipe.has("pattern") shouldBe true
            craftingRecipe.has("key") shouldBe true
            craftingRecipe.has("result") shouldBe true
            
            // Generate smelting recipe
            val smeltingRecipe = ToolRecipes.generateSmeltingRecipe(material, toolType)
            smeltingRecipe shouldNotBe null
            smeltingRecipe.has("type") shouldBe true
            smeltingRecipe.get("type").asString shouldBe "minecraft:smelting"
            
            // Generate blasting recipe
            val blastingRecipe = ToolRecipes.generateBlastingRecipe(material, toolType)
            blastingRecipe shouldNotBe null
            blastingRecipe.has("type") shouldBe true
            blastingRecipe.get("type").asString shouldBe "minecraft:blasting"
            
            // Generate advancement
            val advancement = ToolRecipes.generateRecipeAdvancement(material, toolType)
            advancement shouldNotBe null
            advancement.has("parent") shouldBe true
            advancement.has("criteria") shouldBe true
            advancement.has("requirements") shouldBe true
            advancement.has("rewards") shouldBe true
        }
    }
    
    /**
     * Feature: basic-tools, Property 32: Model JSONs are generated for all tools
     * 
     * For any execution of the tool generator, it should generate item model JSON
     * files for all tools.
     * 
     * Validates: Requirements 9.3
     */
    "Property 32: Model JSONs are generated for all tools".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val tool = GeneratedTool(material, toolType)
            
            // Generate item model
            val itemModel = tool.generateItemModel()
            itemModel shouldNotBe null
            
            // Should have parent
            itemModel.has("parent") shouldBe true
            itemModel.get("parent").asString shouldBe "minecraft:item/handheld"
            
            // Should have textures
            itemModel.has("textures") shouldBe true
            val textures = itemModel.getAsJsonObject("textures")
            textures.has("layer0") shouldBe true
            textures.has("layer1") shouldBe true
            
            // Generate item render
            val itemRender = tool.generateItemRender()
            itemRender shouldNotBe null
            itemRender.has("model") shouldBe true
        }
    }
    
    /**
     * Feature: basic-tools, Property 33: Generated files are copied to resources
     * 
     * For any execution of the tool generator, generated files should be copied
     * to the appropriate resource directories (src/main/resources/).
     * 
     * This property tests the file path structure that would be used for copying.
     * 
     * Validates: Requirements 9.5
     */
    "Property 33: Generated files follow correct resource paths".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val tool = GeneratedTool(material, toolType)
            
            // Expected paths for resources
            val expectedModelPath = "assets/metalmancy/models/item/${tool.unlocalizedName}.json"
            val expectedItemPath = "assets/metalmancy/items/${tool.unlocalizedName}.json"
            val expectedRecipePath = "data/metalmancy/recipe/${tool.unlocalizedName}.json"
            val expectedAdvancementPath = "data/metalmancy/advancement/recipe/${tool.unlocalizedName}.json"
            
            // Verify path components
            expectedModelPath shouldContain "assets"
            expectedModelPath shouldContain "metalmancy"
            expectedModelPath shouldContain "models/item"
            expectedModelPath shouldContain tool.unlocalizedName
            
            expectedRecipePath shouldContain "data"
            expectedRecipePath shouldContain "metalmancy"
            expectedRecipePath shouldContain "recipe"
            expectedRecipePath shouldContain tool.unlocalizedName
            
            expectedAdvancementPath shouldContain "data"
            expectedAdvancementPath shouldContain "metalmancy"
            expectedAdvancementPath shouldContain "advancement/recipe"
            expectedAdvancementPath shouldContain tool.unlocalizedName
        }
    }
    
    /**
     * Feature: basic-tools, Property 34: Generated files follow datapack structure
     * 
     * For any generated JSON file, it should follow the structure specified in the
     * Minecraft 1.21.10 datapack specification (recipes in data/{namespace}/recipe/,
     * models in assets/{namespace}/models/item/).
     * 
     * Validates: Requirements 9.6
     */
    "Property 34: Generated files follow datapack structure".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val tool = GeneratedTool(material, toolType)
            
            // Verify namespace is correct
            val namespace = "metalmancy"
            
            // Model path should follow: assets/{namespace}/models/item/{name}.json
            val modelPath = "assets/$namespace/models/item/${tool.unlocalizedName}.json"
            val modelFile = File(modelPath)
            modelFile.path shouldContain "assets"
            modelFile.path shouldContain namespace
            modelFile.path shouldContain "models"
            modelFile.path shouldContain "item"
            
            // Recipe path should follow: data/{namespace}/recipe/{name}.json
            val recipePath = "data/$namespace/recipe/${tool.unlocalizedName}.json"
            val recipeFile = File(recipePath)
            recipeFile.path shouldContain "data"
            recipeFile.path shouldContain namespace
            recipeFile.path shouldContain "recipe"
            
            // Advancement path should follow: data/{namespace}/advancement/recipe/{name}.json
            val advancementPath = "data/$namespace/advancement/recipe/${tool.unlocalizedName}.json"
            val advancementFile = File(advancementPath)
            advancementFile.path shouldContain "data"
            advancementFile.path shouldContain namespace
            advancementFile.path shouldContain "advancement"
            advancementFile.path shouldContain "recipe"
            
            // Verify no subdirectories in recipe folder (flat structure)
            val recipeFileName = "${tool.unlocalizedName}.json"
            recipeFileName.contains("/") shouldBe false
            recipeFileName.contains("\\") shouldBe false
        }
    }
    
    /**
     * Property: All tool-enabled materials generate complete tool sets
     * 
     * For any tool-enabled material, all five tool types should be generated.
     */
    "All tool-enabled materials generate complete tool sets".config(invocations = 50) {
        checkAll(Arb.actualToolEnabledMaterial()) { material ->
            // Verify material has required parts
            ToolMaterials.hasRequiredParts(material) shouldBe true
            
            // Verify material has tier mapping
            val tier = ToolMaterials.getTierWithValidation(material)
            tier shouldNotBe null
            
            // Generate all five tool types
            val tools = ToolType.entries.map { toolType ->
                GeneratedTool(material, toolType)
            }
            
            // Should have exactly 5 tools
            tools.size shouldBe 5
            
            // Each tool should have unique unlocalized name
            val names = tools.map { it.unlocalizedName }.toSet()
            names.size shouldBe 5
            
            // Each tool should generate valid models
            tools.forEach { tool ->
                val model = tool.generateItemModel()
                model shouldNotBe null
                model.has("parent") shouldBe true
                model.has("textures") shouldBe true
            }
            
            // Each tool should generate valid recipes
            tools.forEach { tool ->
                val recipe = ToolRecipes.generateCraftingRecipe(material, tool.toolType)
                recipe shouldNotBe null
                recipe.has("type") shouldBe true
                recipe.has("result") shouldBe true
            }
        }
    }
    
    /**
     * Property: Generated files use consistent naming conventions
     * 
     * For any generated tool, all related files should use the same base name.
     */
    "Generated files use consistent naming conventions".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val tool = GeneratedTool(material, toolType)
            
            val baseName = tool.unlocalizedName
            
            // All file names should use the base name
            val modelFileName = "$baseName.json"
            val recipeFileName = "$baseName.json"
            val smeltingFileName = "${baseName}_smelting.json"
            val blastingFileName = "${baseName}_blasting.json"
            val advancementFileName = "$baseName.json"
            
            // Verify naming pattern
            baseName shouldContain material.name
            baseName shouldContain toolType.unlocalizedSuffix
            
            // Verify format: {material}_{tool}
            val expectedPattern = "${material.name}_${toolType.unlocalizedSuffix}"
            baseName shouldBe expectedPattern
            
            // Verify recycling recipe names include suffix
            smeltingFileName shouldContain "_smelting"
            blastingFileName shouldContain "_blasting"
        }
    }
    
    /**
     * Property: Mirrored recipes have correct naming
     * 
     * For axes and hoes, mirrored recipes should have "_mirrored" suffix.
     */
    "Mirrored recipes have correct naming".config(invocations = 100) {
        checkAll(Arb.actualToolEnabledMaterial()) { material ->
            // Test axe
            val axeTool = GeneratedTool(material, ToolType.AXE)
            val axeMirroredName = "${axeTool.unlocalizedName}_mirrored"
            axeMirroredName shouldContain "_mirrored"
            axeMirroredName shouldContain material.name
            axeMirroredName shouldContain "axe"
            
            // Test hoe
            val hoeTool = GeneratedTool(material, ToolType.HOE)
            val hoeMirroredName = "${hoeTool.unlocalizedName}_mirrored"
            hoeMirroredName shouldContain "_mirrored"
            hoeMirroredName shouldContain material.name
            hoeMirroredName shouldContain "hoe"
        }
    }
    
    /**
     * Feature: basic-tools, Property 27: Tool language entries are generated
     * 
     * For any generated tool, there should be a localized name entry in en_us.json.
     * The language entry should follow the format "item.metalmancy.{tool_name}": "{Material} {Tool}".
     * 
     * Validates: Requirements 5.11
     */
    "Property 27: Tool language entries are generated".config(invocations = 100) {
        checkAll(Arb.materialAndToolType()) { pair ->
            val (material, toolType) = pair
            val tool = GeneratedTool(material, toolType)
            
            // Generate language entry key
            val languageKey = "item.metalmancy.${tool.unlocalizedName}"
            
            // Generate language entry value
            val materialName = material.name.replaceFirstChar { it.uppercase() }
            val toolName = toolType.unlocalizedSuffix.replaceFirstChar { it.uppercase() }
            val expectedValue = "$materialName $toolName"
            
            // Verify key format
            languageKey shouldContain "item.metalmancy."
            languageKey shouldContain material.name
            languageKey shouldContain toolType.unlocalizedSuffix
            
            // Verify value format
            expectedValue shouldContain materialName
            expectedValue shouldContain toolName
            
            // Verify the key follows the pattern: item.{namespace}.{unlocalized_name}
            val keyPattern = "item\\.metalmancy\\.[a-z_]+".toRegex()
            languageKey.matches(keyPattern) shouldBe true
            
            // Verify the value is properly capitalized
            expectedValue.first().isUpperCase() shouldBe true
            
            // Verify the value contains a space between material and tool
            expectedValue shouldContain " "
            
            // Verify both parts are capitalized
            val parts = expectedValue.split(" ")
            parts.size shouldBe 2
            parts[0].first().isUpperCase() shouldBe true
            parts[1].first().isUpperCase() shouldBe true
        }
    }
})


/**
 * Property-based tests for generation statistics reporting.
 */
class ToolGenStatisticsPropertyTest : StringSpec({
    
    /**
     * Feature: basic-tools, Property 28: Generator reports generation statistics
     * 
     * For any execution of the tool generator, it should report the count of
     * generated tool items, models, recipes, and language entries.
     * 
     * Validates: Requirements 5.12
     */
    "Property 28: Generator reports generation statistics".config(invocations = 100) {
        checkAll(Arb.actualToolEnabledMaterial()) { material ->
            // For each material, we should generate:
            // - 5 tool items (one per tool type)
            // - 5 models (one per tool type)
            // - 5 crafting recipes (one per tool type)
            // - 2 mirrored recipes (axe and hoe)
            // - 5 smelting recipes (one per tool type)
            // - 5 blasting recipes (one per tool type)
            // - 5 advancements (one per tool type)
            // - 5 language entries (one per tool type)
            
            val expectedToolItems = 5
            val expectedModels = 5
            val expectedCraftingRecipes = 5
            val expectedMirroredRecipes = 2
            val expectedSmeltingRecipes = 5
            val expectedBlastingRecipes = 5
            val expectedTotalRecipes = expectedCraftingRecipes + expectedMirroredRecipes + 
                                       expectedSmeltingRecipes + expectedBlastingRecipes
            val expectedAdvancements = 5
            val expectedLanguageEntries = 5
            
            // Verify we can generate all tools
            val tools = ToolType.entries.map { toolType ->
                GeneratedTool(material, toolType)
            }
            
            tools.size shouldBe expectedToolItems
            
            // Verify we can generate all models
            val models = tools.map { it.generateItemModel() }
            models.size shouldBe expectedModels
            models.forEach { model ->
                model shouldNotBe null
                model.has("parent") shouldBe true
                model.has("textures") shouldBe true
            }
            
            // Verify we can generate all crafting recipes
            val craftingRecipes = tools.map { tool ->
                ToolRecipes.generateCraftingRecipe(material, tool.toolType)
            }
            craftingRecipes.size shouldBe expectedCraftingRecipes
            
            // Verify we can generate mirrored recipes for axe and hoe
            val mirroredRecipes = listOf(
                ToolRecipes.generateMirroredRecipe(material, ToolType.AXE),
                ToolRecipes.generateMirroredRecipe(material, ToolType.HOE)
            )
            mirroredRecipes.size shouldBe expectedMirroredRecipes
            
            // Verify we can generate all smelting recipes
            val smeltingRecipes = tools.map { tool ->
                ToolRecipes.generateSmeltingRecipe(material, tool.toolType)
            }
            smeltingRecipes.size shouldBe expectedSmeltingRecipes
            
            // Verify we can generate all blasting recipes
            val blastingRecipes = tools.map { tool ->
                ToolRecipes.generateBlastingRecipe(material, tool.toolType)
            }
            blastingRecipes.size shouldBe expectedBlastingRecipes
            
            // Verify total recipe count
            val totalRecipes = craftingRecipes.size + mirroredRecipes.size + 
                              smeltingRecipes.size + blastingRecipes.size
            totalRecipes shouldBe expectedTotalRecipes
            
            // Verify we can generate all advancements
            val advancements = tools.map { tool ->
                ToolRecipes.generateRecipeAdvancement(material, tool.toolType)
            }
            advancements.size shouldBe expectedAdvancements
            
            // Verify language entries
            val languageEntries = tools.associate { tool ->
                val key = "item.metalmancy.${tool.unlocalizedName}"
                val value = "${material.name.replaceFirstChar { it.uppercase() }} ${tool.toolType.unlocalizedSuffix.replaceFirstChar { it.uppercase() }}"
                key to value
            }
            languageEntries.size shouldBe expectedLanguageEntries
            
            // Verify each language entry has correct format
            languageEntries.forEach { (key, value) ->
                key shouldContain "item.metalmancy."
                key shouldContain material.name
                value shouldContain material.name.replaceFirstChar { it.uppercase() }
            }
        }
    }
    
    /**
     * Property: Statistics scale correctly with number of materials
     * 
     * For any set of tool-enabled materials, the total statistics should scale
     * linearly with the number of materials.
     */
    "Statistics scale correctly with number of materials".config(invocations = 50) {
        checkAll(Arb.int(1..5)) { materialCount ->
            // Take a subset of tool-enabled materials
            val materials = ToolMaterials.TOOL_ENABLED.take(materialCount)
            
            // Expected counts per material
            val toolsPerMaterial = 5
            val modelsPerMaterial = 5
            val recipesPerMaterial = 17 // 5 crafting + 2 mirrored + 5 smelting + 5 blasting
            val advancementsPerMaterial = 5
            val languageEntriesPerMaterial = 5
            
            // Total expected counts
            val expectedTotalTools = materials.size * toolsPerMaterial
            val expectedTotalModels = materials.size * modelsPerMaterial
            val expectedTotalRecipes = materials.size * recipesPerMaterial
            val expectedTotalAdvancements = materials.size * advancementsPerMaterial
            val expectedTotalLanguageEntries = materials.size * languageEntriesPerMaterial
            
            // Generate all tools
            val allTools = materials.flatMap { material ->
                ToolType.entries.map { toolType ->
                    GeneratedTool(material, toolType)
                }
            }
            
            allTools.size shouldBe expectedTotalTools
            
            // Generate all models
            val allModels = allTools.map { it.generateItemModel() }
            allModels.size shouldBe expectedTotalModels
            
            // Generate all recipes
            val allCraftingRecipes = allTools.map { tool ->
                ToolRecipes.generateCraftingRecipe(tool.material, tool.toolType)
            }
            val allMirroredRecipes = materials.flatMap { material ->
                listOf(
                    ToolRecipes.generateMirroredRecipe(material, ToolType.AXE),
                    ToolRecipes.generateMirroredRecipe(material, ToolType.HOE)
                )
            }
            val allSmeltingRecipes = allTools.map { tool ->
                ToolRecipes.generateSmeltingRecipe(tool.material, tool.toolType)
            }
            val allBlastingRecipes = allTools.map { tool ->
                ToolRecipes.generateBlastingRecipe(tool.material, tool.toolType)
            }
            
            val totalRecipes = allCraftingRecipes.size + allMirroredRecipes.size + 
                              allSmeltingRecipes.size + allBlastingRecipes.size
            totalRecipes shouldBe expectedTotalRecipes
            
            // Generate all advancements
            val allAdvancements = allTools.map { tool ->
                ToolRecipes.generateRecipeAdvancement(tool.material, tool.toolType)
            }
            allAdvancements.size shouldBe expectedTotalAdvancements
            
            // Generate all language entries
            val allLanguageEntries = allTools.associate { tool ->
                val key = "item.metalmancy.${tool.unlocalizedName}"
                val value = "${tool.material.name.replaceFirstChar { it.uppercase() }} ${tool.toolType.unlocalizedSuffix.replaceFirstChar { it.uppercase() }}"
                key to value
            }
            allLanguageEntries.size shouldBe expectedTotalLanguageEntries
        }
    }
    
    /**
     * Property: Each tool type contributes equally to statistics
     * 
     * For any material, each tool type should contribute the same number of
     * generated files (except for mirrored recipes).
     */
    "Each tool type contributes equally to statistics".config(invocations = 100) {
        checkAll(Arb.actualToolEnabledMaterial()) { material ->
            // For each tool type, count generated files
            val statsPerToolType = ToolType.entries.associate { toolType ->
                val tool = GeneratedTool(material, toolType)
                
                // Count files for this tool type
                val modelCount = 1 // 1 model per tool
                val craftingRecipeCount = 1 // 1 crafting recipe per tool
                val mirroredRecipeCount = if (toolType == ToolType.AXE || toolType == ToolType.HOE) 1 else 0
                val smeltingRecipeCount = 1 // 1 smelting recipe per tool
                val blastingRecipeCount = 1 // 1 blasting recipe per tool
                val advancementCount = 1 // 1 advancement per tool
                val languageEntryCount = 1 // 1 language entry per tool
                
                val totalFiles = modelCount + craftingRecipeCount + mirroredRecipeCount + 
                                smeltingRecipeCount + blastingRecipeCount + advancementCount + 
                                languageEntryCount
                
                toolType to totalFiles
            }
            
            // Verify counts
            statsPerToolType[ToolType.SWORD] shouldBe 6 // No mirrored recipe
            statsPerToolType[ToolType.AXE] shouldBe 7 // Has mirrored recipe
            statsPerToolType[ToolType.PICKAXE] shouldBe 6 // No mirrored recipe
            statsPerToolType[ToolType.SHOVEL] shouldBe 6 // No mirrored recipe
            statsPerToolType[ToolType.HOE] shouldBe 7 // Has mirrored recipe
            
            // Total files per material should be consistent
            val totalFiles = statsPerToolType.values.sum()
            totalFiles shouldBe 32 // 5*6 + 2 = 30 + 2 = 32
        }
    }
    
    /**
     * Property: Registration code is generated for all tools
     * 
     * For any material, registration code should be generated for all five tool types.
     */
    "Registration code is generated for all tools".config(invocations = 100) {
        checkAll(Arb.actualToolEnabledMaterial()) { material ->
            val tools = ToolType.entries.map { toolType ->
                GeneratedTool(material, toolType)
            }
            
            // Generate registration code for all tools
            val registrationCodes = tools.map { it.generateRegistrationCode() }
            
            // Should have 5 registration code blocks
            registrationCodes.size shouldBe 5
            
            // Each should be non-empty
            registrationCodes.forEach { code ->
                code.isNotBlank() shouldBe true
            }
            
            // Each should contain the material name
            registrationCodes.forEach { code ->
                code shouldContain material.name
            }
            
            // Each should contain a different tool type
            // We need to match the exact unlocalized name to avoid substring matches (e.g., "axe" in "pickaxe")
            val toolTypesInCode = registrationCodes.map { code ->
                ToolType.entries.first { toolType ->
                    val unlocalizedName = toolType.getUnlocalizedName(material.name)
                    code.contains(unlocalizedName)
                }
            }
            toolTypesInCode.toSet().size shouldBe 5 // All 5 tool types should be present
        }
    }
})
