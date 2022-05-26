package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HACKER] category.
 */
@Suppress("unused")
class Hacker internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Hacker>(fakerService) {
    override val yamlCategory = YamlCategory.HACKER
    override val localUniqueDataProvider = LocalUniqueDataProvider<Hacker>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun abbreviation() = resolve("abbreviation")
    fun adjective() = resolve("adjective")
    fun noun() = resolve("noun")
    fun verb() = resolve("verb")
    fun ingverb() = resolve("ingverb")
}
