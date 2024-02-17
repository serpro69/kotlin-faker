@file:Suppress("unused")

package io.github.serpro69.kfaker.books.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.TOLKIEN] category.
 */
class Tolkien internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Tolkien>(fakerService) {
    override val yamlCategory = YamlCategory.TOLKIEN
    override val localUniqueDataProvider = LocalUniqueDataProvider<Tolkien>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    val lordOfTheRings by lazy { TolkienLordOfTheRings(fakerService) }

    val hobbit by lazy { TolkienHobbit(fakerService) }

    fun poems(): String = resolve("poems")
    fun locations(): String = resolve("locations")
    fun races(): String = resolve("races")
    fun characters(): String = resolve("characters")
}

class TolkienLordOfTheRings internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TolkienLordOfTheRings>(fakerService) {
    override val yamlCategory = YamlCategory.TOLKIEN
    override val localUniqueDataProvider = LocalUniqueDataProvider<TolkienLordOfTheRings>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters(): String = resolve("lord_of_the_rings", "characters")
    fun locations(): String = resolve("lord_of_the_rings", "locations")
    fun quotes(): String = resolve("lord_of_the_rings", "quotes")
}

class TolkienHobbit internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TolkienHobbit>(fakerService) {
    override val yamlCategory = YamlCategory.TOLKIEN
    override val localUniqueDataProvider = LocalUniqueDataProvider<TolkienHobbit>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun character(): String = resolve("hobbit", "character")
    fun thorinsCompany(): String = resolve("hobbit", "thorins_company")
    fun quote(): String = resolve("hobbit", "quote")
    fun location(): String = resolve("hobbit", "location")
}
