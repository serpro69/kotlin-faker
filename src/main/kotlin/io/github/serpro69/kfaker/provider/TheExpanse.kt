package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.THE_EXPANSE] category.
 */
@Suppress("unused")
class TheExpanse internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<TheExpanse>(fakerService) {
    override val categoryName = CategoryName.THE_EXPANSE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val locations = resolve("locations")
    val ships = resolve("ships")
    val quotes = resolve("quotes")
}
