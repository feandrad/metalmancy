package io.felipeandrade.metalmancy.registry

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType

object ModBlockEntities {
    val BLOCK_ENTITIES: DeferredRegister<BlockEntityType<*>> =
        DeferredRegister.create(Metalmancy.MOD_ID, Registries.BLOCK_ENTITY_TYPE)

    val CALCINATOR: RegistrySupplier<BlockEntityType<CalcinatorBlockEntity>> =
        BLOCK_ENTITIES.register("calcinator") {
            BlockEntityType.Builder.of(::CalcinatorBlockEntity, ModBlocks.CALCINATOR.get()).build(null)
        }

    fun register() {
        BLOCK_ENTITIES.register()
    }
}
