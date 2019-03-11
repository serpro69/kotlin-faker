package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
class Dog internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.CREATURE

    val name = resolve { fakerService.resolve(Faker, it, "cat", "name") }
    val breed = resolve { fakerService.resolve(Faker, it, "cat", "breed") }
    val sound = resolve { fakerService.resolve(Faker, it, "cat", "sound") }
    val memePhrase = resolve { fakerService.resolve(Faker, it, "cat", "meme_phrase") }
    val age = resolve { fakerService.resolve(Faker, it, "cat", "age") }
    val coatLength = resolve { fakerService.resolve(Faker, it, "cat", "coat_length") }
    val size = resolve { fakerService.resolve(Faker, it, "cat", "size") }
}
