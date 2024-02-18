package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.THE_EXPANSE] category.
 */
@Suppress("unused")
class TheExpanse internal constructor(fakerService: FakerService) : YamlFakeDataProvider<TheExpanse>(fakerService) {
    override val yamlCategory = YamlCategory.THE_EXPANSE
    override val localUniqueDataProvider = LocalUniqueDataProvider<TheExpanse>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun ships() = resolve("ships")
    fun quotes() = resolve("quotes")
}
