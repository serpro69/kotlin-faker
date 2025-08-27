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
class ElderScrolls internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<ElderScrolls>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("ELDER_SCROLLS")
    override val localUniqueDataProvider = LocalUniqueDataProvider<ElderScrolls>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun race() = resolve(secondaryCategory, "race")

    fun creature() = resolve(secondaryCategory, "creature")

    fun region() = resolve(secondaryCategory, "region")

    fun dragon() = resolve(secondaryCategory, "dragon")

    fun city() = resolve(secondaryCategory, "city")

    fun firstName() = resolve(secondaryCategory, "first_name")

    fun lastName() = resolve(secondaryCategory, "last_name")

    fun weapon() = resolve(secondaryCategory, "weapon")

    fun jewelry() = resolve(secondaryCategory, "jewelry")
}
