package io.github.serpro69.kfaker.japmedia.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DORAEMON] category.
 */
@Suppress("unused")
class Doraemon internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Doraemon>(fakerService) {
    override val yamlCategory = YamlCategory.DORAEMON
    override val localUniqueDataProvider = LocalUniqueDataProvider<Doraemon>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters(): String = resolve("characters")
    fun gadgets(): String = resolve("gadgets")
    fun locations(): String = resolve("locations")
}

