package io.github.serpro69.kfaker.kotest.poet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec

fun FileSpec.Builder.addClass(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit,
) {
    addType(TypeSpec.classBuilder(className).apply(block).build())
}
