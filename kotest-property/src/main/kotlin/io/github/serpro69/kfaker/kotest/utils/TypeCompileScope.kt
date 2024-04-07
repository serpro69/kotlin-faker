package io.github.serpro69.kfaker.kotest.utils

import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.getVisibility
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.google.devtools.ksp.symbol.KSTypeParameter
import com.google.devtools.ksp.symbol.KSTypeReference
import com.google.devtools.ksp.symbol.Visibility
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LIST
import com.squareup.kotlinpoet.MAP
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.SET
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.ksp.TypeParameterResolver
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.toTypeParameterResolver
import com.squareup.kotlinpoet.ksp.toTypeVariableName
import io.github.serpro69.kfaker.kotest.LoggerScope
import io.github.serpro69.kfaker.kotest.poet.className
import io.github.serpro69.kfaker.kotest.poet.makeInvariant
import io.github.serpro69.kfaker.kotest.poet.parameterizedWhenNotEmpty
import io.github.serpro69.kfaker.kotest.utils.lang.orElse
import io.github.serpro69.kfaker.kotest.utils.lang.takeIfInstanceOf

internal data class MutationInfo<out T : TypeName>(
    val className: T,
    val toMutable: (String) -> String,
    val freeze: (String) -> String,
)

internal sealed interface TypeCompileScope : KSDeclaration, LoggerScope {
    val typeVariableNames: List<TypeVariableName>
    val typeParameterResolver: TypeParameterResolver
    val visibility: Visibility
    val target: ClassName
    val sealedTypes: Sequence<KSClassDeclaration>
    val properties: Sequence<KSPropertyDeclaration>
    val mutationInfo: Sequence<Pair<KSPropertyDeclaration, MutationInfo<TypeName>>>
        get() = properties.map { it to mutationInfo(it.type.resolve()) }

    val ClassName.parameterized: TypeName
    val KSPropertyDeclaration.typeName: TypeName

    fun KSType.hasMutableCopy(): Boolean

    fun KSPropertyDeclaration.toAssignment(
        wrapper: (String) -> String,
        source: String? = null,
    ): String = "$baseName = ${wrapper("${source ?: ""}$baseName")}"

    fun Sequence<Pair<KSPropertyDeclaration, MutationInfo<TypeName>>>.joinAsAssignmentsWithMutation(
        wrapper: MutationInfo<TypeName>.(String) -> String,
    ) = joinToString { (prop, mut) -> prop.toAssignment({ wrapper(mut, it) }) }

    fun buildFile(
        fileName: String,
        block: FileCompilerScope.() -> Unit,
    ): FileSpec = FileSpec.builder(packageName.asString(), fileName).also { toFileScope(it).block() }.build()

    fun toFileScope(file: FileSpec.Builder): FileCompilerScope

    fun KSClassDeclaration.asScope(): ClassCompileScope

    val KSClassDeclaration.properties: Sequence<KSPropertyDeclaration> get() = asScope().properties
}

internal fun TypeParameterResolver.invariant() =
    object : TypeParameterResolver {
        override val parametersMap: Map<String, TypeVariableName>
            get() = this@invariant.parametersMap.mapValues { (_, v) -> v.makeInvariant() }

        override fun get(index: String): TypeVariableName = this@invariant[index].makeInvariant()
    }

internal class ClassCompileScope(
    val classDeclaration: KSClassDeclaration,
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

    override fun KSType.hasMutableCopy(): Boolean {
        if (declaration is KSTypeParameter) return false
        val closestDecl = declaration.closestClassDeclaration()
        return closestDecl != null && closestDecl in mutableCandidates
    }

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

    override fun KSType.hasMutableCopy(): Boolean {
        val closestDecl = aliasDeclaration.type.resolve().declaration.closestClassDeclaration()
        return closestDecl != null && closestDecl in mutableCandidates
    }

    override val KSPropertyDeclaration.typeName: TypeName
        get() = type.toTypeName(typeParameterResolver).makeInvariant()

    override fun toFileScope(file: FileSpec.Builder): FileCompilerScope = FileCompilerScope(this, file = file)

    override fun KSClassDeclaration.asScope(): ClassCompileScope =
        takeIfInstanceOf<ClassCompileScope>()
            .orElse { ClassCompileScope(this, mutableCandidates, logger) }
}

internal class FileCompilerScope(
    val element: TypeCompileScope,
    val file: FileSpec.Builder,
) {
    fun addFunction(
        name: String,
        receives: TypeName?,
        returns: TypeName,
        block: FunSpec.Builder.() -> Unit = {},
    ) {
        file.addFunction(
            FunSpec.builder(name).apply {
                if (receives != null) receiver(receives)
                returns(returns)
                addTypeVariables(element.typeVariableNames.map { it.makeInvariant() })
            }.apply(block).build(),
        )
    }

    fun addInlinedFunction(
        name: String,
        receives: TypeName?,
        returns: TypeName,
        block: FunSpec.Builder.() -> Unit = {},
    ) {
        addFunction(name, receives, returns) {
            addModifiers(KModifier.INLINE)
            block()
        }
    }
}

/**
 * Obtains the [ClassName] corresponding to a [KSType].
 *
 * We need this function because [KSType.toClassName] doesn't
 * keep the nullability information correctly. As seen in
 * issue #62, this meant that [String?] was for example mapped
 * (incorrectly) to [String] when generating mutation info.
 */
internal fun KSType.toClassNameRespectingNullability(): ClassName =
    toClassName().copy(this.isMarkedNullable, emptyList(), emptyMap())

internal fun KSType.toTypeNameRespectingNullability(typeParamResolver: TypeParameterResolver = TypeParameterResolver.EMPTY): TypeName =
    toTypeName(typeParamResolver).copy(this.isMarkedNullable, emptyList(), emptyMap())

internal fun TypeCompileScope.mutationInfo(ty: KSType): MutationInfo<TypeName> =
    when (ty.declaration) {
        is KSClassDeclaration -> {
            val typeName: TypeName = ty.toTypeNameRespectingNullability(typeParameterResolver)
            val className =
                when (typeName) {
                    is ClassName -> typeName
                    is ParameterizedTypeName -> typeName.rawType
                    else -> ty.toClassNameRespectingNullability()
                }
            val nullableLessClassName = className.copy(nullable = false)

            infix fun String.dot(function: String) = dot(ty, function)
            when {
                nullableLessClassName == LIST -> mutationInfoOfCollection(ty, className, className.simpleName)
                nullableLessClassName == MAP -> mutationInfoOfCollection(ty, className, className.simpleName)
                nullableLessClassName == SET -> mutationInfoOfCollection(ty, className, className.simpleName)
                ty.hasMutableCopy() ->
                    MutationInfo(
                        className.mutable,
                        { it dot "toMutable" },
                        { it dot "freeze" },
                    )
                else ->
                    MutationInfo(
                        className.parameterizedWhenNotEmpty(
                            ty.arguments.map { it.toTypeName(typeParameterResolver) },
                        ),
                        { it },
                        { it },
                    )
            }
        }
        else ->
            MutationInfo(ty.toTypeName(typeParameterResolver), { it }, { it })
    }

internal fun TypeCompileScope.mutationInfoOfCollection(
    ty: KSType,
    className: ClassName,
    collectionType: String,
): MutationInfo<TypeName> {
    infix fun String.dot(function: String) = dot(ty, function)

    infix fun String.dotMap(map: String) = dotMap(ty, map)
    val type = ty.arguments[0].type?.resolve()
    val isMutableCollection =
        (ty.arguments.size == 1 && type?.hasMutableCopy() == true)
    val transform: (String) -> String =
        if (isMutableCollection) {
            { it dotMap "it.toMutable()" dot "toMutable$collectionType" }
        } else {
            { it dot "toMutable$collectionType" }
        }
    val freeze: (String) -> String =
        if (isMutableCollection) {
            { it dotMap "it.freeze()" }
        } else {
            { it }
        }
    return MutationInfo(
        ClassName(className.packageName, "Mutable$collectionType")
            .copy(nullable = ty.isMarkedNullable, annotations = emptyList(), tags = emptyMap())
            .parameterizedWhenNotEmpty(
                type?.takeIf { isMutableCollection }?.toTypeName(typeParameterResolver)?.mutable?.let(::listOf)
                    ?: ty.arguments.map { it.toTypeName(typeParameterResolver) },
            ),
        transform,
        freeze,
    )
}

internal fun String.dot(
    ty: KSType,
    function: String,
) = if (ty.isMarkedNullable) {
    "$this?.$function()"
} else {
    "$this.$function()"
}

internal fun String.dotMap(
    ty: KSType,
    map: String,
) = if (ty.isMarkedNullable) {
    "$this?.map { $map }"
} else {
    "$this.map { $map }"
}
