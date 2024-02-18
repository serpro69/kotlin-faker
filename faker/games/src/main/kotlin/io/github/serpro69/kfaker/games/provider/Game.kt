package io.github.serpro69.kfaker.games.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAME] category.
 */
@Suppress("unused")
class Game internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Game>(fakerService) {
    override val yamlCategory = YamlCategory.GAME
    override val localUniqueDataProvider = LocalUniqueDataProvider<Game>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun title() = resolve("title")
}
