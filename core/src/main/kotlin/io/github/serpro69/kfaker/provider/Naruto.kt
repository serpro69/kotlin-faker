@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.NARUTO] category.
 */
class Naruto internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<Naruto>(fakerService) {
    override val category = YamlCategory.NARUTO
    override val localUniqueDataProvider = LocalUniqueDataProvider<Naruto>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters(): String = resolve("characters")
    fun villages(): String = resolve("villages")
    fun eyes(): String = resolve("eyes")
    fun demons(): String = resolve("demons")
}
