package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

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
