package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.LOVECRAFT] category.
 */
@Suppress("unused")
class Lovecraft internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Lovecraft>(fakerService) {
    override val categoryName = CategoryName.LOVECRAFT
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val fhtagn = resolve("fhtagn")
    val deity = resolve("deity")
    val location = resolve("location")
    val tome = resolve("tome")
    val words = resolve("words")
}
