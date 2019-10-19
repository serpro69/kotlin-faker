package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.INTERNET] category.
 */
@Suppress("unused")
class Internet internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Internet>(fakerService) {
    override val categoryName = CategoryName.INTERNET
    override val localUniqueDataProvider = LocalUniqueDataProvider<Internet>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun freeEmail() = resolve("free_email")
    fun domainSuffix() = resolve("domain_suffix")
    fun userAgent(browserType: String) = resolve("user_agent", browserType.toLowerCase())
}
