package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ADDRESS] category.
 */
@Suppress("unused")
class Address internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Address>(fakerService) {
    override val categoryName = CategoryName.ADDRESS
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val country = resolve("country")
    val countryByCode: (countryCode: String) -> String = { code ->
        resolve("country_by_code", code)
    }
    val countryByName: (countryName: String) -> String = { name ->
        resolve("country_by_name", name)
    }
    val countryCode = resolve("country_code")
    val countryCodeLong = resolve("country_code_long")
    val buildingNumber = resolve("building_number")
    val community = resolve("community")
    val secondaryAddress = resolve("secondary_address")
    val postcode = resolve("postcode")
    val postcodeByState: (state: String) -> String = { state ->
        resolve("postcode_by_state", state)
    }
    val state = resolve("state")
    val stateAbbr = resolve("state_abbr")
    val timeZone = resolve("time_zone")
    val city = resolve("city")
    val streetName = resolve("street_name")
    val streetAddress = resolve("street_address")
    val fullAddress = resolve("full_address")
    val defaultCountry = resolve("default_country")
}
