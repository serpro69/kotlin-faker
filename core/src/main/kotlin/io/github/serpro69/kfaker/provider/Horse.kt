package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
@Suppress("unused")
class Horse internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Horse>(fakerService) {
    override val categoryName = CategoryName.CREATURE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Horse>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun name() = resolve("horse", "name")
    fun breed() = resolve("horse", "breed")
}
