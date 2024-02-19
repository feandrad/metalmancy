package io.felipeandrade.metalmancy.blocks.entity

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import io.felipeandrade.metalmancy.items.ModItems
import io.felipeandrade.metalmancy.screen.CalcinatorScreenHandler
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World


class CalcinatorBlockEntity(
    pos: BlockPos,
    state: BlockState
) : ModMachine(ModBlockEntities.CALCINATOR, pos, state), ExtendedScreenHandlerFactory {

    private var progress = 0
    private var maxProgress = 72
    private var experience = 0
    private var maxExperience = 16000
    private var fuel = 0

    private val inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY)

    private val propertyDelegate: PropertyDelegate = object : PropertyDelegate {
        override fun get(index: Int): Int = when (index) {
            0 -> this@CalcinatorBlockEntity.progress
            1 -> this@CalcinatorBlockEntity.maxProgress
            2 -> this@CalcinatorBlockEntity.experience
            3 -> this@CalcinatorBlockEntity.maxExperience
            else -> 0
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> this@CalcinatorBlockEntity.progress = value
                1 -> this@CalcinatorBlockEntity.maxProgress = value
                2 -> this@CalcinatorBlockEntity.experience = value
                3 -> this@CalcinatorBlockEntity.maxExperience = value
                else -> {}
            }
        }

        override fun size(): Int = PROPERTY_SIZE
    }

    companion object {
        const val NBT_CALCINATOR_PROGRESS = "NBT_CALCINATOR_PROGRESS"
        const val NBT_CALCINATOR_EXP = "NBT_CALCINATOR_EXP"
        const val INVENTORY_SIZE = 5
        const val PROPERTY_SIZE = 4

        const val INPUT_SLOT = 0
        const val FUEL_SLOT = 1
        const val CONTAINER_INPUT_SLOT = 2
        const val CONTAINER_OUTPUT_SLOT = 3
        const val OUTPUT_SLOT = 4
    }

    override fun getItems(): DefaultedList<ItemStack> = inventory

    override fun getContainerName(): Text = Text.translatable(Identifier(MOD_ID, "calcinator").toTranslationKey())

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory): ScreenHandler {
        TODO("Not yet implemented")
    }

    override fun writeScreenOpeningData(serverPlayerEntity: ServerPlayerEntity, packetByteBuf: PacketByteBuf) {
        packetByteBuf.writeBlockPos(pos)
    }

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler {
        return CalcinatorScreenHandler(syncId, playerInventory, this, propertyDelegate)
    }

    fun tick(world: World, pos: BlockPos?, state: BlockState?) {
        if (world.isClient()) return

        if (!hasRecipe(ModItems.ESSENCE_DUST)) {
            resetProgress()
            markDirty(world, pos, state)
            return
        }

        increaseCraftProgress()
        markDirty(world, pos, state)
        if (hasCraftingFinished()) {
            craftItem()
            resetProgress()
        }
    }

//    fun tick(world: World, pos: BlockPos?, state: BlockState, blockEntity: AbstractFurnaceBlockEntity) {
//        var state = state
//        val bl4: Boolean
//        val bl = blockEntity.isBurning()
//        var bl2 = false
//        if (blockEntity.isBurning()) {
//            --blockEntity.burnTime
//        }
//        val itemStack = blockEntity.inventory[1]
//        val bl3 = !blockEntity.inventory[0].isEmpty
//        bl4 = !itemStack.isEmpty
//        val bl5 = bl4
//        if (blockEntity.isBurning() || bl4 && bl3) {
//            val recipeEntry = if (bl3) blockEntity.matchGetter.getFirstMatch(blockEntity, world).orElse(null) else null
//            val i = blockEntity.maxCountPerStack
//            if (!blockEntity.isBurning() && AbstractFurnaceBlockEntity.canAcceptRecipeOutput(
//                    world.registryManager,
//                    recipeEntry,
//                    blockEntity.inventory,
//                    i
//                )
//            ) {
//                blockEntity.burnTime = blockEntity.getFuelTime(itemStack)
//                blockEntity.fuelTime = blockEntity.burnTime
//                if (blockEntity.isBurning()) {
//                    bl2 = true
//                    if (bl4) {
//                        val item = itemStack.item
//                        itemStack.decrement(1)
//                        if (itemStack.isEmpty) {
//                            val item2 = item.recipeRemainder
//                            blockEntity.inventory[1] = item2?.let { ItemStack(it) } ?: ItemStack.EMPTY
//                        }
//                    }
//                }
//            }
//            if (blockEntity.isBurning() && AbstractFurnaceBlockEntity.canAcceptRecipeOutput(
//                    world.registryManager,
//                    recipeEntry,
//                    blockEntity.inventory,
//                    i
//                )
//            ) {
//                ++blockEntity.cookTime
//                if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
//                    blockEntity.cookTime = 0
//                    blockEntity.cookTimeTotal = AbstractFurnaceBlockEntity.getCookTime(world, blockEntity)
//                    if (AbstractFurnaceBlockEntity.craftRecipe(
//                            world.registryManager,
//                            recipeEntry,
//                            blockEntity.inventory,
//                            i
//                        )
//                    ) {
//                        blockEntity.lastRecipe = recipeEntry
//                    }
//                    bl2 = true
//                }
//            } else {
//                blockEntity.cookTime = 0
//            }
//        } else if (!blockEntity.isBurning() && blockEntity.cookTime > 0) {
//            blockEntity.cookTime = MathHelper.clamp(blockEntity.cookTime - 2, 0, blockEntity.cookTimeTotal)
//        }
//        if (bl != blockEntity.isBurning()) {
//            bl2 = true
//            state = state.with(AbstractFurnaceBlock.LIT, blockEntity.isBurning()) as BlockState
//            world.setBlockState(pos, state, Block.NOTIFY_ALL)
//        }
//        if (bl2) {
//            markDirty(world, pos, state)
//        }
//    }
//

    private fun craftItem() {
        removeStack(INPUT_SLOT, 1)
        setStack(OUTPUT_SLOT, ItemStack(ModItems.ESSENCE_DUST, getStack(OUTPUT_SLOT).count + 1))
        experience += 1
    }

    private fun hasRecipe(result: Item) : Boolean {
        return !getStack(INPUT_SLOT).isEmpty && isOutputAvailable(result)
    }

    private fun isOutputAvailable(result: Item): Boolean = getStack(OUTPUT_SLOT).isEmpty ||
            getStack(OUTPUT_SLOT).item == result && getStack(OUTPUT_SLOT).count < getStack(OUTPUT_SLOT).maxCount

    private fun hasCraftingFinished(): Boolean = progress >= maxProgress

    private fun resetProgress() {
        progress = 0
    }

    private fun increaseCraftProgress() {
        progress++
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        Inventories.writeNbt(nbt, inventory)
        nbt.putInt(NBT_CALCINATOR_PROGRESS, progress)
        nbt.putInt(NBT_CALCINATOR_EXP, experience)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        Inventories.readNbt(nbt, inventory)
        progress = nbt.getInt(NBT_CALCINATOR_PROGRESS)
        experience = nbt.getInt(NBT_CALCINATOR_EXP)
    }
}