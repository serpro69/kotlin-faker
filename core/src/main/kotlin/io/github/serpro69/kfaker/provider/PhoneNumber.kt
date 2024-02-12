@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.PHONE_NUMBER] category.
 */
class PhoneNumber internal constructor(fakerService: FakerService) : YamlFakeDataProvider<PhoneNumber>(fakerService) {
    override val yamlCategory = YamlCategory.PHONE_NUMBER
    override val localUniqueDataProvider = LocalUniqueDataProvider<PhoneNumber>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    val cellPhone by lazy { CellPhone(fakerService) }

    @Deprecated(
        message = "This functionality is deprecated and will be removed in future releases",
        ReplaceWith("countryCode()"),
        level = DeprecationLevel.WARNING
    )
    val countryCode by lazy { CountryCode(fakerService) }

    fun areaCode() = resolve("area_code")
    fun countryCode() = resolve("country_code")
    fun exchangeCode() = resolve("exchange_code")
    fun phoneNumber() = with(fakerService) { resolve("formats").numerify() }

    @Deprecated(
        message = "This function is deprecated and will be removed in future releases",
        ReplaceWith("cellPhone.number()"),
        level = DeprecationLevel.WARNING
    )
    fun cellPhone() = cellPhone.number()
}

/**
 * [FakeDataProvider] implementation for [YamlCategory.CELL_PHONE] category.
 */
class CellPhone internal constructor(fakerService: FakerService) : YamlFakeDataProvider<CellPhone>(fakerService) {
    override val yamlCategory = YamlCategory.CELL_PHONE
    override val localUniqueDataProvider = LocalUniqueDataProvider<CellPhone>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun number() = with(fakerService) { resolve("formats").numerify() }
}

/**
 * [FakeDataProvider] implementation for [YamlCategory.COUNTRY_CODE] category.
 */
@Deprecated(
    message = "This functionality is deprecated and will be removed in future releases",
    level = DeprecationLevel.WARNING
)
class CountryCode internal constructor(fakerService: FakerService) : YamlFakeDataProvider<CountryCode>(fakerService) {
    override val yamlCategory = YamlCategory.COUNTRY_CODE
    override val localUniqueDataProvider = LocalUniqueDataProvider<CountryCode>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun code() = resolve("country_code")
}

fun main() {
    faker{}.phoneNumber.countryCode.code()
}
