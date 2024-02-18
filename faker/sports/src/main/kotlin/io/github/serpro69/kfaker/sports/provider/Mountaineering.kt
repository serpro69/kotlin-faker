@file:Suppress("unused")

package io.github.serpro69.kfaker.sports.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
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
