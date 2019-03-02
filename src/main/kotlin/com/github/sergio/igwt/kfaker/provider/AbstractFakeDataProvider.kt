package com.github.sergio.igwt.kfaker.provider

import com.github.sergio.igwt.kfaker.FakerService
import com.github.sergio.igwt.kfaker.dictionary.Category
import com.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * Abstract class for all concrete [FakeDataProvider]'s.
 *
 * All data providers should implement this class.
 */
abstract class AbstractFakeDataProvider internal constructor(
    private val fakerService: FakerService
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

    /**
     * Higher-order function that resolves the [block] expression for this [categoryName].
     *
     * @return parameterless function that returns a [String]: `() -> String`
     */
    internal fun resolve(block: (Category) -> String) = { block(fakerService.fetchCategory(categoryName)) }
}