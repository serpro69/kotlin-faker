package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ADDRESS] category.
 */
@Suppress("unused")
class Address internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Address>(fakerService) {
    override val category = YamlCategory.ADDRESS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Address>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun country() = resolve("country")
    fun countryByCode(countryCode: String) = resolve("country_by_code", countryCode)
    fun countryByName(countryName: String) = resolve("country_by_name", countryName)
    fun countryCode() = resolve("country_code")
    fun countryCodeLong() = resolve("country_code_long")
    fun buildingNumber() = with(fakerService) { resolve("building_number").numerify() }
    fun community() = resolve("community")
    fun secondaryAddress() = with(fakerService) { resolve("secondary_address").numerify() }
    fun postcode() = with(fakerService) {
        when (faker.config.locale) {
            "nl" -> resolve("postcode").generexify().replace("/", "")
            else -> resolve("postcode").numerify()
        }
    }

    fun postcodeByState(state: String) = with(fakerService) { resolve("postcode_by_state", state).numerify() }
    fun state() = resolve("state")
    fun stateAbbr() = resolve("state_abbr")
    fun timeZone() = resolve("time_zone")
    fun city() = resolve("city")
    fun cityWithState() = resolve("city_with_state")
    fun streetName() = resolve("street_name")
    fun streetAddress() = with(fakerService) { resolve("street_address").numerify() }
    fun fullAddress() = with(fakerService) { resolve("full_address").numerify() }
    fun mailbox() = with(fakerService) { resolve("mail_box").numerify() }
    fun defaultCountry() = resolve("default_country")

    // TODO make internal
    @Deprecated(
        message = "This function will become 'private' in 1.9.0",
        replaceWith = ReplaceWith("city()"),
        level = DeprecationLevel.WARNING
    )
    fun cityName() = resolve("city_name")

    // fix #87
    internal fun cityRoot() = resolve("city_root")
}

