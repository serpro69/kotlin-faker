@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ADJECTIVE] category.
 */
class Adjective internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Adjective>(fakerService) {
    override val yamlCategory = YamlCategory.ADJECTIVE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Adjective>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun positive() = resolve("positive")
    fun negative() = resolve("negative")
}

