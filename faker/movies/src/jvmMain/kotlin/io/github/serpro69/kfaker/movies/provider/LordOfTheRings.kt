package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.LORD_OF_THE_RINGS] category. */
@Suppress("unused")
class LordOfTheRings internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<LordOfTheRings>(fakerService) {
    override val yamlCategory = YamlCategory.LORD_OF_THE_RINGS
    override val localUniqueDataProvider = LocalUniqueDataProvider<LordOfTheRings>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")

    fun locations() = resolve("locations")

    fun quotes() = resolve("quotes")
}
