package io.github.serpro69.kfaker.kotest.utils

import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName

internal fun FileCompilerScope.addGeneratedMarker() {
    file.addProperty(
        PropertySpec.builder(Marker, UnitTypeName).addModifiers(PRIVATE).initializer("Unit").build(),
    )
}

internal fun KSFile.hasGeneratedMarker(): Boolean =
    declarations.filterIsInstance<KSPropertyDeclaration>().any { it.baseName == Marker }

@Suppress("PropertyName")
private const val Marker = "generatedByKotlinFaker"

@Suppress("PropertyName")
private val UnitTypeName = Unit::class.asTypeName()
