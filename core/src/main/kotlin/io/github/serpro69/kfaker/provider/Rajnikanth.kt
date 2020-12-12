package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.ANCIENT] category.
 */
@Suppress("unused")
class Rajnikanth internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Rajnikanth>(fakerService) {
    override val categoryName = CategoryName.RAJNIKANTH
    override val localUniqueDataProvider = LocalUniqueDataProvider<Rajnikanth>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun joke() = resolve("joke")
}
