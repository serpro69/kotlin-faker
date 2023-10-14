package io.github.serpro69.kfaker.provider.misc

import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.RandomService
import io.github.serpro69.kfaker.provider.misc.ConstructorFilterStrategy.MAX_NUM_OF_ARGS
import io.github.serpro69.kfaker.provider.misc.ConstructorFilterStrategy.MIN_NUM_OF_ARGS
import io.github.serpro69.kfaker.provider.misc.ConstructorFilterStrategy.NO_ARGS
import io.github.serpro69.kfaker.provider.misc.FallbackStrategy.FAIL_IF_NOT_FOUND
import io.github.serpro69.kfaker.provider.misc.FallbackStrategy.USE_MAX_NUM_OF_ARGS
import io.github.serpro69.kfaker.provider.misc.FallbackStrategy.USE_MIN_NUM_OF_ARGS
import kotlin.Boolean
import kotlin.Char
import kotlin.Double
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.Short
import kotlin.String
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KVisibility
import kotlin.reflect.jvm.jvmName

/**
 * Provider functionality for generating random class instances.
 *
 * Inspired by [Creating a random instance of any class in Kotlin blog post](https://blog.kotlin-academy.com/creating-a-random-instance-of-any-class-in-kotlin-b6168655b64a).
 *
 * @property config configuration for this [RandomClassProvider]
 */
@Suppress("unused")
class RandomClassProvider {
    private val fakerConfig: FakerConfig
    private val randomService: RandomService

    @PublishedApi
    @JvmSynthetic
    internal val config: RandomProviderConfig

    /**
     * Creates an instance of this [RandomClassProvider] with the given [fakerConfig].
     */
    internal constructor(fakerConfig: FakerConfig) {
        this.fakerConfig = fakerConfig
        randomService = RandomService(fakerConfig)
        config = fakerConfig.randomProviderConfig?.copy() ?: RandomProviderConfig()
    }

    /**
     * Private constructor that is only used for [new] and [copy] functions.
     */
    private constructor(fakerConfig: FakerConfig, config: RandomProviderConfig) {
        this.fakerConfig = fakerConfig
        this.config = config.copy()
        randomService = RandomService(fakerConfig)
    }

    /**
     * Applies [configurator] to this [RandomClassProvider].
     */
    fun configure(configurator: RandomProviderConfig.() -> Unit) {
        config.apply(configurator)
    }

    /**
     * Creates a new instance of this [RandomClassProvider].
     *
     * IF [FakerConfig.randomProviderConfig] was configured
     * THEN new instance will be created with a copy of that configuration,
     * ELSE a new instance is created with a new instance of default configuration as defined in [RandomProviderConfig].
     */
    fun new(): RandomClassProvider = RandomClassProvider(
        fakerConfig,
        fakerConfig.randomProviderConfig ?: RandomProviderConfig()
    )

    /**
     * Creates a copy of this [RandomClassProvider] instance with a copy of its [config].
     */
    fun copy(): RandomClassProvider = RandomClassProvider(fakerConfig, config)

    /**
     * Resets [config] to defaults for this [RandomClassProvider] instance.
     */
    fun reset() = config.reset()

    /**
     * Creates an instance of [T]. If [T] has a parameterless public constructor then it will be used to create an instance of this class,
     * otherwise a constructor with minimal number of parameters will be used with randomly-generated values.
     *
     * @throws NoSuchElementException if [T] has no public constructor.
     */
    inline fun <reified T : Any> randomClassInstance() = T::class.randomClassInstance(config)

    /**
     * Creates an instance of [T]. If [T] has a parameterless public constructor then it will be used to create an instance of this class,
     * otherwise a constructor with minimal number of parameters will be used with randomly-generated values.
     *
     * @param configurator configure instance creation.
     *
     * @throws NoSuchElementException if [T] has no public constructor.
     */
    inline fun <reified T : Any> randomClassInstance(configurator: RandomProviderConfig.() -> Unit): T {
        return T::class.randomClassInstance(RandomProviderConfig().apply(configurator))
    }

    @Suppress("UNCHECKED_CAST")
    @JvmSynthetic
    @PublishedApi
    internal fun <T : Any> KClass<T>.randomClassInstance(config: RandomProviderConfig): T {
        val defaultInstance: T? by lazy {
            if (config.constructorParamSize == -1 && config.constructorFilterStrategy == NO_ARGS) {
                randomPrimitiveOrNull() as T? ?: try {
                    constructors.firstOrNull { it.parameters.isEmpty() && it.visibility == KVisibility.PUBLIC }?.call()
                } catch (e: Exception) {
                    throw InstantiationException("Failed to instantiate $this")
                        .initCause(e)
                }
            } else null
        }

        val predefinedInstance: T? by lazy {
            predefinedTypeOrNull(
                config,
                // it's not a constructor parameter, so set some hardcoded values for ParameterInfo
                ParameterInfo(index = -1, name = jvmName, isOptional = false, isVararg = false)
            ) as T?
        }

        return objectInstance ?: defaultInstance ?: run {
            val constructors = constructors.filter { it.visibility == KVisibility.PUBLIC }

            val constructor = constructors.firstOrNull {
                it.parameters.size == config.constructorParamSize
            } ?: when (config.constructorFilterStrategy) {
                MIN_NUM_OF_ARGS -> constructors.minByOrNull { it.parameters.size }
                MAX_NUM_OF_ARGS -> constructors.maxByOrNull { it.parameters.size }
                else -> {
                    when (config.fallbackStrategy) {
                        FAIL_IF_NOT_FOUND -> {
                            throw NoSuchElementException("Constructor with 'parameters.size == ${config.constructorParamSize}' not found for $this")
                        }
                        USE_MIN_NUM_OF_ARGS -> constructors.minByOrNull { it.parameters.size }
                        USE_MAX_NUM_OF_ARGS -> constructors.maxByOrNull { it.parameters.size }
                    }
                }
            }
            ?: predefinedInstance?.let { return@run it }
            ?: throw NoSuchElementException("No suitable constructor or predefined instance found for $this")

            val params = constructor.parameters.map {
                val pInfo = it.toParameterInfo()
                val klass = it.type.classifier as KClass<*>
                when {
                    config.namedParameterGenerators.containsKey(it.name) -> {
                        config.namedParameterGenerators[it.name]?.invoke(pInfo)
                    }
                    it.type.isMarkedNullable && config.nullableGenerators.containsKey(klass) -> {
                        config.nullableGenerators[klass]?.invoke(pInfo)
                    }
                    else -> {
                        klass.objectInstance
                            ?: klass.predefinedTypeOrNull(config, pInfo)
                            ?: klass.randomPrimitiveOrNull()
                            ?: klass.randomEnumOrNull()
                            ?: klass.randomSealedClassOrNull(config)
                            ?: klass.randomCollectionOrNull(it.type, config)
                            ?: klass.randomClassInstance(config)
                    }
                }
            }

            try {
                constructor.call(*params.toTypedArray())
            } catch (e: Exception) {
                throw InstantiationException("Failed to instantiate $this with $params")
                    .initCause(e)
            }
        }
    }

    private fun <T : Any> KClass<T>.predefinedTypeOrNull(config: RandomProviderConfig, pInfo: ParameterInfo): Any? {
        return config.predefinedGenerators[this]?.invoke(pInfo)
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
        String::class -> randomService.randomString()
        Char::class -> randomService.nextChar()
        Boolean::class -> randomService.nextBoolean()
        else -> null
    }

    /**
     * Handles generation of enums types since they do not have a public constructor.
     */
    private fun KClass<*>.randomEnumOrNull(): Any? {
        return if (this.java.isEnum) randomService.randomValue(this.java.enumConstants) else null
    }

    private fun KClass<*>.randomSealedClassOrNull(config: RandomProviderConfig): Any? {
        return if (isSealed) randomService.randomValue(sealedSubclasses).randomClassInstance(config) else null
    }

    private fun KClass<*>.randomCollectionOrNull(kType: KType, config: RandomProviderConfig): Any? {
        return when (this) {
            List::class -> {
                val elementType = kType.arguments[0].type?.classifier as KClass<*>
                List(config.collectionsSize) { elementType.randomClassInstance(config) }
            }
            Set::class -> {
                val elementType = kType.arguments[0].type?.classifier as KClass<*>
                List(config.collectionsSize) { elementType.randomClassInstance(config) }.toSet()
            }
            Map::class -> {
                val keyElementType = kType.arguments[0].type?.classifier as KClass<*>
                val valElementType = kType.arguments[1].type?.classifier as KClass<*>
                val keys = List(config.collectionsSize) { keyElementType.randomClassInstance(config) }
                val values = List(config.collectionsSize) { valElementType.randomClassInstance(config) }
                keys.zip(values).associate { (k, v) -> k to v }
            }
            else -> null
        }
    }
}

/**
 * Configuration for [RandomClassProvider.randomClassInstance].
 *
 * @property collectionsSize the size of the generated [Collection] type arguments.
 * Defaults to `1`.
 *
 * @property constructorParamSize will try to look up the constructor with specified number of arguments,
 * and use that to create the instance of the class.
 * Defaults to `-1`, which ignores this configuration property.
 * This takes precedence over [constructorFilterStrategy] configuration.
 *
 * @property constructorFilterStrategy default strategy for looking up a constructor
 * that is used to create the instance of a class.
 * By default, a zero-args constructor will be used.
 *
 * @property fallbackStrategy fallback strategy that is used to look up a constructor
 * if no constructor with [constructorParamSize] or [constructorFilterStrategy] was found.
 */
class RandomProviderConfig @PublishedApi internal constructor() {
    var collectionsSize: Int = 1
    var constructorParamSize: Int = -1
    var constructorFilterStrategy: ConstructorFilterStrategy = NO_ARGS
    var fallbackStrategy: FallbackStrategy = USE_MIN_NUM_OF_ARGS

    @PublishedApi
    internal val namedParameterGenerators = mutableMapOf<String, (pInfo: ParameterInfo) -> Any?>()

    @PublishedApi
    internal val predefinedGenerators = mutableMapOf<KClass<*>, (pInfo: ParameterInfo) -> Any>()

    @PublishedApi
    internal val nullableGenerators = mutableMapOf<KClass<*>, (pInfo: ParameterInfo) -> Any?>()

    /**
     * Configures generation for a specific named parameter. Overrides all other generators
     */
    inline fun <reified K : Any> namedParameterGenerator(
        parameterName: String,
        noinline generator: (pInfo: ParameterInfo) -> K?
    ) {
        namedParameterGenerators[parameterName] = generator
    }

    /**
     * Configures generation for a specific type. It can override internal generators (for primitives, for example)
     */
    inline fun <reified K : Any> typeGenerator(noinline generator: (pInfo: ParameterInfo) -> K) {
        predefinedGenerators[K::class] = generator
    }

    /**
     * Configures generation for a specific nullable type. It can override internal generators (for primitives, for example)
     */
    inline fun <reified K : Any?> nullableTypeGenerator(noinline generator: (pInfo: ParameterInfo) -> K?) {
        nullableGenerators[K::class] = generator
    }
}

private fun RandomProviderConfig.reset() {
    collectionsSize = 1
    constructorParamSize = -1
    constructorFilterStrategy = NO_ARGS
    fallbackStrategy = USE_MIN_NUM_OF_ARGS
    namedParameterGenerators.clear()
    predefinedGenerators.clear()
    nullableGenerators.clear()
}

private fun RandomProviderConfig.copy(
    collectionsSize: Int? = null,
    constructorParamSize: Int? = null,
    constructorFilterStrategy: ConstructorFilterStrategy? = null,
    fallbackStrategy: FallbackStrategy? = null,
    namedParameterGenerators: Map<String, (pInfo: ParameterInfo) -> Any?>? = null,
    predefinedGenerators: Map<KClass<*>, (pInfo: ParameterInfo) -> Any>? = null,
    nullableGenerators: Map<KClass<*>, (pInfo: ParameterInfo) -> Any?>? = null
): RandomProviderConfig = RandomProviderConfig().apply {
    this@apply.collectionsSize = collectionsSize ?: this@copy.collectionsSize
    this@apply.constructorParamSize = constructorParamSize ?: this@copy.constructorParamSize
    this@apply.constructorFilterStrategy = constructorFilterStrategy ?: this@copy.constructorFilterStrategy
    this@apply.fallbackStrategy = fallbackStrategy ?: this@copy.fallbackStrategy
    this@apply.namedParameterGenerators.putAll(namedParameterGenerators ?: this@copy.namedParameterGenerators)
    this@apply.predefinedGenerators.putAll(predefinedGenerators ?: this@copy.predefinedGenerators)
    this@apply.nullableGenerators.putAll(nullableGenerators ?: this@copy.nullableGenerators)
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
