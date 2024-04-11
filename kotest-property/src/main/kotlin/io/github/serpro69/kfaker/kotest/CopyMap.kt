package io.github.serpro69.kfaker.kotest

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import io.github.serpro69.kfaker.kotest.poet.append
import io.github.serpro69.kfaker.kotest.utils.TypeCategory
import io.github.serpro69.kfaker.kotest.utils.TypeCompileScope
import io.github.serpro69.kfaker.kotest.utils.addGeneratedMarker
import io.github.serpro69.kfaker.kotest.utils.fullName
import io.github.serpro69.kfaker.kotest.utils.typeCategory
import java.util.Locale

internal val TypeCompileScope.copyMapFunctionKt: FileSpec
    get() =
        buildFile(fileName = target.append("Arb").reflectionName()) {
            val parameterized = target.parameterized
            val arbClassName = ClassName(file.packageName, "Arb${target.simpleName}")
            addGeneratedMarker()
            addPropertyWithGetter("arb", parameterized, arbClassName, "return ${arbClassName.simpleName}(this)")
            addPropertyWithGetter(
                target.simpleName.replaceFirstChar { if (it.isUpperCase()) it.lowercase(Locale.getDefault()) else it.toString() },
                ClassName("io.kotest.property", "Arb", "Companion"),
                arbClassName,
                "return $arbClassName(${target.simpleName}())",
            )
            addArbFakerClass(arbClassName, classDeclaration)
            /* generate the following code
            val Arb.Companion.booksFaker get() = ArbBooks(BooksFaker())
            val BooksFaker.arb: ArbBooks get() = ArbBooks(this)

            class ArbBooks(booksFaker: BooksFaker) {
                val bible: ArbBible by lazy { ArbBible(booksFaker.bible) }
            }

            class ArbBible(private val bible: Bible) {
                fun character(): Arb<String> = arbitrary { bible.character() }

                fun location(): Arb<String> = arbitrary { bible.location() }

                fun quote(): Arb<String> = arbitrary { bible.quote() }
            }

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
             */
        }

private fun TypeCompileScope.repeatOnSubclasses(
    line: String,
    functionName: String,
): String =
    when (typeCategory) {
        TypeCategory.Known.Faker -> "$fullName($line)"
        TypeCategory.Known.Provider -> "$functionName($line)"
//        Sealed -> sealedTypes.joinWithWhen { "is ${it.fullName} -> $functionName($line)" }
        else -> error("Unknown type category for ${target.canonicalName}")
    }
