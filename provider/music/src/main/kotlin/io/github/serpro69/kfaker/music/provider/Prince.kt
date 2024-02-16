package io.github.serpro69.kfaker.music.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.PRINCE] category.
 */
@Suppress("unused")
class Prince internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Prince>(fakerService) {
    override val yamlCategory = YamlCategory.PRINCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Prince>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun lyric() = resolve("lyric")
    fun song() = resolve("song")
    fun album() = resolve("album")
    fun band() = resolve("band")
}
