package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.UMPHREYS_MCGEE] category.
 */
@Suppress("unused")
class UmphreysMcgee internal constructor(fakerService: FakerService) : YamlFakeDataProvider<UmphreysMcgee>(fakerService) {
    override val yamlCategory = YamlCategory.UMPHREYS_MCGEE
    override val localUniqueDataProvider = LocalUniqueDataProvider<UmphreysMcgee>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun song() = resolve("song")
}
