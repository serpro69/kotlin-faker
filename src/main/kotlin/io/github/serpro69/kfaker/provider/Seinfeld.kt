package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SEINFELD] category.
 */
class Seinfeld internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SEINFELD

    val character = resolve { fakerService.resolve(Faker, it, "character") }
    val quote = resolve { fakerService.resolve(Faker, it, "quote") }
    val business = resolve { fakerService.resolve(Faker, it, "business") }
}
