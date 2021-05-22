package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.PHISH] category.
 */
@Suppress("unused")
class Phish internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Phish>(fakerService) {
    override val categoryName = CategoryName.PHISH
    override val localUniqueDataProvider = LocalUniqueDataProvider<Phish>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun albums() = resolve("albums")
    fun musicians() = resolve("musicians")
    fun songs() = resolve("songs")
}
