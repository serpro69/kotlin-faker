package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MARKETING] category.
 */
@Suppress("unused")
class Marketing internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Marketing>(fakerService) {
    override val categoryName = CategoryName.MARKETING
    override val localUniqueDataProvider = LocalUniqueDataProvider<Marketing>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun buzzwords() = resolve("buzzwords")
}
