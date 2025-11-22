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
            // Add gem blocks (ores and blocks only)
            event.acceptAll(
                MaterialItems.RUBY.filterBlocks() +
                MaterialItems.SAPPHIRE.filterBlocks() +
                MaterialItems.TOPAZ.filterBlocks()
            )
            
            // Add copper-like metal blocks
            event.acceptAll(
                MaterialItems.TIN.filterBlocks() +
                MaterialItems.ZINC.filterBlocks()
            )
            
            // Add iron-like metal blocks
            event.acceptAll(
                MaterialItems.ALUMINUM.filterBlocks() +
                MaterialItems.SILVER.filterBlocks()
            )
            
            // Add diamond-like and netherite-like metal blocks
            event.acceptAll(
                MaterialItems.PLATINUM.filterBlocks() +
                MaterialItems.TITANIUM.filterBlocks() +
                MaterialItems.COBALT.filterBlocks() +
                MaterialItems.MITHRIL.filterBlocks() +
                MaterialItems.ORICHALCUM.filterBlocks()
            )
        }
        
        if (event.tabKey == CreativeModeTabs.INGREDIENTS) {
            // Raw items (after raw gold in vanilla)
            event.acceptAll(
                MaterialItems.RUBY.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                MaterialItems.SAPPHIRE.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                MaterialItems.TOPAZ.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                MaterialItems.TIN.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                MaterialItems.ZINC.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                MaterialItems.ALUMINUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                MaterialItems.SILVER.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                MaterialItems.PLATINUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                MaterialItems.TITANIUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                MaterialItems.COBALT.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                MaterialItems.MITHRIL.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM) +
                MaterialItems.ORICHALCUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.RAW_ITEM)
            )
            
            // Gems (after diamond in vanilla)
            event.acceptAll(
                MaterialItems.RUBY.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.GEM) +
                MaterialItems.SAPPHIRE.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.GEM) +
                MaterialItems.TOPAZ.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.GEM)
            )
            
            // Ingots (after gold ingot in vanilla)
            event.acceptAll(
                MaterialItems.TIN.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                MaterialItems.ZINC.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                MaterialItems.ALUMINUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                MaterialItems.SILVER.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                MaterialItems.PLATINUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                MaterialItems.TITANIUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                MaterialItems.COBALT.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                MaterialItems.MITHRIL.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT) +
                MaterialItems.ORICHALCUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.INGOT)
            )
            
            // Nuggets (after gold nugget in vanilla)
            event.acceptAll(
                MaterialItems.TIN.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                MaterialItems.ZINC.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                MaterialItems.ALUMINUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                MaterialItems.SILVER.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                MaterialItems.PLATINUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                MaterialItems.TITANIUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                MaterialItems.COBALT.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                MaterialItems.MITHRIL.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET) +
                MaterialItems.ORICHALCUM.filterByPartType(io.felipeandrade.metalmancy.registry.material.Part.NUGGET)
            )
        }
    }
    
    private fun Map<io.felipeandrade.metalmancy.registry.material.Part, net.minecraft.world.item.Item>.filterBlocks(): List<ItemStack> {
        return this.filter { it.key.isBlock }.values.map { ItemStack(it) }
    }
    
    private fun Map<io.felipeandrade.metalmancy.registry.material.Part, net.minecraft.world.item.Item>.filterItems(): List<ItemStack> {
        return this.filter { !it.key.isBlock }.values.map { ItemStack(it) }
    }
    
    private fun Map<io.felipeandrade.metalmancy.registry.material.Part, net.minecraft.world.item.Item>.filterByPartType(partType: io.felipeandrade.metalmancy.registry.material.Part): List<ItemStack> {
        return this.filter { it.key == partType }.values.map { ItemStack(it) }
    }
}
