package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BACK_TO_THE_FUTURE] category.
 */
@Suppress("unused")
class BackToTheFuture internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<BackToTheFuture>(fakerService) {
    override val categoryName = CategoryName.BACK_TO_THE_FUTURE
    override val localUniqueDataProvider = LocalUniqueDataProvider<BackToTheFuture>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun dates() = resolve("dates")
    fun quotes() = resolve("quotes")
}