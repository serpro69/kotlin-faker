package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.PRINCE] category.
 */
@Suppress("unused")
class Prince internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Prince>(fakerService) {
    override val category = YamlCategory.PRINCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Prince>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun lyric() = resolve("lyric")
    fun song() = resolve("song")
    fun album() = resolve("album")
    fun band() = resolve("band")
}
