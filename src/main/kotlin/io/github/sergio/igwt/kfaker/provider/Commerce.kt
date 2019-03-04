package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
class Commerce internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.COMMERCE

    val department = resolve { fakerService.resolve(Faker, it, "department") }
    val productName: (type: String) -> String = { type ->
        resolve { fakerService.resolve(Faker, it, "product_name", type) }.invoke()
    }
    val promotionCode: (type: String) -> String = { type ->
        resolve { fakerService.resolve(Faker, it, "promotion_code", type) }.invoke()
    }
}
