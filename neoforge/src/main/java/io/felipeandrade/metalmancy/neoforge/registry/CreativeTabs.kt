package io.felipeandrade.metalmancy.neoforge.registry

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.items.MaterialItems
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.ItemStack
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent

@EventBusSubscriber(modid = Metalmancy.MOD_ID)
object CreativeTabs {
    
    @JvmStatic
    @SubscribeEvent
    fun buildContents(event: BuildCreativeModeTabContentsEvent) {
        if (event.tabKey == CreativeModeTabs.NATURAL_BLOCKS) {
            // Add gems after emerald ore
            event.acceptAll(
                MaterialItems.RUBY.values.map { ItemStack(it) } +
                MaterialItems.SAPPHIRE.values.map { ItemStack(it) } +
                MaterialItems.TOPAZ.values.map { ItemStack(it) }
            )
            
            // Add copper-like metals
            event.acceptAll(
                MaterialItems.TIN.values.map { ItemStack(it) } +
                MaterialItems.ZINC.values.map { ItemStack(it) }
            )
            
            // Add iron-like metals
            event.acceptAll(
                MaterialItems.ALUMINUM.values.map { ItemStack(it) } +
                MaterialItems.SILVER.values.map { ItemStack(it) }
            )
            
            // Add diamond-like and netherite-like metals
            event.acceptAll(
                MaterialItems.PLATINUM.values.map { ItemStack(it) } +
                MaterialItems.TITANIUM.values.map { ItemStack(it) } +
                MaterialItems.COBALT.values.map { ItemStack(it) } +
                MaterialItems.MITHRIL.values.map { ItemStack(it) } +
                MaterialItems.ORICHALCUM.values.map { ItemStack(it) }
            )
        }
    }
}
