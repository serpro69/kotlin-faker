package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.RICK_AND_MORTY] category.
 */
@Suppress("unused")
class RickAndMorty internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<RickAndMorty>(fakerService) {
    override val categoryName = CategoryName.RICK_AND_MORTY
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val locations = resolve("locations")
    val quotes = resolve("quotes")
}
