package io.github.serpro69.kfaker.kotest.extensions.lang

internal inline fun <T> T?.orElse(block: () -> T): T = this ?: block()
