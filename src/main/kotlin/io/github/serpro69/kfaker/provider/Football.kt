package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FOOTBALL] category.
 */
@Suppress("unused")
class Football internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Football>(fakerService) {
    override val categoryName = CategoryName.FOOTBALL

    fun teams() = resolve("teams")
    fun players() = resolve("players")
    fun coaches() = resolve("coaches")
    fun competitions() = resolve("competitions")
    fun positions() = resolve("positions")
}
