package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SEINFELD] category.
 */
@Suppress("unused")
class Seinfeld internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Seinfeld>(fakerService) {
    override val yamlCategory = YamlCategory.SEINFELD
    override val localUniqueDataProvider = LocalUniqueDataProvider<Seinfeld>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun character() = resolve("character")
    fun quote() = resolve("quote")
    fun business() = resolve("business")
}
