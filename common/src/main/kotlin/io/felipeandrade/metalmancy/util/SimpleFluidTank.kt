package io.felipeandrade.metalmancy.util

import dev.architectury.fluid.FluidStack
import net.minecraft.nbt.CompoundTag

class SimpleFluidTank(val capacity: Long) {
    var fluid: FluidStack = FluidStack.empty()

    fun getFluidAmount(): Long = fluid.amount

    fun setFluid(stack: FluidStack) {
        this.fluid = stack
    }

    fun isEmpty(): Boolean = fluid.isEmpty

    fun isFull(): Boolean = fluid.amount >= capacity

    fun read(tag: CompoundTag) {
        if (tag.contains("Fluid")) {
            fluid = FluidStack.CODEC.decode(net.minecraft.nbt.NbtOps.INSTANCE, tag.get("Fluid")).result().orElseThrow().first
        } else {
             fluid = FluidStack.empty()
        }
    }

    fun write(tag: CompoundTag) {
        FluidStack.CODEC.encodeStart(net.minecraft.nbt.NbtOps.INSTANCE, fluid).result().ifPresent {
            tag.put("Fluid", it)
        }
    }
    
    // TODO: Add insert/extract logic if needed for automation
}
