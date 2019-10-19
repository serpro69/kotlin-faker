package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.VERBS] category.
 */
@Suppress("unused")
class Verbs internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Verbs>(fakerService) {
    override val categoryName = CategoryName.VERBS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Verbs>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun base() = resolve("base")
    fun past() = resolve("past")
    fun pastParticiple() = resolve("past_participle")
    fun simplePresent() = resolve("simple_present")
    fun ingForm() = resolve("ing_form")
}
