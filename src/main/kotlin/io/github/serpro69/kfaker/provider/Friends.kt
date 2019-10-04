package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FRIENDS] category.
 */
@Suppress("unused")
class Friends internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Friends>(fakerService) {
    override val categoryName = CategoryName.FRIENDS
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val locations = resolve("locations")
    val quotes = resolve("quotes")
}
