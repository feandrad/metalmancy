package io.felipeandrade.metalmancy.blocks

import com.mojang.serialization.MapCodec
import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity
import net.minecraft.block.AbstractFurnaceBlock
import net.minecraft.block.BlockState
import net.minecraft.block.SmokerBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CalcinatorBlock(settings: Settings) : AbstractFurnaceBlock(settings) {

    companion object {
        val CODEC: MapCodec<SmokerBlock> = createCodec { settings: Settings -> SmokerBlock(settings) }
    }

    override fun getCodec(): MapCodec<out AbstractFurnaceBlock> = CODEC

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = CalcinatorBlockEntity(pos, state)

    override fun openScreen(world: World, pos: BlockPos, player: PlayerEntity) {
        val blockEntity = world.getBlockEntity(pos)
        if (blockEntity is CalcinatorBlockEntity) {
            player.openHandledScreen(blockEntity)
        }
    }
}