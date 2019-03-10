package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.SEINFELD] category.
 */
class Seinfeld internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SEINFELD

    val character = resolve { fakerService.resolve(Faker, it, "character") }
    val quote = resolve { fakerService.resolve(Faker, it, "quote") }
    val business = resolve { fakerService.resolve(Faker, it, "business") }
}
