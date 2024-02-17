@file:Suppress("unused")

package io.github.serpro69.kfaker.books.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BIBLE] category.
 */
class Bible internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Bible>(fakerService) {
    override val yamlCategory = YamlCategory.BIBLE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Bible>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun character() = resolve("character")
    fun location() = resolve("location")
    fun quote() = resolve("quote")
}
