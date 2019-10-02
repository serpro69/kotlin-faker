package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FOOD] category.
 */
class Food internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.FOOD

    val dish = resolve { fakerService.resolve(it, "dish") }
    val descriptions = resolve { fakerService.resolve(it, "descriptions") }
    val ingredients = resolve { fakerService.resolve(it, "ingredients") }
    val fruits = resolve { fakerService.resolve(it, "fruits") }
    val vegetables = resolve { fakerService.resolve(it, "vegetables") }
    val spices = resolve { fakerService.resolve(it, "spices") }
    val measurements = resolve { fakerService.resolve(it, "measurements") }
    val measurementSizes = resolve { fakerService.resolve(it, "measurement_sizes") }
    val metricMeasurements = resolve { fakerService.resolve(it, "metric_measurements") }
    val sushi = resolve { fakerService.resolve(it, "sushi") }
}
