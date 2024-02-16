package io.felipeandrade.metalmancy.items.tools

import io.felipeandrade.metalmancy.ModItemTags
import net.fabricmc.yarn.constants.MiningLevels
import net.minecraft.item.Items
import net.minecraft.item.ToolMaterial
import net.minecraft.recipe.Ingredient

class ModToolMaterial(
    private val miningLevel: Int,
    private val itemDurability: Int,
    private val miningSpeed: Float,
    private val attackDamage: Float,
    private val enchantability: Int,
    private val repairIngredient: Ingredient,
) : ToolMaterial {

    override fun getDurability(): Int = itemDurability
    override fun getMiningSpeedMultiplier(): Float = miningSpeed
    override fun getAttackDamage(): Float = attackDamage
    override fun getMiningLevel(): Int = miningLevel
    override fun getEnchantability(): Int = enchantability
    override fun getRepairIngredient(): Ingredient = repairIngredient

    companion object {
        val BONE = ModToolMaterial(MiningLevels.WOOD, 59, 2.0f, 0f, 16, Ingredient.ofItems(Items.BONE))
        val FLINT = ModToolMaterial(MiningLevels.STONE, 131, 4.0f, 1.0f, 5, Ingredient.ofItems(Items.FLINT))
        val COPPER = ModToolMaterial(MiningLevels.STONE, 160, 5.0f, 1.6f, 16, Ingredient.fromTag(ModItemTags.COPPER_INGOTS))

        val SILVER = ModToolMaterial(MiningLevels.IRON, 250, 6.0f, 2.0f, 14, Ingredient.fromTag(ModItemTags.SILVER_INGOTS))
        val PLATINUM = ModToolMaterial(MiningLevels.DIAMOND, 1561, 8.0f, 3.0f, 22, Ingredient.fromTag(ModItemTags.PLATINUM_INGOTS))
        val TITANIUM = ModToolMaterial(MiningLevels.DIAMOND, 1561, 8.0f, 3.0f, 1, Ingredient.fromTag(ModItemTags.TITANIUM_INGOTS))
        val COBALT = ModToolMaterial(MiningLevels.DIAMOND, 1561, 8.0f, 3.0f, 10, Ingredient.fromTag(ModItemTags.COBALT_INGOTS))
        val MITHRIL = ModToolMaterial(MiningLevels.NETHERITE, 2031, 9.0f, 4.0f, 22, Ingredient.fromTag(ModItemTags.MITHRIL_INGOTS))
        val ORICHALCUM = ModToolMaterial(MiningLevels.NETHERITE, 2031, 9.0f, 4.0f, 1, Ingredient.fromTag(ModItemTags.ORICHALCUM_INGOTS))

        val BRONZE = ModToolMaterial(MiningLevels.IRON, 250, 6.0f, 2.0f, 14, Ingredient.fromTag(ModItemTags.BRONZE_INGOTS))
        val BRASS = ModToolMaterial(MiningLevels.IRON, 250, 6.0f, 2.0f, 14, Ingredient.fromTag(ModItemTags.BRASS_INGOTS))
        val STEEL = ModToolMaterial(MiningLevels.DIAMOND, 500, 8.0f, 3.0f, 1, Ingredient.fromTag(ModItemTags.STEEL_INGOTS))

        val RUBY = ModToolMaterial(MiningLevels.DIAMOND, 500, 8.0f, 3.0f, 1, Ingredient.fromTag(ModItemTags.RUBYS))
        val SAPPHIRE = ModToolMaterial(MiningLevels.DIAMOND, 500, 8.0f, 3.0f, 1, Ingredient.fromTag(ModItemTags.SAPPHIRES))
        val TOPAZ = ModToolMaterial(MiningLevels.DIAMOND, 500, 8.0f, 3.0f, 1, Ingredient.fromTag(ModItemTags.TOPAZES))

        fun initialize() {}
    }
}