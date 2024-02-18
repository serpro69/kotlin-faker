package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HOBBIT] category.
 */
@Suppress("unused")
class Hobbit internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Hobbit>(fakerService) {
    override val yamlCategory = YamlCategory.HOBBIT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Hobbit>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun character() = resolve("character")
    fun thorinsCompany() = resolve("thorins_company")
    fun quote() = resolve("quote")
    fun location() = resolve("location")
}
