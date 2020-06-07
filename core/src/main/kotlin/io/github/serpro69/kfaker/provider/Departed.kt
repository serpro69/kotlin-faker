package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DEPARTED] category.
 */
@Suppress("unused")
class Departed internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Departed>(fakerService) {
    override val categoryName = CategoryName.DEPARTED
    override val localUniqueDataProvider = LocalUniqueDataProvider<Departed>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun actors() = resolve("actors")
    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
