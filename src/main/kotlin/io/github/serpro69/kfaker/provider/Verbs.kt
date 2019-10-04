package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.VERBS] category.
 */
@Suppress("unused")
class Verbs internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Verbs>(fakerService) {
    override val categoryName = CategoryName.VERBS
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val base = resolve("base")
    val past = resolve("past")
    val pastParticiple = resolve("past_participle")
    val simplePresent = resolve("simple_present")
    val ingForm = resolve("ing_form")
}
