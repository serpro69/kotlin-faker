package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.COMPANY] category.
 */
@Suppress("unused")
class Company internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Company>(fakerService) {
    override val yamlCategory = YamlCategory.COMPANY
    override val localUniqueDataProvider = LocalUniqueDataProvider<Company>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun buzzwords() = resolve("buzzwords")
    fun bs() = resolve("bs")
    fun name() = resolve("name")
    fun industry() = resolve("industry")
    fun profession() = resolve("profession")
    fun type() = resolve("type")
    fun sicCode() = resolve("sic_code")
}
