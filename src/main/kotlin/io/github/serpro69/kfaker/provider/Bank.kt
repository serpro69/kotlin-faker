package io.github.sergio.igwt.kfaker.provider

import com.mifmif.common.regex.Generex
import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.BANK] category.
 */
class Bank internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BANK

    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val swiftBic = resolve { fakerService.resolve(Faker, it, "swift_bic") }
    val ibanDetails: (countryCode: String) -> String = { code ->
        val regex = resolve { fakerService.resolve(Faker, it, "iban_details", code) }.invoke()
            .drop(1)
            .dropLast(1)
            .split(", ")
            .last()
            .split("=")
            .last()

        Generex(regex).random()
    }
}
