package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class SuperSmashBros internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val fighter = resolve { fakerService.resolve(Faker, it, "super_smash_bros", "fighter") }
    val stage = resolve { fakerService.resolve(Faker, it, "super_smash_bros", "stage") }
}
