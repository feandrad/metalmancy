package io.felipeandrade.metalmancy.datagen

import io.felipeandrade.metalmancy.blocks.ModBlocks
import io.felipeandrade.metalmancy.items.ModItems
import io.felipeandrade.metalmancy.items.armor.ModArmorItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models
import net.minecraft.item.ArmorItem

class ModelProvider(
    output: FabricDataOutput
) : FabricModelProvider(output) {

    override fun generateBlockStateModels(
        blockStateModelGenerator: BlockStateModelGenerator
    ) = with(blockStateModelGenerator) {

        // Metals
        registerSimpleCubeAll(ModBlocks.ZINC_BLOCK)
        registerSimpleCubeAll(ModBlocks.ZINC_RAW_BLOCK)
        registerSimpleCubeAll(ModBlocks.ZINC_ORE)
        registerSimpleCubeAll(ModBlocks.ZINC_DEEPSLATE_ORE)

        registerSimpleCubeAll(ModBlocks.TIN_BLOCK)
        registerSimpleCubeAll(ModBlocks.TIN_RAW_BLOCK)
        registerSimpleCubeAll(ModBlocks.TIN_ORE)
        registerSimpleCubeAll(ModBlocks.TIN_DEEPSLATE_ORE)

        registerSimpleCubeAll(ModBlocks.SILVER_BLOCK)
        registerSimpleCubeAll(ModBlocks.SILVER_RAW_BLOCK)
        registerSimpleCubeAll(ModBlocks.SILVER_ORE)
        registerSimpleCubeAll(ModBlocks.SILVER_DEEPSLATE_ORE)

        registerSimpleCubeAll(ModBlocks.PLATINUM_BLOCK)
        registerSimpleCubeAll(ModBlocks.PLATINUM_RAW_BLOCK)
        registerSimpleCubeAll(ModBlocks.PLATINUM_ORE)
        registerSimpleCubeAll(ModBlocks.PLATINUM_DEEPSLATE_ORE)

        registerSimpleCubeAll(ModBlocks.TITANIUM_BLOCK)
        registerSimpleCubeAll(ModBlocks.TITANIUM_RAW_BLOCK)
        registerSimpleCubeAll(ModBlocks.TITANIUM_ORE)
        registerSimpleCubeAll(ModBlocks.TITANIUM_DEEPSLATE_ORE)

        registerSimpleCubeAll(ModBlocks.COBALT_BLOCK)
        registerSimpleCubeAll(ModBlocks.COBALT_RAW_BLOCK)
        registerSimpleCubeAll(ModBlocks.COBALT_ORE)
        registerSimpleCubeAll(ModBlocks.COBALT_DEEPSLATE_ORE)

        registerSimpleCubeAll(ModBlocks.MITHRIL_BLOCK)
        registerSimpleCubeAll(ModBlocks.MITHRIL_RAW_BLOCK)
        registerSimpleCubeAll(ModBlocks.MITHRIL_ORE)
        registerSimpleCubeAll(ModBlocks.MITHRIL_DEEPSLATE_ORE)

        registerSimpleCubeAll(ModBlocks.ORICHALCUM_BLOCK)
        registerSimpleCubeAll(ModBlocks.ORICHALCUM_RAW_BLOCK)
        registerSimpleCubeAll(ModBlocks.ORICHALCUM_ORE)
        registerSimpleCubeAll(ModBlocks.ORICHALCUM_DEEPSLATE_ORE)

        // Alloys
        registerSimpleCubeAll(ModBlocks.BRASS_BLOCK)
        registerSimpleCubeAll(ModBlocks.BRONZE_BLOCK)
        registerSimpleCubeAll(ModBlocks.STEEL_BLOCK)

        // Gems
        registerSimpleCubeAll(ModBlocks.RUBY_BLOCK)
        registerSimpleCubeAll(ModBlocks.RUBY_ORE)
        registerSimpleCubeAll(ModBlocks.RUBY_DEEPSLATE_ORE)

        registerSimpleCubeAll(ModBlocks.SAPPHIRE_BLOCK)
        registerSimpleCubeAll(ModBlocks.SAPPHIRE_ORE)
        registerSimpleCubeAll(ModBlocks.SAPPHIRE_DEEPSLATE_ORE)

        registerSimpleCubeAll(ModBlocks.TOPAZ_BLOCK)
        registerSimpleCubeAll(ModBlocks.TOPAZ_ORE)
        registerSimpleCubeAll(ModBlocks.TOPAZ_DEEPSLATE_ORE)

    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator): Unit = with(itemModelGenerator) {
        // Vanilla
        register(ModItems.COPPER_NUGGET, Models.GENERATED)
        register(ModItems.COPPER_DUST, Models.GENERATED)
        register(ModItems.COPPER_CRUSHED, Models.GENERATED)
        register(ModItems.COAL_DUST, Models.GENERATED)
        register(ModItems.COAL_CRUSHED, Models.GENERATED)

        // Tin
        register(ModItems.TIN_CRUSHED, Models.GENERATED)
        register(ModItems.TIN_DUST, Models.GENERATED)
        register(ModItems.TIN_INGOT, Models.GENERATED)
        register(ModItems.TIN_NUGGET, Models.GENERATED)
        register(ModItems.TIN_RAW, Models.GENERATED)

        // Zinc
        register(ModItems.ZINC_CRUSHED, Models.GENERATED)
        register(ModItems.ZINC_DUST, Models.GENERATED)
        register(ModItems.ZINC_INGOT, Models.GENERATED)
        register(ModItems.ZINC_NUGGET, Models.GENERATED)
        register(ModItems.ZINC_RAW, Models.GENERATED)

        // Silver
        register(ModItems.SILVER_CRUSHED, Models.GENERATED)
        register(ModItems.SILVER_DUST, Models.GENERATED)
        register(ModItems.SILVER_INGOT, Models.GENERATED)
        register(ModItems.SILVER_NUGGET, Models.GENERATED)
        register(ModItems.SILVER_RAW, Models.GENERATED)

        // Platinum
        register(ModItems.PLATINUM_CRUSHED, Models.GENERATED)
        register(ModItems.PLATINUM_DUST, Models.GENERATED)
        register(ModItems.PLATINUM_INGOT, Models.GENERATED)
        register(ModItems.PLATINUM_NUGGET, Models.GENERATED)
        register(ModItems.PLATINUM_RAW, Models.GENERATED)

        // Titanium
        register(ModItems.TITANIUM_CRUSHED, Models.GENERATED)
        register(ModItems.TITANIUM_DUST, Models.GENERATED)
        register(ModItems.TITANIUM_INGOT, Models.GENERATED)
        register(ModItems.TITANIUM_NUGGET, Models.GENERATED)
        register(ModItems.TITANIUM_RAW, Models.GENERATED)

        // Cobalt
        register(ModItems.COBALT_CRUSHED, Models.GENERATED)
        register(ModItems.COBALT_DUST, Models.GENERATED)
        register(ModItems.COBALT_INGOT, Models.GENERATED)
        register(ModItems.COBALT_NUGGET, Models.GENERATED)
        register(ModItems.COBALT_RAW, Models.GENERATED)

        // Mithril
        register(ModItems.MITHRIL_CRUSHED, Models.GENERATED)
        register(ModItems.MITHRIL_DUST, Models.GENERATED)
        register(ModItems.MITHRIL_INGOT, Models.GENERATED)
        register(ModItems.MITHRIL_NUGGET, Models.GENERATED)
        register(ModItems.MITHRIL_RAW, Models.GENERATED)

        // Orichalcum
        register(ModItems.ORICHALCUM_CRUSHED, Models.GENERATED)
        register(ModItems.ORICHALCUM_DUST, Models.GENERATED)
        register(ModItems.ORICHALCUM_INGOT, Models.GENERATED)
        register(ModItems.ORICHALCUM_NUGGET, Models.GENERATED)
        register(ModItems.ORICHALCUM_RAW, Models.GENERATED)


        // Bronze
        register(ModItems.BRONZE_CRUSHED, Models.GENERATED)
        register(ModItems.BRONZE_DUST, Models.GENERATED)
        register(ModItems.BRONZE_INGOT, Models.GENERATED)
        register(ModItems.BRONZE_NUGGET, Models.GENERATED)

        // Brass
        register(ModItems.BRASS_CRUSHED, Models.GENERATED)
        register(ModItems.BRASS_DUST, Models.GENERATED)
        register(ModItems.BRASS_INGOT, Models.GENERATED)
        register(ModItems.BRASS_NUGGET, Models.GENERATED)

        // Steel
        register(ModItems.STEEL_CRUSHED, Models.GENERATED)
        register(ModItems.STEEL_DUST, Models.GENERATED)
        register(ModItems.STEEL_INGOT, Models.GENERATED)
        register(ModItems.STEEL_NUGGET, Models.GENERATED)

        // Gems
        register(ModItems.RUBY, Models.GENERATED)
        register(ModItems.SAPPHIRE, Models.GENERATED)
        register(ModItems.TOPAZ, Models.GENERATED)


        // Armors
        registerArmor(ModArmorItems.WOOLEN_BOOTS as ArmorItem)
        registerArmor(ModArmorItems.WOOLEN_LEGGINGS as ArmorItem)
        registerArmor(ModArmorItems.WOOLEN_CHESTPLATE as ArmorItem)
        registerArmor(ModArmorItems.WOOLEN_HELMET as ArmorItem)

        registerArmor(ModArmorItems.WOODEN_BOOTS as ArmorItem)
        registerArmor(ModArmorItems.WOODEN_LEGGINGS as ArmorItem)
        registerArmor(ModArmorItems.WOODEN_CHESTPLATE as ArmorItem)
        registerArmor(ModArmorItems.WOODEN_HELMET as ArmorItem)

        registerArmor(ModArmorItems.COPPER_BOOTS as ArmorItem)
        registerArmor(ModArmorItems.COPPER_LEGGINGS as ArmorItem)
        registerArmor(ModArmorItems.COPPER_CHESTPLATE as ArmorItem)
        registerArmor(ModArmorItems.COPPER_HELMET as ArmorItem)

        // Metals
        registerArmor(ModArmorItems.SILVER_BOOTS as ArmorItem)
        registerArmor(ModArmorItems.SILVER_LEGGINGS as ArmorItem)
        registerArmor(ModArmorItems.SILVER_CHESTPLATE as ArmorItem)
        registerArmor(ModArmorItems.SILVER_HELMET as ArmorItem)

        registerArmor(ModArmorItems.PLATINUM_BOOTS as ArmorItem)
        registerArmor(ModArmorItems.PLATINUM_LEGGINGS as ArmorItem)
        registerArmor(ModArmorItems.PLATINUM_CHESTPLATE as ArmorItem)
        registerArmor(ModArmorItems.PLATINUM_HELMET as ArmorItem)

        registerArmor(ModArmorItems.TITANIUM_BOOTS as ArmorItem)
        registerArmor(ModArmorItems.TITANIUM_LEGGINGS as ArmorItem)
        registerArmor(ModArmorItems.TITANIUM_CHESTPLATE as ArmorItem)
        registerArmor(ModArmorItems.TITANIUM_HELMET as ArmorItem)

        registerArmor(ModArmorItems.COBALT_BOOTS as ArmorItem)
        registerArmor(ModArmorItems.COBALT_LEGGINGS as ArmorItem)
        registerArmor(ModArmorItems.COBALT_CHESTPLATE as ArmorItem)
        registerArmor(ModArmorItems.COBALT_HELMET as ArmorItem)

        registerArmor(ModArmorItems.MITHRIL_BOOTS as ArmorItem)
        registerArmor(ModArmorItems.MITHRIL_LEGGINGS as ArmorItem)
        registerArmor(ModArmorItems.MITHRIL_CHESTPLATE as ArmorItem)
        registerArmor(ModArmorItems.MITHRIL_HELMET as ArmorItem)

        registerArmor(ModArmorItems.ORICHALCUM_BOOTS as ArmorItem)
        registerArmor(ModArmorItems.ORICHALCUM_LEGGINGS as ArmorItem)
        registerArmor(ModArmorItems.ORICHALCUM_CHESTPLATE as ArmorItem)
        registerArmor(ModArmorItems.ORICHALCUM_HELMET as ArmorItem)

        // Alloys
        registerArmor(ModArmorItems.BRASS_BOOTS as ArmorItem)
        registerArmor(ModArmorItems.BRASS_LEGGINGS as ArmorItem)
        registerArmor(ModArmorItems.BRASS_CHESTPLATE as ArmorItem)
        registerArmor(ModArmorItems.BRASS_HELMET as ArmorItem)

        registerArmor(ModArmorItems.BRONZE_BOOTS as ArmorItem)
        registerArmor(ModArmorItems.BRONZE_LEGGINGS as ArmorItem)
        registerArmor(ModArmorItems.BRONZE_CHESTPLATE as ArmorItem)
        registerArmor(ModArmorItems.BRONZE_HELMET as ArmorItem)

        registerArmor(ModArmorItems.STEEL_BOOTS as ArmorItem)
        registerArmor(ModArmorItems.STEEL_LEGGINGS as ArmorItem)
        registerArmor(ModArmorItems.STEEL_CHESTPLATE as ArmorItem)
        registerArmor(ModArmorItems.STEEL_HELMET as ArmorItem)
    }
}