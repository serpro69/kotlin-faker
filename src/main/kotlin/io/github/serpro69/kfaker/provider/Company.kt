package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COMPANY] category.
 */
@Suppress("unused")
class Company internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Company>(fakerService) {
    override val categoryName = CategoryName.COMPANY
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val buzzwords = resolve("buzzwords")
    val bs = resolve("bs")
    val name = resolve("name")
    val industry = resolve("industry")
    val profession = resolve("profession")
    val type = resolve("type")
}
