package io.felipeandrade.metalmancy.network

object ModNetwork {
    // val CHANNEL: NetworkChannel = NetworkManager.createNetworkChannel(Metalmancy.asResource("main"))

    fun registerAll() {
        // CHANNEL.register(FluidSyncPacket::class.java, FluidSyncPacket::encode, FluidSyncPacket::decode, FluidSyncPacket::apply)
    }

    /*
    class FluidSyncPacket(val pos: BlockPos, val fluidAmount: Long, val fluidVariant: dev.architectury.fluid.FluidStack) {
        companion object {
            fun encode(packet: FluidSyncPacket, buf: RegistryFriendlyByteBuf) {
                buf.writeBlockPos(packet.pos)
                buf.writeLong(packet.fluidAmount)
                packet.fluidVariant.write(buf)
            }

            fun decode(buf: RegistryFriendlyByteBuf): FluidSyncPacket {
                val pos = buf.readBlockPos()
                val amount = buf.readLong()
                val variant = dev.architectury.fluid.FluidStack.read(buf)
                return FluidSyncPacket(pos, amount, variant)
            }

            fun apply(packet: FluidSyncPacket, context: dev.architectury.networking.NetworkManager.PacketContext) {
                context.queue {
                    val player = context.player
                    val level = player.level()
                    if (level.isClientSide) {
                        val be = level.getBlockEntity(packet.pos)
                        if (be is CalcinatorBlockEntity) {
                            be.setFluidLevel(packet.fluidVariant, packet.fluidAmount)
                        }
                    }
                }
            }
        }
    }
    */
}
