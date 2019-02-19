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
     */
    internal abstract val categoryName: CategoryName

    /**
     * Higher-order function that resolves the [block] expression for this [categoryName].
     *
     * @return [Unit]-type function that returns [String]: `() -> String`
     */
    internal fun resolve(block: (Category) -> String) = { block(fakerService.fetchCategory(categoryName)) }
}