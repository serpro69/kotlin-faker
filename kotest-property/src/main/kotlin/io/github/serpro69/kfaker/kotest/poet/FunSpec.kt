package io.github.serpro69.kfaker.kotest.poet

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName

public fun FunSpec.Builder.addReturn(
    expr: String,
    vararg args: Any?,
): FunSpec.Builder = addCode("return $expr", args)

public fun FunSpec.Builder.addParameter(
    name: String,
    type: TypeName,
    defaultValue: String? = null,
) {
    addParameter(
        ParameterSpec.builder(name = name, type = type).apply {
            if (defaultValue != null) defaultValue(defaultValue)
        }.build(),
    )
}
