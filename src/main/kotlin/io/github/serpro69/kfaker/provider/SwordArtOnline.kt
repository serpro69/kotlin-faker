package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.SWORD_ART_ONLINE] category.
 */
class SwordArtOnline internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SWORD_ART_ONLINE

    val realName = resolve { fakerService.resolve(Faker, it, "real_name") }
    val gameName = resolve { fakerService.resolve(Faker, it, "game_name") }
    val location = resolve { fakerService.resolve(Faker, it, "location") }
    val item = resolve { fakerService.resolve(Faker, it, "item") }
}
