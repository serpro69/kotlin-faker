package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DUMB_AND_DUMBER] category.
 */
@Suppress("unused")
class DumbAndDumber internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<DumbAndDumber>(fakerService) {
    override val categoryName = CategoryName.DUMB_AND_DUMBER
    override val localUniqueDataProvider = LocalUniqueDataProvider<DumbAndDumber>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun actors() = resolve("actors")
    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
