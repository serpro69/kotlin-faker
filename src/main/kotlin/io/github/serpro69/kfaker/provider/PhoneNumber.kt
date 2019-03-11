package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.PHONE_NUMBER] category.
 */
class PhoneNumber internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.PHONE_NUMBER

    val phoneNumber = resolve { fakerService.resolve(Faker, it, "phone_number", "formats") }
    val cellPhone = resolve { fakerService.resolve(Faker, it, "cell_phone", "formats") }
    val countryCode = resolve { fakerService.resolve(Faker, it, "country_code", "codes") }
}
