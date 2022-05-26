package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DEVICE] category.
 */
@Suppress("unused")
class Device internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Device>(fakerService) {
    override val yamlCategory = YamlCategory.DEVICE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Device>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun modelName() = resolve("model_name")
    fun platform() = resolve("platform")
    fun manufacturer() = resolve("manufacturer")
    fun serial() = resolve("serial")
}
