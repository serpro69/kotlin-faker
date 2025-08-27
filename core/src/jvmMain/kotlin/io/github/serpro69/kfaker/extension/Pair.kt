package io.github.serpro69.kfaker.extension

typealias AltKey<A, B> = Pair<A, B>

internal infix fun <A, B> A.or(second: B): AltKey<A, B> = AltKey(this, second)
