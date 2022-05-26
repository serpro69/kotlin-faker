package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.APPLIANCE] category.
 */
@Suppress("unused")
class Appliance internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Appliance>(fakerService) {
    override val yamlCategory = YamlCategory.APPLIANCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Appliance>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun brand() = resolve("brand")
    fun equipment() = resolve("equipment")
}
