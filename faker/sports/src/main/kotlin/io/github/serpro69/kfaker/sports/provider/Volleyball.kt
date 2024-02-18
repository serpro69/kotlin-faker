@file:Suppress("unused")

package io.github.serpro69.kfaker.sports.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.VOLLEYBALL] category.
 */
class Volleyball internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Volleyball>(fakerService) {
    override val yamlCategory = YamlCategory.VOLLEYBALL
    override val localUniqueDataProvider = LocalUniqueDataProvider<Volleyball>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun team(): String = resolve("team")
    fun player(): String = resolve("player")
    fun coach(): String = resolve("coach")
    fun position(): String = resolve("position")
    fun formation(): String = resolve("formation")
}
