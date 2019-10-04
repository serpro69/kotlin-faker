package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HOW_I_MET_YOUR_MOTHER] category.
 */
@Suppress("unused")
class HowIMetYourMother internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<HowIMetYourMother>(fakerService) {
    override val categoryName = CategoryName.HOW_I_MET_YOUR_MOTHER
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val character = resolve("character")
    val catchPhrase = resolve("catch_phrase")
    val highFive = resolve("high_five")
    val quote = resolve("quote")
}
