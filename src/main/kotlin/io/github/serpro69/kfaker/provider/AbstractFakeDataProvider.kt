package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * Abstract class for all concrete [FakeDataProvider]'s.
 *
 * All data providers should implement this class.
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
     * then the category name would be [CategoryName.ADDRESS]
     */
    internal abstract val categoryName: CategoryName

    internal abstract val uniqueDataProvider: UniqueDataProvider<T>

    abstract val unique: T

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

        val key = listOfNotNull(primaryKey, secondaryKey, thirdKey).joinToString("$")

        return if (uniqueDataProvider.markedUnique.contains(this)) {
            when (val set = uniqueDataProvider.usedValues[key]) {
                null -> {
                    uniqueDataProvider.usedValues[key] = mutableSetOf(result)
                    result
                }
                else -> {
                    if (counter >= fakerService.faker.fakerConfig.uniqueGeneratorRetryLimit) throw Error("Retry limit of $counter exceeded")
                    else if (!set.contains(result)) result.also {
                        uniqueDataProvider.usedValues[key] = mutableSetOf(result).also { it.addAll(set) }
                    }
                    else returnOrResolveUnique(primaryKey, secondaryKey, thirdKey, counter + 1)
                }
            }
        } else if (!globalUniqueProvider.markedUnique.contains(this::class)) {
            result
        } else {
            val usedValuesMap = requireNotNull(globalUniqueProvider.usedValues[this::class])
            when (val set = usedValuesMap[key]) {
                null -> {
                    usedValuesMap[key] = mutableSetOf(result)
                    result
                }
                else -> {
                    if (counter >= fakerService.faker.fakerConfig.uniqueGeneratorRetryLimit) throw Error("Retry limit of $counter exceeded")
                    else if (!set.contains(result)) result.also {
                        usedValuesMap[key] = mutableSetOf(result).also { it.addAll(set) }
                    }
                    else returnOrResolveUnique(primaryKey, secondaryKey, thirdKey, counter + 1)
                }
            }
        }
    }
}
