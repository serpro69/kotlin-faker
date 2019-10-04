package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.INTERNET] category.
 */
@Suppress("unused")
class Internet internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Internet>(fakerService) {
    override val categoryName = CategoryName.INTERNET
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val freeEmail = resolve("free_email")
    val domainSuffix = resolve("domain_suffix")
    val userAgent: (browserType: String) -> String = { browserType ->
        resolve { fakerService.resolve(it, "user_agent", browserType.toLowerCase()) }.invoke()
    }
}
