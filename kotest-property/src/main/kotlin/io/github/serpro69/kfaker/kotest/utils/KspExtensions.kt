package io.github.serpro69.kfaker.kotest.utils

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSDeclarationContainer
import com.google.devtools.ksp.symbol.KSPropertyDeclaration

internal val KSDeclaration.baseName: String
    get() = simpleName.asString().sanitize()

internal val KSDeclaration.fullName: String
    get() = qualifiedName?.asString() ?: simpleName.asString()

internal val KSClassDeclaration.sealedTypes get() = getSealedSubclasses()

internal fun KSDeclarationContainer.allNestedDeclarations(): Sequence<KSDeclaration> =
    declarations +
        declarations
            .filterIsInstance<KSDeclarationContainer>()
            .flatMap { it.allNestedDeclarations() }

/**
 * Obtains those properties which are defined in the primary constructor,
 * or in every primary constructor of their children.
 */
internal fun KSClassDeclaration.getPrimaryConstructorProperties() =
    getAllProperties().filter { property ->
        hasPrimaryProperty(property) || (sealedTypes.any() && sealedTypes.all { it.hasPrimaryProperty(property) })
    }

private fun KSClassDeclaration.hasPrimaryProperty(property: KSPropertyDeclaration) =
    primaryConstructor?.parameters.orEmpty().any { param ->
        (param.isVal || param.isVar) && param.name?.asString() == property.baseName
    }

/**
 * Sanitizes each delimited section if it matches with Kotlin reserved keywords.
 */
private fun String.sanitize(
    delimiter: String = ".",
    prefix: String = "",
) = splitToSequence(delimiter).joinToString(delimiter, prefix) { if (it in KOTLIN_KEYWORDS) "`$it`" else it }

private val KOTLIN_KEYWORDS =
    setOf(
        // Hard keywords
        "as",
        "break",
        "class",
        "continue",
        "do",
        "else",
        "false",
        "for",
        "fun",
        "if",
        "in",
        "interface",
        "is",
        "null",
        "object",
        "package",
        "return",
        "super",
        "this",
        "throw",
        "true",
        "try",
        "typealias",
        "typeof",
        "val",
        "var",
        "when",
        "while",
        // Soft keywords
        "by",
        "catch",
        "constructor",
        "delegate",
        "dynamic",
        "field",
        "file",
        "finally",
        "get",
        "import",
        "init",
        "param",
        "property",
        "receiver",
        "set",
        "setparam",
        "where",
        // Modifier keywords
        "actual",
        "abstract",
        "annotation",
        "companion",
        "const",
        "crossinline",
        "data",
        "enum",
        "expect",
        "external",
        "final",
        "infix",
        "inline",
        "inner",
        "internal",
        "lateinit",
        "noinline",
        "open",
        "operator",
        "out",
        "override",
        "private",
        "protected",
        "public",
        "reified",
        "sealed",
        "suspend",
        "tailrec",
        "value",
        "vararg",
        // These aren't keywords anymore but still break some code if unescaped.
        // https://youtrack.jetbrains.com/issue/KT-52315
        "header",
        "impl",
        // Other reserved keywords
        "yield",
    )
