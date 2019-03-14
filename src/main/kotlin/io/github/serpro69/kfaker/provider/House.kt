package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HOUSE] category.
 */
class House internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HOUSE

    val furniture = resolve { fakerService.resolve(Faker, it, "furniture") }
    val rooms = resolve { fakerService.resolve(Faker, it, "rooms") }
}
