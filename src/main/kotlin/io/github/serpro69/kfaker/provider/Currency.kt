package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CURRENCY] category.
 */
class Currency internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.CURRENCY

    val code = resolve { fakerService.resolve(Faker, it, "code") }
    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val symbol = resolve { fakerService.resolve(Faker, it, "symbol") }
}
