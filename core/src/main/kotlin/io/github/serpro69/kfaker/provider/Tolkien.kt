@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.TOLKIEN] category.
 */
class Tolkien internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<Tolkien>(fakerService) {
    override val category = YamlCategory.TOLKIEN
    override val localUniqueDataProvider = LocalUniqueDataProvider<Tolkien>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    val lordOfTheRings = TolkienLordOfTheRings(fakerService)

    val hobbit = TolkienHobbit(fakerService)

    fun poems(): String = resolve("poems")
    fun locations(): String = resolve("locations")
    fun races(): String = resolve("races")
    fun characters(): String = resolve("characters")
}

class TolkienLordOfTheRings internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<TolkienLordOfTheRings>(fakerService) {
    override val category = YamlCategory.TOLKIEN
    override val localUniqueDataProvider = LocalUniqueDataProvider<TolkienLordOfTheRings>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters(): String = resolve("characters")
    fun locations(): String = resolve("locations")
    fun quotes(): String = resolve("quotes")
}

class TolkienHobbit internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<TolkienHobbit>(fakerService) {
    override val category = YamlCategory.TOLKIEN
    override val localUniqueDataProvider = LocalUniqueDataProvider<TolkienHobbit>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun character(): String = resolve("character")
    fun thorinsCompany(): String = resolve("thorins_company")
    fun quote(): String = resolve("quote")
    fun location(): String = resolve("location")
}
