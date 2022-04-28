package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.LOVECRAFT] category.
 */
@Suppress("unused")
class Lovecraft internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Lovecraft>(fakerService) {
    override val category = YamlCategory.LOVECRAFT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Lovecraft>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun fhtagn() = resolve("fhtagn")
    fun deity() = resolve("deity")
    fun location() = resolve("location")
    fun tome() = resolve("tome")
    fun words() = resolve("words")
}
