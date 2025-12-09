package io.felipeandrade.metalmancy.fabric.platform

import io.felipeandrade.metalmancy.platform.PlatformHelper
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.tags.BlockTags
// import net.minecraft.world.item.DiggerItem
import net.minecraft.world.item.Item
// import net.minecraft.world.item.SwordItem
// import net.minecraft.world.item.AxeItem
// import net.minecraft.world.item.PickaxeItem
// import net.minecraft.world.item.ShovelItem
// import net.minecraft.world.item.HoeItem
import net.minecraft.world.item.ToolMaterial
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup

class FabricPlatformHelper : PlatformHelper {
    override fun <T : BlockEntity> createBlockEntityType(
        factory: (BlockPos, BlockState) -> T,
        vararg blocks: Block
    ): BlockEntityType<T> {
        return FabricBlockEntityTypeBuilder.create(
            FabricBlockEntityTypeBuilder.Factory(factory),
            *blocks
        ).build()
    }

    override fun createSword(tier: ToolMaterial, attackDamage: Int, attackSpeed: Float, properties: Item.Properties): Item {
        return Item(properties)
    }

    override fun createAxe(tier: ToolMaterial, attackDamage: Float, attackSpeed: Float, properties: Item.Properties): Item {
        return Item(properties)
    }

    override fun createPickaxe(tier: ToolMaterial, attackDamage: Int, attackSpeed: Float, properties: Item.Properties): Item {
        return Item(properties)
    }

    override fun createShovel(tier: ToolMaterial, attackDamage: Float, attackSpeed: Float, properties: Item.Properties): Item {
        return Item(properties)
    }

    override fun createHoe(tier: ToolMaterial, attackDamage: Int, attackSpeed: Float, properties: Item.Properties): Item {
        return Item(properties)
    }
}
