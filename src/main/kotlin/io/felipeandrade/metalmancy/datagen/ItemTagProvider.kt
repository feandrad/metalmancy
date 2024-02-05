package io.felipeandrade.metalmancy.datagen

import io.felipeandrade.metalmancy.ModItemTags.IRON_ALTERNATIVE
import io.felipeandrade.metalmancy.items.BRASS_INGOT
import io.felipeandrade.metalmancy.items.BRONZE_INGOT
import io.felipeandrade.metalmancy.items.SILVER_INGOT
import io.felipeandrade.metalmancy.items.TIN_INGOT
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
            .add(TIN_INGOT)
            .add(BRASS_INGOT)
            .add(BRONZE_INGOT)
            .add(SILVER_INGOT)
    }
}