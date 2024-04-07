package io.github.serpro69.kfaker.kotest.poet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec

public fun TypeSpec.Builder.primaryConstructor(block: FunSpec.Builder.() -> Unit) {
    primaryConstructor(FunSpec.constructorBuilder().apply(block).build())
}

public fun FileSpec.Builder.addClass(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit,
) {
    addType(TypeSpec.classBuilder(className).apply(block).build())
}

public fun TypeSpec.Builder.addProperty(
    name: String,
    type: TypeName,
    modifiers: Iterable<KModifier> = emptyList(),
    initializer: String? = null,
) {
    addProperty(name, type, modifiers, initializer) { mutable(false) }
}

public fun TypeSpec.Builder.addMutableProperty(
    name: String,
    type: TypeName,
    modifiers: Iterable<KModifier> = emptyList(),
    initializer: String? = null,
) {
    addProperty(name, type, modifiers, initializer) { mutable(true) }
}

private fun TypeSpec.Builder.addProperty(
    name: String,
    type: TypeName,
    modifiers: Iterable<KModifier> = emptyList(),
    initializer: String? = null,
    block: PropertySpec.Builder.() -> Unit = { },
) {
    addProperty(
        PropertySpec.builder(name = name, type = type).apply {
            if (initializer != null) initializer(initializer)
            addModifiers(modifiers)
        }.apply(block).build(),
    )
}
