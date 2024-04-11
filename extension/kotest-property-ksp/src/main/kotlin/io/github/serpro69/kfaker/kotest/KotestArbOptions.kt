package io.github.serpro69.kfaker.kotest

import com.google.devtools.ksp.processing.KSPLogger

internal sealed interface KotestArbGenerate {
    data object Error : KotestArbGenerate

    data object Annotated : KotestArbGenerate

    sealed interface NotAnnotated : KotestArbGenerate

    data object All : NotAnnotated

    data class Packages(val patterns: List<String>) : NotAnnotated

    companion object {
        const val ALL = "all"
        const val ANNOTATED = "annotated"
        const val PACKAGES_PREFIX = "packages"

        fun fromKspOptions(
            logger: KSPLogger,
            generate: String?,
        ): KotestArbGenerate =
            when {
                generate == null -> All
                generate == ALL -> All
                generate == ANNOTATED -> Annotated
                generate.startsWith(PACKAGES_PREFIX) -> Packages(generate.split(':').drop(1))
                else -> {
                    logger.error("Unrecognized value for 'generate'", null)
                    Error // return something, although the error is reported
                }
            }
    }
}

internal data class KotestArbOptions(
    val generate: KotestArbGenerate,
)

internal fun KotestArbOptions(
    logger: KSPLogger,
    options: Map<String, String>,
) = KotestArbOptions(
    generate = KotestArbGenerate.fromKspOptions(logger, options[GENERATE]),
)

private const val GENERATE = "generate"
