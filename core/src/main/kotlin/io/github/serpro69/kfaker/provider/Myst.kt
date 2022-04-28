package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class Myst internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Myst>(fakerService) {
    override val category = YamlCategory.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<Myst>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun games() = resolve("myst", "games")
    fun creatures() = resolve("myst", "creatures")
    fun characters() = resolve("myst", "characters")
    fun ages() = resolve("myst", "ages")
    fun quotes() = resolve("myst", "quotes")
}
