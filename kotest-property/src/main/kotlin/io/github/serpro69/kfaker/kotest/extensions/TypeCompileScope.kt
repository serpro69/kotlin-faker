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
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.google.devtools.ksp.symbol.KSTypeReference
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
import com.squareup.kotlinpoet.ksp.TypeParameterResolver
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.toTypeParameterResolver
import com.squareup.kotlinpoet.ksp.toTypeVariableName
import io.github.serpro69.kfaker.kotest.LoggerScope
import io.github.serpro69.kfaker.kotest.extensions.lang.orElse
import io.github.serpro69.kfaker.kotest.extensions.lang.takeIfInstanceOf
import io.github.serpro69.kfaker.kotest.poet.addClass
import io.github.serpro69.kfaker.kotest.poet.className
import io.github.serpro69.kfaker.kotest.poet.makeInvariant
import io.github.serpro69.kfaker.kotest.poet.parameterizedWhenNotEmpty

internal sealed interface TypeCompileScope : KSDeclaration, LoggerScope {
    val typeVariableNames: List<TypeVariableName>
    val typeParameterResolver: TypeParameterResolver
    val visibility: Visibility
    val target: ClassName
    val sealedTypes: Sequence<KSClassDeclaration>
    val properties: Sequence<KSPropertyDeclaration>

    val ClassName.parameterized: TypeName
    val KSPropertyDeclaration.typeName: TypeName

    fun buildFile(
        fileName: String,
        block: FileCompilerScope.() -> Unit,
    ): FileSpec = FileSpec.builder(packageName.asString(), fileName).also { toFileScope(it).block() }.build()

    fun toFileScope(file: FileSpec.Builder): FileCompilerScope

    fun KSClassDeclaration.asScope(): ClassCompileScope

    val classDeclaration: KSClassDeclaration
}

internal fun TypeParameterResolver.invariant() =
    object : TypeParameterResolver {
        override val parametersMap: Map<String, TypeVariableName>
            get() = this@invariant.parametersMap.mapValues { (_, v) -> v.makeInvariant() }

        override fun get(index: String): TypeVariableName = this@invariant[index].makeInvariant()
    }

internal class ClassCompileScope(
    override val classDeclaration: KSClassDeclaration,
    private val mutableCandidates: Sequence<KSDeclaration>,
    override val logger: KSPLogger,
) : TypeCompileScope, KSClassDeclaration by classDeclaration {
    override val typeVariableNames: List<TypeVariableName> =
        classDeclaration.typeParameters.map { it.toTypeVariableName() }
    override val typeParameterResolver: TypeParameterResolver
        get() = classDeclaration.typeParameters.toTypeParameterResolver().invariant()

    override val visibility: Visibility = classDeclaration.getVisibility()
    override val target: ClassName = classDeclaration.className
    override val sealedTypes: Sequence<KSClassDeclaration> = classDeclaration.sealedTypes
    override val properties: Sequence<KSPropertyDeclaration> = classDeclaration.getPrimaryConstructorProperties()

    override val ClassName.parameterized
        get() = parameterizedWhenNotEmpty(typeVariableNames.map { it.makeInvariant() })

    override val KSPropertyDeclaration.typeName: TypeName
        get() = type.toTypeName(typeParameterResolver).makeInvariant()

    override fun toFileScope(file: FileSpec.Builder): FileCompilerScope = FileCompilerScope(this, file = file)

    override fun KSClassDeclaration.asScope(): ClassCompileScope =
        takeIfInstanceOf<ClassCompileScope>()
            .orElse { ClassCompileScope(this, mutableCandidates, logger) }
}

internal class TypeAliasCompileScope(
    private val aliasDeclaration: KSTypeAlias,
    private val mutableCandidates: Sequence<KSDeclaration>,
    override val logger: KSPLogger,
) : TypeCompileScope, KSTypeAlias by aliasDeclaration {
    init {
        requireNotNull(aliasDeclaration.ultimateDeclaration)
    }

    override val classDeclaration: KSClassDeclaration = aliasDeclaration.type as KSClassDeclaration

    override val typeVariableNames: List<TypeVariableName> =
        aliasDeclaration.typeParameters.map { it.toTypeVariableName() }
    override val typeParameterResolver: TypeParameterResolver
        get() = aliasDeclaration.typeParameters.toTypeParameterResolver().invariant()

    override val visibility: Visibility
        get() {
            val ultimate = aliasDeclaration.ultimateDeclaration?.getVisibility() ?: Visibility.PUBLIC
            return listOf(aliasDeclaration.getVisibility(), ultimate).minimal()
        }

    override val target: ClassName = aliasDeclaration.className
    override val sealedTypes: Sequence<KSClassDeclaration> =
        aliasDeclaration.ultimateDeclaration?.sealedTypes.orEmpty()
    val typeSubstitution: TypeSubstitution = aliasDeclaration.unravelTypeParameters()
    override val properties: Sequence<KSPropertyDeclaration> =
        aliasDeclaration.ultimateDeclaration?.getPrimaryConstructorProperties().orEmpty().map {
            val originalTy = it.type
            object : KSPropertyDeclaration by it {
                override val type: KSTypeReference = originalTy.substitute(typeSubstitution)
            }
        }

    override val ClassName.parameterized
        get() = parameterizedWhenNotEmpty(typeVariableNames.map { it.makeInvariant() })

    override val KSPropertyDeclaration.typeName: TypeName
        get() = type.toTypeName(typeParameterResolver).makeInvariant()

    override fun toFileScope(file: FileSpec.Builder): FileCompilerScope = FileCompilerScope(this, file = file)

    override fun KSClassDeclaration.asScope(): ClassCompileScope =
        takeIfInstanceOf<ClassCompileScope>()
            .orElse { ClassCompileScope(this, mutableCandidates, logger) }
}

internal class FileCompilerScope(
    private val element: TypeCompileScope,
    val file: FileSpec.Builder,
) {
    fun addArbFakerClass(
        type: ClassName,
        faker: KSClassDeclaration,
    ) {
        file.addClass(type) {
            primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(ParameterSpec.builder("faker", faker.className).build())
                    .addCode(
                        """
                        this.faker = faker
                        """.trimIndent(),
                    )
                    .build(),
            )
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
                    element.logger.warn("ksclass: $ksClass", ksClass)
                    val isFakeDataProvider =
                        ksClass.getAllSuperTypes().any { r ->
                            element.logger.warn("Provider: $r")
                            r.toClassName() == ClassName("io.github.serpro69.kfaker.provider", "FakeDataProvider")
                        }
                    // exclude misc providers because they're mostly "special cases" that don't make much sense here
                    val excluded = ksClass.packageName.asString() == "io.github.serpro69.kfaker.provider.misc"
                    if (isFakeDataProvider && !excluded) it to ksClass else null
                }
        props.forEach { (ksProp, ksClass) ->
            val providerClassName = ksClass.simpleName.asString()
            val className = ClassName(ksProp.packageName.asString(), "Arb$providerClassName")
            addProperty(
                PropertySpec.builder(ksProp.baseName, className)
                    .delegate(
                        """
                        lazy { ${className.simpleName}(faker.${ksProp.simpleName.asString()}) }
                        """.trimIndent(),
                    )
                    .build(),
            )
        }
        return props
    }

    private fun addArbProviderClass(provider: Pair<KSPropertyDeclaration, KSClassDeclaration>) {
        val (prop, type) = provider
        element.logger.warn("prop: $prop, type: $type")
        val className = ClassName(type.packageName.asString(), "Arb${type.simpleName.asString()}")
        file.addImport("io.kotest.property.arbitrary", "arbitrary")
        file.addClass(className) {
            primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(ParameterSpec.builder(prop.baseName, type.className).build())
                    .addCode(
                        """
                        this.${prop.baseName} = ${prop.baseName}
                        """.trimIndent(),
                    )
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
                        it.getVisibility() == Visibility.PUBLIC
                }
                .forEach {
                    element.logger.warn("function: $it")
                    element.logger.warn("type params: ${it.typeParameters}")
                    // filter out generics via typeParameters
                    val returnTypeName = if (it.typeParameters.isEmpty()) it.returnType?.toTypeName() else null
                    element.logger.warn("returnTypeName: $returnTypeName")
                    val functionParams =
                        it.parameters.map { p ->
                            ParameterSpec.builder(p.name?.asString() ?: "noname", p.type.toTypeName())
                                .build()
                        }
                    addFunction(
                        FunSpec.builder(it.baseName)
                            .addParameters(functionParams)
                            .returns(
                                ClassName(
                                    "io.kotest.property",
                                    "Arb",
                                ).parameterizedWhenNotEmpty(
                                    returnTypeName?.let { t -> listOf(t) } ?: emptyList(),
                                ),
                            )
                            .addCode(
                                """
                                return arbitrary { ${prop.baseName}.${it.baseName}(${
                                    if (functionParams.isNotEmpty()) {
                                        functionParams.joinToString(
                                            ", ",
                                        ) { p -> p.name }
                                    } else {
                                        ""
                                    }
                                }) }
                                """.trimIndent(),
                            )
                            .build(),
                    )
                }
        }
    }

    fun addPropertyWithGetter(
        name: String,
        receives: TypeName,
        returns: ClassName,
        code: String,
        block: PropertySpec.Builder.() -> Unit = {},
    ) {
        file.addProperty(
            PropertySpec.builder(name, returns).apply {
                receiver(receives)
                getter(
                    FunSpec.getterBuilder().apply {
                        addTypeVariables(element.typeVariableNames.map { it.makeInvariant() })
                        addCode(code)
                    }.build(),
                )
                addTypeVariables(element.typeVariableNames.map { it.makeInvariant() })
            }.apply(block).build(),
        )
    }
}
