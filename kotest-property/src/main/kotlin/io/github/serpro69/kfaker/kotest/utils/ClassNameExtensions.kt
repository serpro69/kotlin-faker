package io.github.serpro69.kfaker.kotest.utils

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import io.github.serpro69.kfaker.kotest.poet.flattenWithSuffix

internal val ClassName.mutable: ClassName get() = flattenWithSuffix("Mutable")
internal val ParameterizedTypeName.mutable: ParameterizedTypeName get() = flattenWithSuffix("Mutable")
internal val ClassName.dslMarker: ClassName get() = flattenWithSuffix("DslMarker")

internal val TypeName.mutable: TypeName?
    get() =
        when (this) {
            is ClassName -> this.mutable
            is ParameterizedTypeName -> this.mutable
            else -> null
        }
