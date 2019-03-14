package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.NATION] category.
 */
class Nation internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.NATION

//    val flag = resolve { fakerService.resolve(Faker, it, "flag") } // currently not supported due to logic for getting raw value for List<List<*>> types
    val nationality = resolve { fakerService.resolve(Faker, it, "nationality") }
    val language = resolve { fakerService.resolve(Faker, it, "language") }
    val capitalCity = resolve { fakerService.resolve(Faker, it, "capital_city") }
}
