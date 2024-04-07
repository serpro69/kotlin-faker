package io.github.serpro69.kfaker.kotest.utils.lang

internal fun <A> Sequence<A>.joinWithWhen(
    subject: String = "this",
    transform: (A) -> String = { it.toString() },
) = joinToString(prefix = "when ($subject) {\n", separator = "\n", postfix = "\n}", transform = transform)
