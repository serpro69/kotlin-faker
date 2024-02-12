package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SMASHING_PUMPKINS] category.
 */
@Suppress("unused")
class SmashingPumpkins internal constructor(fakerService: FakerService) : YamlFakeDataProvider<SmashingPumpkins>(fakerService) {
    override val yamlCategory = YamlCategory.SMASHING_PUMPKINS
    override val localUniqueDataProvider = LocalUniqueDataProvider<SmashingPumpkins>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun musicians() = resolve("musicians")
    fun albums() = resolve("albums")
    fun lyric() = resolve("lyric")
    fun songs() = resolve("songs")
}
