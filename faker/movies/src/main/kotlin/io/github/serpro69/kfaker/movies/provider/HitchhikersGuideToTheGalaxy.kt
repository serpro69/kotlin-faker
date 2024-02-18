package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HITCHHIKERS_GUIDE_TO_THE_GALAXY] category.
 */
@Suppress("unused")
class HitchhikersGuideToTheGalaxy internal constructor(
    fakerService: FakerService
) : YamlFakeDataProvider<HitchhikersGuideToTheGalaxy>(fakerService) {
    override val yamlCategory = YamlCategory.HITCHHIKERS_GUIDE_TO_THE_GALAXY
    override val localUniqueDataProvider = LocalUniqueDataProvider<HitchhikersGuideToTheGalaxy>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun marvinQuote() = resolve("marvin_quote")
    fun planets() = resolve("planets")
    fun quotes() = resolve("quotes")
    fun species() = resolve("species")
    fun starships() = resolve("starships")
}
