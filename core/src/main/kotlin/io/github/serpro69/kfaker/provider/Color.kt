package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.COLOR] category.
 */
@Suppress("unused")
class Color internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Color>(fakerService) {
    override val categoryName = CategoryName.COLOR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Color>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun name() = resolve("name")
}
