package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FOOD] category.
 */
class Food internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.FOOD

    val dish = resolve { fakerService.resolve(Faker, it, "dish") }
    val descriptions = resolve { fakerService.resolve(Faker, it, "descriptions") }
    val ingredients = resolve { fakerService.resolve(Faker, it, "ingredients") }
    val fruits = resolve { fakerService.resolve(Faker, it, "fruits") }
    val vegetables = resolve { fakerService.resolve(Faker, it, "vegetables") }
    val spices = resolve { fakerService.resolve(Faker, it, "spices") }
    val measurements = resolve { fakerService.resolve(Faker, it, "measurements") }
    val measurementSizes = resolve { fakerService.resolve(Faker, it, "measurement_sizes") }
    val metricMeasurements = resolve { fakerService.resolve(Faker, it, "metric_measurements") }
    val sushi = resolve { fakerService.resolve(Faker, it, "sushi") }
}
