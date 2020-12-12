package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.HIPSTER] category.
 */
@Suppress("unused")
class Hipster internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Hipster>(fakerService) {
    override val categoryName = CategoryName.HIPSTER
    override val localUniqueDataProvider = LocalUniqueDataProvider<Hipster>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun words() = resolve("words")
}
