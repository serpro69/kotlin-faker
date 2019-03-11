package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.V_FOR_VENDETTA] category.
 */
class VForVendetta internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.V_FOR_VENDETTA

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val speeches = resolve { fakerService.resolve(Faker, it, "speeches") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
