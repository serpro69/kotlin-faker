package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BOSSA_NOVA] category.
 */
@Suppress("unused")
class BossaNova internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<BossaNova>(fakerService) {
    override val category = YamlCategory.BOSSA_NOVA
    override val localUniqueDataProvider = LocalUniqueDataProvider<BossaNova>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun artists() = resolve("artists")
    fun songs() = resolve("songs")
}
