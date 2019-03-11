package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.SOUTH_PARK] category.
 */
class SouthPark internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SOUTH_PARK

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
