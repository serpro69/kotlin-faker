package io.github.serpro69.kfaker.music.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.PHISH] category.
 */
@Suppress("unused")
class Phish internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Phish>(fakerService) {
    override val yamlCategory = YamlCategory.PHISH
    override val localUniqueDataProvider = LocalUniqueDataProvider<Phish>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun albums() = resolve("albums")
    fun musicians() = resolve("musicians")
    fun songs() = resolve("songs")
}
