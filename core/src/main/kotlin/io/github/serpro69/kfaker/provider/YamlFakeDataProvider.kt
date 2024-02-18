package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.exception.RetryLimitException

/**
 * Abstract class for all concrete [FakeDataProvider]'s that use yml files as data source.
 *
 * @param T type of data provider (i.e. [Address])
 */
abstract class YamlFakeDataProvider<T : FakeDataProvider>(
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
     * then the [yamlCategory] would be [YamlCategory.ADDRESS]
     *
     * _NB! If the [secondaryCategory] is NOT set,
     * the dictionary filename should match the [yamlCategory] name,
     * i.e. the file name should be `address.yml` for the [YamlCategory.ADDRESS]._
     */
    protected abstract val yamlCategory: YamlCategory

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
     * then the [yamlCategory] would be [YamlCategory.CREATURE], and the  secondary [Category.name] would be `"dog"`
     *
     * _TIP: Use [Category.ofName] helper function when needed._
     *
     * _NB! If the [secondaryCategory] is set,
     * the dictionary filename should match the [Category.name] of this [secondaryCategory],
     * i.e. the file name should be `dog.yml` for the `[Category.ofName]` dog._
     */
    protected open val secondaryCategory: Category? = null

    /*
     * FYI, we override the [category] from the super class
     * so that we don't need to do it in each of this [YamlFakeDataProvider] implementations
     */
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
     * TIP: Can be useful for providers that override this [secondaryCategory]
     * to use a compile-safe object instead of a string for the [primaryKey].
     *
     * Example:
     * ```diff
     * class Minecraft internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Minecraft>(fakerService) {
     *   override val yamlCategory = YamlCategory.GAMES
     *   override val secondaryCategory: Category = Category.ofName("MINECRAFT")
     *   override val localUniqueDataProvider = LocalUniqueDataProvider<Minecraft>()
     *   override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)
     *
     *   fun achievement() = resolve("minecraft", "achievement")
     *   fun biome() = resolve(secondaryCategory, "biome")
     * }
     * ```
     *
     * Will return a unique value if the call to the function is prefixed with `unique` property.
     * Example:
     * ```
     * faker.address.unique.countryByCode(countryCode) => will return a unique value for the `city` parameter
     * ```
     */
    protected fun resolve(primaryKey: Category, secondaryKey: String): String {
        return resolve(primaryKey.name.lowercase(), secondaryKey)
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
    protected fun resolve(primaryKey: Category, secondaryKey: String, thirdKey: String): String {
        return resolve(primaryKey.name.lowercase(), secondaryKey, thirdKey)
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
     * IF [AbstractFaker.unique] is enabled for this [T] provider type
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
