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
class Myst internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Myst>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("MYST")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Myst>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun games() = resolve(secondaryCategory, "games")

    fun creatures() = resolve(secondaryCategory, "creatures")

    fun characters() = resolve(secondaryCategory, "characters")

    fun ages() = resolve(secondaryCategory, "ages")

    fun quotes() = resolve(secondaryCategory, "quotes")
}
