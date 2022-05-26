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
    YamlFakeDataProvider<Hobby>(fakerService) {
    override val yamlCategory = YamlCategory.HOBBY
    override val localUniqueDataProvider = LocalUniqueDataProvider<Hobby>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun activity(): String = resolve("activity")
}
