package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
class BackToTheFuture internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BACK_TO_THE_FUTURE

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val dates = resolve { fakerService.resolve(Faker, it, "dates") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}