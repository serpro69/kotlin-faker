package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ANCIENT] category.
 */
@Suppress("unused")
class Rajnikanth internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Rajnikanth>(fakerService) {
    override val yamlCategory = YamlCategory.RAJNIKANTH
    override val localUniqueDataProvider = LocalUniqueDataProvider<Rajnikanth>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun joke() = resolve("joke")
}
