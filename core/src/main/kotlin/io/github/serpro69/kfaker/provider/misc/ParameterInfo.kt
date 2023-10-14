package io.github.serpro69.kfaker.provider.misc

import kotlin.reflect.KParameter

/**
 * Provides additional information about Class parameter to custom defined generators.
 * The reason why KParameter is not used is that you will want to provide
 * additional information about parameter that is not available in KParameter class.
 */
data class ParameterInfo(
    val index: Int,
    val name: String,
    val isOptional: Boolean,
    val isVararg: Boolean
)

/**
 * Extension function that maps KParameter to ParameterInfo dataclass.
 */
internal fun KParameter.toParameterInfo() = ParameterInfo(
    index = index,
    name = name.toString(),
    isOptional = isOptional,
    isVararg = isVararg
)
