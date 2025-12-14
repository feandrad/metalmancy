package io.felipeandrade.metalmancy.fabric.platform

import io.felipeandrade.metalmancy.platform.PlatformHelper
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.Item
import net.minecraft.world.item.ToolMaterial
import net.minecraft.world.item.component.ItemAttributeModifiers
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
        val attr = ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (attackDamage + tier.attackDamageBonus()).toDouble(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed.toDouble(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.SWEEPING_DAMAGE_RATIO, AttributeModifier(ResourceLocation.withDefaultNamespace("base_sweeping_damage_ratio"), 0.75, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build()
        // We still call properties.sword() to ensure other components (like TOOL) are set, but we override attributes
        return Item(properties.sword(tier, attackDamage.toFloat(), attackSpeed).attributes(attr))
    }

    override fun createAxe(tier: ToolMaterial, attackDamage: Float, attackSpeed: Float, properties: Item.Properties): Item {
        val attr = ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (attackDamage + tier.attackDamageBonus()).toDouble(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed.toDouble(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build()
        return Item(properties.axe(tier, attackDamage, attackSpeed).attributes(attr))
    }

    override fun createPickaxe(tier: ToolMaterial, attackDamage: Int, attackSpeed: Float, properties: Item.Properties): Item {
        val attr = ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (attackDamage + tier.attackDamageBonus()).toDouble(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed.toDouble(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build()
        return Item(properties.pickaxe(tier, attackDamage.toFloat(), attackSpeed).attributes(attr))
    }

    override fun createShovel(tier: ToolMaterial, attackDamage: Float, attackSpeed: Float, properties: Item.Properties): Item {
        val attr = ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (attackDamage + tier.attackDamageBonus()).toDouble(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed.toDouble(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build()
        return Item(properties.shovel(tier, attackDamage, attackSpeed).attributes(attr))
    }

    override fun createHoe(tier: ToolMaterial, attackDamage: Int, attackSpeed: Float, properties: Item.Properties): Item {
        val attr = ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (attackDamage + tier.attackDamageBonus()).toDouble(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed.toDouble(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build()
        return Item(properties.hoe(tier, attackDamage.toFloat(), attackSpeed).attributes(attr))
    }
}