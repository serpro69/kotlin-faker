package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.STAR_TREK] category.
 */
@Suppress("unused")
class StarTrek internal constructor(fakerService: FakerService) : YamlFakeDataProvider<StarTrek>(fakerService) {
    override val yamlCategory = YamlCategory.STAR_TREK
    override val localUniqueDataProvider = LocalUniqueDataProvider<StarTrek>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun character() = resolve("character")
    fun location() = resolve("location")
    fun specie() = resolve("specie")
    fun villain() = resolve("villain")
}
