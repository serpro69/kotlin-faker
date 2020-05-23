package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.PHONE_NUMBER] category.
 */
@Suppress("unused")
class PhoneNumber internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<PhoneNumber>(fakerService) {
    override val categoryName = CategoryName.PHONE_NUMBER
    override val localUniqueDataProvider = LocalUniqueDataProvider<PhoneNumber>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun phoneNumber() = resolve("formats")
/*
    TODO: 04.10.2019 this needs reimplementation

    fun cellPhone() = resolve {
        fakerService.resolve(fakerService.fetchCategory(CategoryName.CELL_PHONE), "formats")
    }
    fun countryCode() = resolve {
        fakerService.resolve(fakerService.fetchCategory(CategoryName.COUNTRY_CODE), "codes")
    }
*/
}
