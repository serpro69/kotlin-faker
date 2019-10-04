package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HARRY_POTTER] category.
 */
@Suppress("unused")
class HarryPotter internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<HarryPotter>(fakerService) {
    override val categoryName = CategoryName.HARRY_POTTER
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val locations = resolve("locations")
    val quotes = resolve("quotes")
    val books = resolve("books")
    val houses = resolve("houses")
    val spells = resolve("spells")
}
