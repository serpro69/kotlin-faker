package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SLACK_EMOJI] category.
 */
class SlackEmoji internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SLACK_EMOJI

    val people = resolve { fakerService.resolve(it, "people") }
    val nature = resolve { fakerService.resolve(it, "nature") }
    val foodAndDrink = resolve { fakerService.resolve(it, "food_and_drink") }
    val celebration = resolve { fakerService.resolve(it, "celebration") }
    val activity = resolve { fakerService.resolve(it, "activity") }
    val travelAndPlaces = resolve { fakerService.resolve(it, "travel_and_places") }
    val objectsAndSymbols = resolve { fakerService.resolve(it, "objects_and_symbols") }
    val custom = resolve { fakerService.resolve(it, "custom") }
    val emoji = resolve { fakerService.resolve(it, "emoji") }
}
