package io.github.serpro69.kfaker.kotest

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import io.github.serpro69.kfaker.kotest.extensions.lang.forEachRun
import io.github.serpro69.kfaker.kotest.extensions.lang.mapRun

internal interface LoggerScope {
    val logger: KSPLogger
}

internal interface OptionsScope {
    val options: KotestArbOptions
}

internal class ProcessorScope(environment: SymbolProcessorEnvironment) : LoggerScope, OptionsScope {
    val codegen = environment.codeGenerator
    override val logger = environment.logger
    override val options = KotestArbOptions(environment.logger, environment.options)
}

internal class KotestArbProcessor(private val scope: ProcessorScope) : SymbolProcessor {
    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        scope.processFiles(resolver) {
            scope.logger.warn("FileCompileScope: $this")
            files.forEachRun {
                scope.logger.warn("File: $this", this)
                getAnnotationsByType(FakerArb::class).forEach { a ->
                    scope.logger.warn("Found Annotation: $a", this)
                    a.fakers.asSequence()
                        .mapRun { resolver.getClassDeclarationByName(qualifiedName!!) }
                        .forEachRun {
                            scope.logger.warn("Processing Faker: $this", this)
                            this?.classScope?.arbExtensions?.write()
                        }
                }
            }
        }
        return emptyList()
    }
}
