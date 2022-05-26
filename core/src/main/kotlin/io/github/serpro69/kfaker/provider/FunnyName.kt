package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.FUNNY_NAME] category.
 */
@Suppress("unused")
class FunnyName internal constructor(fakerService: FakerService) : YamlFakeDataProvider<FunnyName>(fakerService) {
    override val yamlCategory = YamlCategory.FUNNY_NAME
    override val localUniqueDataProvider = LocalUniqueDataProvider<FunnyName>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun name() = resolve("name")
}
