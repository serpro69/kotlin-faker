package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HOW_I_MET_YOUR_MOTHER] category.
 */
@Suppress("unused")
class HowIMetYourMother internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HOW_I_MET_YOUR_MOTHER

    val character = resolve { fakerService.resolve(it, "character") }
    val catchPhrase = resolve { fakerService.resolve(it, "catch_phrase") }
    val highFive = resolve { fakerService.resolve(it, "high_five") }
    val quote = resolve { fakerService.resolve(it, "quote") }
}
