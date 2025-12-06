package io.felipeandrade.metalmancy.blocks.entity

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.blocks.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

object ModBlockEntities {

    val CALCINATOR: BlockEntityType<CalcinatorBlockEntity> = register(
        "calcinator",
        { pos, state -> CalcinatorBlockEntity(pos, state) },
        ModBlocks.CALCINATOR
    )

    fun registerAll() = Unit

    private fun <T : BlockEntity> register(
        name: String,
        factory: (BlockPos, BlockState) -> T,
        vararg blocks: Block
    ): BlockEntityType<T> {
        val type = Metalmancy.helper.createBlockEntityType(factory, *blocks)
        return Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Metalmancy.asResource(name),
            type
        )
    }
}
