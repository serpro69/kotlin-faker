package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.NAME] category.
 */
@Suppress("unused")
class Name internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Name>(fakerService) {
    override val categoryName = CategoryName.NAME
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val maleFirstName = resolve("male_first_name")
    val femaleFirstName = resolve("female_first_name")
    val firstName = resolve("first_name")
    val lastName = resolve("last_name")
    val name = resolve("name")
    val nameWithMiddle = resolve("name_with_middle")
}