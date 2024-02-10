package io.felipeandrade.metalmancy.datagen

import io.felipeandrade.metalmancy.ModItemTags.IRON_ALTERNATIVE
import io.felipeandrade.metalmancy.items.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import java.util.concurrent.CompletableFuture

class ItemTagProvider(
    output: FabricDataOutput, completableFuture: CompletableFuture<WrapperLookup>
) : ItemTagProvider(output, completableFuture) {
    override fun configure(arg: WrapperLookup) {
        ironAlternatives()
//        pickaxes()
//        armorTrims()
    }

    private fun ironAlternatives() {
        getOrCreateTagBuilder(IRON_ALTERNATIVE)
            .add(ModItems.TIN_INGOT)
            .add(ModItems.ZINC_INGOT)
            .add(ModItems.BRASS_INGOT)
            .add(ModItems.BRONZE_INGOT)
            .add(ModItems.SILVER_INGOT)
    }
}