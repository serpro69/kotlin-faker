package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.misc.RandomClassProvider
import io.github.serpro69.kfaker.provider.misc.RandomProviderConfig
import kotlin.reflect.KClass

@PublishedApi internal val rcp = RandomClassProvider(fakerConfig {})

/**
 * Creates an instance of [T]. If [T] has a parameterless public/internal constructor then it will
 * be used to create an instance of this class, otherwise a constructor with minimal number of
 * parameters will be used with randomly-generated values.
 *
 * @param configurator configure random class instance creation
 * @throws NoSuchElementException if [T] has no public or internal constructor.
 */
inline fun <reified T : Any> randomClassInstance(
    configurator: RandomProviderConfig.() -> Unit = {}
): T = rcp.randomClassInstance<T>(configurator)

/**
 * Creates an instance of [T]. If [T] has a parameterless public/internal constructor then it will
 * be used to create an instance of this class, otherwise a constructor with minimal number of
 * parameters will be used with randomly-generated values.
 *
 * @param fakerConfig configure [RandomClassProvider] via the [FakerConfig]
 * @throws NoSuchElementException if [T] has no public or internal constructor.
 */
inline fun <reified T : Any> randomClassInstance(fakerConfig: FakerConfig): T =
    RandomClassProvider(fakerConfig).randomClassInstance<T>()

/**
 * Creates an instance of [T] from the [KClass] input. If [T] has a parameterless public/internal
 * constructor then it will be used to create an instance of this class, otherwise a constructor
 * with minimal number of parameters will be used with randomly-generated values.
 *
 * @param kClass a [KClass] of [T]
 * @param configurator configure random class instance creation
 * @throws NoSuchElementException if [T] has no public or internal constructor.
 */
fun <T : Any> randomClassInstance(
    kClass: KClass<T>,
    configurator: RandomProviderConfig.() -> Unit = {},
): T = rcp.randomClassInstance(kClass, configurator)

/**
 * Creates an instance of [T] from the [KClass] input. If [T] has a parameterless public/internal
 * constructor then it will be used to create an instance of this class, otherwise a constructor
 * with minimal number of parameters will be used with randomly-generated values.
 *
 * @param kClass a [KClass] of [T]
 * @param fakerConfig configure [RandomClassProvider] via the [FakerConfig]
 * @throws NoSuchElementException if [T] has no public or internal constructor.
 */
fun <T : Any> randomClassInstance(kClass: KClass<T>, fakerConfig: FakerConfig): T =
    RandomClassProvider(fakerConfig).randomClassInstance(kClass)
