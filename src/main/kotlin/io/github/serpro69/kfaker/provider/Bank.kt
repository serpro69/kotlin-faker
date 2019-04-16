package io.github.serpro69.kfaker.provider

import com.mifmif.common.regex.*
import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BANK] category.
 */
class Bank internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BANK

    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val swiftBic = resolve { fakerService.resolve(Faker, it, "swift_bic") }
    val ibanDetails: (countryCode: String) -> String = { code ->
        val regex = resolve { fakerService.resolve(Faker, it, "iban_details", code.toLowerCase()) }.invoke()
            .drop(1)
            .dropLast(1)
            .split(", ")
            .last()
            .split("=")
            .last()

        Generex(regex).random()
    }
}
