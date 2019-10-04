package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GRATEFUL_DEAD] category.
 */
@Suppress("unused")
class GratefulDead internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<GratefulDead>(fakerService) {
    override val categoryName = CategoryName.GRATEFUL_DEAD
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val players = resolve("players")
    val songs = resolve("songs")
}
