package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class StreetFighter internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<StreetFighter>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<StreetFighter>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("street_fighter", "characters")
    fun stages() = resolve("street_fighter", "stages")
    fun quotes() = resolve("street_fighter", "quotes")
    fun moves() = resolve("street_fighter", "moves")
}