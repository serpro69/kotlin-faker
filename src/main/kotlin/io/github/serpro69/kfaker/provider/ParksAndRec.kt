package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.PARKS_AND_REC] category.
 */
class ParksAndRec internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.PARKS_AND_REC

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val cities = resolve { fakerService.resolve(Faker, it, "cities") }
}
