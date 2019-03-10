package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.FOOTBALL] category.
 */
class Football internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.FOOTBALL

    val teams = resolve { fakerService.resolve(Faker, it, "teams") }
    val players = resolve { fakerService.resolve(Faker, it, "players") }
    val coaches = resolve { fakerService.resolve(Faker, it, "coaches") }
    val competitions = resolve { fakerService.resolve(Faker, it, "competitions") }
    val positions = resolve { fakerService.resolve(Faker, it, "positions") }
}
