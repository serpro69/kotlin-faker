package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class ElderScrolls internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<ElderScrolls>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val race = resolve { fakerService.resolve(it, "elder_scrolls", "race") }
    val creature = resolve { fakerService.resolve(it, "elder_scrolls", "creature") }
    val region = resolve { fakerService.resolve(it, "elder_scrolls", "region") }
    val dragon = resolve { fakerService.resolve(it, "elder_scrolls", "dragon") }
    val city = resolve { fakerService.resolve(it, "elder_scrolls", "city") }
    val firstName = resolve { fakerService.resolve(it, "elder_scrolls", "first_name") }
    val lastName = resolve { fakerService.resolve(it, "elder_scrolls", "last_name") }
}
