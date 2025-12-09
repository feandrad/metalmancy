package io.felipeandrade.metalmancy.platform

import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
// import net.minecraft.world.item.Tier
import net.minecraft.world.item.ToolMaterial

interface PlatformHelper {
    fun <T : BlockEntity> createBlockEntityType(
        factory: (BlockPos, BlockState) -> T,
        vararg blocks: Block
    ): BlockEntityType<T>

    fun createSword(tier: ToolMaterial, attackDamage: Int, attackSpeed: Float, properties: Item.Properties): Item
    fun createAxe(tier: ToolMaterial, attackDamage: Float, attackSpeed: Float, properties: Item.Properties): Item
    fun createPickaxe(tier: ToolMaterial, attackDamage: Int, attackSpeed: Float, properties: Item.Properties): Item
    fun createShovel(tier: ToolMaterial, attackDamage: Float, attackSpeed: Float, properties: Item.Properties): Item
    fun createHoe(tier: ToolMaterial, attackDamage: Int, attackSpeed: Float, properties: Item.Properties): Item
}
