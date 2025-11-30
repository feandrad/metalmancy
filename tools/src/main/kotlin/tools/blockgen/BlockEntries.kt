package tools.blockgen

import io.felipeandrade.metalmancy.registry.material.Materials

object BlockEntries {
    val blocks = mutableListOf<GeneratedBlock>()

    init {
        Materials.ALL.forEach { mat ->
            mat.parts.forEach { part ->
                if (part.isBlock) {
                    blocks.add(DefaultBlock(mat.unlocalizedName(part)))
                }
            }
        }
    }
}