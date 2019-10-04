package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.LORD_OF_THE_RINGS] category.
 */
@Suppress("unused")
class LordOfTheRings internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<LordOfTheRings>(fakerService) {
    override val categoryName = CategoryName.LORD_OF_THE_RINGS
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val locations = resolve("locations")
    val quotes = resolve("quotes")
}
