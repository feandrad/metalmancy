package io.felipeandrade.metalmancy.items

import io.felipeandrade.metalmancy.registry.material.Material
import net.minecraft.world.item.ToolMaterial

data class ToolStats(
    val material: Material,
    val tier: ToolMaterial,
    val durability: Int,
    val speed: Float,
    val damage: Float,
    val enchantment: Int,
)
