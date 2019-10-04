package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FOOTBALL] category.
 */
@Suppress("unused")
class Football internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Football>(fakerService) {
    override val categoryName = CategoryName.FOOTBALL
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val teams = resolve("teams")
    val players = resolve("players")
    val coaches = resolve("coaches")
    val competitions = resolve("competitions")
    val positions = resolve("positions")
}
