package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HEROES] category.
 */
class Heroes internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HEROES

    val names = resolve { fakerService.resolve(Faker, it, "names") }
    val specialties = resolve { fakerService.resolve(Faker, it, "specialties") }
    val klasses = resolve { fakerService.resolve(Faker, it, "klasses") }
}
