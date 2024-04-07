package io.github.serpro69.kfaker.kotest

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.toKModifier
import io.github.serpro69.kfaker.kotest.poet.addParameter
import io.github.serpro69.kfaker.kotest.poet.addReturn
import io.github.serpro69.kfaker.kotest.poet.append
import io.github.serpro69.kfaker.kotest.poet.asTransformLambda
import io.github.serpro69.kfaker.kotest.utils.TypeCategory.Known.Data
import io.github.serpro69.kfaker.kotest.utils.TypeCategory.Known.Sealed
import io.github.serpro69.kfaker.kotest.utils.TypeCategory.Known.Value
import io.github.serpro69.kfaker.kotest.utils.TypeCompileScope
import io.github.serpro69.kfaker.kotest.utils.addGeneratedMarker
import io.github.serpro69.kfaker.kotest.utils.baseName
import io.github.serpro69.kfaker.kotest.utils.fullName
import io.github.serpro69.kfaker.kotest.utils.lang.joinWithWhen
import io.github.serpro69.kfaker.kotest.utils.lang.mapRun
import io.github.serpro69.kfaker.kotest.utils.lang.onEachRun
import io.github.serpro69.kfaker.kotest.utils.typeCategory

internal val TypeCompileScope.copyMapFunctionKt: FileSpec
    get() = buildFile(fileName = target.append("CopyMap").reflectionName()) {
        val parameterized = target.parameterized
        addGeneratedMarker()
        addInlinedFunction(name = "copyMap", receives = parameterized, returns = parameterized) {
            visibility.toKModifier()?.let { addModifiers(it) }
            properties
                .onEachRun {
                    addParameter(
                        name = baseName,
                        type = typeName.asTransformLambda(receiver = parameterized),
                        defaultValue = "{ it }",
                    )
                }
                .mapRun { "$baseName = $baseName(this, this.$baseName)" }
                .run { addReturn(repeatOnSubclasses(joinToString(), "copy")) }
        }
    }

private fun TypeCompileScope.repeatOnSubclasses(
    line: String,
    functionName: String,
): String =
    when (typeCategory) {
        Value -> "$fullName($line)"
        Data -> "$functionName($line)"
        Sealed -> sealedTypes.joinWithWhen { "is ${it.fullName} -> $functionName($line)" }
        else -> error("Unknown type category for ${target.canonicalName}")
    }
