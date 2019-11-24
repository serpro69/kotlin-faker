package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.OPERA] category.
 */
@Suppress("unused")
class Opera internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Opera>(fakerService) {
    override val categoryName = CategoryName.OPERA
    override val localUniqueDataProvider = LocalUniqueDataProvider<Opera>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)
    val italian = Italian()

    // TODO: 24.11.2019 fun italian() // resolves to a random italian opera

    inner class Italian internal constructor() {
        fun byGiuseppeVerdi() = resolve("italian", "by_giuseppe_verdi")
        fun byGioacchinoRossini() = resolve("italian", "by_gioacchino_rossini")
        fun byGaetanoDonizetti() = resolve("italian", "by_gaetano_donizetti")
        fun byVincenzoBellini() = resolve("italian", "by_vincenzo_bellini")
    }
}
