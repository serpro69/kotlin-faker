package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.FAMILY_GUY] category.
 */
@Suppress("unused")
class FamilyGuy internal constructor(fakerService: FakerService) : YamlFakeDataProvider<FamilyGuy>(fakerService) {
    override val yamlCategory = YamlCategory.FAMILY_GUY
    override val localUniqueDataProvider = LocalUniqueDataProvider<FamilyGuy>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun character() = resolve("character")
    fun location() = resolve("location")
    fun quote() = resolve("quote")
}
