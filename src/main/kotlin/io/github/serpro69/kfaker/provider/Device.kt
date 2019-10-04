package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DEVICE] category.
 */
@Suppress("unused")
class Device internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Device>(fakerService) {
    override val categoryName = CategoryName.DEVICE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val modelName = resolve("model_name")
    val platform = resolve("platform")
    val manufacturer = resolve("manufacturer")
    val serial = resolve("serial")
}
