package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HEY_ARNOLD] category.
 */
@Suppress("unused")
class HeyArnold internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<HeyArnold>(fakerService) {
    override val categoryName = CategoryName.HEY_ARNOLD
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val locations = resolve("locations")
    val quotes = resolve("quotes")
}
