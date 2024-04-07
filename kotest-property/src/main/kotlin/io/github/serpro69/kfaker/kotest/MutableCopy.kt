package io.github.serpro69.kfaker.kotest

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ksp.toKModifier
import io.github.serpro69.kfaker.kotest.poet.addClass
import io.github.serpro69.kfaker.kotest.poet.addMutableProperty
import io.github.serpro69.kfaker.kotest.poet.addParameter
import io.github.serpro69.kfaker.kotest.poet.addProperty
import io.github.serpro69.kfaker.kotest.poet.addReturn
import io.github.serpro69.kfaker.kotest.poet.asReceiverConsumer
import io.github.serpro69.kfaker.kotest.poet.makeInvariant
import io.github.serpro69.kfaker.kotest.poet.parameterModifiers
import io.github.serpro69.kfaker.kotest.poet.primaryConstructor
import io.github.serpro69.kfaker.kotest.poet.propertyModifiers
import io.github.serpro69.kfaker.kotest.utils.FileCompilerScope
import io.github.serpro69.kfaker.kotest.utils.TypeCategory.Known
import io.github.serpro69.kfaker.kotest.utils.TypeCategory.Known.Data
import io.github.serpro69.kfaker.kotest.utils.TypeCategory.Known.Sealed
import io.github.serpro69.kfaker.kotest.utils.TypeCategory.Known.Value
import io.github.serpro69.kfaker.kotest.utils.TypeCompileScope
import io.github.serpro69.kfaker.kotest.utils.addGeneratedMarker
import io.github.serpro69.kfaker.kotest.utils.baseName
import io.github.serpro69.kfaker.kotest.utils.dslMarker
import io.github.serpro69.kfaker.kotest.utils.fullName
import io.github.serpro69.kfaker.kotest.utils.lang.forEachRun
import io.github.serpro69.kfaker.kotest.utils.lang.joinWithWhen
import io.github.serpro69.kfaker.kotest.utils.mutable
import io.github.serpro69.kfaker.kotest.utils.onKnownCategory
import io.github.serpro69.kfaker.kotest.utils.typeCategory

internal val TypeCompileScope.mutableCopyKt: FileSpec
    get() =
        buildFile(target.mutable.reflectionName()) {
            addGeneratedMarker()
            addDslMarkerClass()
            addMutableCopy()
            addFreezeFunction()
            addToMutateFunction()
            addCopyClosure()

            if (typeCategory is Sealed) {
                addRetrofittedCopyFunction()
            }
        }

internal fun FileCompilerScope.addMutableCopy() {
    with(element) {
        file.addClass(target.mutable) {
            visibility.toKModifier()?.let { addModifiers(it) }
            addAnnotation(target.dslMarker)
            addTypeVariables(typeVariableNames.map { it.makeInvariant() })
            primaryConstructor {
                mutationInfo.forEach { (property, mutationInfo) ->
                    with(property) {
                        addParameter(
                            name = baseName,
                            type = mutationInfo.className,
                            modifiers = parameterModifiers,
                        )
                        addMutableProperty(
                            name = baseName,
                            type = mutationInfo.className,
                            modifiers = propertyModifiers,
                            initializer = baseName,
                        )
                    }
                }
                addParameter(name = "old", type = target.parameterized)
                addProperty(name = "old", type = target.parameterized, initializer = "old")
            }
        }
    }
}

internal fun FileCompilerScope.addFreezeFunction() {
    with(element) {
        onKnownCategory { category ->
            addFunction(name = "freeze", receives = target.mutable.parameterized, returns = target.parameterized) {
                visibility.toKModifier()?.let { addModifiers(it) }
                addReturn(
                    when (category) {
                        Known.Class -> error("Plain classes are not supported as mutable")
                        Data, Value -> "${target.canonicalName}(${mutationInfo.joinAsAssignmentsWithMutation { freeze(it) }})"
                        Sealed ->
                            sealedTypes.joinWithWhen(subject = "old") { type ->
                                "is ${type.fullName} -> old.copy(${
                                    mutationInfo.joinAsAssignmentsWithMutation {
                                        freeze(
                                            it
                                        )
                                    }
                                })"
                            }
                    },
                )
            }
        }
    }
}

internal fun FileCompilerScope.addToMutateFunction() {
    with(element) {
        val parameterized = target.mutable.parameterized
        addFunction(name = "toMutable", receives = target.parameterized, returns = parameterized) {
            visibility.toKModifier()?.let { addModifiers(it) }
            addReturn("$parameterized(old = this, ${mutationInfo.joinAsAssignmentsWithMutation { toMutable(it) }})")
        }
    }
}

internal fun FileCompilerScope.addCopyClosure() {
    with(element) {
        val parameterized = target.mutable.parameterized
        addCopyFunction {
            addParameter(name = "block", type = parameterized.asReceiverConsumer())
            addReturn("toMutable().apply(block).freeze()")
        }
    }
}

private fun FileCompilerScope.addRetrofittedCopyFunction() {
    with(element) {
        addCopyFunction {
            properties.forEachRun { addParameter(name = baseName, type = typeName, defaultValue = "this.$baseName") }
            addReturn(
                sealedTypes.joinWithWhen { type ->
                    "is ${type.fullName} -> this.copy(${properties.joinToString { "${it.baseName} = ${it.baseName}" }})"
                },
            )
        }
    }
}

internal fun FileCompilerScope.addCopyFunction(block: FunSpec.Builder.() -> Unit) {
    with(element) {
        addInlinedFunction(name = "copy", receives = target.parameterized, returns = target.parameterized) {
            visibility.toKModifier()?.let { addModifiers(it) }
            block()
        }
    }
}

private fun FileCompilerScope.addDslMarkerClass() {
    file.addClass(element.target.dslMarker) {
        addAnnotation(DslMarker::class)
        addModifiers(KModifier.ANNOTATION)
    }
}
