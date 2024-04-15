package io.github.serpro69.kfaker.kotest.extensions

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.getVisibility
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.FunctionKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.Visibility
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.toTypeVariableName
import io.github.serpro69.kfaker.kotest.LoggerScope
import io.github.serpro69.kfaker.kotest.poet.addClass
import io.github.serpro69.kfaker.kotest.poet.className
import io.github.serpro69.kfaker.kotest.poet.makeInvariant
import io.github.serpro69.kfaker.kotest.poet.parameterizedWhenNotEmpty

/**
 * Represents a compile scope for a [KSDeclaration].
 *
 * The [TypeCompileScope] interface defines a compile scope specific to a [KSDeclaration].
 *
 * @property classDeclaration       The KSClassDeclaration associated with this compile scope.
 * @property typeVariableNames      The type variable names associated with this compile scope.
 * @property target                 The target class name to which this compile-scope is associated.
 */
internal sealed interface TypeCompileScope : KSDeclaration, LoggerScope {
    val classDeclaration: KSClassDeclaration
    val typeVariableNames: List<TypeVariableName>
    val target: ClassName

    val ClassName.parameterized: TypeName

    /**
     * Returns a [FileSpec] with the given [fileName] and the [block] function applied to it.
     */
    fun buildFile(
        fileName: String,
        block: FileCompilerScope.() -> Unit,
    ): FileSpec {
        return FileSpec.builder(packageName.asString(), fileName)
            .also { toFileScope(it).block() }
            .build()
    }

    /**
     * Converts this [TypeCompileScope] to a [FileCompilerScope] for a given [file] input.
     */
    fun toFileScope(file: FileSpec.Builder): FileCompilerScope
}

/**
 * Represents a compile scope for a [KSClassDeclaration].
 *
 * The `ClassCompileScope` class defines a compile scope specific to a [KSClassDeclaration].
 * It encapsulates information about the [classDeclaration], and [logger] used for logging.
 *
 * @property classDeclaration   The KSClassDeclaration associated with this compile scope.
 * @property logger             The KSPLogger instance used for logging within the compile scope.
 */
internal class ClassCompileScope(
    override val classDeclaration: KSClassDeclaration,
    override val logger: KSPLogger,
) : TypeCompileScope, KSClassDeclaration by classDeclaration {
    override val typeVariableNames: List<TypeVariableName> =
        classDeclaration.typeParameters.map { it.toTypeVariableName() }
    override val target: ClassName = classDeclaration.className

    override val ClassName.parameterized
        get() = parameterizedWhenNotEmpty(typeVariableNames.map { it.makeInvariant() })

    override fun toFileScope(file: FileSpec.Builder): FileCompilerScope = FileCompilerScope(this, file = file)
}

/**
 * Represents a compile scope of an [element] in a given [file].
 */
internal class FileCompilerScope(
    private val element: TypeCompileScope,
    val file: FileSpec.Builder,
) {
    fun addArbFakerClass(
        type: ClassName,
        faker: KSClassDeclaration,
    ) {
        file.addClass(type) {
            val constructor =
                FunSpec.constructorBuilder()
                    .addParameter(ParameterSpec.builder("faker", faker.className).build())
                    .addCode("this.faker = faker\n")
                    .build()
            primaryConstructor(constructor)
            addProperty("faker", faker.className, KModifier.PRIVATE)
            val providers = addProviders(faker)
            providers.forEach { addArbProviderClass(it) }
        }
    }

    private fun TypeSpec.Builder.addProviders(faker: KSClassDeclaration): Sequence<Pair<KSPropertyDeclaration, KSClassDeclaration>> {
        val props =
            faker.getDeclaredProperties()
                .mapNotNull {
                    val ksClass = it.type.resolve().declaration as KSClassDeclaration
                    element.logger.logging("ksclass: $ksClass", ksClass)
                    val isFakeDataProvider =
                        ksClass.getAllSuperTypes().any { r ->
                            element.logger.logging("Provider: $r")
                            r.toClassName() == ClassName("io.github.serpro69.kfaker.provider", "FakeDataProvider")
                        }
                    // exclude misc providers because they're mostly "special cases" that don't make much sense here
                    val excluded = ksClass.packageName.asString() == "io.github.serpro69.kfaker.provider.misc"
                    if (isFakeDataProvider && !excluded) it to ksClass else null
                }
        props.forEach { (ksProp, ksClass) ->
            val providerClassName = ksClass.simpleName.asString()
            val className = ClassName(ksProp.packageName.asString(), "Arb$providerClassName")
            val prop =
                PropertySpec.builder(ksProp.baseName, className)
                    .delegate("lazy { ${className.simpleName}(faker.${ksProp.simpleName.asString()}) }")
                    .build()
            addProperty(prop)
        }
        return props
    }

    private fun addArbProviderClass(provider: Pair<KSPropertyDeclaration, KSClassDeclaration>) {
        val (prop, type) = provider
        element.logger.logging("prop: $prop, type: $type")
        val className = ClassName(type.packageName.asString(), "Arb${type.simpleName.asString()}")
        file.addImport("io.kotest.property.arbitrary", "arbitrary")
        file.addClass(className) {
            primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(ParameterSpec.builder(prop.baseName, type.className).build())
                    .addCode("this.${prop.baseName} = ${prop.baseName}\n")
                    .addModifiers(KModifier.INTERNAL)
                    .build(),
            )
            addProperty(
                PropertySpec.builder(prop.baseName, type.className)
                    .addModifiers(KModifier.PRIVATE)
                    .build(),
            )
            type.getDeclaredFunctions()
                .filter {
                    it.functionKind == FunctionKind.MEMBER &&
                        it.getVisibility() == Visibility.PUBLIC &&
                        !it.hasAnnotation<Deprecated> { a -> a.level == DeprecationLevel.ERROR }
                }
                .forEach {
                    element.logger.logging("function: $it")
                    element.logger.logging("type params: ${it.typeParameters}")
                    // filter out generics via typeParameters
                    val returnTypeName = if (it.typeParameters.isEmpty()) it.returnType?.toTypeName() else null
                    element.logger.logging("returnTypeName: $returnTypeName")
                    val functionParams =
                        it.parameters.map { p ->
                            ParameterSpec.builder(p.name?.asString() ?: "noname", p.type.toTypeName())
                                .build()
                        }
                    addFunction(
                        FunSpec.builder(it.baseName).apply {
                            addParameters(functionParams)
                            val typeArgs = returnTypeName?.let { t -> listOf(t) } ?: emptyList()
                            returns(
                                ClassName("io.kotest.property", "Arb")
                                    .parameterizedWhenNotEmpty(typeArgs)
                            )
                            val params = if (functionParams.isNotEmpty()) {
                                functionParams.joinToString(", ") { p -> p.name }
                            } else {
                                ""
                            }
                            addCode("return arbitrary·{·${prop.baseName}.${it.baseName}($params)·}")
                        }.build()
                    )
                }
        }
    }

    fun addPropertyWithGetter(
        name: String,
        receives: TypeName,
        returns: ClassName,
        block: FunSpec.Builder.(PropertySpec.Builder) -> Unit = {},
    ) {
        val prop =
            PropertySpec.builder(name, returns).apply ps@{
                receiver(receives)
                addTypeVariables(element.typeVariableNames.map { it.makeInvariant() })
                val getter =
                    FunSpec.getterBuilder()
                        .apply { addTypeVariables(element.typeVariableNames.map { it.makeInvariant() }) }
                        .apply fs@{ block(this@fs, this@ps) }
                        .build()
                getter(getter)
            }.build()
        file.addProperty(prop)
    }
}
