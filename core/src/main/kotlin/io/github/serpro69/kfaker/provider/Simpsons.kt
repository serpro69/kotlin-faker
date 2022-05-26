package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SIMPSONS] category.
 */
@Suppress("unused")
class Simpsons internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Simpsons>(fakerService) {
    override val yamlCategory = YamlCategory.SIMPSONS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Simpsons>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun quotes() = resolve("quotes")
    fun episodeTitles() = resolve("episode_titles")
}
