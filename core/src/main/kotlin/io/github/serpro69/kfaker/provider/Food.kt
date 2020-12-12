package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.FOOD] category.
 */
@Suppress("unused")
class Food internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Food>(fakerService) {
    override val categoryName = CategoryName.FOOD
    override val localUniqueDataProvider = LocalUniqueDataProvider<Food>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun dish() = resolve("dish")
    fun descriptions() = resolve("descriptions")
    fun ingredients() = resolve("ingredients")
    fun fruits() = resolve("fruits")
    fun vegetables() = resolve("vegetables")
    fun spices() = resolve("spices")
    fun measurements() = resolve("measurements")
    fun measurementSizes() = resolve("measurement_sizes")
    fun metricMeasurements() = resolve("metric_measurements")
    fun sushi() = resolve("sushi")
}
