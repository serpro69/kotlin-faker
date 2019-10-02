package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ADDRESS] category.
 */
class Address internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.ADDRESS

    val country = resolve { fakerService.resolve(it, "country") }
    val countryByCode: (countryCode: String) -> String = { code ->
        resolve { fakerService.resolve(it, "country_by_code", code) }.invoke()
    }
    val countryByName: (countryName: String) -> String = { name ->
        resolve { fakerService.resolve(it, "country_by_name", name) }.invoke()
    }
    val countryCode = resolve { fakerService.resolve(it, "country_code") }
    val countryCodeLong = resolve { fakerService.resolve(it, "country_code_long") }
    val buildingNumber = resolve { fakerService.resolve(it, "building_number") }
    val community = resolve { fakerService.resolve(it, "community") }
    val secondaryAddress = resolve { fakerService.resolve(it, "secondary_address") }
    val postcode = resolve { fakerService.resolve(it, "postcode") }
    val postcodeByState: (state: String) -> String = { state ->
        resolve { fakerService.resolve(it, "postcode_by_state", state) }.invoke()
    }
    val state = resolve { fakerService.resolve(it, "state") }
    val stateAbbr = resolve { fakerService.resolve(it, "state_abbr") }
    val timeZone = resolve { fakerService.resolve(it, "time_zone") }
    val city = resolve { fakerService.resolve(it, "city") }
    val streetName = resolve { fakerService.resolve(it, "street_name") }
    val streetAddress = resolve { fakerService.resolve(it, "street_address") }
    val fullAddress = resolve { fakerService.resolve(it, "full_address") }
    val defaultCountry = resolve { fakerService.resolve(it, "default_country") }
}
