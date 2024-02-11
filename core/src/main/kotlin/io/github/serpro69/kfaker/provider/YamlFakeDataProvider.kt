package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.exception.RetryLimitException

/**
 * Abstract class for all concrete [FakeDataProvider]'s that use yml files as data source.
 *
 * @param T type of data provider (i.e. [Address])
 */
abstract class YamlFakeDataProvider<T : FakeDataProvider> internal constructor(
    fakerService: FakerService
) : AbstractFakeDataProvider<T>(fakerService) {

    /**
     * Category for `this` fake yaml data provider class.
     *
     * This is the key entry after the `faker` key in `.yml` locale file.
     *
     * For example `address.yml` file has the following structure:
     * ```
     * en:
     *   faker:
     *     address:
     * ```
     * then the category name would be [YamlCategory.ADDRESS]
     */
    internal abstract val yamlCategory: YamlCategory

    /**
     * Secondary category for `this` fake yaml data provider class.
     *
     * This is the key entry after the [yamlCategory] key in `.yml` locale file.
     *
     * For example `dog.yml` file has the following structure:
     * ```
     * en:
     *   faker:
     *     creature:
     *       dog:
     * ```
     * then the category name would be `"dog"`
     */
    internal open val secondaryCategory: Category? = null

    final override val category: YamlCategory
        get() = yamlCategory

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
        return resolveUniqueValue(primaryKey, secondaryKey, thirdKey) {
            when {
                secondaryKey != null && thirdKey != null -> {
                    fakerService.resolve(yamlCategory, primaryKey, secondaryKey, thirdKey)
                }
                secondaryKey != null -> fakerService.resolve(yamlCategory, primaryKey, secondaryKey)
                else -> fakerService.resolve(yamlCategory, primaryKey)
            }
        }
    }
}
