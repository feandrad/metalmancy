package io.felipeandrade.metalmancy.blocks

import io.felipeandrade.metalmancy.Metalmancy
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

object ModBlocks {

    val CALCINATOR: CalcinatorBlock = register(
        "calcinator",
        CalcinatorBlock(BlockBehaviour.Properties.of()
            .strength(3.5f)
            .setId(Metalmancy.resourceKey("calcinator", net.minecraft.core.registries.Registries.BLOCK))
        )
    )

    fun registerAll() = Unit

    private fun <T : Block> register(name: String, block: T): T =
        Registry.register(BuiltInRegistries.BLOCK, Metalmancy.asResource(name), block)
}
