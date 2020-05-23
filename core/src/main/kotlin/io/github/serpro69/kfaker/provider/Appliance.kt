package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.APPLIANCE] category.
 */
@Suppress("unused")
class Appliance internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Appliance>(fakerService) {
    override val categoryName = CategoryName.APPLIANCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Appliance>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun brand() = resolve("brand")
    fun equipment() = resolve("equipment")
}