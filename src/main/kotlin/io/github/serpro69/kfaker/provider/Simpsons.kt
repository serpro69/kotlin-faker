package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SIMPSONS] category.
 */
@Suppress("unused")
class Simpsons internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Simpsons>(fakerService) {
    override val categoryName = CategoryName.SIMPSONS
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val locations = resolve("locations")
    val quotes = resolve("quotes")
}
