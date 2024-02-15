package io.github.serpro69.kfaker.games.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HEROES] category.
 */
@Suppress("unused")
class Heroes internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Heroes>(fakerService) {
    override val yamlCategory = YamlCategory.HEROES
    override val localUniqueDataProvider = LocalUniqueDataProvider<Heroes>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun names() = resolve("names")
    fun specialties() = resolve("specialties")
    fun klasses() = resolve("klasses")
    fun artifacts() = resolve("artifacts")
}
