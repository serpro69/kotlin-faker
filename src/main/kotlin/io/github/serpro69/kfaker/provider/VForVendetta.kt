package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.V_FOR_VENDETTA] category.
 */
@Suppress("unused")
class VForVendetta internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<VForVendetta>(fakerService) {
    override val categoryName = CategoryName.V_FOR_VENDETTA
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val speeches = resolve("speeches")
    val quotes = resolve("quotes")
}
