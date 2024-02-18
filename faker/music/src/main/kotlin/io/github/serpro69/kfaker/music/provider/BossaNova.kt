package io.github.serpro69.kfaker.music.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BOSSA_NOVA] category.
 */
@Suppress("unused")
class BossaNova internal constructor(fakerService: FakerService) : YamlFakeDataProvider<BossaNova>(fakerService) {
    override val yamlCategory = YamlCategory.BOSSA_NOVA
    override val localUniqueDataProvider = LocalUniqueDataProvider<BossaNova>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun artists() = resolve("artists")
    fun songs() = resolve("songs")
}
