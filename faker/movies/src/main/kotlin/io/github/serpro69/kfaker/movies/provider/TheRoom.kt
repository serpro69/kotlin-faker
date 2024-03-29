@file:Suppress("unused")

package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.THE_ROOM] category.
 */
class TheRoom internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TheRoom>(fakerService) {
    override val yamlCategory = YamlCategory.THE_ROOM
    override val localUniqueDataProvider = LocalUniqueDataProvider<TheRoom>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun actors(): String = resolve("actors")
    fun characters(): String = resolve("characters")
    fun locations(): String = resolve("locations")
    fun quotes(): String = resolve("quotes")
}
