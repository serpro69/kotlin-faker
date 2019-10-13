package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HOW_I_MET_YOUR_MOTHER] category.
 */
@Suppress("unused")
class HowIMetYourMother internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<HowIMetYourMother>(fakerService) {
    override val categoryName = CategoryName.HOW_I_MET_YOUR_MOTHER
    override val uniqueDataProvider = UniqueDataProvider<HowIMetYourMother>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun character() = resolve("character")
    fun catchPhrase() = resolve("catch_phrase")
    fun highFive() = resolve("high_five")
    fun quote() = resolve("quote")
}
