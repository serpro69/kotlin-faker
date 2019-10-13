package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MUSIC] category.
 */
@Suppress("unused")
class Music internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Music>(fakerService) {
    override val categoryName = CategoryName.MUSIC
    override val uniqueDataProvider = UniqueDataProvider<Music>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun instruments() = resolve("instruments")
    fun bands() = resolve("bands")
    fun albums() = resolve("albums")
    fun genres() = resolve("genres")
}
