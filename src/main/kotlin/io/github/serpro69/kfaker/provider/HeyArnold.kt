package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HEY_ARNOLD] category.
 */
@Suppress("unused")
class HeyArnold internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<HeyArnold>(fakerService) {
    override val categoryName = CategoryName.HEY_ARNOLD
    override val localUniqueDataProvider = LocalUniqueDataProvider<HeyArnold>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun quotes() = resolve("quotes")
}
