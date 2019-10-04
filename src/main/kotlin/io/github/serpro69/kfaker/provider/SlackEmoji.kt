package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SLACK_EMOJI] category.
 */
@Suppress("unused")
class SlackEmoji internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<SlackEmoji>(fakerService) {
    override val categoryName = CategoryName.SLACK_EMOJI
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val people = resolve("people")
    val nature = resolve("nature")
    val foodAndDrink = resolve("food_and_drink")
    val celebration = resolve("celebration")
    val activity = resolve("activity")
    val travelAndPlaces = resolve("travel_and_places")
    val objectsAndSymbols = resolve("objects_and_symbols")
    val custom = resolve("custom")
    val emoji = resolve("emoji")
}
