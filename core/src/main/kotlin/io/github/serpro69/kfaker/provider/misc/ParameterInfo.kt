package io.github.serpro69.kfaker.provider.misc

import kotlin.reflect.KCallable
import kotlin.reflect.KParameter
import kotlin.reflect.KType

/**
 * Provides additional information about Class parameter to custom defined generators. The reason
 * why KParameter is not used is that you will want to provide additional information about
 * parameter that is not available in KParameter class.
 *
 * @property index 0-based index of this parameter in the parameter list of its containing callable.
 * @property name Name of this parameter as it was declared in the source code, or `null` if the
 *   parameter has no name or its name is not available at runtime. Examples of nameless parameters
 *   include `this` instance for member functions, extension receiver for extension functions or
 *   properties, parameters of Java methods compiled without the debug information, and others.
 * @property type Type of this parameter. For a `vararg` parameter, this is the type of the
 *   corresponding array, not the individual element.
 * @property kind Kind of this parameter. Kind represents a particular position of the parameter
 *   declaration in the source code, such as an instance, an extension receiver parameter or a value
 *   parameter.
 * @property isOptional `true` if this parameter is optional and can be omitted when making a call
 *   via [KCallable.callBy], or `false` otherwise.
 *
 * A parameter is optional in any of the two cases:
 * 1. The default value is provided at the declaration of this parameter.
 * 2. The parameter is declared in a member function and one of the corresponding parameters in the
 *    super functions is optional.
 *
 * @property isVararg `true` if this parameter is `vararg`. See the
 *   [Kotlin language documentation](https://kotlinlang.org/docs/reference/functions.html#variable-number-of-arguments-varargs)
 *   for more information.
 */
data class ParameterInfo(
    val index: Int,
    val name: String,
    val isOptional: Boolean,
    val isVararg: Boolean,
    val type: KType,
    val kind: KParameter.Kind,
)

/** Extension function that maps KParameter to ParameterInfo dataclass. */
internal fun KParameter.toParameterInfo() =
    ParameterInfo(
        index = index,
        name = name.toString(),
        isOptional = isOptional,
        isVararg = isVararg,
        type = type,
        kind = kind,
    )
