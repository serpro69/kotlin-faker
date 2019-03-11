package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.MUSIC] category.
 */
class Music internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.MUSIC

    val instruments = resolve { fakerService.resolve(Faker, it, "instruments") }
    val bands = resolve { fakerService.resolve(Faker, it, "bands") }
    val albums = resolve { fakerService.resolve(Faker, it, "albums") }
    val genres = resolve { fakerService.resolve(Faker, it, "genres") }
}
