package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.NAME] category.
 */
@Suppress("unused")
class Name internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Name>(fakerService) {
    override val categoryName = CategoryName.NAME
    override val localUniqueDataProvider = LocalUniqueDataProvider<Name>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun maleFirstName() = resolve("male_first_name")
    fun femaleFirstName() = resolve("female_first_name")
    fun firstName() = resolve("first_name")
    fun lastName() = resolve("last_name")
    fun name() = resolve("name")
    fun nameWithMiddle() = resolve("name_with_middle")
}