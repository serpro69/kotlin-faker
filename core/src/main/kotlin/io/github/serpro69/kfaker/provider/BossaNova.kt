package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BOSSA_NOVA] category.
 */
@Suppress("unused")
class BossaNova internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<BossaNova>(fakerService) {
    override val categoryName = CategoryName.BOSSA_NOVA
    override val localUniqueDataProvider = LocalUniqueDataProvider<BossaNova>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun artists() = resolve("artists")
    fun songs() = resolve("songs")
}
