package io.felipeandrade.metalmancy.blocks.entity

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import io.felipeandrade.metalmancy.blocks.ModBlocks
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


class ModBlockEntities {
    companion object {
        val CALCINATOR = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier(MOD_ID, "calcinator_be"),
            FabricBlockEntityTypeBuilder.create(
                { pos, state -> CalcinatorBlockEntity(pos, state) },
                ModBlocks.CALCINATOR
            ).build()
        )

        fun initialize() {
            FluidStorage.SIDED.registerForBlockEntity(
                { blockEntity, direction -> blockEntity.fluidStorage },
                CALCINATOR
            )
        }
    }
}