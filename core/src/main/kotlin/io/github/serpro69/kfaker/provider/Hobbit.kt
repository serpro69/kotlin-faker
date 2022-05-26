package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HOBBIT] category.
 */
@Suppress("unused")
class Hobbit internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Hobbit>(fakerService) {
    override val yamlCategory = YamlCategory.HOBBIT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Hobbit>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun character() = resolve("character")
    fun thorinsCompany() = resolve("thorins_company")
    fun quote() = resolve("quote")
    fun location() = resolve("location")
}
