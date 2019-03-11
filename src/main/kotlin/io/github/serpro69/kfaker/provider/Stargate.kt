package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.STARGATE] category.
 */
class Stargate internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.STARGATE

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val planets = resolve { fakerService.resolve(Faker, it, "planets") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
