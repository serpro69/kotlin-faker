package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BASKETBALL] category.
 */
@Suppress("unused")
class Basketball internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Basketball>(fakerService) {
    override val categoryName = CategoryName.BASKETBALL
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val teams = resolve("teams")
    val players = resolve("players")
    val coaches = resolve("coaches")
    val positions = resolve("positions")
}
