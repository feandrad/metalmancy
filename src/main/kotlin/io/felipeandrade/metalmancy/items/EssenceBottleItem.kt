package io.felipeandrade.metalmancy.items

import net.minecraft.advancement.criterion.Criteria
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoneyBottleItem
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsage
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.world.World

class EssenceBottleItem(settings: Settings) : HoneyBottleItem(settings) {

    override fun finishUsing(stack: ItemStack, world: World, user: LivingEntity): ItemStack {
        if (user is ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(user, stack)
            user.incrementStat(Stats.USED.getOrCreateStat(this))
            user.addExperience(100)
        }

        return if (stack.isEmpty) {
            ItemStack(Items.GLASS_BOTTLE)
        } else {
            if (user is PlayerEntity && user.abilities.creativeMode.not()) {
                val itemStack = ItemStack(Items.GLASS_BOTTLE)
                if (user.inventory.insertStack(itemStack).not()) {
                    user.dropItem(itemStack, false)
                }
            }
            stack
        }
    }

    override fun getMaxUseTime(stack: ItemStack): Int = 40

    override fun getUseAction(stack: ItemStack): UseAction = UseAction.DRINK

    override fun getDrinkSound(): SoundEvent = SoundEvents.ITEM_HONEY_BOTTLE_DRINK

    override fun getEatSound(): SoundEvent = SoundEvents.ITEM_HONEY_BOTTLE_DRINK

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> =
        ItemUsage.consumeHeldItem(world, user, hand)
}