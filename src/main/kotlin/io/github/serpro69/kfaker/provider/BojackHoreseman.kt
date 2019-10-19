package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BOJACK_HORSEMAN] category.
 */
@Suppress("unused")
class BojackHoreseman internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<BojackHoreseman>(fakerService) {
    override val categoryName = CategoryName.BOJACK_HORSEMAN
    override val localUniqueDataProvider = LocalUniqueDataProvider<BojackHoreseman>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
    fun tongueTwisters() = resolve("tongue_twisters")
}
