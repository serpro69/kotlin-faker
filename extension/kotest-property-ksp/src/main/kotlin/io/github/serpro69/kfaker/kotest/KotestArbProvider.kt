package io.github.serpro69.kfaker.kotest

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

/**
 * [SymbolProcessorProvider] implementation for kotlin-faker Arbs generators
 * to use with [kotest property testing](https://kotest.io/docs/proptest/property-based-testing.html).
 */
class KotestArbProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return KotestArbProcessor(ProcessorScope(environment))
    }
}
