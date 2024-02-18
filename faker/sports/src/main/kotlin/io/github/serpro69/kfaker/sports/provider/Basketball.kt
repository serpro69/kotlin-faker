package io.github.serpro69.kfaker.sports.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BASKETBALL] category.
 */
@Suppress("unused")
class Basketball internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Basketball>(fakerService) {
    override val yamlCategory = YamlCategory.BASKETBALL
    override val localUniqueDataProvider = LocalUniqueDataProvider<Basketball>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun teams() = resolve("teams")
    fun players() = resolve("players")
    fun coaches() = resolve("coaches")
    fun positions() = resolve("positions")
}
