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
        val instance = this.constructors.find { it.parameters.isEmpty() && it.visibility == KVisibility.PUBLIC }?.call()

        return if (instance != null) instance else {
            val constructor = this.constructors
                .filter { it.visibility == KVisibility.PUBLIC }
                .minByOrNull { it.parameters.size }
                ?: throw NoSuchElementException("No suitable constructor found for $this")

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

class RandomProviderConfig @PublishedApi internal constructor() {
    @PublishedApi
    internal val predefinedGenerators = mutableMapOf<KClass<*>, () -> Any>()

    /**
     * Configures generation for a specific type. It can override internal generators (for primitives, for example)
     */
    inline fun <reified K : Any> typeGenerator(noinline generator: () -> K) {
        predefinedGenerators[K::class] = generator
    }
}
