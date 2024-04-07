package io.github.serpro69.kfaker.kotest

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated

class ProcessorScope(val environment: SymbolProcessorEnvironment)

class KotestArbsProcessor(scope: ProcessorScope) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        return emptyList()
    }
}
