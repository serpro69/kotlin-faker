package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SWORD_ART_ONLINE] category.
 */
@Suppress("unused")
class SwordArtOnline internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SWORD_ART_ONLINE

    val realName = resolve { fakerService.resolve(it, "real_name") }
    val gameName = resolve { fakerService.resolve(it, "game_name") }
    val location = resolve { fakerService.resolve(it, "location") }
    val item = resolve { fakerService.resolve(it, "item") }
}
