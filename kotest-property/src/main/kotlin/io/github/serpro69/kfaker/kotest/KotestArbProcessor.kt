package io.github.serpro69.kfaker.kotest

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import io.github.serpro69.kfaker.kotest.utils.TypeCategory
import io.github.serpro69.kfaker.kotest.utils.TypeCompileScope
import io.github.serpro69.kfaker.kotest.utils.lang.forEachRun
import io.github.serpro69.kfaker.kotest.utils.lang.mapRun
import io.github.serpro69.kfaker.kotest.utils.lang.onEachRun
import io.github.serpro69.kfaker.kotest.utils.typeCategory

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

    override fun process(resolver: Resolver): List<KSAnnotated> {
        scope.processFiles(resolver) {
            // add Arb extensions
            (classes.mapRun { classScope } + typeAliases.mapRun { typealiasScope })
                .filter { it.canHaveCopyFunctions(true) }
                .onEachRun { logger.logging("Processing ${simpleName.asString()}") }
                .forEachRun {
                    copyMapFunctionKt.write()
                    mutableCopyKt.write()
                }
        }
        return emptyList()
    }

    private fun TypeCompileScope.canHaveCopyFunctions(hierarchyCopy: Boolean) =
        typeCategory in listOf(TypeCategory.Known.Data, TypeCategory.Known.Value)
            || typeCategory is TypeCategory.Known.Sealed && hierarchyCopy
}
