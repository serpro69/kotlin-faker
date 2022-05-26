package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.MUSIC] category.
 */
@Suppress("unused")
class Music internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Music>(fakerService) {
    override val yamlCategory = YamlCategory.MUSIC
    override val localUniqueDataProvider = LocalUniqueDataProvider<Music>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    val hipHop = HipHop(fakerService)

    fun instruments() = resolve("instruments")
    fun bands() = resolve("bands")
    fun albums() = resolve("albums")
    fun genres() = resolve("genres")
    fun mamboNo5() = resolve("mambo_no_5")
}

class HipHop internal constructor(fakerService: FakerService) : YamlFakeDataProvider<HipHop>(fakerService) {
    override val yamlCategory = YamlCategory.MUSIC
    override val localUniqueDataProvider = LocalUniqueDataProvider<HipHop>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun subgenres() = resolve("subgenres")
    fun groups() = resolve("groups")
    fun artist() = resolve("artist")
}
