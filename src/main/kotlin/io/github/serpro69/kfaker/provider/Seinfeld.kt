package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SEINFELD] category.
 */
@Suppress("unused")
class Seinfeld internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Seinfeld>(fakerService) {
    override val categoryName = CategoryName.SEINFELD
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val character = resolve("character")
    val quote = resolve("quote")
    val business = resolve("business")
}
