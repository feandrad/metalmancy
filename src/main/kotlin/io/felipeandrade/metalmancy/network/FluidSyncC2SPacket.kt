package io.felipeandrade.metalmancy.network

import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity
import io.felipeandrade.metalmancy.screen.CalcinatorScreenHandler
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity

/*
server – the server
player – the player
handler – the network handler that received this packet, representing the player/client who sent the packet
buf – the payload of the packet
responseSender – the packet sender
 */
object FluidSyncC2SPacket {
    fun receive(
        server: MinecraftServer,
        player: ServerPlayerEntity,
        handler: ServerPlayNetworkHandler,
        buf: PacketByteBuf,
        sender: PacketSender
    ) {
        val variant = FluidVariant.fromPacket(buf)
        val fluidLevel = buf.readLong()
        val pos = buf.readBlockPos()

        val blockEntity = player.world!!.getBlockEntity(pos)

        if (blockEntity is CalcinatorBlockEntity) {
            blockEntity.setFluidLevel(variant, fluidLevel)
            val screenHandler = player.currentScreenHandler
            if (screenHandler is CalcinatorScreenHandler && screenHandler.blockEntity.pos == pos) {
                screenHandler.setFluid(variant, fluidLevel)
            }
        }
    }
}