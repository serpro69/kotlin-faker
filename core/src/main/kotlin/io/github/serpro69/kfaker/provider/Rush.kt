package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.RUSH] category.
 */
@Suppress("unused")
class Rush internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Rush>(fakerService) {
    override val categoryName = CategoryName.RUSH
    override val localUniqueDataProvider = LocalUniqueDataProvider<Rush>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun players() = resolve("players")
    fun albums() = resolve("albums")
}