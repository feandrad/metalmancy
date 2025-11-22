package io.felipeandrade.metalmancy.material

import io.kotest.property.Arb
import io.kotest.property.arbitrary.*

/**
 * Custom generators (Arb) for property-based testing of the Material system.
 * These generators create random but valid instances of Material, Part, and Family.
 */

/**
 * Generates random Part enum values.
 */
fun Arb.Companion.part(): Arb<Part> = arbitrary {
    Part.entries.random()
}

/**
 * Generates random Family enum values.
 */
fun Arb.Companion.family(): Arb<Family> = arbitrary {
    Family.entries.random()
}

/**
 * Generates random valid material names (lowercase, alphanumeric with underscores).
 */
fun Arb.Companion.materialName(): Arb<String> = arbitrary {
    val length = (3..15).random()
    buildString {
        append(('a'..'z').random()) // Start with letter
        repeat(length - 1) {
            when ((0..2).random()) {
                0 -> append(('a'..'z').random())
                1 -> append(('0'..'9').random())
                else -> if (this.isNotEmpty() && this.last() != '_') append('_')
            }
        }
    }.replace(Regex("_+"), "_").trim('_')
}

/**
 * Generates random non-empty sets of Parts.
 */
fun Arb.Companion.partSet(): Arb<Set<Part>> = arbitrary {
    val size = (1..Part.entries.size).random()
    Part.entries.shuffled().take(size).toSet()
}

/**
 * Generates random valid Material instances.
 * Materials will have valid names, families, and non-empty part sets.
 */
fun Arb.Companion.material(): Arb<Material> = arbitrary {
    Material(
        name = Arb.materialName().bind(),
        family = Arb.family().bind(),
        parts = Arb.partSet().bind()
    )
}

/**
 * Generates Materials with specific family.
 */
fun Arb.Companion.materialWithFamily(family: Family): Arb<Material> = arbitrary {
    Material(
        name = Arb.materialName().bind(),
        family = family,
        parts = Arb.partSet().bind()
    )
}

/**
 * Generates Materials that have a specific part.
 */
fun Arb.Companion.materialWithPart(part: Part): Arb<Material> = arbitrary {
    val otherParts = Arb.partSet().bind()
    Material(
        name = Arb.materialName().bind(),
        family = Arb.family().bind(),
        parts = otherParts + part
    )
}
