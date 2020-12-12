package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.PROGRAMMING_LANGUAGE] category.
 */
@Suppress("unused")
class ProgrammingLanguage internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<ProgrammingLanguage>(fakerService) {
    override val categoryName = CategoryName.PROGRAMMING_LANGUAGE
    override val localUniqueDataProvider = LocalUniqueDataProvider<ProgrammingLanguage>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun name() = resolve("name")
    fun creator() = resolve("creator")
}
