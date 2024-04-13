package io.github.serpro69.kfaker.kotest.poet

import com.squareup.kotlinpoet.TypeVariableName

/**
 * Returns this [TypeVariableName] receiver as invariant instance.
 */
fun TypeVariableName.makeInvariant(): TypeVariableName = TypeVariableName(name, bounds, null)
