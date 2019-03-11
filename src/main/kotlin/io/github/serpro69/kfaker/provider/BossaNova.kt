package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.BOSSA_NOVA] category.
 */
class BossaNova internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BOSSA_NOVA

    val artists = resolve { fakerService.resolve(Faker, it, "artists") }
    val songs = resolve { fakerService.resolve(Faker, it, "songs") }
}
