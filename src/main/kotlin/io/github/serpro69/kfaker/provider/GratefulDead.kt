package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GRATEFUL_DEAD] category.
 */
class GratefulDead internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GRATEFUL_DEAD

    val players = resolve { fakerService.resolve(Faker, it, "players") }
    val songs = resolve { fakerService.resolve(Faker, it, "songs") }
}
