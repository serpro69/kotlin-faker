package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DUMB_AND_DUMBER] category.
 */
@Suppress("unused")
class DumbAndDumber internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<DumbAndDumber>(fakerService) {
    override val categoryName = CategoryName.DUMB_AND_DUMBER
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val actors = resolve("actors")
    val characters = resolve("characters")
    val quotes = resolve("quotes")
}
