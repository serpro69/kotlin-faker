package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.NATION] category.
 */
class Nation internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.NATION

    val flag = resolve { fakerService.resolve(Faker, it, "flag") }
    val nationality = resolve { fakerService.resolve(Faker, it, "nationality") }
    val language = resolve { fakerService.resolve(Faker, it, "language") }
    val capitalCity = resolve { fakerService.resolve(Faker, it, "capital_city") }
}
