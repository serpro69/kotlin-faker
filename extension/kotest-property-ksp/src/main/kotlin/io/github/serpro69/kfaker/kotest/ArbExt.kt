package io.github.serpro69.kfaker.kotest

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import io.github.serpro69.kfaker.kotest.extensions.TypeCompileScope
import io.github.serpro69.kfaker.kotest.extensions.addGeneratedMarker
import io.github.serpro69.kfaker.kotest.poet.append
import java.util.Locale

internal val TypeCompileScope.arbExtensions: FileSpec
    get() =
        buildFile(fileName = target.append("Arb").reflectionName()) {
            val parameterized = target.parameterized
            val arbClassName = ClassName(file.packageName, "Arb${target.simpleName}")
            addGeneratedMarker()
            addPropertyWithGetter("arb", parameterized, arbClassName) { _ ->
                addCode("return ${arbClassName.simpleName}(this)")
            }
            addPropertyWithGetter(
                target.simpleName.replaceFirstChar { if (it.isUpperCase()) it.lowercase(Locale.getDefault()) else it.toString() },
                ClassName("io.kotest.property", "Arb", "Companion"),
                arbClassName,
            ) { _ ->
                addCode("return $arbClassName(${target.simpleName}())")
            }
            addArbFakerClass(arbClassName, classDeclaration)
        }
