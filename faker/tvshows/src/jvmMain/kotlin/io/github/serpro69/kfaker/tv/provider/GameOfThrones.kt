package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.GAME_OF_THRONES] category. */
@Suppress("unused")
class GameOfThrones internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<GameOfThrones>(fakerService) {
    override val yamlCategory = YamlCategory.GAME_OF_THRONES
    override val localUniqueDataProvider = LocalUniqueDataProvider<GameOfThrones>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")

    fun houses() = resolve("houses")

    fun cities() = resolve("cities")

    fun quotes() = resolve("quotes")

    fun dragons() = resolve("dragons")
}
