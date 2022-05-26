package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class Pokemon internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Pokemon>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<Pokemon>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun names() = resolve("pokemon", "names")
    fun locations() = resolve("pokemon", "locations")
    fun moves() = resolve("pokemon", "moves")
}
