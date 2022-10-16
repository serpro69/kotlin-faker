@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.MOUNTAINEERING] category.
 */
class Mountaineering internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Mountaineering>(fakerService) {
    override val yamlCategory = YamlCategory.MOUNTAINEERING
    override val localUniqueDataProvider = LocalUniqueDataProvider<Mountaineering>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun mountaineer(): String = resolve("mountaineer")
}
