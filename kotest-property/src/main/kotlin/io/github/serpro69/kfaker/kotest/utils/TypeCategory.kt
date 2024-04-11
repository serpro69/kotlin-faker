package io.github.serpro69.kfaker.kotest.utils

import com.google.devtools.ksp.isAbstract
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.symbol.Modifier.SEALED
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ksp.toClassName
import io.github.serpro69.kfaker.kotest.utils.TypeCategory.Known
import io.github.serpro69.kfaker.kotest.utils.TypeCategory.Unknown

internal val KSDeclaration.typeCategory: TypeCategory
    get() =
        when (this) {
            is KSTypeAlias ->
                when (val result = type.resolve().declaration.typeCategory) {
                    is Unknown -> Unknown(this)
                    else -> result
                }
            is KSClassDeclaration ->
                when {
                    isFaker() -> Known.Faker
                    isDataProvider() -> Known.Provider
                    else -> Unknown(this)
                }
            else -> Unknown(this)
        }

internal val KSTypeAlias.ultimateDeclaration: KSClassDeclaration?
    get() =
        when (val oneStep = type.resolve().declaration) {
            is KSClassDeclaration -> oneStep
            is KSTypeAlias -> oneStep.ultimateDeclaration
            else -> null
        }

internal inline fun TypeCompileScope.onKnownCategory(block: (Known) -> Unit) {
    (typeCategory as? Known)?.apply(block) ?: logger.error("Type $fullName is not supported by faker")
}

internal sealed interface TypeCategory {
    sealed interface Known : TypeCategory {
        data object Faker : Known

        data object Provider : Known
    }

    @JvmInline
    value class Unknown(val original: KSDeclaration) : TypeCategory
}

internal fun KSClassDeclaration.isConstructable() = primaryConstructor?.isPublic() == true

private fun KSClassDeclaration.isFaker(): Boolean {
    return isConstructable() &&
        superTypes.any {
            it.resolve().toClassName() == ClassName("io.github.serpro69.kfaker", "AbstractFaker")
        }
}

private fun KSClassDeclaration.isDataProvider(): Boolean {
    return superTypes.any {
        it.resolve().toClassName() == ClassName("io.github.serpro69.kfaker.provider", "FakeDataProvider")
    }
}

private fun KSClassDeclaration.isDataClass() = isConstructable() && Modifier.DATA in modifiers

private fun KSClassDeclaration.isValueClass() = isConstructable() && Modifier.VALUE in modifiers

private fun KSClassDeclaration.isSealedDataHierarchy() = SEALED in modifiers && isAbstract() && hasOnlyDataClassChildren()

private fun KSClassDeclaration.hasOnlyDataClassChildren() = sealedTypes.any() && sealedTypes.all { it.isDataClass() || it.isValueClass() }
