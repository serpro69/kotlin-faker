package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.RESTAURANT] category.
 */
@Suppress("unused")
class Restaurant internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Restaurant>(fakerService) {
    override val yamlCategory = YamlCategory.RESTAURANT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Restaurant>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun name() = with(fakerService) { resolve("name").numerify().letterify() }
    fun type() = resolve("type")
    fun description() = resolve("description")
    fun review() = resolve("review")
}
