@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.FINAL_SPACE] category.
 */
class FinalSpace internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<FinalSpace>(fakerService) {
    override val category = YamlCategory.FINAL_SPACE
    override val localUniqueDataProvider = LocalUniqueDataProvider<FinalSpace>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters(): String = resolve("characters")
    fun vehicles(): String = resolve("vehicles")
    fun quotes(): String = resolve("quotes")
}

