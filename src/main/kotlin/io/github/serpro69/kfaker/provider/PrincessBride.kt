package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.PRINCESS_BRIDE] category.
 */
class PrincessBride internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.PRINCESS_BRIDE

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
