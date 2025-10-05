package tools.itemgen

import io.felipeandrade.metalmancy.registry.material.Materials

object ItemEntries {
    val items = mutableListOf<GeneratedItem>()

    init {
        Materials.ALL.forEach { mat ->
            mat.parts.forEach { part ->
                if (part.isBlock) {
                    items.add(GeneratedBlockItem(mat.unlocalizedName(part)))
                } else {
                    items.add(GeneratedItem(mat.unlocalizedName(part)))
                }
            }
        }
    }
}
