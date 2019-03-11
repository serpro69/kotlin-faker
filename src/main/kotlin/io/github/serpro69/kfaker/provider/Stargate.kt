package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.STARGATE] category.
 */
class Stargate internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.STARGATE

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val planets = resolve { fakerService.resolve(Faker, it, "planets") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
