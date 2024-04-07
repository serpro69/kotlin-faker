package io.github.serpro69.kfaker.kotest.utils.lang

internal inline fun <reified T> Any?.takeIfInstanceOf(): T? = this as? T
