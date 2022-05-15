@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BIBLE] category.
 */
class Bible internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Bible>(fakerService) {
    override val category = YamlCategory.BIBLE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Bible>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun character() = resolve("character")
    fun location() = resolve("location")
    fun quote() = resolve("quote")
}
