package io.github.serpro69.kfaker.provider

import com.mifmif.common.regex.*
import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BANK] category.
 */
@Suppress("unused")
class Bank internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Bank>(fakerService) {
    override val categoryName = CategoryName.BANK
    override val localUniqueDataProvider = LocalUniqueDataProvider<Bank>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun name() = resolve("name")
    fun swiftBic() = resolve("swift_bic")
    fun ibanDetails(countryCode: String): String {
        val regex = resolve("iban_details", countryCode.toLowerCase())
            .drop(1)
            .dropLast(1)
            .split(", ")
            .last()
            .split("=")
            .last()

        return Generex(regex).random()
    }
}
