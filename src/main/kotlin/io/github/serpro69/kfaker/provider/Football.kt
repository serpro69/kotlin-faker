package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

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
