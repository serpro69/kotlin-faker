package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.LOVECRAFT] category.
 */
@Suppress("unused")
class Lovecraft internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Lovecraft>(fakerService) {
    override val categoryName = CategoryName.LOVECRAFT
    override val uniqueDataProvider = UniqueDataProvider<Lovecraft>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun fhtagn() = resolve("fhtagn")
    fun deity() = resolve("deity")
    fun location() = resolve("location")
    fun tome() = resolve("tome")
    fun words() = resolve("words")
}
