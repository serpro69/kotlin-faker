package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.PHONE_NUMBER] category.
 */
class PhoneNumber internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.PHONE_NUMBER

    val phoneNumber = resolve { fakerService.resolve(Faker, it, "phone_number", "formats") }
    val cellPhone = resolve { fakerService.resolve(Faker, it, "cell_phone", "formats") }
    val countryCode = resolve { fakerService.resolve(Faker, it, "country_code", "codes") }
}
