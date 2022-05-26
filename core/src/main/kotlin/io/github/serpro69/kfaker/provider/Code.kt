package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CODE] category.
 */
@Suppress("unused")
class Code internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Code>(fakerService) {
    override val yamlCategory = YamlCategory.CODE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Code>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun asin() = resolve("asin")
}
