package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.CategoryName
import io.github.serpro69.kfaker.exception.RetryLimitException
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import org.slf4j.LoggerFactory

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
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Name of the category for `this` fake data provider class.
     *
     * This is the key entry after the `faker` key in `.yml` locale file.
     *
     * For example `address.yml` file has the following structure - `en: faker: address:`,
     * then the category name would be [CategoryName.ADDRESS]
     */
    internal abstract val categoryName: CategoryName

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
     * Higher-order function that resolves the [block] expression for this [categoryName].
     *
     * @return parameterless function that returns a [String]: `() -> String`
     */
    private fun resolve(block: (Category) -> String): () -> String = {
        block(fakerService.fetchCategory(categoryName))
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
    private tailrec fun returnOrResolveUnique(
        primaryKey: String,
        secondaryKey: String? = null,
        thirdKey: String? = null,
        counter: Int = 0
    ): String {
        val result = if (secondaryKey != null && thirdKey != null) {
            resolve { fakerService.resolve(it, primaryKey, secondaryKey, thirdKey) }.invoke()
        } else if (secondaryKey != null) {
            resolve { fakerService.resolve(it, primaryKey, secondaryKey) }.invoke()
        } else {
            resolve { fakerService.resolve(it, primaryKey) }.invoke()
        }

        val globalUniqueProvider = fakerService.faker.unique
        val fakerConfig = fakerService.faker.fakerConfig

        val key = listOfNotNull(primaryKey, secondaryKey, thirdKey).joinToString("$")

        return if (localUniqueDataProvider.markedUnique.contains(this)) {
            // if function is prefixed with `unique` -> try to resolve a unique value
            when (val set = localUniqueDataProvider.usedValues[key]) {
                null -> {
                    localUniqueDataProvider.usedValues[key] = mutableSetOf(result)
                    result
                }
                else -> {
                    if (counter >= fakerConfig.uniqueGeneratorRetryLimit) {
                        throw RetryLimitException("Retry limit of $counter exceeded")
                    } else if (!set.contains(result)) result.also {
                        localUniqueDataProvider.usedValues[key] = mutableSetOf(result).also { it.addAll(set) }
                    } else returnOrResolveUnique(
                        primaryKey = primaryKey,
                        secondaryKey = secondaryKey,
                        thirdKey = thirdKey,
                        counter = counter + 1
                    )
                }
            }
        } else if (!globalUniqueProvider.config.markedUnique.contains(this::class)) {
            // if global unique provider is not enabled for this category -> return result
            result
        } else {
            val usedProviderFunctionsValuesMap = requireNotNull(globalUniqueProvider.config.usedProviderFunctionValues[this::class])
            val usedProviderValuesMap = requireNotNull(globalUniqueProvider.config.usedProviderValues[this::class])
            val exclusionValues = globalUniqueProvider.config.excludedValues
            val exclusionPatterns = globalUniqueProvider.config.excludedPatterns

            when {
                // Check globally excluded values
                exclusionValues.isNotEmpty() && exclusionValues.contains(result) -> {
                    returnOrResolveUnique(
                        primaryKey = primaryKey,
                        secondaryKey = secondaryKey,
                        thirdKey = thirdKey,
                        counter = counter + 1
                    )
                }
                // Check globally excluded patterns
                exclusionPatterns.isNotEmpty() && exclusionPatterns.any { r -> r.containsMatchIn(result) } -> {
                    returnOrResolveUnique(
                        primaryKey = primaryKey,
                        secondaryKey = secondaryKey,
                        thirdKey = thirdKey,
                        counter = counter + 1
                    )
                }
                // Provider-based exclusions for all functions
                usedProviderValuesMap.isNotEmpty() && usedProviderValuesMap.contains(result) -> {
                    returnOrResolveUnique(
                        primaryKey = primaryKey,
                        secondaryKey = secondaryKey,
                        thirdKey = thirdKey,
                        counter = counter + 1
                    )
                }
                // Provider-based exclusions for specific functions
                else -> {
                    when (val set = usedProviderFunctionsValuesMap[key]) {
                        null -> {
                            usedProviderFunctionsValuesMap[key] = mutableSetOf(result)
                            result
                        }
                        else -> {
                            if (counter >= fakerConfig.uniqueGeneratorRetryLimit) {
                                throw RetryLimitException("Retry limit of $counter exceeded")
                            } else if (!set.contains(result)) result.also {
                                usedProviderFunctionsValuesMap[key] = mutableSetOf(result).also { it.addAll(set) }
                            } else returnOrResolveUnique(
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
