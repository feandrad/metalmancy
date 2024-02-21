package io.felipeandrade.metalmancy.network

import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity
import io.felipeandrade.metalmancy.screen.CalcinatorScreenHandler
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.network.PacketByteBuf

object FluidSyncS2CPacket {
    fun receive(client: MinecraftClient, handler: ClientPlayNetworkHandler, buf: PacketByteBuf, responseSender: PacketSender) {
        val variant = FluidVariant.fromPacket(buf)
        val fluidLevel = buf.readLong()
        val pos = buf.readBlockPos()

        val blockEntity = client.world!!.getBlockEntity(pos)

        if ( blockEntity is CalcinatorBlockEntity) {
            blockEntity.setFluidLevel(variant, fluidLevel)
            val screenHandler = client.player!!.currentScreenHandler
            if ( screenHandler is CalcinatorScreenHandler && screenHandler.blockEntity.pos == pos) {
                screenHandler.setFluid(variant, fluidLevel)
            }
        }
    }
}