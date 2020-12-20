package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.RandomService
import java.util.*
import kotlin.Boolean
import kotlin.Char
import kotlin.Double
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.NoSuchElementException
import kotlin.Short
import kotlin.String
import kotlin.reflect.KClass
import kotlin.reflect.KVisibility

/**
 * Provider for functionality not covered by the standard dictionary files.
 *
 * Inspired by [Creating a random instance of any class in Kotlin blog post](https://blog.kotlin-academy.com/creating-a-random-instance-of-any-class-in-kotlin-b6168655b64a).
 */
@Suppress("unused")
class RandomProvider internal constructor(random: Random) {
    private val randomService = RandomService(random)

    /**
     * Creates an instance of [T]. If [T] has a parameterless public constructor then it will be used to create an instance of this class,
     * otherwise a constructor with minimal number of parameters will be used with randomly-generated values.
     *
     * @throws NoSuchElementException if [T] has no public constructor.
     */
    inline fun <reified T : Any> randomClassInstance() = T::class.randomClassInstance(RandomProviderConfig())

    /**
     * Creates an instance of [T]. If [T] has a parameterless public constructor then it will be used to create an instance of this class,
     * otherwise a constructor with minimal number of parameters will be used with randomly-generated values.
     *
     * @param configurator configure instance creation.
     *
     * @throws NoSuchElementException if [T] has no public constructor.
     */
    inline fun <reified T : Any> randomClassInstance(configurator: RandomProviderConfig.() -> Unit): T {
        val config = RandomProviderConfig().apply(configurator)
        return T::class.randomClassInstance(config)
    }

    @JvmSynthetic
    @PublishedApi
    internal fun <T : Any> KClass<T>.randomClassInstance(config: RandomProviderConfig): T {
        val defaultInstance: T? = if (
            config.constructorParamSize == -1
            && config.constructorFilterStrategy == ConstructorFilterStrategy.NO_ARGS
        ) {
            constructors.firstOrNull { it.parameters.isEmpty() && it.visibility == KVisibility.PUBLIC }?.call()
        } else null

        return if (defaultInstance != null) defaultInstance else {
            val constructors = constructors
                .filter { it.visibility == KVisibility.PUBLIC }

            val constructor = constructors.firstOrNull {
                it.parameters.size == config.constructorParamSize
            } ?: when (config.constructorFilterStrategy) {
                ConstructorFilterStrategy.MIN_NUM_OF_ARGS -> constructors.minByOrNull { it.parameters.size }
                ConstructorFilterStrategy.MAX_NUM_OF_ARGS -> constructors.maxByOrNull { it.parameters.size }
                else -> {
                    when (config.fallbackStrategy) {
                        FallbackStrategy.FAIL_IF_NOT_FOUND -> {
                            throw NoSuchElementException("Constructor with 'parameters.size == ${config.constructorParamSize}' not found for $this")
                        }
                        FallbackStrategy.USE_MIN_NUM_OF_ARGS -> constructors.minByOrNull { it.parameters.size }
                        FallbackStrategy.USE_MAX_NUM_OF_ARGS -> constructors.maxByOrNull { it.parameters.size }
                    }
                }
            } ?: throw NoSuchElementException("No suitable constructor found for $this")

            val params = constructor.parameters
                .map { it.type.classifier as KClass<*> }
                .map {
                    it.predefinedTypeOrNull(config)
                        ?: it.randomPrimitiveOrNull()
                        ?: it.randomEnumOrNull()
                        ?: it.randomClassInstance(config)
                }
                .toTypedArray()

            constructor.call(*params)
        }
    }

    private fun <T : Any> KClass<T>.predefinedTypeOrNull(config: RandomProviderConfig): Any? {
        return config.predefinedGenerators[this]?.invoke()
    }

    /**
     * Handles generation of primitive types since they do not have a public constructor.
     */
    private fun KClass<*>.randomPrimitiveOrNull(): Any? = when (this) {
        Double::class -> randomService.nextDouble()
        Float::class -> randomService.nextFloat()
        Long::class -> randomService.nextLong()
        Int::class -> randomService.nextInt()
        Short::class -> randomService.nextInt().toShort()
        Byte::class -> randomService.nextInt().toByte()
        String::class -> randomService.nextString()
        Char::class -> randomService.nextChar()
        Boolean::class -> randomService.nextBoolean()
        // TODO: 16.06.19 Arrays
        else -> null
    }

    /**
     * Handles generation of enums types since they do not have a public constructor.
     */
    private fun KClass<*>.randomEnumOrNull(): Any? =
        if (this.java.isEnum) randomService.randomValue(this.java.enumConstants) else null

}

/**
 * Configuration for [RandomProvider.randomClassInstance].
 *
 * @property constructorParamSize will try to look up the constructor with specified number of arguments,
 * and use that to create the instance of the class.
 * Defaults to `-1`, which ignores this configuration property.
 * This takes precedence over [constructorFilterStrategy] configuration.
 *
 * @property constructorFilterStrategy default strategy for looking up a constructor
 * that is used to create the instance of a class.
 * By default a zero-args constructor will be used.
 *
 * @property fallbackStrategy fallback strategy that is used to look up a constructor
 * if no constructor with [constructorParamSize] or [constructorFilterStrategy] was found.
 */
class RandomProviderConfig @PublishedApi internal constructor() {
    var constructorParamSize: Int = -1
    var constructorFilterStrategy: ConstructorFilterStrategy = ConstructorFilterStrategy.NO_ARGS
    var fallbackStrategy: FallbackStrategy = FallbackStrategy.USE_MIN_NUM_OF_ARGS

    @PublishedApi
    internal val predefinedGenerators = mutableMapOf<KClass<*>, () -> Any>()

    /**
     * Configures generation for a specific type. It can override internal generators (for primitives, for example)
     */
    inline fun <reified K : Any> typeGenerator(noinline generator: () -> K) {
        predefinedGenerators[K::class] = generator
    }
}

enum class FallbackStrategy {
    USE_MIN_NUM_OF_ARGS,
    USE_MAX_NUM_OF_ARGS,
    FAIL_IF_NOT_FOUND
}

enum class ConstructorFilterStrategy {
    NO_ARGS,
    MIN_NUM_OF_ARGS,
    MAX_NUM_OF_ARGS
}
