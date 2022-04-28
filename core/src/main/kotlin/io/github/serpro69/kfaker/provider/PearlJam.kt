package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.PEARL_JAM] category.
 */
@Suppress("unused")
class PearlJam internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<PearlJam>(fakerService) {
    override val category = YamlCategory.PEARL_JAM
    override val localUniqueDataProvider = LocalUniqueDataProvider<PearlJam>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun musicians() = resolve("musicians")
    fun albums() = resolve("albums")
    fun songs() = resolve("songs")
}
