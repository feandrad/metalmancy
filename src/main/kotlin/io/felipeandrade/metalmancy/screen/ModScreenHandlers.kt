package io.felipeandrade.metalmancy.screen

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.block.entity.BlockEntity
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier


class ModScreenHandlers {
    companion object {
        val CALCINATOR_SCREEN_HANDLER: ScreenHandlerType<CalcinatorScreenHandler> = Registry.register(
            Registries.SCREEN_HANDLER, Identifier(MOD_ID, "calcinator"),
            ExtendedScreenHandlerType{ syncId, inventory, buf ->
                val blockEntity: BlockEntity = inventory.player.world.getBlockEntity(buf.readBlockPos())!!
                CalcinatorScreenHandler(syncId, inventory, blockEntity, ArrayPropertyDelegate(CalcinatorBlockEntity.PROPERTY_SIZE))
            }
        )

        fun initialize() {}
    }
}