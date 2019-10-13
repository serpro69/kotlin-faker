package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COIN] category.
 */
@Suppress("unused")
class Coin internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Coin>(fakerService) {
    override val categoryName = CategoryName.COIN
    override val uniqueDataProvider = UniqueDataProvider<Coin>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun flip() = resolve("flip")
}
