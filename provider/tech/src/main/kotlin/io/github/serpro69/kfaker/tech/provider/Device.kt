package io.github.serpro69.kfaker.tech.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
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
