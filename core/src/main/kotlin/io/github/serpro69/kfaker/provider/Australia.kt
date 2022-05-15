@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.AUSTRALIA] category.
 */
class Australia internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Australia>(fakerService) {
    override val category = YamlCategory.AUSTRALIA
    override val localUniqueDataProvider = LocalUniqueDataProvider<Australia>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun locations() = resolve("locations")
    fun animals() = resolve("animals")
    fun states() = resolve("states")
}

