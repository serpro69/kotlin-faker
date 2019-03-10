package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.SCIENCE] category.
 */
class Science internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SCIENCE

    val element = resolve { fakerService.resolve(Faker, it, "element") }
    val elementSymbol = resolve { fakerService.resolve(Faker, it, "element_symbol") }
    val scientist = resolve { fakerService.resolve(Faker, it, "scientist") }
}
