package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GHOSTBUSTERS] category.
 */
@Suppress("unused")
class GhostBusters internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<GhostBusters>(fakerService) {
    override val category = YamlCategory.GHOSTBUSTERS
    override val localUniqueDataProvider = LocalUniqueDataProvider<GhostBusters>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun actors() = resolve("actors")
    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
