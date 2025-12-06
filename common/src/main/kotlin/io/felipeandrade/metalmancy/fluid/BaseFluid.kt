package io.felipeandrade.metalmancy.fluid

import io.felipeandrade.metalmancy.registry.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.Item
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.material.FlowingFluid
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState

abstract class BaseFluid : FlowingFluid() {
    override fun getFlowing(): Fluid = ModFluids.FLOWING_ESSENCE.get()
    override fun getSource(): Fluid = ModFluids.STILL_ESSENCE.get()
    override fun getBucket(): Item = ModItems.ESSENCE_BOTTLE // Placeholder without full bucket item
    
    override fun animateTick(level: Level, pos: BlockPos, fluidState: FluidState, random: net.minecraft.util.RandomSource) {}
    
    override fun getTickDelay(level: LevelReader): Int = 5
    override fun getExplosionResistance(): Float = 100f
    
    override fun canBeReplacedWith(fluidState: FluidState, blockGetter: BlockGetter, blockPos: BlockPos, fluid: Fluid, direction: Direction): Boolean = false
    
    // override fun getDrop(level: LevelAccessor, pos: BlockPos, state: BlockState): Item = Items.AIR // Removed as it overrides nothing in 1.21?
    
    override fun getSlopeFindDistance(level: LevelReader): Int = 4
    override fun getDropOff(level: LevelReader): Int = 1
    
    override fun createLegacyBlock(state: FluidState): BlockState = Blocks.AIR.defaultBlockState()
    
    override fun isSame(fluid: Fluid): Boolean = fluid == source || fluid == flowing

    override fun canConvertToSource(level: ServerLevel): Boolean = false
    override fun beforeDestroyingBlock(level: LevelAccessor, pos: BlockPos, state: BlockState) {}
}

class StillEssence : BaseFluid() {
    override fun getAmount(state: FluidState): Int = 8
    override fun isSource(state: FluidState): Boolean = true
}

class FlowingEssence : BaseFluid() {
    override fun createFluidStateDefinition(builder: StateDefinition.Builder<Fluid, FluidState>) {
        super.createFluidStateDefinition(builder)
        builder.add(LEVEL)
    }
    override fun getAmount(state: FluidState): Int = state.getValue(LEVEL)
    override fun isSource(state: FluidState): Boolean = false
}
