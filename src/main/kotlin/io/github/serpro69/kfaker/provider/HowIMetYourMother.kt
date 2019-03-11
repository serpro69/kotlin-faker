package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.HOW_I_MET_YOUR_MOTHER] category.
 */
class HowIMetYourMother internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HOW_I_MET_YOUR_MOTHER

    val character = resolve { fakerService.resolve(Faker, it, "character") }
    val catchPhrase = resolve { fakerService.resolve(Faker, it, "catch_phrase") }
    val highFive = resolve { fakerService.resolve(Faker, it, "high_five") }
    val quote = resolve { fakerService.resolve(Faker, it, "quote") }
}
