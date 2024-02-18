package io.github.serpro69.kfaker.japmedia.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.COWBOY_BEBOP] category.
 */
@Suppress("unused")
class CowboyBebop internal constructor(fakerService: FakerService) : YamlFakeDataProvider<CowboyBebop>(fakerService) {
    override val yamlCategory = YamlCategory.COWBOY_BEBOP
    override val localUniqueDataProvider = LocalUniqueDataProvider<CowboyBebop>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun character() = resolve("character")
    fun episode() = resolve("episode")
    fun song() = resolve("song")
    fun quote() = resolve("quote")
}
