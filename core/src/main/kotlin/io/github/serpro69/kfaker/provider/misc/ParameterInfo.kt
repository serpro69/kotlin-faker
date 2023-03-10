package io.github.serpro69.kfaker.provider.misc

import kotlin.reflect.KParameter

/**
 * Provides additional informations about Class parameter to custom defined generators.
 * The reason why is not used KParameter is that you will want to provide
 * additional informations about parameter that is not available in KParameter class.
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
fun KParameter.toParameterInfo() = ParameterInfo(
    index = index,
    name = name.toString(),
    isOptional = isOptional,
    isVararg = isVararg
)
