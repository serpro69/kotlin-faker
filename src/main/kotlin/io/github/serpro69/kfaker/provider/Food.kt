package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FOOD] category.
 */
@Suppress("unused")
class Food internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Food>(fakerService) {
    override val categoryName = CategoryName.FOOD
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val dish = resolve("dish")
    val descriptions = resolve("descriptions")
    val ingredients = resolve("ingredients")
    val fruits = resolve("fruits")
    val vegetables = resolve("vegetables")
    val spices = resolve("spices")
    val measurements = resolve("measurements")
    val measurementSizes = resolve("measurement_sizes")
    val metricMeasurements = resolve("metric_measurements")
    val sushi = resolve("sushi")
}
