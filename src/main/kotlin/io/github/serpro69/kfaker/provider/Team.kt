package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.TEAM] category.
 */
class Team internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.TEAM

    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val sport = resolve { fakerService.resolve(Faker, it, "sport") }
    val mascot = resolve { fakerService.resolve(Faker, it, "mascot") }
}
