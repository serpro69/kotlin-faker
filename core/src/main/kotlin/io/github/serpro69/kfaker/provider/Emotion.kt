@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.EMOTION] category.
 */
class Emotion internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<Emotion>(fakerService) {
    override val category = YamlCategory.EMOTION
    override val localUniqueDataProvider = LocalUniqueDataProvider<Emotion>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun adjective(): String = resolve("adjective")
    fun noun(): String = resolve("noun")
}

