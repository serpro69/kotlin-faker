package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SUPERHERO] category.
 */
@Suppress("unused")
class Superhero internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SUPERHERO

    // These properties are needed because the value for `name` property
    // is resolved with these properties through yml expression in the form of `#{Superhero.prefix}`
    internal val prefix = resolve { fakerService.resolve(it, "prefix") }
    internal val suffix = resolve { fakerService.resolve(it, "suffix") }
    internal val descriptor = resolve { fakerService.resolve(it, "descriptor") }

    val power = resolve { fakerService.resolve(it, "power") }
    val name = resolve { fakerService.resolve(it, "name") }
}
