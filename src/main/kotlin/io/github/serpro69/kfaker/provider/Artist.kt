package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ARTIST] category.
 */
@Suppress("unused")
class Artist internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Artist>(fakerService) {
    override val categoryName = CategoryName.ARTIST
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun names() = resolve("names")
}