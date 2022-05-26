@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.TEA] category.
 */
class Tea internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Tea>(fakerService) {
    override val yamlCategory = YamlCategory.TEA
    override val localUniqueDataProvider = LocalUniqueDataProvider<Tea>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    val variety = TeaVariety(fakerService)

    fun type(): String = resolve("type")
}

class TeaVariety internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TeaVariety>(fakerService) {
    override val yamlCategory = YamlCategory.TEA
    override val localUniqueDataProvider = LocalUniqueDataProvider<TeaVariety>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun black(): String = resolve("black")
    fun oolong(): String = resolve("oolong")
    fun green(): String = resolve("green")
    fun white(): String = resolve("white")
    fun herbal(): String = resolve("herbal")
}
