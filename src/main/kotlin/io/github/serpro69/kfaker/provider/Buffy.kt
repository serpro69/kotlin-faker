package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BUFFY] category.
 */
@Suppress("unused")
class Buffy internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Buffy>(fakerService) {
    override val categoryName = CategoryName.BUFFY
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val quotes = resolve("quotes")
    val celebrities = resolve("celebrities")
    val bigBads = resolve("big_bads")
    val episodes = resolve("episodes")
}
