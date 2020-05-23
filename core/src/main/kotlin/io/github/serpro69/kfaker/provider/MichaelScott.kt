package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MICHAEL_SCOTT] category.
 */
@Suppress("unused")
class MichaelScott internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<MichaelScott>(fakerService) {
    override val categoryName = CategoryName.MICHAEL_SCOTT
    override val localUniqueDataProvider = LocalUniqueDataProvider<MichaelScott>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun quotes() = resolve("quotes")
}
