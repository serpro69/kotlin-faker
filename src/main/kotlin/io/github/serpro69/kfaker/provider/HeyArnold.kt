package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.HEY_ARNOLD] category.
 */
class HeyArnold internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HEY_ARNOLD

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val locations = resolve { fakerService.resolve(Faker, it, "locations") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
