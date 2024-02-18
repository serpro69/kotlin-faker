package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.STRANGER_THINGS] category.
 */
@Suppress("unused")
class StrangerThings internal constructor(fakerService: FakerService) : YamlFakeDataProvider<StrangerThings>(fakerService) {
    override val yamlCategory = YamlCategory.STRANGER_THINGS
    override val localUniqueDataProvider = LocalUniqueDataProvider<StrangerThings>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun character() = resolve("character")
    fun quote() = resolve("quote")
}
