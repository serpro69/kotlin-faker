package io.github.serpro69.kfaker.kotest.utils

import com.google.devtools.ksp.symbol.Visibility

private fun Visibility.min(other: Visibility): Visibility =
    when {
        this == other -> this
        this == Visibility.PUBLIC -> other
        other == Visibility.PUBLIC -> this
        else -> Visibility.PRIVATE
    }

internal fun List<Visibility>.minimal(): Visibility = reduce(Visibility::min)
