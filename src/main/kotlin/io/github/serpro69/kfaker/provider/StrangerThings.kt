package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.STRANGER_THINGS] category.
 */
@Suppress("unused")
class StrangerThings internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<StrangerThings>(fakerService) {
    override val categoryName = CategoryName.STRANGER_THINGS
    override val localUniqueDataProvider = LocalUniqueDataProvider<StrangerThings>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun character() = resolve("character")
    fun quote() = resolve("quote")
}
