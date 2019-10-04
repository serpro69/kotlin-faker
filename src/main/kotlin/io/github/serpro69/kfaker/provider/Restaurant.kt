package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.RESTAURANT] category.
 */
@Suppress("unused")
class Restaurant internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Restaurant>(fakerService) {
    override val categoryName = CategoryName.RESTAURANT
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val name = resolve("name")
    val type = resolve("type")
    val description = resolve("description")
    val review = resolve("review")
}
