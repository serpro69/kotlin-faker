package io.github.serpro69.kfaker.kotest

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.squareup.kotlinpoet.FileSpec
import io.github.serpro69.kfaker.kotest.extensions.ClassCompileScope
import io.github.serpro69.kfaker.kotest.extensions.TypeAliasCompileScope
import io.github.serpro69.kfaker.kotest.extensions.TypeCategory
import io.github.serpro69.kfaker.kotest.extensions.allNestedDeclarations
import io.github.serpro69.kfaker.kotest.extensions.hasGeneratedMarker
import io.github.serpro69.kfaker.kotest.extensions.lang.filterIsInstance
import io.github.serpro69.kfaker.kotest.extensions.typeCategory
import io.github.serpro69.kfaker.kotest.poet.writeTo
import org.apache.commons.io.FilenameUtils

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

    val declarations =
        files.flatMap { it.allNestedDeclarations() }.onEach { it.isKnownWithCopyExtension() }
            .onEach { it.checkRedundantAnnotation() }.filter { it.typeCategory is TypeCategory.Known }

    val typeAliases = declarations.filterIsInstance<KSTypeAlias> { isKnownWithCopyExtension() }

    val classes = declarations.filterIsInstance<KSClassDeclaration>().filter { it.shouldGenerate() }

    val KSClassDeclaration.classScope: ClassCompileScope
        get() = ClassCompileScope(this, classes, logger)

    val KSTypeAlias.typealiasScope: TypeAliasCompileScope
        get() = TypeAliasCompileScope(this, classes, logger)

    private fun KSDeclaration.isKnownWithCopyExtension() =
        hasAnnotation<FakerArb>().also { isCopyExtension ->
            if (isCopyExtension && (typeCategory !is TypeCategory.Known || typeCategory is TypeCategory.Known.Faker)) {
                logger.error(
                    """
                    '@CopyExtensions' may only be used in data or value classes,
                    sealed hierarchies of those, or type aliases of those.
                    """.trimIndent(),
                    this,
                )
            }
        }

    fun FileSpec.write() {
        writeTo(codegen)
    }

    @OptIn(KspExperimental::class)
    private fun KSDeclaration.checkRedundantAnnotation() {
        if (isAnnotationPresent(FakerArb::class) && options.generate is KotestArbGenerate.NotAnnotated) {
            logger.warn(
                """
                Unused '@FakerArb' annotation, the plug-in is configured to process all classes.
                Add 'arg("annotatedOnly", "true")' to your KSP configuration to change this option.
                More info at https://serpro69.github.io/kotlin-faker/wiki/unique-generator/#selective-generation
                """.trimIndent(),
                this,
            )
        }
    }

    @OptIn(KspExperimental::class)
    private fun KSDeclaration.shouldGenerate(): Boolean =
        when (options.generate) {
            is KotestArbGenerate.Error -> false
            is KotestArbGenerate.All -> true
            is KotestArbGenerate.Annotated -> isAnnotationPresent(FakerArb::class)
            is KotestArbGenerate.Packages -> {
                val pkg = packageName.asString()
                options.generate.patterns.any { pattern ->
                    FilenameUtils.wildcardMatch(pkg, pattern)
                }
            }
        }
}
