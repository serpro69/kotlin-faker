package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.THE_IT_CROWD] category.
 */
@Suppress("unused")
class TheITCrowd internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<TheITCrowd>(fakerService) {
    override val categoryName = CategoryName.THE_IT_CROWD
    override val uniqueDataProvider = UniqueDataProvider<TheITCrowd>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun actors() = resolve("actors")
    fun characters() = resolve("characters")
    fun emails() = resolve("emails")
    fun quotes() = resolve("quotes")
}
