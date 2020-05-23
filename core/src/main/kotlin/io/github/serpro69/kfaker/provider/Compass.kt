package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COMPASS] category.
 */
@Suppress("unused")
class Compass internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Compass>(fakerService) {
    override val categoryName = CategoryName.COMPASS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Compass>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    // TODO: 3/7/2019 this needs some custom logic for the resolver function
}