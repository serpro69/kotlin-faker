package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HIPSTER] category.
 */
@Suppress("unused")
class Hipster internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Hipster>(fakerService) {
    override val categoryName = CategoryName.HIPSTER
    override val uniqueDataProvider = UniqueDataProvider<Hipster>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun words() = resolve("words")
}
