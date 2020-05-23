package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HARRY_POTTER] category.
 */
@Suppress("unused")
class HarryPotter internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<HarryPotter>(fakerService) {
    override val categoryName = CategoryName.HARRY_POTTER
    override val localUniqueDataProvider = LocalUniqueDataProvider<HarryPotter>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun quotes() = resolve("quotes")
    fun books() = resolve("books")
    fun houses() = resolve("houses")
    fun spells() = resolve("spells")
}
