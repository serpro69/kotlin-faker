package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MEASUREMENT] category.
 */
@Suppress("unused")
class Measurement internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Measurement>(fakerService) {
    override val categoryName = CategoryName.MEASUREMENT
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val height = resolve("height")
    val length = resolve("length")
    val volume = resolve("volume")
    val weight = resolve("weight")
    val metricHeight = resolve("metric_height")
    val metricLength = resolve("metric_length")
    val metricVolume = resolve("metric_volume")
    val metricWeight = resolve("metric_weight")
}
