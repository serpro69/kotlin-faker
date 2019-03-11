package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

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
