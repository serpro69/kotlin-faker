package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GHOSTBUSTERS] category.
 */
@Suppress("unused")
class GhostBusters internal constructor(fakerService: FakerService) : YamlFakeDataProvider<GhostBusters>(fakerService) {
    override val yamlCategory = YamlCategory.GHOSTBUSTERS
    override val localUniqueDataProvider = LocalUniqueDataProvider<GhostBusters>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun actors() = resolve("actors")
    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
