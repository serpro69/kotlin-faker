package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.RESTAURANT] category.
 */
@Suppress("unused")
class Restaurant internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Restaurant>(fakerService) {
    override val categoryName = CategoryName.RESTAURANT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Restaurant>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun name() = resolve("name")
    fun type() = resolve("type")
    fun description() = resolve("description")
    fun review() = resolve("review")
}
