package tools

data class OreGen(
    val ore: String,
    val deepslate: String? = null,
    val yRange: IntRange = -80..80,
    val heightType: OreGenHeightType = OreGenHeightType.TRAPEZOID,
    val veinSize: Int = 4,
    val countPerChunk: Int = 7,
    val suffix: String? = null,
    val targets: List<OreGenTarget>? = null,
)

data class OreGenTarget(
    val predicateType: OreGenPredicateType = OreGenPredicateType.BLOCK_MATCH,
    val block: String,
)

enum class OreGenPredicateType(val id: String) {
    BLOCK_MATCH("minecraft:block_match"),
    TAG_MATCH("minecraft:tag_match"),
}

enum class OreGenHeightType (val id: String) {
    TRAPEZOID("minecraft:trapezoid"),
    UNIFORM("minecraft:uniform"),
}