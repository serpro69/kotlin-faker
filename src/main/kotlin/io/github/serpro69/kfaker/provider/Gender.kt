package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GENDER] category.
 */
class Gender internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GENDER

    val types = resolve { fakerService.resolve(Faker, it, "types") }
    val binaryTypes = resolve { fakerService.resolve(Faker, it, "binary_types") }
}
