package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.VENTURE_BROS] category.
 */
class VentureBros internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.VENTURE_BROS

    val character = resolve { fakerService.resolve(Faker, it, "character") }
    val organization = resolve { fakerService.resolve(Faker, it, "organization") }
    val vehicle = resolve { fakerService.resolve(Faker, it, "vehicle") }
    val quote = resolve { fakerService.resolve(Faker, it, "quote") }
}
