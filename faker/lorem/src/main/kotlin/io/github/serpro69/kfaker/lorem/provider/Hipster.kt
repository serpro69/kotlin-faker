package io.github.serpro69.kfaker.lorem.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HIPSTER] category.
 */
@Suppress("unused")
class Hipster internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Hipster>(fakerService) {
    override val yamlCategory = YamlCategory.HIPSTER
    override val localUniqueDataProvider = LocalUniqueDataProvider<Hipster>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun words() = resolve("words")
}
