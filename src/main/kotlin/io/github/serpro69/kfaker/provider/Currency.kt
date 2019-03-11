package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.CURRENCY] category.
 */
class Currency internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.CURRENCY

    val code = resolve { fakerService.resolve(Faker, it, "code") }
    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val symbol = resolve { fakerService.resolve(Faker, it, "symbol") }
}
