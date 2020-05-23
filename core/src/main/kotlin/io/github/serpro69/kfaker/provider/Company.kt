package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COMPANY] category.
 */
@Suppress("unused")
class Company internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Company>(fakerService) {
    override val categoryName = CategoryName.COMPANY
    override val localUniqueDataProvider = LocalUniqueDataProvider<Company>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun buzzwords() = resolve("buzzwords")
    fun bs() = resolve("bs")
    fun name() = resolve("name")
    fun industry() = resolve("industry")
    fun profession() = resolve("profession")
    fun type() = resolve("type")
    fun sicCode() = resolve("sic_code")
}
