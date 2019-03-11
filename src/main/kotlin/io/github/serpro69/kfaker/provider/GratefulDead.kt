package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GRATEFUL_DEAD] category.
 */
class GratefulDead internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GRATEFUL_DEAD

    val players = resolve { fakerService.resolve(Faker, it, "players") }
    val songs = resolve { fakerService.resolve(Faker, it, "songs") }
}
