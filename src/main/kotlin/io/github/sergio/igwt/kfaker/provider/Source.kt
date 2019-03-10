package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.SOURCE] category.
 */
@Deprecated(level = DeprecationLevel.ERROR, message = "Not implemented")
class Source internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SOURCE

    // TODO: 3/10/2019 not sure how this is used. See original faker ruby gem for insights
}
