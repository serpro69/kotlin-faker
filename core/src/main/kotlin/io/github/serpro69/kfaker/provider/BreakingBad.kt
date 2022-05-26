package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BREAKING_BAD] category.
 */
@Suppress("unused")
class BreakingBad internal constructor(fakerService: FakerService) : YamlFakeDataProvider<BreakingBad>(fakerService) {
    override val yamlCategory = YamlCategory.BREAKING_BAD
    override val localUniqueDataProvider = LocalUniqueDataProvider<BreakingBad>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun character() = resolve("character")
    fun episode() = resolve("episode")
}
