@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
class SuperMario internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<SuperMario>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<SuperMario>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters(): String = resolve("super_mario", "characters")
    fun games(): String = resolve("super_mario", "games")
    fun locations(): String = resolve("super_mario", "locations")
}
