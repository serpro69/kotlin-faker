@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HOBBY] category.
 */
class Hobby internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<Hobby>(fakerService) {
    override val category = YamlCategory.HOBBY
    override val localUniqueDataProvider = LocalUniqueDataProvider<Hobby>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun activity(): String = resolve("activity")
}
