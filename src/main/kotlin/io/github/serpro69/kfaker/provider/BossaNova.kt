package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BOSSA_NOVA] category.
 */
class BossaNova internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BOSSA_NOVA

    val artists = resolve { fakerService.resolve(it, "artists") }
    val songs = resolve { fakerService.resolve(it, "songs") }
}
