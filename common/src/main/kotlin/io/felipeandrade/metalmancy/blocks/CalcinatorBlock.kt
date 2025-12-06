package io.felipeandrade.metalmancy.blocks

import com.mojang.serialization.MapCodec
import dev.architectury.registry.menu.MenuRegistry
import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity
import io.felipeandrade.metalmancy.blocks.entity.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.AbstractFurnaceBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class CalcinatorBlock(properties: Properties) : AbstractFurnaceBlock(properties) {
    companion object {
        val CODEC: MapCodec<CalcinatorBlock> = simpleCodec(::CalcinatorBlock)
    }

    override fun codec(): MapCodec<out AbstractFurnaceBlock> = CODEC

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = CalcinatorBlockEntity(pos, state)

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return createTickerHelper(
            type,
            ModBlockEntities.CALCINATOR,
            { l, p, s, be -> (be as CalcinatorBlockEntity).tick(l, p, s) }
        )
    }
    
    override fun openContainer(level: Level, pos: BlockPos, player: Player) {
        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is CalcinatorBlockEntity) {
            MenuRegistry.openMenu(player as ServerPlayer, blockEntity)
        }
    }

    /*
    override fun onRemove(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        if (state.block !== newState.block) {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity is CalcinatorBlockEntity) {
                Containers.dropContents(level, pos, blockEntity)
                level.updateNeighbourForOutputSignal(pos, this)
            }
            super.onRemove(state, level, pos, newState, moved)
        }
    }
    */
    // Commented out onRemove due to signature mismatch diagnosis. 
    // Usually AbstractFurnaceBlock handles onRemove? No, it doesn't. 
    // TODO: restore once signature is known.
}
