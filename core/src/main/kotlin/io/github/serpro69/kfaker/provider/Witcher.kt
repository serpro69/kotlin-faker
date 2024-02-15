package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class Witcher internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Witcher>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("WITCHER")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Witcher>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun characters() = resolve(secondaryCategory, "characters")
    fun witchers() = resolve(secondaryCategory, "witchers")
    fun schools() = resolve(secondaryCategory, "schools")
    fun locations() = resolve(secondaryCategory, "locations")
    fun quotes() = resolve(secondaryCategory, "quotes")
    fun monsters() = resolve(secondaryCategory, "monsters")
    fun signs() = resolve(secondaryCategory, "signs")
    fun potions() = resolve(secondaryCategory, "potions")
    fun books() = resolve(secondaryCategory, "books")
}
