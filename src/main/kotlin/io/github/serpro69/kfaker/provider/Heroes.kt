package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HEROES] category.
 */
@Suppress("unused")
class Heroes internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Heroes>(fakerService) {
    override val categoryName = CategoryName.HEROES
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun names() = resolve("names")
    fun specialties() = resolve("specialties")
    fun klasses() = resolve("klasses")
}
