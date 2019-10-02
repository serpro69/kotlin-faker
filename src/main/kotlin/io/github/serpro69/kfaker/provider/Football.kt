package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FOOTBALL] category.
 */
class Football internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.FOOTBALL

    val teams = resolve { fakerService.resolve(it, "teams") }
    val players = resolve { fakerService.resolve(it, "players") }
    val coaches = resolve { fakerService.resolve(it, "coaches") }
    val competitions = resolve { fakerService.resolve(it, "competitions") }
    val positions = resolve { fakerService.resolve(it, "positions") }
}
