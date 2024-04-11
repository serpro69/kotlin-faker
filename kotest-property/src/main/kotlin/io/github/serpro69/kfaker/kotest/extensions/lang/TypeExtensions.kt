package io.github.serpro69.kfaker.kotest.extensions.lang

internal inline fun <reified T> Any?.takeIfInstanceOf(): T? = this as? T
