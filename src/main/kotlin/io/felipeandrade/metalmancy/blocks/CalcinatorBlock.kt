package io.felipeandrade.metalmancy.blocks

import com.mojang.serialization.MapCodec
import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity
import io.felipeandrade.metalmancy.blocks.entity.ModBlockEntities
import net.minecraft.block.AbstractFurnaceBlock
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ItemScatterer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CalcinatorBlock(settings: Settings) : AbstractFurnaceBlock(settings) {

    companion object {
        val CODEC: MapCodec<CalcinatorBlock> = createCodec { settings: Settings -> CalcinatorBlock(settings) }
    }

    override fun getCodec(): MapCodec<out AbstractFurnaceBlock> = CODEC

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = CalcinatorBlockEntity(pos, state)

    override fun openScreen(world: World, pos: BlockPos, player: PlayerEntity) {
        val blockEntity = world.getBlockEntity(pos) as? CalcinatorBlockEntity ?: return
        player.openHandledScreen(blockEntity)
    }

    override fun onStateReplaced(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        if (state.block !== newState.block) {
            val blockEntity = world.getBlockEntity(pos)
            if (blockEntity is CalcinatorBlockEntity) {
                ItemScatterer.spawn(world, pos, blockEntity)
                world.updateComparators(pos, this)
            }
            super.onStateReplaced(state, world, pos, newState, moved)
        }
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? =  validateTicker(type, ModBlockEntities.CALCINATOR) { world1, pos2, state3, blockEntity ->
        blockEntity.tick(world1, pos2, state3)
    }
}