package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.INTERNET] category.
 */
class Internet internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.INTERNET

    val freeEmail = resolve { fakerService.resolve(it, "free_email") }
    val domainSuffix = resolve { fakerService.resolve(it, "domain_suffix") }
    val userAgent: (browserType: String) -> String = { browserType ->
        resolve { fakerService.resolve(it, "user_agent", browserType.toLowerCase()) }.invoke()
    }
}
