package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MUSIC] category.
 */
@Suppress("unused")
class Music internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Music>(fakerService) {
    override val categoryName = CategoryName.MUSIC
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val instruments = resolve("instruments")
    val bands = resolve("bands")
    val albums = resolve("albums")
    val genres = resolve("genres")
}
