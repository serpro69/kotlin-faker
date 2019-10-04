package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HOBBIT] category.
 */
@Suppress("unused")
class Hobbit internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Hobbit>(fakerService) {
    override val categoryName = CategoryName.HOBBIT
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val character = resolve("character")
    val thorinsCompany = resolve("thorins_company")
    val quote = resolve("quote")
    val location = resolve("location")
}
