package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

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
