package io.github.serpro69.kfaker.kotest.poet

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSName
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.ksp.writeTo

fun ClassName.parameterizedWhenNotEmpty(typeArguments: List<TypeName>): TypeName =
    takeIf { typeArguments.isNotEmpty() }?.parameterizedBy(typeArguments)?.copy(nullable = this.isNullable) ?: this

fun FileSpec.writeTo(codeGenerator: CodeGenerator) {
    writeTo(codeGenerator = codeGenerator, aggregating = false)
}

fun ClassName.append(name: String): ClassName = ClassName(packageName, simpleNames + name)

@Suppress("RecursivePropertyAccessor")
val KSDeclaration.className: ClassName
    get() =
        when (val parent = parentDeclaration) {
            is KSClassDeclaration -> parent.className.append(simpleName.asString())
            else -> ClassName(packageName = packageName.asStringQuoted(), simpleName.asString())
        }

fun KSName.asStringQuoted(): String =
    asString().split('.').joinToString(separator = ".") {
        when (it) {
            in KOTLIN_KEYWORDS -> "`$it`"
            else -> it
        }
    }

// https://kotlinlang.org/docs/reference/keyword-reference.html
internal val KOTLIN_KEYWORDS =
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
