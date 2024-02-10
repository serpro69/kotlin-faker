package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.exception.RetryLimitException
import io.github.serpro69.kfaker.provider.misc.StringProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider

/**
 * Abstract class for all concrete [FakeDataProvider]'s that do not use yml files as data source.
 *
 * @param T type of data provider (i.e. [StringProvider])
 */
abstract class AbstractFakeDataProvider<T : FakeDataProvider> internal constructor(
    internal val fakerService: FakerService
) : FakeDataProvider {

    /**
     * Category of `this` fake data provider class.
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

    protected fun resolveUniqueValue(
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
                    } else if (!set.contains(resultString)) resultString.also {
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
                providerExclusionPatterns.isNotEmpty()
                    && providerExclusionPatterns.any { it.containsMatchIn(resultString) } -> {
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
                        !patterns.isNullOrEmpty() && patterns.any { r -> r.containsMatchIn(resultString) } -> {
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
