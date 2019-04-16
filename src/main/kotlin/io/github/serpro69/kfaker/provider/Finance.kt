package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FINANCE] category.
 */
@Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
class Finance internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.FINANCE

//    val creditCard: (type: String) -> String = { type ->
//        resolve { fakerService.resolve(Faker, it, "credit_card", type) }.invoke()
//        TODO("Not implemented") //needs custom logic to resolve expressions
//    }
//    val vatNumber: (countryCode: String) -> String = { countryCode ->
//        resolve { fakerService.resolve(Faker, it, "vat_number", countryCode) }.invoke()
//        TODO("Not implemented") //needs custom logic to resolve expressions
//    }
}
