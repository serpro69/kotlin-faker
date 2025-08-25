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
class StreetFighter internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<StreetFighter>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("STREET_FIGHTER")
    override val localUniqueDataProvider = LocalUniqueDataProvider<StreetFighter>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun characters() = resolve(secondaryCategory, "characters")

    fun stages() = resolve(secondaryCategory, "stages")

    fun quotes() = resolve(secondaryCategory, "quotes")

    fun moves() = resolve(secondaryCategory, "moves")
}
