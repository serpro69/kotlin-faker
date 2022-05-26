package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.FOOTBALL] category.
 */
@Suppress("unused")
class Football internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Football>(fakerService) {
    override val yamlCategory = YamlCategory.FOOTBALL
    override val localUniqueDataProvider = LocalUniqueDataProvider<Football>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun teams() = resolve("teams")
    fun players() = resolve("players")
    fun coaches() = resolve("coaches")
    fun competitions() = resolve("competitions")
    fun positions() = resolve("positions")
}
