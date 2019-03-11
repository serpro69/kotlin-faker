package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.LORD_OF_THE_RINGS] category.
 */
class LordOfTheRings internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.LORD_OF_THE_RINGS

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val locations = resolve { fakerService.resolve(Faker, it, "locations") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
