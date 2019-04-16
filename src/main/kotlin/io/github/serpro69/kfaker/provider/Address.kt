package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ADDRESS] category.
 */
class Address internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.ADDRESS

    val country = resolve { fakerService.resolve(Faker, it, "country") }
    val countryByCode: (countryCode: String) -> String = { code ->
        resolve { fakerService.resolve(Faker, it, "country_by_code", code) }.invoke()
    }
    val countryByName: (countryName: String) -> String = { name ->
        resolve { fakerService.resolve(Faker, it, "country_by_name", name) }.invoke()
    }
    val countryCode = resolve { fakerService.resolve(Faker, it, "country_code") }
    val countryCodeLong = resolve { fakerService.resolve(Faker, it, "country_code_long") }
    val buildingNumber = resolve { fakerService.resolve(Faker, it, "building_number") }
    val community = resolve { fakerService.resolve(Faker, it, "community") }
    val secondaryAddress = resolve { fakerService.resolve(Faker, it, "secondary_address") }
    val postcode = resolve { fakerService.resolve(Faker, it, "postcode") }
    val postcodeByState: (state: String) -> String = { state ->
        resolve { fakerService.resolve(Faker, it, "postcode_by_state", state) }.invoke()
    }
    val state = resolve { fakerService.resolve(Faker, it, "state") }
    val stateAbbr = resolve { fakerService.resolve(Faker, it, "state_abbr") }
    val timeZone = resolve { fakerService.resolve(Faker, it, "time_zone") }
    val city = resolve { fakerService.resolve(Faker, it, "city") }
    val streetName = resolve { fakerService.resolve(Faker, it, "street_name") }
    val streetAddress = resolve { fakerService.resolve(Faker, it, "street_address") }
    val fullAddress = resolve { fakerService.resolve(Faker, it, "full_address") }
    val defaultCountry = resolve { fakerService.resolve(Faker, it, "default_country") }
}
