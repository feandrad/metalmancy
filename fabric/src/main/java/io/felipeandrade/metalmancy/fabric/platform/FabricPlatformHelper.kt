package io.felipeandrade.metalmancy.fabric.platform

import io.felipeandrade.metalmancy.platform.PlatformHelper
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class FabricPlatformHelper : PlatformHelper {
    override fun <T : BlockEntity> createBlockEntityType(
        factory: (BlockPos, BlockState) -> T,
        vararg blocks: Block
    ): BlockEntityType<T> {
        return FabricBlockEntityTypeBuilder.create(
            FabricBlockEntityTypeBuilder.Factory(factory),
            *blocks
        ).build()
    }
}
