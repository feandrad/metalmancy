package io.felipeandrade.metalmancy.blocks

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.registry.material.Material
import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour

object MaterialBlocks {
    val GEMS: Map<Material, Map<Part, Block>> = Materials.GEMS.associateWith { material ->
        material.parts.filter { it.isBlock }.associateWith { part ->
            val id = material.unlocalizedName(part)
            val properties = createLike(id, Blocks.EMERALD_ORE)
            register(id, Block(properties))
        }
    }

    val SALTS: Map<Material, Map<Part, Block>> = Materials.SALTS.associateWith { material ->
        material.parts.filter { it.isBlock }.associateWith { part ->
            val id = material.unlocalizedName(part)
            val properties = createLike(id, Blocks.COAL_ORE)
            register(id, Block(properties))
        }
    }

    val METALS: Map<Material, Map<Part, Block>> = Materials.COPPER_LIKE_METALS.associateWith { material ->
        material.parts.filter { it.isBlock }.associateWith { part ->
            val id = material.unlocalizedName(part)
            val properties = createLike(id, Blocks.COPPER_ORE)
            register(id, Block(properties))
        }
    } + Materials.IRON_LIKE_METALS.associateWith { material ->
        material.parts.filter { it.isBlock }.associateWith { part ->
            val id = material.unlocalizedName(part)
            val properties = createLike(id, Blocks.IRON_ORE)
            register(id, Block(properties))
        }
    } + Materials.GOLD_LIKE_METALS.associateWith { material ->
        material.parts.filter { it.isBlock }.associateWith { part ->
            val id = material.unlocalizedName(part)
            val properties = createLike(id, Blocks.GOLD_BLOCK)
            register(id, Block(properties))
        }
    } + Materials.DIAMOND_LIKE_METALS.associateWith { material ->
        material.parts.filter { it.isBlock }.associateWith { part ->
            val id = material.unlocalizedName(part)
            val properties = createLike(id, Blocks.DIAMOND_ORE)
            register(id, Block(properties))
        }
    }

    fun createLike(id: String, block: Block): BlockBehaviour.Properties =
        BlockBehaviour.Properties.ofFullCopy(block).setId(Metalmancy.resourceKey(id, Registries.BLOCK))

    fun registerAll() = Unit

    private fun <T : Block> register(name: String, block: T): T =
        Registry.register(BuiltInRegistries.BLOCK, Metalmancy.asResource(name), block)
}
