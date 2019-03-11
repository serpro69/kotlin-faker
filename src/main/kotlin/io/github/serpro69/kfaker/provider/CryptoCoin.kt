package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CRYPTO_COIN] category.
 */
class CryptoCoin internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.CRYPTO_COIN

    val coin = resolve { fakerService.resolve(Faker, it, "coin") }
}
