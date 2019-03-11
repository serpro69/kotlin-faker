package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.HOBBIT] category.
 */
class Hobbit internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HOBBIT

    val character = resolve { fakerService.resolve(Faker, it, "character") }
    val thorinsCompany = resolve { fakerService.resolve(Faker, it, "thorins_company") }
    val quote = resolve { fakerService.resolve(Faker, it, "quote") }
    val location = resolve { fakerService.resolve(Faker, it, "location") }
}
