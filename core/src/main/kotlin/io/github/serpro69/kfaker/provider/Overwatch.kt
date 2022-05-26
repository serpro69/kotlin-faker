package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class Overwatch internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Overwatch>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<Overwatch>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun heroes() = resolve("overwatch", "heroes")
    fun locations() = resolve("overwatch", "locations")
    fun quotes() = resolve("overwatch", "quotes")
}
