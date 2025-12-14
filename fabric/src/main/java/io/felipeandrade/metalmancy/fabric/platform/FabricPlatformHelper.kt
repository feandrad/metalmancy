package io.felipeandrade.metalmancy.fabric.platform

import io.felipeandrade.metalmancy.platform.PlatformHelper
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.core.BlockPos
import net.minecraft.world.item.Item
import net.minecraft.world.item.ToolMaterial
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

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
        return Item(properties.sword(tier, attackDamage.toFloat(), attackSpeed))
    }

    override fun createAxe(tier: ToolMaterial, attackDamage: Float, attackSpeed: Float, properties: Item.Properties): Item {
        return Item(properties.axe(tier, attackDamage, attackSpeed))
    }

    override fun createPickaxe(tier: ToolMaterial, attackDamage: Int, attackSpeed: Float, properties: Item.Properties): Item {
        return Item(properties.pickaxe(tier, attackDamage.toFloat(), attackSpeed))
    }

    override fun createShovel(tier: ToolMaterial, attackDamage: Float, attackSpeed: Float, properties: Item.Properties): Item {
        return Item(properties.shovel(tier, attackDamage, attackSpeed))
    }

    override fun createHoe(tier: ToolMaterial, attackDamage: Int, attackSpeed: Float, properties: Item.Properties): Item {
        return Item(properties.hoe(tier, attackDamage.toFloat(), attackSpeed))
    }
}
