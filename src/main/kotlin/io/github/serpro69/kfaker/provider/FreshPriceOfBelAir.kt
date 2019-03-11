package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.THE_FRESH_PRINCE_OF_BEL_AIR] category.
 */
class FreshPriceOfBelAir internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.THE_FRESH_PRINCE_OF_BEL_AIR

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val celebrities = resolve { fakerService.resolve(Faker, it, "celebrities") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
