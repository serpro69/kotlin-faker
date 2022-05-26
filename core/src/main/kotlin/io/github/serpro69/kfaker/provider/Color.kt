package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.COLOR] category.
 */
@Suppress("unused")
class Color internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Color>(fakerService) {
    override val yamlCategory = YamlCategory.COLOR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Color>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun name() = resolve("name")
}
