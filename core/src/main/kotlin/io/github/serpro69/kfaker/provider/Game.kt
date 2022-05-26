package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAME] category.
 */
@Suppress("unused")
class Game internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Game>(fakerService) {
    override val yamlCategory = YamlCategory.GAME
    override val localUniqueDataProvider = LocalUniqueDataProvider<Game>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun title() = resolve("title")
}
