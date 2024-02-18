package io.github.serpro69.kfaker.misc.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HOBBY] category.
 */
@Suppress("unused")
class Hobby internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Hobby>(fakerService) {
    override val yamlCategory = YamlCategory.HOBBY
    override val localUniqueDataProvider = LocalUniqueDataProvider<Hobby>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun activity(): String = resolve("activity")
}
