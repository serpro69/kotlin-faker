package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ELECTRICAL_COMPONENTS] category.
 */
@Suppress("unused")
class ElectricalComponents internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<ElectricalComponents>(fakerService) {
    override val categoryName = CategoryName.ELECTRICAL_COMPONENTS
    override val localUniqueDataProvider = LocalUniqueDataProvider<ElectricalComponents>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun active() = resolve("active")
    fun passive() = resolve("passive")
    fun electromechanical() = resolve("electromechanical")
}
