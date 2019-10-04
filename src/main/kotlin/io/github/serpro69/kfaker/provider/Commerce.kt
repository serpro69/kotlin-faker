package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COMMERCE] category.
 */
@Suppress("unused")
class Commerce internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Commerce>(fakerService) {
    override val categoryName = CategoryName.COMMERCE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val department = resolve("department")
    val productName = {
        val adjective = resolve { fakerService.resolve(it, "product_name", "adjective") }.invoke()
        val material = resolve { fakerService.resolve(it, "product_name", "material") }.invoke()
        val product = resolve { fakerService.resolve(it, "product_name", "product") }.invoke()

        "$adjective $material $product"
    }
    val promotionCode = {
        val adjective = resolve { fakerService.resolve(it, "promotion_code", "adjective") }.invoke()
        val noun = resolve { fakerService.resolve(it, "promotion_code", "noun") }.invoke()

        "$adjective $noun"
    }
}
