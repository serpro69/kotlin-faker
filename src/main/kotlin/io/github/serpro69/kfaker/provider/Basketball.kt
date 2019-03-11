package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

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
