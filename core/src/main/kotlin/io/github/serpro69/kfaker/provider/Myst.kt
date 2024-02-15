package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class Myst internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Myst>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("MYST")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Myst>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun games() = resolve(secondaryCategory, "games")
    fun creatures() = resolve(secondaryCategory, "creatures")
    fun characters() = resolve(secondaryCategory, "characters")
    fun ages() = resolve(secondaryCategory, "ages")
    fun quotes() = resolve(secondaryCategory, "quotes")
}
