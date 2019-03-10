package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.COMPASS] category.
 */
@Deprecated(level = DeprecationLevel.ERROR, message = "Provider not implemented.")
class Compass internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.COMPASS

    // TODO: 3/7/2019 this needs some custom logic for the resolver function
}