package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ROCK_BAND] category.
 */
@Suppress("unused")
class RockBand internal constructor(fakerService: FakerService) : YamlFakeDataProvider<RockBand>(fakerService) {
    override val yamlCategory = YamlCategory.ROCK_BAND
    override val localUniqueDataProvider = LocalUniqueDataProvider<RockBand>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun name() = resolve("name")
    fun song() = resolve("song")
}
