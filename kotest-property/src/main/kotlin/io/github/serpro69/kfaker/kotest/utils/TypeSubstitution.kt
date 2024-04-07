package io.github.serpro69.kfaker.kotest.utils

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSName
import com.google.devtools.ksp.symbol.KSReferenceElement
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.google.devtools.ksp.symbol.KSTypeParameter
import com.google.devtools.ksp.symbol.KSTypeReference
import com.google.devtools.ksp.symbol.Variance

internal typealias TypeSubstitution = Map<KSName, KSType>

/**
 * This function needs to "jump" across type aliases
 * to get the type parameters applied to the "real" type
 */
internal fun KSTypeAlias.unravelTypeParameters(): TypeSubstitution {
    val resolvedType = type.resolve() // this is expensive, we don't want to do it twice
    return when (val resolvedDeclaration = resolvedType.declaration) {
        is KSClassDeclaration -> {
            val typeParameterNames = resolvedDeclaration.typeParameters.map { it.name }
            val typeArguments = resolvedType.arguments.map { it.type?.resolve() }
            // keep only those type parameters for which we know the substituted type
            typeParameterNames.zip(typeArguments) { typeParamName, typeArgument ->
                typeArgument?.let { typeParamName to it }
            }.filterNotNull().toMap()
        }
        // implement for type aliases referencing other aliases
        else -> emptyMap()
    }
}

internal fun KSType.substitute(subst: TypeSubstitution): KSType =
    when (val declaration = declaration) {
        is KSTypeParameter -> subst[declaration.name] ?: this
        else -> replace(arguments.map { it.substitute(subst) })
    }

internal fun KSTypeArgument.substitute(subst: TypeSubstitution): KSTypeArgument {
    val previous: KSTypeArgument = this
    return object : KSTypeArgument by previous {
        override val variance = Variance.INVARIANT
        override val type = previous.type?.substitute(subst)
    }
}

internal fun KSTypeReference.substitute(subst: TypeSubstitution): KSTypeReference {
    val previous: KSTypeReference = this
    return object : KSTypeReference by previous {
        override val element: KSReferenceElement? = null

        override fun resolve(): KSType = previous.resolve().substitute(subst)
    }
}
