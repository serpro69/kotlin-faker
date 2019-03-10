package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.RUPAUL] category.
 */
class Rupaul internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.RUPAUL

    val queens = resolve { fakerService.resolve(Faker, it, "queens") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
