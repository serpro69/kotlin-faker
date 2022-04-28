package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.VENTURE_BROS] category.
 */
@Suppress("unused")
class VentureBros internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<VentureBros>(fakerService) {
    override val category = YamlCategory.VENTURE_BROS
    override val localUniqueDataProvider = LocalUniqueDataProvider<VentureBros>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun character() = resolve("character")
    fun organization() = resolve("organization")
    fun vehicle() = resolve("vehicle")
    fun quote() = resolve("quote")
}
