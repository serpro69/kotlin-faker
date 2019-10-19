package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MEASUREMENT] category.
 */
@Suppress("unused")
class Measurement internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Measurement>(fakerService) {
    override val categoryName = CategoryName.MEASUREMENT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Measurement>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun height() = resolve("height")
    fun length() = resolve("length")
    fun volume() = resolve("volume")
    fun weight() = resolve("weight")
    fun metricHeight() = resolve("metric_height")
    fun metricLength() = resolve("metric_length")
    fun metricVolume() = resolve("metric_volume")
    fun metricWeight() = resolve("metric_weight")
}
