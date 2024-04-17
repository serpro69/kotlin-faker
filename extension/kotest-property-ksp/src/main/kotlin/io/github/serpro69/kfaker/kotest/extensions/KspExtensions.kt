package io.github.serpro69.kfaker.kotest.extensions

import com.google.devtools.ksp.symbol.KSDeclaration
import io.github.serpro69.kfaker.kotest.poet.KOTLIN_KEYWORDS

internal val KSDeclaration.baseName: String
    get() = simpleName.asString().sanitize()

/**
 * Sanitizes each delimited section if it matches with Kotlin reserved keywords.
 */
private fun String.sanitize(
    delimiter: String = ".",
    prefix: String = "",
) = splitToSequence(delimiter).joinToString(delimiter, prefix) { if (it in KOTLIN_KEYWORDS) "`$it`" else it }
