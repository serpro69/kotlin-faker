package io.github.serpro69.kfaker.commerce.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.COMMERCE] category. */
@Suppress("unused")
class Commerce internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Commerce>(fakerService) {
    override val yamlCategory = YamlCategory.COMMERCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Commerce>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun department() = resolve("department")

    fun productName(): String {
        val adjective = resolve("product_name", "adjective")
        val material = resolve("product_name", "material")
        val product = resolve("product_name", "product")

        return "$adjective $material $product"
    }

    fun promotionCode(): String {
        val adjective = resolve("promotion_code", "adjective")
        val noun = resolve("promotion_code", "noun")

        return "$adjective $noun"
    }

    fun brand() = resolve("brand")

    fun vendor() = resolve("vendor")
}
