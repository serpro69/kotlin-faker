package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.BASKETBALL] category.
 */
class Basketball internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BASKETBALL

    val teams = resolve { fakerService.resolve(Faker, it, "teams") }
    val players = resolve { fakerService.resolve(Faker, it, "players") }
    val coaches = resolve { fakerService.resolve(Faker, it, "coaches") }
    val positions = resolve { fakerService.resolve(Faker, it, "positions") }
}
