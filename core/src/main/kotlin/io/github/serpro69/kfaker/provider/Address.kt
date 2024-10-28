package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.extension.or
import io.github.serpro69.kfaker.faker
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ADDRESS] category.
 */
@Suppress("unused")
class Address internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Address>(fakerService) {
    override val yamlCategory = YamlCategory.ADDRESS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Address>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun cityPrefix() = resolve("city_prefix")
    fun citySuffix() = resolve("city_suffix")
    fun country() = resolve("country")
    fun countryByCode(countryCode: String) = resolve("country_by_code", countryCode)
    fun countryByName(countryName: String) = resolve("country_by_name", countryName)
    fun countryCode() = resolve("default_country_code" or "country_code")
    fun countryCodeLong() = resolve("country_code_long")
    fun buildingNumber() = with(fakerService) { resolve("building_number").numerify() }
    fun communityPrefix() = resolve("community_prefix")
    fun communitySuffix() = resolve("community_suffix")
    fun community() = resolve("community")
    fun streetSuffix() = resolve("street_suffix")
    fun secondaryAddress() = with(fakerService) { resolve("secondary_address").numerify() }
    fun postcode() = with(fakerService) {
        val pattern = resolve("postcode")
        when {
            pattern.contains('#') -> pattern.numerify()
            else -> pattern.regexify().replace("/", "")
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
}
