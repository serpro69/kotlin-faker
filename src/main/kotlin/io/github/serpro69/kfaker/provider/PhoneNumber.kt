package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.PHONE_NUMBER] category.
 */
@Suppress("unused")
class PhoneNumber internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<PhoneNumber>(fakerService) {
    override val categoryName = CategoryName.PHONE_NUMBER
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val phoneNumber = resolve("formats")
    val cellPhone = resolve {
        fakerService.resolve(fakerService.fetchCategory(CategoryName.CELL_PHONE), "formats")
    }
    val countryCode = resolve {
        fakerService.resolve(fakerService.fetchCategory(CategoryName.COUNTRY_CODE), "codes")
    }
}
