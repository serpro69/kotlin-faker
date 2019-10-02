package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MEASUREMENT] category.
 */
@Suppress("unused")
class Measurement internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.MEASUREMENT

    val height = resolve { fakerService.resolve(it, "height") }
    val length = resolve { fakerService.resolve(it, "length") }
    val volume = resolve { fakerService.resolve(it, "volume") }
    val weight = resolve { fakerService.resolve(it, "weight") }
    val metricHeight = resolve { fakerService.resolve(it, "metric_height") }
    val metricLength = resolve { fakerService.resolve(it, "metric_length") }
    val metricVolume = resolve { fakerService.resolve(it, "metric_volume") }
    val metricWeight = resolve { fakerService.resolve(it, "metric_weight") }
}
