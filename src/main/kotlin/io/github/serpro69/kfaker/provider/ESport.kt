package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ESPORT] category.
 */
class ESport internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.ESPORT

    val players = resolve { fakerService.resolve(Faker, it, "players") }
    val teams = resolve { fakerService.resolve(Faker, it, "teams") }
    val events = resolve { fakerService.resolve(Faker, it, "events") }
    val leagues = resolve { fakerService.resolve(Faker, it, "leagues") }
    val games = resolve { fakerService.resolve(Faker, it, "games") }
}
