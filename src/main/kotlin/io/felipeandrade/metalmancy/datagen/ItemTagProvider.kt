package io.felipeandrade.metalmancy.datagen

import io.felipeandrade.metalmancy.ModItemTags
import io.felipeandrade.metalmancy.items.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider
import net.minecraft.item.Items
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import java.util.concurrent.CompletableFuture

class ItemTagProvider(
    output: FabricDataOutput, completableFuture: CompletableFuture<WrapperLookup>
) : ItemTagProvider(output, completableFuture) {
    override fun configure(arg: WrapperLookup) {
        materialItems()
        ironAlternatives()
//        pickaxes()
//        armorTrims()
    }


    private fun materialItems() {

        // Vanilla
        getOrCreateTagBuilder(ModItemTags.COPPER_INGOTS)
            .add(Items.COPPER_INGOT)
        getOrCreateTagBuilder(ModItemTags.COPPER_NUGGETS)
            .add(ModItems.COPPER_NUGGET)

        // Metals
        getOrCreateTagBuilder(ModItemTags.TIN_INGOTS)
            .add(ModItems.TIN_INGOT)
        getOrCreateTagBuilder(ModItemTags.ZINC_INGOTS)
            .add(ModItems.ZINC_INGOT)
        getOrCreateTagBuilder(ModItemTags.SILVER_INGOTS)
            .add(ModItems.SILVER_INGOT)
        getOrCreateTagBuilder(ModItemTags.PLATINUM_INGOTS)
            .add(ModItems.PLATINUM_INGOT)
        getOrCreateTagBuilder(ModItemTags.TITANIUM_INGOTS)
            .add(ModItems.TITANIUM_INGOT)
        getOrCreateTagBuilder(ModItemTags.COBALT_INGOTS)
            .add(ModItems.COBALT_INGOT)
        getOrCreateTagBuilder(ModItemTags.MITHRIL_INGOTS)
            .add(ModItems.MITHRIL_INGOT)
        getOrCreateTagBuilder(ModItemTags.ORICHALCUM_INGOTS)
            .add(ModItems.ORICHALCUM_INGOT)
        
        getOrCreateTagBuilder(ModItemTags.TIN_NUGGETS)
            .add(ModItems.TIN_NUGGET)
        getOrCreateTagBuilder(ModItemTags.ZINC_NUGGETS)
            .add(ModItems.ZINC_NUGGET)
        getOrCreateTagBuilder(ModItemTags.SILVER_NUGGETS)
            .add(ModItems.SILVER_NUGGET)
        getOrCreateTagBuilder(ModItemTags.PLATINUM_NUGGETS)
            .add(ModItems.PLATINUM_NUGGET)
        getOrCreateTagBuilder(ModItemTags.TITANIUM_NUGGETS)
            .add(ModItems.TITANIUM_NUGGET)
        getOrCreateTagBuilder(ModItemTags.COBALT_NUGGETS)
            .add(ModItems.COBALT_NUGGET)
        getOrCreateTagBuilder(ModItemTags.MITHRIL_NUGGETS)
            .add(ModItems.MITHRIL_NUGGET)
        getOrCreateTagBuilder(ModItemTags.ORICHALCUM_NUGGETS)
            .add(ModItems.ORICHALCUM_NUGGET)

        // Alloys
        getOrCreateTagBuilder(ModItemTags.BRONZE_INGOTS)
            .add(ModItems.BRONZE_INGOT)
        getOrCreateTagBuilder(ModItemTags.BRASS_INGOTS)
            .add(ModItems.BRASS_INGOT)
        getOrCreateTagBuilder(ModItemTags.STEEL_INGOTS)
            .add(ModItems.STEEL_INGOT)
        
        getOrCreateTagBuilder(ModItemTags.BRONZE_NUGGETS)
            .add(ModItems.BRONZE_NUGGET)
        getOrCreateTagBuilder(ModItemTags.BRASS_NUGGETS)
            .add(ModItems.BRASS_NUGGET)
        getOrCreateTagBuilder(ModItemTags.STEEL_NUGGETS)
            .add(ModItems.STEEL_NUGGET)

    }

    private fun ironAlternatives() {
        getOrCreateTagBuilder(ModItemTags.IRON_ALTERNATIVE)
            .forceAddTag(ModItemTags.BRASS_INGOTS)
            .forceAddTag(ModItemTags.BRONZE_INGOTS)
            .forceAddTag(ModItemTags.SILVER_INGOTS)
    }
}