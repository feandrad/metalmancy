package io.felipeandrade.metalmancy.fluid

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Blocks
import net.minecraft.block.FluidBlock
import net.minecraft.item.BucketItem
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

class ModFluids {
    companion object {

        val STILL_ESSENCE = Registry.register(Registries.FLUID, Identifier(MOD_ID, "essence"), EssenceStill())
        val FLOWING_ESSENCE = Registry.register(Registries.FLUID, Identifier(MOD_ID, "flowing_essence"), EssenceFlowing())

        val ESSENCE = Registry.register(
            Registries.BLOCK, Identifier(MOD_ID, "essence"), FluidBlock(
                STILL_ESSENCE, FabricBlockSettings.copy(Blocks.WATER)
            )
        )
        val ESSENCE_BUCKET = Registry.register(
            Registries.ITEM, Identifier(MOD_ID, "essence_bucket"),
            BucketItem(STILL_ESSENCE, Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1))
        )

        fun initialize() {}
    }
}