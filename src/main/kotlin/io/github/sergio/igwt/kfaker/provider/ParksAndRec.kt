package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.PARKS_AND_REC] category.
 */
class ParksAndRec internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.PARKS_AND_REC

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val cities = resolve { fakerService.resolve(Faker, it, "cities") }
}
