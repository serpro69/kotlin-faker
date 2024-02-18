@file:Suppress("unused")

package io.github.serpro69.kfaker.commerce.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.TEA] category.
 */
class Tea internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Tea>(fakerService) {
    override val yamlCategory = YamlCategory.TEA
    override val localUniqueDataProvider = LocalUniqueDataProvider<Tea>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    val variety by lazy { TeaVariety(fakerService) }

    fun type(): String = resolve("type")
}

class TeaVariety internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TeaVariety>(fakerService) {
    override val yamlCategory = YamlCategory.TEA
    override val localUniqueDataProvider = LocalUniqueDataProvider<TeaVariety>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun black(): String = resolve("variety", "black")
    fun oolong(): String = resolve("variety", "oolong")
    fun green(): String = resolve("variety", "green")
    fun white(): String = resolve("variety", "white")
    fun herbal(): String = resolve("variety", "herbal")
}
