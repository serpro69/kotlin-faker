package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.SIMPSONS] category.
 */
@Suppress("unused")
class Simpsons internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Simpsons>(fakerService) {
    override val categoryName = CategoryName.SIMPSONS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Simpsons>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun quotes() = resolve("quotes")
    fun episodeTitles() = resolve("episode_titles")
}
