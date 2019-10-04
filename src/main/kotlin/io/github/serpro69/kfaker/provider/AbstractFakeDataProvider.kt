package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * Abstract class for all concrete [FakeDataProvider]'s.
 *
 * All data providers should implement this class.
 */
abstract class AbstractFakeDataProvider<T : FakeDataProvider> internal constructor(
    private val fakerService: FakerService
) : FakeDataProvider {
    internal val uniqueDataProvider by lazy { UniqueDataProvider<T>() }
    abstract val unique: AbstractFakeDataProvider<T>

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
        val result = resolve { fakerService.resolve(it, key) }.invoke()

        return if (!uniqueDataProvider.markedUnique.contains(unique)) {
            result
        } else {
            when (val set = uniqueDataProvider.usedValues[key]) {
                null -> {
                    uniqueDataProvider.usedValues[key] = mutableSetOf(result)
                    result
                }
                else -> {
                    (if (!set.contains(result)) result.also {
                        uniqueDataProvider.usedValues[key] = mutableSetOf(result).also { it.addAll(set) }
                    } else resolve(key))
                }
            }
        }
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
        val result = resolve { fakerService.resolve(it, primaryKey, secondaryKey) }.invoke()

        return if (!uniqueDataProvider.markedUnique.contains(unique)) {
            result
        } else {
            when (val set = uniqueDataProvider.usedValues[primaryKey]) {
                null -> {
                    uniqueDataProvider.usedValues[primaryKey] = mutableSetOf(result)
                    result
                }
                else -> {
                    (if (!set.contains(result)) result.also {
                        uniqueDataProvider.usedValues[primaryKey] = mutableSetOf(result).also { it.addAll(set) }
                    } else resolve(primaryKey))
                }
            }
        }
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
        val result = resolve { fakerService.resolve(it, primaryKey, secondaryKey, thirdKey) }.invoke()

        return if (!uniqueDataProvider.markedUnique.contains(unique)) {
            result
        } else {
            when (val set = uniqueDataProvider.usedValues[primaryKey]) {
                null -> {
                    uniqueDataProvider.usedValues[primaryKey] = mutableSetOf(result)
                    result
                }
                else -> {
                    (if (!set.contains(result)) result.also {
                        uniqueDataProvider.usedValues[primaryKey] = mutableSetOf(result).also { it.addAll(set) }
                    } else resolve(primaryKey))
                }
            }
        }
    }
}
