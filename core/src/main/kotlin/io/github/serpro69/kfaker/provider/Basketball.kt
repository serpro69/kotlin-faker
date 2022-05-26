package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BASKETBALL] category.
 */
@Suppress("unused")
class Basketball internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Basketball>(fakerService) {
    override val yamlCategory = YamlCategory.BASKETBALL
    override val localUniqueDataProvider = LocalUniqueDataProvider<Basketball>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun teams() = resolve("teams")
    fun players() = resolve("players")
    fun coaches() = resolve("coaches")
    fun positions() = resolve("positions")
}
