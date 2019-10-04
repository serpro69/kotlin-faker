package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.LEBOWSKI] category.
 */
@Suppress("unused")
class Lebowski internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Lebowski>(fakerService) {
    override val categoryName = CategoryName.LEBOWSKI
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val actors = resolve("actors")
    val characters = resolve("characters")
    val quotes = resolve("quotes")
}
