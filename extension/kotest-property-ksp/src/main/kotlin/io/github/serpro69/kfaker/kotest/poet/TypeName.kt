package io.github.serpro69.kfaker.kotest.poet

import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName

fun TypeVariableName.makeInvariant(): TypeVariableName = TypeVariableName(name, bounds, null)

fun TypeName.makeInvariant(): TypeName =
    when (this) {
        is TypeVariableName -> TypeVariableName(name, bounds, null)
        is ParameterizedTypeName ->
            copy(
                typeArguments = typeArguments.map { it.makeInvariant() },
            )
        else -> this
    }
