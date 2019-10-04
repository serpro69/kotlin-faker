package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SOURCE] category.
 */
@Suppress("unused")
class Source internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Source>(fakerService) {
    override val categoryName = CategoryName.SOURCE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    // TODO: 3/10/2019 not sure how this is used. See original faker ruby gem for insights
}
