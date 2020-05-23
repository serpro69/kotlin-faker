package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SEINFELD] category.
 */
@Suppress("unused")
class Seinfeld internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Seinfeld>(fakerService) {
    override val categoryName = CategoryName.SEINFELD
    override val localUniqueDataProvider = LocalUniqueDataProvider<Seinfeld>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun character() = resolve("character")
    fun quote() = resolve("quote")
    fun business() = resolve("business")
}
