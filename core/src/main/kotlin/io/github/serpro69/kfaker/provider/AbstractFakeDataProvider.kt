package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.DictEntry
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.exception.RetryLimitException
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider

/**
 * Abstract class for all concrete [FakeDataProvider]'s.
 *
 * All data providers should implement this class.
 *
 * @param T type of data provider (i.e. [Address])
 */
abstract class AbstractFakeDataProvider<T : FakeDataProvider> internal constructor(
    internal val fakerService: FakerService
) : FakeDataProvider {

    /**
     * Name of the category for `this` fake data provider class.
     *
     * This is the key entry after the `faker` key in `.yml` locale file.
     *
     * For example `address.yml` file has the following structure - `en: faker: address:`,
     * then the category name would be [YamlCategory.ADDRESS]
     */
    internal abstract val category: Category

    /**
     * A [LocalUniqueDataProvider] instance that is used with this [unique] provider.
     */
    internal abstract val localUniqueDataProvider: LocalUniqueDataProvider<T>

    /**
     * An instance of [T] for generating unique values
     */
    abstract val unique: T

    /**
     * Clears used unique values for the function [name] of this provider.
     */
    fun clear(name: String) = localUniqueDataProvider.clear(name)

    /**
     * Clears all used unique values of this provider.
     */
    fun clearAll() = localUniqueDataProvider.clearAll()

    /**
     * Higher-order function that resolves the [block] expression for this [category].
     *
     * @return parameterless function that returns a [String]: `() -> String`
     */
    private fun resolve(block: (DictEntry) -> String): () -> String = {
        block(fakerService.fetchEntry(category))
    }

    /**
     * Returns resolved (unique) value for the parameter with the specified [key].
     *
     * Will return a unique value if the call to the function is prefixed with `unique` property.
     * Example:
     * ```
     * faker.address.unique.city() => will return a unique value for the `city` parameter
     * ```
     */
    protected fun resolve(key: String): String {
        return returnOrResolveUnique(key)
    }

    /**
     * Returns resolved (unique) value for the parameter with the specified [primaryKey] and [secondaryKey].
     *
     * Will return a unique value if the call to the function is prefixed with `unique` property.
     * Example:
     * ```
     * faker.address.unique.countryByCode(countryCode) => will return a unique value for the `city` parameter
     * ```
     */
    protected fun resolve(primaryKey: String, secondaryKey: String): String {
        return returnOrResolveUnique(primaryKey, secondaryKey)
    }

    /**
     * Returns resolved (unique) value for the parameter with the specified [primaryKey], [secondaryKey] and [thirdKey]
     *
     * Will return a unique value if the call to the function is prefixed with `unique` property.
     * Example:
     * ```
     * faker.educator.tertiaryDegree(type) => will return a unique value for the `tertiaryDegree` parameter
     * ```
     */
    protected fun resolve(primaryKey: String, secondaryKey: String, thirdKey: String): String {
        return returnOrResolveUnique(primaryKey, secondaryKey, thirdKey)
    }

    /**
     * Returns the result of this [resolve] function.
     *
     * IF [Faker.unique] is enabled for this [T] provider type
     * OR this [unique] is used
     * THEN will attempt to return a unique value.
     *
     * @throws RetryLimitException if exceeds number of retries to generate a unique value.
     */
    private fun returnOrResolveUnique(
        primaryKey: String,
        secondaryKey: String? = null,
        thirdKey: String? = null,
    ): String {
        val result: () -> String = {
            when {
                secondaryKey != null && thirdKey != null -> {
                    resolve { fakerService.resolve(it, primaryKey, secondaryKey, thirdKey) }.invoke()
                }
                secondaryKey != null -> resolve { fakerService.resolve(it, primaryKey, secondaryKey) }.invoke()
                else -> resolve { fakerService.resolve(it, primaryKey) }.invoke()
            }
        }

        return resolveUniqueValue(result, primaryKey, secondaryKey, thirdKey)
    }

    internal fun resolveUniqueValue(
        result: () -> String,
        primaryKey: String,
        secondaryKey: String? = null,
        thirdKey: String? = null,
    ): String = resolveUniqueValue(result, primaryKey, secondaryKey, thirdKey, 0)

    private tailrec fun resolveUniqueValue(
        result: () -> String,
        primaryKey: String,
        secondaryKey: String? = null,
        thirdKey: String? = null,
        counter: Int,
    ): String {
        val globalUniqueProvider = fakerService.faker.unique
        val fakerConfig = fakerService.faker.config

        val key = listOfNotNull(primaryKey, secondaryKey, thirdKey).joinToString("$")
        val resultString = result()

        return if (localUniqueDataProvider.markedUnique.contains(this)) {
            // if function is prefixed with `unique` -> try to resolve a unique value
            when (val set = localUniqueDataProvider.usedValues[key]) {
                null -> {
                    localUniqueDataProvider.usedValues[key] = mutableSetOf(resultString)
                    resultString
                }
                else -> {
                    if (counter >= fakerConfig.uniqueGeneratorRetryLimit) {
                        throw RetryLimitException("Retry limit of $counter exceeded")
                    } else if (!set.contains(resultString))resultString.also {
                        localUniqueDataProvider.usedValues[key] = mutableSetOf(resultString).also { it.addAll(set) }
                    } else resolveUniqueValue(
                        result = result,
                        primaryKey = primaryKey,
                        secondaryKey = secondaryKey,
                        thirdKey = thirdKey,
                        counter = counter + 1
                    )
                }
            }
        } else if (!globalUniqueProvider.config.markedUnique.contains(this::class)) {
            // if global unique provider is not enabled for this category -> return result
            resultString
        } else {
            val exclusionValues = globalUniqueProvider.config.excludedValues
            val exclusionPatterns = globalUniqueProvider.config.excludedPatterns
            val usedProviderValues = requireNotNull(globalUniqueProvider.config.usedProviderValues[this::class])
            val providerExclusionPatterns =
                requireNotNull(globalUniqueProvider.config.providerExclusionPatterns[this::class])
            val providerFunctionExclusionPatternsMap =
                requireNotNull(globalUniqueProvider.config.providerFunctionExclusionPatterns[this::class])
            val usedProviderFunctionsValuesMap =
                requireNotNull(globalUniqueProvider.config.usedProviderFunctionValues[this::class])

            when {
                // Globally excluded values
                exclusionValues.isNotEmpty() && exclusionValues.contains(resultString) -> {
                    resolveUniqueValue(
                        result = result,
                        primaryKey = primaryKey,
                        secondaryKey = secondaryKey,
                        thirdKey = thirdKey,
                        counter = counter + 1
                    )
                }
                // Global exclusion patterns
                exclusionPatterns.isNotEmpty() && exclusionPatterns.any { r -> r.containsMatchIn(resultString) } -> {
                    resolveUniqueValue(
                        result = result,
                        primaryKey = primaryKey,
                        secondaryKey = secondaryKey,
                        thirdKey = thirdKey,
                        counter = counter + 1
                    )
                }
                // Provider-based excluded values for all functions
                usedProviderValues.isNotEmpty() && usedProviderValues.contains(resultString) -> {
                    resolveUniqueValue(
                        result = result,
                        primaryKey = primaryKey,
                        secondaryKey = secondaryKey,
                        thirdKey = thirdKey,
                        counter = counter + 1
                    )
                }
                // Provider-based exclusion patterns for all functions
                providerExclusionPatterns.isNotEmpty() && providerExclusionPatterns.any { it.containsMatchIn(resultString) } -> {
                    resolveUniqueValue(
                        result = result,
                        primaryKey = primaryKey,
                        secondaryKey = secondaryKey,
                        thirdKey = thirdKey,
                        counter = counter + 1
                    )
                }
                else -> {
                    val patterns = providerFunctionExclusionPatternsMap[key]
                    val usedValues = usedProviderFunctionsValuesMap[key]

                    when {
                        patterns != null && patterns.isNotEmpty() && patterns.any { r -> r.containsMatchIn(resultString) } -> {
                            resolveUniqueValue(
                                result = result,
                                primaryKey = primaryKey,
                                secondaryKey = secondaryKey,
                                thirdKey = thirdKey,
                                counter = counter + 1
                            )
                        }
                        usedValues == null -> resultString.also {
                            // Create 'usedValues' set with the returned value for the 'key'
                            usedProviderFunctionsValuesMap[key] = mutableSetOf(it)
                        }
                        else -> {
                            if (counter >= fakerConfig.uniqueGeneratorRetryLimit) {
                                throw RetryLimitException("Retry limit of $counter exceeded")
                            } else if (!usedValues.contains(resultString)) resultString.also { r ->
                                // Add returned value at the beginning of existing 'usedValues' set for the 'key'
                                usedProviderFunctionsValuesMap[key] = mutableSetOf(r).also { it.addAll(usedValues) }
                            } else resolveUniqueValue(
                                result = result,
                                primaryKey = primaryKey,
                                secondaryKey = secondaryKey,
                                thirdKey = thirdKey,
                                counter = counter + 1
                            )
                        }
                    }
                }
            }
        }
    }
}
