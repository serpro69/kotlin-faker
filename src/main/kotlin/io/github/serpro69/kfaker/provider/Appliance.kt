package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.APPLIANCE] category.
 */
class Appliance internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.APPLIANCE

    val brand = resolve { fakerService.resolve(Faker, it, "brand") }
    val equipment = resolve { fakerService.resolve(Faker, it, "equipment") }
}