package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.MEASUREMENT] category.
 */
class Measurement internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.MEASUREMENT

    val height = resolve { fakerService.resolve(Faker, it, "height") }
    val length = resolve { fakerService.resolve(Faker, it, "length") }
    val volume = resolve { fakerService.resolve(Faker, it, "volume") }
    val weight = resolve { fakerService.resolve(Faker, it, "weight") }
    val metricHeight = resolve { fakerService.resolve(Faker, it, "metric_height") }
    val metricLength = resolve { fakerService.resolve(Faker, it, "metric_length") }
    val metricVolume = resolve { fakerService.resolve(Faker, it, "metric_volume") }
    val metricWeight = resolve { fakerService.resolve(Faker, it, "metric_weight") }
}
