package io.felipeandrade.metalmancy.menu

import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity
import io.felipeandrade.metalmancy.registry.ModMenus
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack

class CalcinatorMenu(
    syncId: Int,
    playerInventory: Inventory,
    val blockEntity: CalcinatorBlockEntity?,
    val data: ContainerData
) : AbstractContainerMenu(ModMenus.CALCINATOR_MENU.get(), syncId) {

    constructor(syncId: Int, playerInventory: Inventory, buf: FriendlyByteBuf) : this(
        syncId,
        playerInventory,
        // Client-side block entity retrieval or null if not needed for basic slots
        // Usually we read pos and get BE from world
        playerInventory.player.level().getBlockEntity(buf.readBlockPos()) as? CalcinatorBlockEntity,
        SimpleContainerData(4)
    )

    init {
        checkContainerDataCount(data, 4)
        
        // Block Entity Slots
        // Input (0)
        this.addSlot(net.minecraft.world.inventory.Slot(blockEntity as net.minecraft.world.Container, 0, 25, 17))
        // Fuel (1)
        this.addSlot(io.felipeandrade.metalmancy.menu.slot.FuelSlot(blockEntity as net.minecraft.world.Container, 1, 25, 53))
        // Container Input (2)
        this.addSlot(net.minecraft.world.inventory.Slot(blockEntity as net.minecraft.world.Container, 2, 136, 17))
        // Container Output (3)
        this.addSlot(net.minecraft.world.inventory.FurnaceResultSlot(playerInventory.player, blockEntity as net.minecraft.world.Container, 3, 136, 53))
        // Output (4)
        this.addSlot(net.minecraft.world.inventory.FurnaceResultSlot(playerInventory.player, blockEntity as net.minecraft.world.Container, 4, 80, 35))

        // Player Inventory
        for (i in 0..2) {
            for (l in 0..8) {
                this.addSlot(net.minecraft.world.inventory.Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18))
            }
        }

        // Player Hotbar
        for (i in 0..8) {
            this.addSlot(net.minecraft.world.inventory.Slot(playerInventory, i, 8 + i * 18, 142))
        }
        
        addDataSlots(data)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        // TODO: Implement quick move
        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean {
        return blockEntity?.stillValid(player) ?: true
    }

    fun isBurning(): Boolean = data.get(0) > 0
    
    fun getBurnProgress(): Int {
        val burnTime = data.get(0)
        val maxBurnTime = data.get(1)
        if (maxBurnTime == 0 || burnTime == 0) return 0
        return burnTime * 13 / maxBurnTime
    }

    fun isCrafting(): Boolean = data.get(2) > 0

    fun getCraftProgress(): Int {
        val progress = data.get(2)
        val maxProgress = data.get(3)
        if (maxProgress == 0 || progress == 0) return 0
        return progress * 24 / maxProgress
    }
}
