package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.BREAKING_BAD] category. */
@Suppress("unused")
class BreakingBad internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<BreakingBad>(fakerService) {
    override val yamlCategory = YamlCategory.BREAKING_BAD
    override val localUniqueDataProvider = LocalUniqueDataProvider<BreakingBad>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun character() = resolve("character")

    fun episode() = resolve("episode")
}
