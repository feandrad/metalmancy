package io.felipeandrade.metalmancy.items.armor

import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import io.felipeandrade.metalmancy.items.ModItems
import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.recipe.Ingredient
import net.minecraft.registry.tag.ItemTags
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import java.util.function.Supplier

class ModArmorMaterial(
    private val unlocalizedName: String,
    private val durabilityMultiplier: Int,
    private val protectionAmounts: IntArray,
    private val enchantability: Int,
    private val equipSound: SoundEvent,
    private val toughness: Float,
    private val knockbackResistance: Float,
    private val repairIngredient: Supplier<Ingredient>,
) : ArmorMaterial {

    override fun getDurability(type: ArmorItem.Type): Int = BASE_DURABILITY[type.ordinal] * durabilityMultiplier
    override fun getProtection(type: ArmorItem.Type): Int = protectionAmounts[type.ordinal]
    override fun getEnchantability(): Int = enchantability
    override fun getEquipSound(): SoundEvent = equipSound
    override fun getRepairIngredient(): Ingredient = repairIngredient.get()
    override fun getName(): String = "$MOD_ID:$unlocalizedName"
    override fun getToughness(): Float = toughness
    override fun getKnockbackResistance(): Float = knockbackResistance


    companion object {

        private val BASE_DURABILITY = intArrayOf(11, 16, 15, 13)

        val WOOL = ModArmorMaterial(
            unlocalizedName = "woolen",
            durabilityMultiplier = 4,
            protectionAmounts = intArrayOf(1, 2, 3, 1),
            enchantability = 5,
            equipSound = SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            toughness = 0f,
            knockbackResistance = 0f,
            repairIngredient = { Ingredient.fromTag(ItemTags.LOGS) }
        )

        val WOOD = ModArmorMaterial(
            unlocalizedName = "wooden",
            durabilityMultiplier = 4,
            protectionAmounts = intArrayOf(1, 2, 3, 1),
            enchantability = 5,
            equipSound = SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            toughness = 0f,
            knockbackResistance = 0f,
            repairIngredient = { Ingredient.fromTag(ItemTags.WOOL) }
        )

        val COPPER = ModArmorMaterial(
            unlocalizedName = "copper",
            durabilityMultiplier = 6,
            protectionAmounts = intArrayOf(2, 5, 6, 2),
            enchantability = 16,
            equipSound = SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
            toughness = 0f,
            knockbackResistance = 0f,
            repairIngredient = { Ingredient.ofItems(ModItems.BRASS_INGOT) }
        )

        // Metals
        val SILVER = ModArmorMaterial(
            unlocalizedName = "silver",
            durabilityMultiplier = 12,
            protectionAmounts = intArrayOf(2, 5, 6, 2),
            enchantability = 18,
            equipSound = SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
            toughness = 0f,
            knockbackResistance = 0f,
            repairIngredient = { Ingredient.ofItems(ModItems.SILVER_INGOT) }
        )

        // Alloys
        val BRONZE = ModArmorMaterial(
            unlocalizedName = "bronze",
            durabilityMultiplier = 8,
            protectionAmounts = intArrayOf(2, 5, 6, 2),
            enchantability = 14,
            equipSound = SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
            toughness = 0f,
            knockbackResistance = 0f,
            repairIngredient = { Ingredient.ofItems(ModItems.BRONZE_INGOT) }
        )

        val BRASS = ModArmorMaterial(
            unlocalizedName = "brass",
            durabilityMultiplier = 8,
            protectionAmounts = intArrayOf(2, 5, 6, 2),
            enchantability = 14,
            equipSound = SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
            toughness = 0f,
            knockbackResistance = 0f,
            repairIngredient = { Ingredient.ofItems(ModItems.BRASS_INGOT) }
        )

        val STEEL = ModArmorMaterial(
            unlocalizedName = "steel",
            durabilityMultiplier = 20,
            protectionAmounts = intArrayOf(3, 6, 8, 3),
            enchantability = 5,
            equipSound = SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
            toughness = 0.5f,
            knockbackResistance = 0.1f,
            repairIngredient = { Ingredient.ofItems(ModItems.STEEL_INGOT) }
        )
    }
}
