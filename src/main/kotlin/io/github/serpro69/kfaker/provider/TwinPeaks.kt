package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.TWIN_PEAKS] category.
 */
@Suppress("unused")
class TwinPeaks internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<TwinPeaks>(fakerService) {
    override val categoryName = CategoryName.TWIN_PEAKS
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val locations = resolve("locations")
    val quotes = resolve("quotes")
}
