package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class Overwatch internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Overwatch>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val uniqueDataProvider = UniqueDataProvider<Overwatch>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun heroes() = resolve("overwatch", "heroes")
    fun locations() = resolve("overwatch", "locations")
    fun quotes() = resolve("overwatch", "quotes")
}
