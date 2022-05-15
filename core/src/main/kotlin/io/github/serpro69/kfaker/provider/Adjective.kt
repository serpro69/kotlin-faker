@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ADJECTIVE] category.
 */
class Adjective internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Adjective>(fakerService) {
    override val category = YamlCategory.ADJECTIVE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Adjective>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun positive() = resolve("positive")
    fun negative() = resolve("negative")
}

