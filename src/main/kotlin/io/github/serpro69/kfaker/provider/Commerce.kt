package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COMMERCE] category.
 */
class Commerce internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.COMMERCE

    val department = resolve { fakerService.resolve(Faker, it, "department") }
    val productName = {
        val adjective = resolve { fakerService.resolve(Faker, it, "product_name", "adjective") }.invoke()
        val material = resolve { fakerService.resolve(Faker, it, "product_name", "material") }.invoke()
        val product = resolve { fakerService.resolve(Faker, it, "product_name", "product") }.invoke()

        "$adjective $material $product"
    }
    val promotionCode = {
        val adjective = resolve { fakerService.resolve(Faker, it, "promotion_code", "adjective") }.invoke()
        val noun = resolve { fakerService.resolve(Faker, it, "promotion_code", "noun") }.invoke()

        "$adjective $noun"
    }
}
