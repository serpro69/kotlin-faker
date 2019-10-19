package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DEVICE] category.
 */
@Suppress("unused")
class Device internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Device>(fakerService) {
    override val categoryName = CategoryName.DEVICE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Device>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun modelName() = resolve("model_name")
    fun platform() = resolve("platform")
    fun manufacturer() = resolve("manufacturer")
    fun serial() = resolve("serial")
}
