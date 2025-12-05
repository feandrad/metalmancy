package io.felipeandrade.metalmancy.registry

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.blocks.CalcinatorBlock
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

object ModBlocks {
    val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(Metalmancy.MOD_ID, Registries.BLOCK)

    val CALCINATOR: RegistrySupplier<Block> = BLOCKS.register("calcinator") {
        CalcinatorBlock(BlockBehaviour.Properties.of().strength(3.5f))
    }

    fun register() {
        BLOCKS.register()
    }
}
