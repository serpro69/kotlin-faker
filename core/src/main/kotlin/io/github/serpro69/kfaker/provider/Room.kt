@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ROOM] category.
 */
class Room internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Room>(fakerService) {
    override val yamlCategory = YamlCategory.ROOM
    override val localUniqueDataProvider = LocalUniqueDataProvider<Room>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun actors(): String = resolve("actors")
    fun characters(): String = resolve("characters")
    fun locations(): String = resolve("locations")
    fun quotes(): String = resolve("quotes")
}
