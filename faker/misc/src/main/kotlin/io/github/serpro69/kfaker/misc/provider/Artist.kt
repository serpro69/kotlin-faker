package io.github.serpro69.kfaker.misc.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.ARTIST] category. */
@Suppress("unused")
class Artist internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Artist>(fakerService) {
    override val yamlCategory = YamlCategory.ARTIST
    override val localUniqueDataProvider = LocalUniqueDataProvider<Artist>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun names() = resolve("names")
}
