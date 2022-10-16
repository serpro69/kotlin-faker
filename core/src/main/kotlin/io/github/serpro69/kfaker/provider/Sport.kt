package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SPORT] category.
 */
@Suppress("unused")
class Sport internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Sport>(fakerService) {
    override val yamlCategory = YamlCategory.SPORT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Sport>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun summerOlympics() = resolve("summer_olympics")
    fun winterOlympics() = resolve("winter_olympics")
    fun summerParalympics() = resolve("summer_paralympics")
    fun winterParalympics() = resolve("winter_paralympics")
    fun ancientOlympics() = resolve("ancient_olympics")
    fun unusual() = resolve("unusual")
}
