package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.BREAKING_BAD] category.
 */
class BreakingBad internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BREAKING_BAD

    val character = resolve { fakerService.resolve(Faker, it, "character") }
    val episode = resolve { fakerService.resolve(Faker, it, "episode") }
}
