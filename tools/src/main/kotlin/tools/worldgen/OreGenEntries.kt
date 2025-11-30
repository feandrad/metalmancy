package tools

import io.felipeandrade.metalmancy.registry.material.Materials
import io.felipeandrade.metalmancy.registry.material.Part

object OreGenEntries {
    val overworld: List<OreGen> =
        Materials.ALL
            .filter { Part.ORE in it.parts }
            .flatMap { material ->
                val ore = material.unlocalizedName(Part.ORE)
                val deep = material.unlocalizedName(Part.ORE_DEEPSLATE)

                buildList {
                    when (material.name) {
                        "zinc", "tin" -> add(OreGen(ore, deep, -16..112))

                        "silver", "manganese" -> add(OreGen(ore, deep, -64..32))

                        "aluminum", "nickel", "lead" -> {
                            add(OreGen(ore, deep, -64..32))
                            add(OreGen(ore, deep, -64..0, OreGenHeightType.TRAPEZOID, 4, 7, "small"))
                        }

                        "cobalt", "lithium" -> add(OreGen(ore, deep, -64..-4, OreGenHeightType.UNIFORM))

                        "titanium", "platinum", "uranium" -> {
                            add(OreGen(ore, deep, -64..0))
                            add(OreGen(ore, deep, -64..-24, OreGenHeightType.UNIFORM, 2, 1, "tiny"))
                        }

                        "rock_salt" -> {
                            add(OreGen(ore, deep, -16..192, OreGenHeightType.UNIFORM, 20, 8))
                            add(OreGen(ore, deep, -64..0, OreGenHeightType.TRAPEZOID, 20, 8, "bonus"))
                        }

                        "cinnabar" -> add(OreGen(ore, deep, -64..32, OreGenHeightType.UNIFORM, 2, 4))

                        "potash" -> add(OreGen(ore, deep, -64..-4, OreGenHeightType.UNIFORM, 12, 8))

                        "ruby", "sapphire", "topaz" -> add(OreGen(ore, deep, -64..-4, OreGenHeightType.UNIFORM, 4, 2))

                        else -> {}
                    }
                }
            }

    val nether: List<OreGen> =
        Materials.ALL.filter { Part.ORE in it.parts }.flatMap { material ->
            val ore = material.unlocalizedName(Part.ORE)
            when (material.name) {
                "mithril" -> listOf(OreGen(ore))
                else -> emptyList()
            }
        }

    val ender: List<OreGen> =
        Materials.ALL.filter { Part.ORE in it.parts }.flatMap { material ->
            val ore = material.unlocalizedName(Part.ORE)
            when (material.name) {
                "orichalcum" -> listOf(OreGen(ore))
                else -> emptyList()
            }
        }
}
