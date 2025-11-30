package io.felipeandrade.metalmancy.util

/**
 * Extends String to join itself with any subsequent parts using an underscore,
 * automatically filtering out any empty or blank components.
 *
 * @param subString A variable number of strings (vararg) to append to the base string.
 * @return The finalized name string (e.g., "base_part2_part3").
 */
fun String.appendUnlocalizedAll(vararg subString: String): String {
    val allParts = listOf(this) + subString.toList()
    return allParts
        .filter { it.isNotBlank() }
        .joinToString(separator = "_")
}