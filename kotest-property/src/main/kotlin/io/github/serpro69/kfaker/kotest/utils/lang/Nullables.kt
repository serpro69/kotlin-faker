package io.github.serpro69.kfaker.kotest.utils.lang

internal inline fun <T> T?.orElse(block: () -> T): T = this ?: block()
