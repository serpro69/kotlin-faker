package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.RUSH] category.
 */
@Suppress("unused")
class Rush internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Rush>(fakerService) {
    override val yamlCategory = YamlCategory.RUSH
    override val localUniqueDataProvider = LocalUniqueDataProvider<Rush>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun players() = resolve("players")
    fun albums() = resolve("albums")
}
