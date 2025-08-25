package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.RICK_AND_MORTY] category. */
@Suppress("unused")
class RickAndMorty internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<RickAndMorty>(fakerService) {
    override val yamlCategory = YamlCategory.RICK_AND_MORTY
    override val localUniqueDataProvider = LocalUniqueDataProvider<RickAndMorty>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")

    fun locations() = resolve("locations")

    fun quotes() = resolve("quotes")
}
