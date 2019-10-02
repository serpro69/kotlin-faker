package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class ElderScrolls internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val race = resolve { fakerService.resolve(it, "elder_scrolls", "race") }
    val creature = resolve { fakerService.resolve(it, "elder_scrolls", "creature") }
    val region = resolve { fakerService.resolve(it, "elder_scrolls", "region") }
    val dragon = resolve { fakerService.resolve(it, "elder_scrolls", "dragon") }
    val city = resolve { fakerService.resolve(it, "elder_scrolls", "city") }
    val first_name = resolve { fakerService.resolve(it, "elder_scrolls", "first_name") }
    val last_name = resolve { fakerService.resolve(it, "elder_scrolls", "last_name") }
}
