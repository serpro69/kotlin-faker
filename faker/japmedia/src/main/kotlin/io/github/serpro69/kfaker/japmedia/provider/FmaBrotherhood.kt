package io.github.serpro69.kfaker.japmedia.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.FMA_BROTHERHOOD] category.
 */
@Suppress("unused")
class FmaBrotherhood internal constructor(fakerService: FakerService) : YamlFakeDataProvider<FmaBrotherhood>(fakerService) {
    override val yamlCategory = YamlCategory.FMA_BROTHERHOOD
    override val localUniqueDataProvider = LocalUniqueDataProvider<FmaBrotherhood>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters(): String = resolve("characters")
    fun cities(): String = resolve("cities")
    fun countries(): String = resolve("countries")
}
