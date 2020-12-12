package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.SUITS] category.
 */
@Suppress("unused")
class Suits internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Suits>(fakerService) {
    override val categoryName = CategoryName.SUITS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Suits>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
