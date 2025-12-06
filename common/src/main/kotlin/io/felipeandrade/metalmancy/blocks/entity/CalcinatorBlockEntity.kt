package io.felipeandrade.metalmancy.blocks.entity

import dev.architectury.fluid.FluidStack
import dev.architectury.registry.menu.ExtendedMenuProvider
// import dev.architectury.networking.PlayerLookup
import io.felipeandrade.metalmancy.fluid.ModFluids
import io.felipeandrade.metalmancy.menu.CalcinatorMenu
import io.felipeandrade.metalmancy.recipe.CalcinatingRecipe
import io.felipeandrade.metalmancy.registry.ModRecipes
import io.felipeandrade.metalmancy.util.SimpleFluidTank
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.block.AbstractFurnaceBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class CalcinatorBlockEntity(pos: BlockPos, state: BlockState) : 
    BlockEntity(ModBlockEntities.CALCINATOR, pos, state),
    ExtendedMenuProvider, Container {

    // Inventory: 0=Input, 1=Fuel, 2=ContainerInput, 3=ContainerOutput, 4=Output
    private val inventory = SimpleContainer(5)
    val fluidTank = SimpleFluidTank(16000) // 16 buckets (1000mb each)

    var burnTime = 0
    var maxBurnTime = 0
    var progress = 0
    var maxProgress = 300

    protected val dataAccess: ContainerData = object : ContainerData {
        override fun get(index: Int): Int = when (index) {
            0 -> burnTime
            1 -> maxBurnTime
            2 -> progress
            3 -> maxProgress
            else -> 0
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> burnTime = value
                1 -> maxBurnTime = value
                2 -> progress = value
                3 -> maxProgress = value
            }
        }

        override fun getCount(): Int = 4
    }

/*
    override fun saveAdditional(tag: CompoundTag, registries: net.minecraft.core.HolderLookup.Provider) {
        super.saveAdditional(tag, registries)
        tag.putInt("BurnTime", burnTime)
        tag.putInt("MaxBurnTime", maxBurnTime)
        tag.putInt("Progress", progress)
        val itemsTag = net.minecraft.nbt.ListTag()
        for (i in 0 until inventory.containerSize) {
            val stack = inventory.getItem(i)
            if (!stack.isEmpty) {
                val itemTag = CompoundTag()
                itemTag.putByte("Slot", i.toByte())
                stack.save(registries, itemTag)
                itemsTag.add(itemTag)
            }
        }
        tag.put("Inventory", itemsTag)
        val fluidTag = CompoundTag()
        fluidTank.write(fluidTag)
        tag.put("Fluid", fluidTag)
    }

    override fun loadAdditional(tag: CompoundTag, registries: net.minecraft.core.HolderLookup.Provider) {
        super.loadAdditional(tag, registries)
        burnTime = tag.getInt("BurnTime")
        maxBurnTime = tag.getInt("MaxBurnTime")
        progress = tag.getInt("Progress")
        if (tag.contains("Inventory")) {
            val itemsTag = tag.getList("Inventory", 10)
            for (i in 0 until itemsTag.size) {
                val itemTag = itemsTag.getCompound(i)
                val slot = itemTag.getByte("Slot").toInt()
                if (slot in 0 until inventory.containerSize) {
                    inventory.setItem(slot, ItemStack.parseOptional(registries, itemTag))
                }
            }
        }
        if (tag.contains("Fluid")) {
            fluidTank.read(tag.getCompound("Fluid"))
        }
    }
*/

    fun tick(level: net.minecraft.world.level.Level, pos: BlockPos, state: BlockState) {
        if (level.isClientSide) return

        var isDirty = false
        val isBurning = burnTime > 0

        if (isBurning) {
            burnTime--
        }

        val recipeHolder = getCurrentRecipe()
        val hasRecipe = recipeHolder != null && canCraft(recipeHolder.value)

        if (burnTime <= 0 && hasRecipe && !fluidTank.isFull()) {
            // Consume fuel
            val fuelStack = inventory.getItem(1)
            if (!fuelStack.isEmpty) {
                val fuelValue = 1600 // TODO: Get actual fuel value
                burnTime = fuelValue
                maxBurnTime = fuelValue
                fuelStack.shrink(1)
                isDirty = true
            }
        }

        if (burnTime > 0 && hasRecipe) {
            progress++
            if (progress >= maxProgress) {
                craftItem(recipeHolder!!.value)
                progress = 0
                isDirty = true
            }
        } else {
            if (progress > 0) {
                progress = 0
                isDirty = true
            }
        }

        if (isBurning != (burnTime > 0)) {
            isDirty = true
            level.setBlock(pos, state.setValue(AbstractFurnaceBlock.LIT, burnTime > 0), 3)
        }

        // Fluid container filling logic
        if (processFluidContainer()) {
            isDirty = true
        }

        if (isDirty) {
            setChanged()
            syncFluid()
        }
    }

    private fun getCurrentRecipe(): RecipeHolder<CalcinatingRecipe>? {
        val input = inventory.getItem(0)
        if (input.isEmpty || level !is ServerLevel) return null

        val serverLevel = level as ServerLevel
        val recipeManager = serverLevel.server.recipeManager

        return recipeManager
            .getRecipeFor(
                ModRecipes.CALCINATING_TYPE.get() as RecipeType<CalcinatingRecipe>,
                asRecipeInput(),
                serverLevel
            )
            .orElse(null)
    }


    private fun canCraft(recipe: CalcinatingRecipe): Boolean {
        val result = recipe.output
        val currentOutput = inventory.getItem(4)
        if (currentOutput.isEmpty) return true
        if (!ItemStack.isSameItemSameComponents(currentOutput, result)) return false
        return currentOutput.count + result.count <= currentOutput.maxStackSize
    }

    private fun craftItem(recipe: CalcinatingRecipe) {
        inventory.getItem(0).shrink(1)
        val result = recipe.output.copy()
        val currentOutput = inventory.getItem(4)
        if (currentOutput.isEmpty) {
            inventory.setItem(4, result)
        } else {
            currentOutput.grow(result.count)
        }
        
        // Add Essence
        val fluidToAdd = FluidStack.create(ModFluids.STILL_ESSENCE.get(), recipe.essence)
        if (fluidTank.fluid.isEmpty) {
            fluidTank.fluid = fluidToAdd
        } else if (fluidTank.fluid.isFluidEqual(fluidToAdd)) {
            fluidTank.fluid.amount += fluidToAdd.amount
        }
    }

    private fun processFluidContainer(): Boolean {
        // TODO: Implement bucket/bottle filling
        return false
    }

    // Helper to create RecipeInput from this container
    private fun asRecipeInput(): net.minecraft.world.item.crafting.RecipeInput {
        return object : net.minecraft.world.item.crafting.RecipeInput {
            override fun getItem(slot: Int): ItemStack = inventory.getItem(slot)
            override fun size(): Int = inventory.containerSize
        }
    }

    fun syncFluid() {
        if (level != null && !level!!.isClientSide) {
             // val players = dev.architectury.networking.PlayerLookup.tracking(this)
             // ModNetwork.CHANNEL.sendToPlayers(ModNetwork.FluidSyncPacket(blockPos, fluidTank.getFluidAmount(), fluidTank.fluid), players)
        }
    }

    fun setFluidLevel(variant: FluidStack, amount: Long) {
        fluidTank.fluid = variant
        fluidTank.fluid.amount = amount
    }

    // Container methods
    override fun getContainerSize(): Int = 5
    override fun isEmpty(): Boolean = inventory.isEmpty
    override fun getItem(slot: Int): ItemStack = inventory.getItem(slot)
    override fun removeItem(slot: Int, amount: Int): ItemStack = inventory.removeItem(slot, amount)
    override fun removeItemNoUpdate(slot: Int): ItemStack = inventory.removeItemNoUpdate(slot)
    override fun setItem(slot: Int, stack: ItemStack) = inventory.setItem(slot, stack)
    override fun stillValid(player: Player): Boolean = Container.stillValidBlockEntity(this, player)
    override fun clearContent() = inventory.clearContent()

    // MenuProvider
    override fun createMenu(id: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
        return CalcinatorMenu(id, inventory, this, dataAccess)
    }

    override fun getDisplayName(): Component = Component.translatable("block.metalmancy.calcinator")

    // ExtendedMenuProvider
    override fun saveExtraData(buf: FriendlyByteBuf) {
        buf.writeBlockPos(blockPos)
        // fluidTank.fluid.write(buf)
    }
}
