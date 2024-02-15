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
    val fakerService: FakerService
) : FakeDataProvider {

    /**
     * Category of `this` fake data provider class.
     */
    protected abstract val category: Category

    /**
     * A [LocalUniqueDataProvider] instance that is used with this [unique] provider.
     */
    protected abstract val localUniqueDataProvider: LocalUniqueDataProvider<T>

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
        primaryKey: String,
        result: () -> String,
    ): String = resolveUniqueValue(primaryKey, null, null, 0, result)

    protected fun resolveUniqueValue(
        primaryKey: String,
        secondaryKey: String?,
        result: () -> String,
    ): String = resolveUniqueValue(primaryKey, secondaryKey, null, 0, result)

    protected fun resolveUniqueValue(
        primaryKey: String,
        secondaryKey: String?,
        thirdKey: String?,
        result: () -> String,
    ): String = resolveUniqueValue(primaryKey, secondaryKey, thirdKey, 0, result)

    protected fun <T> resolveUniqueValue(primaryKey: String, result: () -> T): T {
        return resolveUniqueValue(primaryKey, null, null, 0, result)
    }

    private fun <T> resolveUniqueValue(
        primaryKey: String,
        secondaryKey: String?,
        thirdKey: String?,
        counter: Int,
        f: () -> T,
    ): T {
        val fakerConfig = fakerService.faker.config
        val key = listOfNotNull(primaryKey, secondaryKey, thirdKey).joinToString("$")
        val result = f()
        val resultString = result.toString()
        val resolveNewUniqueValue: () -> T = {
            resolveUniqueValue(
                primaryKey = primaryKey,
                secondaryKey = secondaryKey,
                thirdKey = thirdKey,
                counter = counter + 1,
                f = f,
            )
        }

        return if (localUniqueDataProvider.markedUnique.contains(this)) {
            // if function is prefixed with `unique` -> try to resolve a unique value
            when (val set = localUniqueDataProvider.usedValues[key]) {
                null -> {
                    localUniqueDataProvider.usedValues[key] = mutableSetOf(resultString)
                    result
                }
                else -> {
                    if (counter >= fakerConfig.uniqueGeneratorRetryLimit) {
                        throw RetryLimitException("Retry limit of $counter exceeded")
                    } else if (!set.contains(resultString)) result.also {
                        localUniqueDataProvider.usedValues[key] = mutableSetOf(resultString).also { it.addAll(set) }
                    } else resolveNewUniqueValue()
                }
            }
        } else {
            val glUniqueProvider = fakerService.faker.unique
            if (!glUniqueProvider.config.markedUnique.contains(this::class)) {
                // if global unique provider is not enabled for this category -> return result
                result
            } else {
                val exclusionValues = glUniqueProvider.config.excludedValues
                val exclusionPatterns by lazy { glUniqueProvider.config.excludedPatterns }
                val usedProviderValues by lazy {
                    requireNotNull(glUniqueProvider.config.usedProviderValues[this::class])
                }
                val providerExclusionPatterns by lazy {
                    requireNotNull(glUniqueProvider.config.providerExclusionPatterns[this::class])
                }
                val providerFunctionExclusionPatternsMap by lazy {
                    requireNotNull(glUniqueProvider.config.providerFunctionExclusionPatterns[this::class])
                }
                val usedProviderFunctionsValuesMap by lazy {
                    requireNotNull(glUniqueProvider.config.usedProviderFunctionValues[this::class])
                }
                when {
                    // Globally excluded values
                    (exclusionValues.isNotEmpty() && exclusionValues.contains(resultString))
                        // Global exclusion patterns
                        || (exclusionPatterns.isNotEmpty() && exclusionPatterns.any { r ->
                        r.containsMatchIn(
                            resultString
                        )
                    })
                        // Provider-based excluded values for all functions
                        || (usedProviderValues.isNotEmpty() && usedProviderValues.contains(resultString))
                        // Provider-based exclusion patterns for all functions
                        || (providerExclusionPatterns.isNotEmpty()
                        && providerExclusionPatterns.any { it.containsMatchIn(resultString) }
                        ) -> resolveNewUniqueValue()
                    else -> {
                        val patterns = providerFunctionExclusionPatternsMap[key]
                        val usedValues = usedProviderFunctionsValuesMap[key]

                        when {
                            !patterns.isNullOrEmpty() && patterns.any { r -> r.containsMatchIn(resultString) } -> {
                                resolveNewUniqueValue()
                            }
                            usedValues == null -> result.also {
                                // Create 'usedValues' set with the returned value for the 'key'
                                usedProviderFunctionsValuesMap[key] = mutableSetOf(it.toString())
                            }
                            else -> {
                                if (counter >= fakerConfig.uniqueGeneratorRetryLimit) {
                                    throw RetryLimitException("Retry limit of $counter exceeded")
                                } else if (!usedValues.contains(resultString)) result.also { r ->
                                    // Add returned value at the beginning of existing 'usedValues' set for the 'key'
                                    usedProviderFunctionsValuesMap[key] =
                                        mutableSetOf(r.toString()).also { it.addAll(usedValues) }
                                } else resolveNewUniqueValue()
                            }
                        }
                    }
                }
            }
        }
    }
}
