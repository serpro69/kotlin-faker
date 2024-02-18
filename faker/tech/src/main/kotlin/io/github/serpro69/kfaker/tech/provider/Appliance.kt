package io.github.serpro69.kfaker.tech.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.APPLIANCE] category.
 */
@Suppress("unused")
class Appliance internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Appliance>(fakerService) {
    override val yamlCategory = YamlCategory.APPLIANCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Appliance>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun brand() = resolve("brand")
    fun equipment() = resolve("equipment")
}
