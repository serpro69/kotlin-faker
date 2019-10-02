package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HARRY_POTTER] category.
 */
class HarryPotter internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HARRY_POTTER

    val characters = resolve { fakerService.resolve(it, "characters") }
    val locations = resolve { fakerService.resolve(it, "locations") }
    val quotes = resolve { fakerService.resolve(it, "quotes") }
    val books = resolve { fakerService.resolve(it, "books") }
    val houses = resolve { fakerService.resolve(it, "houses") }
    val spells = resolve { fakerService.resolve(it, "spells") }
}
