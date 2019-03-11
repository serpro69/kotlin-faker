package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HARRY_POTTER] category.
 */
class HarryPotter internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HARRY_POTTER

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val locations = resolve { fakerService.resolve(Faker, it, "locations") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
    val books = resolve { fakerService.resolve(Faker, it, "books") }
    val houses = resolve { fakerService.resolve(Faker, it, "houses") }
    val spells = resolve { fakerService.resolve(Faker, it, "spells") }
}
