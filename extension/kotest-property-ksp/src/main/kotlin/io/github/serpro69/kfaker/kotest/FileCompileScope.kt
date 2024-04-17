package io.github.serpro69.kfaker.kotest

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.squareup.kotlinpoet.FileSpec
import io.github.serpro69.kfaker.kotest.extensions.ClassCompileScope
import io.github.serpro69.kfaker.kotest.extensions.hasGeneratedMarker
import io.github.serpro69.kfaker.kotest.poet.writeTo

internal fun ProcessorScope.processFiles(
    resolver: Resolver,
    block: FileCompileScope.() -> Unit,
) {
    val files = resolver.getAllFiles()
    if (files.none(KSFile::hasGeneratedMarker)) {
        block(FileCompileScope(files, this))
    }
}

internal class FileCompileScope(
    val files: Sequence<KSFile>,
    scope: ProcessorScope,
) : LoggerScope by scope, OptionsScope by scope {
    private val codegen: CodeGenerator = scope.codegen

    val KSClassDeclaration.classScope: ClassCompileScope
        get() = ClassCompileScope(this, logger)

    fun FileSpec.write() {
        writeTo(codegen)
    }
}
