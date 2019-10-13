package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
@Suppress("unused")
class Cat internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Cat>(fakerService) {
    override val categoryName = CategoryName.CREATURE
    override val uniqueDataProvider = UniqueDataProvider<Cat>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun name() = resolve("cat", "name")
    fun breed() = resolve("cat", "breed")
    fun registry() = resolve("cat", "registry")
}
