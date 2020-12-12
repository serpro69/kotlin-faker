package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.MUSIC] category.
 */
@Suppress("unused")
class Music internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Music>(fakerService) {
    override val categoryName = CategoryName.MUSIC
    override val localUniqueDataProvider = LocalUniqueDataProvider<Music>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun instruments() = resolve("instruments")
    fun bands() = resolve("bands")
    fun albums() = resolve("albums")
    fun genres() = resolve("genres")
}
