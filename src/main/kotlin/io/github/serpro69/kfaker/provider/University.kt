package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.UNIVERSITY] category.
 */
@Suppress("unused")
class University internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<University>(fakerService) {
    override val categoryName = CategoryName.UNIVERSITY
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    // Prefix and suffix are needed because they are used as expressions, i.e. `#{University.prefix}`
    val prefix = resolve("prefix")
    val suffix = resolve("suffix")
    val name = resolve("name")
}
