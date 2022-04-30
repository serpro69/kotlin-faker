package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.PHONE_NUMBER] category.
 */
@Suppress("unused")
class PhoneNumber internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<PhoneNumber>(fakerService) {
    override val category = YamlCategory.PHONE_NUMBER
    override val localUniqueDataProvider = LocalUniqueDataProvider<PhoneNumber>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun phoneNumber() = with(fakerService) { resolve("formats").numerify() }
    fun cellPhone() = with(fakerService) {
        resolve(fetchEntry(YamlCategory.CELL_PHONE), "formats").numerify()
    }
    fun countryCode() = with(fakerService) {
        resolve(fetchEntry(YamlCategory.COUNTRY_CODE), "codes")
    }
}
