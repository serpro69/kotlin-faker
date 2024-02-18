package io.github.serpro69.kfaker.games.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DND] category.
 */
@Suppress("unused")
class DnD internal constructor(fakerService: FakerService) : YamlFakeDataProvider<DnD>(fakerService) {
    override val yamlCategory = YamlCategory.DND
    override val localUniqueDataProvider = LocalUniqueDataProvider<DnD>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    val name by lazy { DndName(fakerService) }

    fun alignments() = resolve("alignments")
    fun backgrounds() = resolve("backgrounds")
    fun cities() = resolve("cities")
    fun klasses() = resolve("klasses")
    fun languages() = resolve("languages")
    fun meleeWeapons() = resolve("melee_weapons")
    fun monsters() = resolve("monsters")
    fun races() = resolve("races")
    fun rangedWeapons() = resolve("ranged_weapons")
}

/**
 * [FakeDataProvider] implementation for [YamlCategory.DND] category.
 */
class DndName internal constructor(fakerService: FakerService) : YamlFakeDataProvider<DndName>(fakerService) {
    override val yamlCategory = YamlCategory.DND
    override val localUniqueDataProvider = LocalUniqueDataProvider<DndName>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun firstName() = resolve("name", "first_name")
    fun lastName() = resolve("name", "last_name")
    fun title() = resolve("name", "title")
}
