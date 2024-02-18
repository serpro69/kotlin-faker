package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.FUTURAMA] category.
 */
@Suppress("unused")
class Futurama internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Futurama>(fakerService) {
    override val yamlCategory = YamlCategory.FUTURAMA
    override val localUniqueDataProvider = LocalUniqueDataProvider<Futurama>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun quotes() = resolve("quotes")
    fun hermesCatchphrases() = resolve("hermes_catchphrases")
}
