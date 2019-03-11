package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.HEROES] category.
 */
class Heroes internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HEROES

    val names = resolve { fakerService.resolve(Faker, it, "names") }
    val specialties = resolve { fakerService.resolve(Faker, it, "specialties") }
    val klasses = resolve { fakerService.resolve(Faker, it, "klasses") }
}
