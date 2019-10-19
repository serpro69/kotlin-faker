package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GREEK_PHILOSOPHERS] category.
 */
@Suppress("unused")
class GreekPhilosophers internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<GreekPhilosophers>(fakerService) {
    override val categoryName = CategoryName.GREEK_PHILOSOPHERS
    override val localUniqueDataProvider = LocalUniqueDataProvider<GreekPhilosophers>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun names() = resolve("names")
    fun quotes() = resolve("quotes")
}
