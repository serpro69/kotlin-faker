package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COMMERCE] category.
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
