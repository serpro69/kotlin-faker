package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SWORD_ART_ONLINE] category.
 */
@Suppress("unused")
class SwordArtOnline internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<SwordArtOnline>(fakerService) {
    override val categoryName = CategoryName.SWORD_ART_ONLINE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val realName = resolve("real_name")
    val gameName = resolve("game_name")
    val location = resolve("location")
    val item = resolve("item")
}
