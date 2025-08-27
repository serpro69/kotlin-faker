package io.github.serpro69.kfaker.games.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.GAMES] category. */
@Suppress("unused")
class WorldOfWarcraft internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<WorldOfWarcraft>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("WORLD_OF_WARCRAFT")
    override val localUniqueDataProvider = LocalUniqueDataProvider<WorldOfWarcraft>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun hero() = resolve(secondaryCategory, "heros") // key typo in the yml file

    fun quotes() = resolve(secondaryCategory, "quotes")

    fun classNames() = resolve(secondaryCategory, "class_names")

    fun races() = resolve(secondaryCategory, "races")
}
