@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.QUOTE] category.
 */
class JackHandey internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<JackHandey>(fakerService) {
    override val category = YamlCategory.QUOTE
    override val localUniqueDataProvider = LocalUniqueDataProvider<JackHandey>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun quote(): String = resolve("jack_handey")
}
