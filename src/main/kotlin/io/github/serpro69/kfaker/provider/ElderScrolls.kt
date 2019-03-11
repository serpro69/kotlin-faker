package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class ElderScrolls internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val race = resolve { fakerService.resolve(Faker, it, "elder_scrolls", "race") }
    val creature = resolve { fakerService.resolve(Faker, it, "elder_scrolls", "creature") }
    val region = resolve { fakerService.resolve(Faker, it, "elder_scrolls", "region") }
    val dragon = resolve { fakerService.resolve(Faker, it, "elder_scrolls", "dragon") }
    val city = resolve { fakerService.resolve(Faker, it, "elder_scrolls", "city") }
    val first_name = resolve { fakerService.resolve(Faker, it, "elder_scrolls", "first_name") }
    val last_name = resolve { fakerService.resolve(Faker, it, "elder_scrolls", "last_name") }
}
