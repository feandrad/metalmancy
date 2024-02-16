package io.felipeandrade.metalmancy.datagen

import io.felipeandrade.metalmancy.ModItemTags
import io.felipeandrade.metalmancy.items.ModItems
import io.felipeandrade.metalmancy.items.armor.ModArmorItems
import io.felipeandrade.metalmancy.items.tools.ModTools
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider
import net.minecraft.item.Items
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.registry.tag.ItemTags
import java.util.concurrent.CompletableFuture

class ItemTagProvider(
    output: FabricDataOutput, completableFuture: CompletableFuture<WrapperLookup>
) : ItemTagProvider(output, completableFuture) {
    override fun configure(arg: WrapperLookup) {
        materialItems()
        ironAlternatives()
        pickaxes()
        armorTrims()
    }


    private fun materialItems() {
        // Vanilla
        getOrCreateTagBuilder(ModItemTags.BONES).add(Items.BONE)
        getOrCreateTagBuilder(ModItemTags.FLINTS).add(Items.FLINT)
        getOrCreateTagBuilder(ModItemTags.COPPER_INGOTS).add(Items.COPPER_INGOT)
        getOrCreateTagBuilder(ModItemTags.COPPER_NUGGETS).add(ModItems.COPPER_NUGGET)
        getOrCreateTagBuilder(ModItemTags.DIAMOND_NUGGETS).add(ModItems.DIAMOND_NUGGET)

        // Metals
        getOrCreateTagBuilder(ModItemTags.TIN_INGOTS).add(ModItems.TIN_INGOT)
        getOrCreateTagBuilder(ModItemTags.ZINC_INGOTS).add(ModItems.ZINC_INGOT)
        getOrCreateTagBuilder(ModItemTags.SILVER_INGOTS).add(ModItems.SILVER_INGOT)
        getOrCreateTagBuilder(ModItemTags.PLATINUM_INGOTS).add(ModItems.PLATINUM_INGOT)
        getOrCreateTagBuilder(ModItemTags.TITANIUM_INGOTS).add(ModItems.TITANIUM_INGOT)
        getOrCreateTagBuilder(ModItemTags.COBALT_INGOTS).add(ModItems.COBALT_INGOT)
        getOrCreateTagBuilder(ModItemTags.MITHRIL_INGOTS).add(ModItems.MITHRIL_INGOT)
        getOrCreateTagBuilder(ModItemTags.ORICHALCUM_INGOTS).add(ModItems.ORICHALCUM_INGOT)
        
        getOrCreateTagBuilder(ModItemTags.TIN_NUGGETS).add(ModItems.TIN_NUGGET)
        getOrCreateTagBuilder(ModItemTags.ZINC_NUGGETS).add(ModItems.ZINC_NUGGET)
        getOrCreateTagBuilder(ModItemTags.SILVER_NUGGETS).add(ModItems.SILVER_NUGGET)
        getOrCreateTagBuilder(ModItemTags.PLATINUM_NUGGETS).add(ModItems.PLATINUM_NUGGET)
        getOrCreateTagBuilder(ModItemTags.TITANIUM_NUGGETS).add(ModItems.TITANIUM_NUGGET)
        getOrCreateTagBuilder(ModItemTags.COBALT_NUGGETS).add(ModItems.COBALT_NUGGET)
        getOrCreateTagBuilder(ModItemTags.MITHRIL_NUGGETS).add(ModItems.MITHRIL_NUGGET)
        getOrCreateTagBuilder(ModItemTags.ORICHALCUM_NUGGETS).add(ModItems.ORICHALCUM_NUGGET)

        // Alloys
        getOrCreateTagBuilder(ModItemTags.BRONZE_INGOTS).add(ModItems.BRONZE_INGOT)
        getOrCreateTagBuilder(ModItemTags.BRASS_INGOTS).add(ModItems.BRASS_INGOT)
        getOrCreateTagBuilder(ModItemTags.STEEL_INGOTS).add(ModItems.STEEL_INGOT)
        
        getOrCreateTagBuilder(ModItemTags.BRONZE_NUGGETS).add(ModItems.BRONZE_NUGGET)
        getOrCreateTagBuilder(ModItemTags.BRASS_NUGGETS).add(ModItems.BRASS_NUGGET)
        getOrCreateTagBuilder(ModItemTags.STEEL_NUGGETS).add(ModItems.STEEL_NUGGET)

        getOrCreateTagBuilder(ModItemTags.RUBYS).add(ModItems.RUBY)
        getOrCreateTagBuilder(ModItemTags.SAPPHIRES).add(ModItems.SAPPHIRE)
        getOrCreateTagBuilder(ModItemTags.TOPAZES).add(ModItems.TOPAZ)
    }

    private fun ironAlternatives() {
        getOrCreateTagBuilder(ModItemTags.IRON_ALTERNATIVE)
            .forceAddTag(ModItemTags.BRASS_INGOTS)
            .forceAddTag(ModItemTags.BRONZE_INGOTS)
            .forceAddTag(ModItemTags.SILVER_INGOTS)
    }

    private fun armorTrims() {
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
            .add(ModArmorItems.WOOLEN_BOOTS, ModArmorItems.WOOLEN_LEGGINGS, ModArmorItems.WOOLEN_CHESTPLATE, ModArmorItems.WOOLEN_HELMET)
            .add(ModArmorItems.WOODEN_BOOTS, ModArmorItems.WOODEN_LEGGINGS, ModArmorItems.WOODEN_CHESTPLATE, ModArmorItems.WOODEN_HELMET)
            .add(ModArmorItems.COPPER_BOOTS, ModArmorItems.COPPER_LEGGINGS, ModArmorItems.COPPER_CHESTPLATE, ModArmorItems.COPPER_HELMET)
            .add(ModArmorItems.SILVER_BOOTS, ModArmorItems.SILVER_LEGGINGS, ModArmorItems.SILVER_CHESTPLATE, ModArmorItems.SILVER_HELMET)
            .add(ModArmorItems.PLATINUM_BOOTS, ModArmorItems.PLATINUM_LEGGINGS, ModArmorItems.PLATINUM_CHESTPLATE, ModArmorItems.PLATINUM_HELMET)
            .add(ModArmorItems.TITANIUM_BOOTS, ModArmorItems.TITANIUM_LEGGINGS, ModArmorItems.TITANIUM_CHESTPLATE, ModArmorItems.TITANIUM_HELMET)
            .add(ModArmorItems.COBALT_BOOTS, ModArmorItems.COBALT_LEGGINGS, ModArmorItems.COBALT_CHESTPLATE, ModArmorItems.COBALT_HELMET)
            .add(ModArmorItems.MITHRIL_BOOTS, ModArmorItems.MITHRIL_LEGGINGS, ModArmorItems.MITHRIL_CHESTPLATE, ModArmorItems.MITHRIL_HELMET)
            .add(ModArmorItems.ORICHALCUM_BOOTS, ModArmorItems.ORICHALCUM_LEGGINGS, ModArmorItems.ORICHALCUM_CHESTPLATE, ModArmorItems.ORICHALCUM_HELMET)
            .add(ModArmorItems.BRONZE_BOOTS, ModArmorItems.BRONZE_LEGGINGS, ModArmorItems.BRONZE_CHESTPLATE, ModArmorItems.BRONZE_HELMET)
            .add(ModArmorItems.BRASS_BOOTS, ModArmorItems.BRASS_LEGGINGS, ModArmorItems.BRASS_CHESTPLATE, ModArmorItems.BRASS_HELMET)
            .add(ModArmorItems.STEEL_BOOTS, ModArmorItems.STEEL_LEGGINGS, ModArmorItems.STEEL_CHESTPLATE, ModArmorItems.STEEL_HELMET)
    }

    private fun pickaxes() {
        getOrCreateTagBuilder(ModItemTags.PICKAXES)
            .add(ModTools.BONE_PICKAXE)
            .add(ModTools.FLINT_PICKAXE)
            .add(ModTools.COPPER_PICKAXE)
            .add(ModTools.BRONZE_PICKAXE)
            .add(ModTools.STEEL_PICKAXE)
            .add(ModTools.SILVER_PICKAXE)
            .add(ModTools.PLATINUM_PICKAXE)
            .add(ModTools.TITANIUM_PICKAXE)
            .add(ModTools.COBALT_PICKAXE)
            .add(ModTools.MITHRIL_PICKAXE)
            .add(ModTools.ORICHALCUM_PICKAXE)
            .add(ModTools.RUBY_PICKAXE)
            .add(ModTools.SAPPHIRE_PICKAXE)
            .add(ModTools.TOPAZ_PICKAXE)
    }
}