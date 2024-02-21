package io.felipeandrade.metalmancy.fluid

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.Item
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView


abstract class EssenceFluid: AbstractFluid() {

    override fun getFlowing(): Fluid = ModFluids.FLOWING_ESSENCE

    override fun getStill(): Fluid = ModFluids.STILL_ESSENCE

    override fun getBucketItem(): Item = ModFluids.ESSENCE_BUCKET

    override fun toBlockState(state: FluidState): BlockState = ModFluids.ESSENCE.defaultState
        .with(Properties.LEVEL_15, getBlockStateLevel(state))

    override fun isInfinite(world: World): Boolean = false
}

class EssenceFlowing : EssenceFluid() {
    override fun appendProperties(builder: StateManager.Builder<Fluid, FluidState>) {
        super.appendProperties(builder)
        builder.add(LEVEL)
    }

    override fun getLevel(state: FluidState): Int = state.get(LEVEL)

    override fun isStill(state: FluidState): Boolean = false
}

class EssenceStill : EssenceFluid() {

    override fun getLevel(state: FluidState): Int = 8

    override fun isStill(state: FluidState): Boolean = true
}


abstract class AbstractFluid : FlowableFluid() {

    /**
     * @return whether the given fluid an instance of this fluid
     */
    override fun matchesType(fluid: Fluid): Boolean {
        return fluid === still || fluid === flowing
    }

    protected val isInfinite: Boolean
        /**
         * @return whether the fluid is infinite (which means can be infinitely created like water). In vanilla, it depends on the game rule.
         */
        get() = false

    /**
     * Perform actions when the fluid flows into a replaceable block. Water drops
     * the block's loot table. Lava plays the "block.lava.extinguish" sound.
     */
    override fun beforeBreakingBlock(world: WorldAccess, pos: BlockPos, state: BlockState) {
        val blockEntity = if (state.hasBlockEntity()) world.getBlockEntity(pos) else null
        Block.dropStacks(state, world, pos, blockEntity)
    }

    /**
     * Lava returns true if it's FluidState is above a certain height and the
     * Fluid is Water.
     *
     * @return whether the given Fluid can flow into this FluidState
     */
    override fun canBeReplacedWith(
        fluidState: FluidState,
        blockView: BlockView,
        blockPos: BlockPos,
        fluid: Fluid,
        direction: Direction
    ): Boolean = false

    /**
     * Possibly related to the distance checks for flowing into nearby holes?
     * Water returns 4. Lava returns 2 in the Overworld and 4 in the Nether.
     */
    override fun getFlowSpeed(worldView: WorldView): Int = 4

    /**
     * Water returns 1. Lava returns 2 in the Overworld and 1 in the Nether.
     */
    override fun getLevelDecreasePerBlock(worldView: WorldView): Int = 1

    /**
     * Water returns 5. Lava returns 30 in the Overworld and 10 in the Nether.
     */
    override fun getTickRate(worldView: WorldView): Int = 5

    /**
     * Water and Lava both return 100.0F.
     */
    override fun getBlastResistance(): Float = 100.0f
}