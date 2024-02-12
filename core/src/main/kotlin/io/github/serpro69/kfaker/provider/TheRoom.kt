@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.THE_ROOM] category.
 */
class TheRoom internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TheRoom>(fakerService) {
    override val yamlCategory = YamlCategory.THE_ROOM
    override val localUniqueDataProvider = LocalUniqueDataProvider<TheRoom>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun actors(): String = resolve("actors")
    fun characters(): String = resolve("characters")
    fun locations(): String = resolve("locations")
    fun quotes(): String = resolve("quotes")
}
