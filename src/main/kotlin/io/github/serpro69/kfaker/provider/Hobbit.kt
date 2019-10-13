package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HOBBIT] category.
 */
@Suppress("unused")
class Hobbit internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Hobbit>(fakerService) {
    override val categoryName = CategoryName.HOBBIT
    override val uniqueDataProvider = UniqueDataProvider<Hobbit>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun character() = resolve("character")
    fun thorinsCompany() = resolve("thorins_company")
    fun quote() = resolve("quote")
    fun location() = resolve("location")
}
