package io.felipeandrade.metalmancy.registry.material

object Materials {
    // Gems
    val RUBY = Material("ruby", Family.GEM, setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.BLOCK, Part.GEM))
    val SAPPHIRE = Material("sapphire", Family.GEM, setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.BLOCK, Part.GEM))
    val TOPAZ = Material("topaz", Family.GEM, setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.BLOCK, Part.GEM))

    // Base metals
    val ZINC = Material(
        "zinc", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )
    val TIN = Material(
        "tin", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )
    val LEAD = Material(
        "lead", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )
    val NICKEL = Material(
        "nickel", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )

    val ALUMINUM = Material(
        "aluminum", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )
    val SILVER = Material(
        "silver", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )
    val COBALT = Material(
        "cobalt", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )
    val MANGANESE = Material(
        "manganese", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )

    val LITHIUM = Material(
        "lithium", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )
    val PLATINUM = Material(
        "platinum", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )
    val TITANIUM = Material(
        "titanium", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )
    val URANIUM = Material(
        "uranium", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )

    // Alchemy
    val MERCURY = Material(
        "cinnabar", Family.STONE,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.GEM)
    )
    val POTASH = Material(
        "potash", Family.SALT,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.BLOCK, Part.DUST)
    )
    val SALT = Material(
        "rock_salt", Family.SALT,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.BLOCK, Part.GEM, Part.DUST)
    )

    // Mystic
    val MITHRIL = Material(
        "mithril", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )
    val ORICHALCUM = Material(
        "orichalcum", Family.METAL,
        setOf(Part.ORE, Part.ORE_DEEPSLATE, Part.RAW_BLOCK, Part.BLOCK, Part.RAW_ITEM, Part.INGOT, Part.NUGGET)
    )

    // Alloys
    val PEWTER = Material("pewter", Family.ALLOY, setOf(Part.BLOCK, Part.INGOT, Part.NUGGET))
    val BRASS = Material("brass", Family.ALLOY, setOf(Part.BLOCK, Part.INGOT, Part.NUGGET))
    val BRONZE = Material("bronze", Family.ALLOY, setOf(Part.BLOCK, Part.INGOT, Part.NUGGET))
    val STEEL = Material("steel", Family.ALLOY, setOf(Part.BLOCK, Part.INGOT, Part.NUGGET))
    val ELECTRUM = Material("electrum", Family.ALLOY, setOf(Part.BLOCK, Part.INGOT, Part.NUGGET))
    val INVAR = Material("invar", Family.ALLOY, setOf(Part.BLOCK, Part.INGOT, Part.NUGGET))

    // Property groups
    val GEMS = listOf(RUBY, SAPPHIRE, TOPAZ)
    val SALTS = listOf(POTASH, SALT)
    val COPPER_LIKE_METALS = listOf(ZINC, TIN, LEAD, NICKEL)
    val IRON_LIKE_METALS = listOf(BRASS, BRONZE, STEEL, ALUMINUM, MANGANESE)
    val GOLD_LIKE_METALS = listOf(MERCURY, SILVER, COBALT, LITHIUM)
    val DIAMOND_LIKE_METALS = listOf(PLATINUM, TITANIUM, MITHRIL, ORICHALCUM, URANIUM)

    val METALS = COPPER_LIKE_METALS + IRON_LIKE_METALS + GOLD_LIKE_METALS + DIAMOND_LIKE_METALS
    val ALL = GEMS + SALTS + METALS
}
