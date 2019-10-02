package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MUSIC] category.
 */
class Music internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.MUSIC

    val instruments = resolve { fakerService.resolve(it, "instruments") }
    val bands = resolve { fakerService.resolve(it, "bands") }
    val albums = resolve { fakerService.resolve(it, "albums") }
    val genres = resolve { fakerService.resolve(it, "genres") }
}
