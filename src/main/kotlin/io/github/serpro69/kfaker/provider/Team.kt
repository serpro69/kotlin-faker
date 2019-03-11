package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.TEAM] category.
 */
class Team internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.TEAM

    val creature = resolve { fakerService.resolve(Faker, it, "creature") }
    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val sport = resolve { fakerService.resolve(Faker, it, "sport") }
    val mascot = resolve { fakerService.resolve(Faker, it, "mascot") }
}
