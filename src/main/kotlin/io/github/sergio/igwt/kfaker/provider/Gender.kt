package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GENDER] category.
 */
class Gender internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GENDER

    val types = resolve { fakerService.resolve(Faker, it, "types") }
    val binaryTypes = resolve { fakerService.resolve(Faker, it, "binary_types") }
}
