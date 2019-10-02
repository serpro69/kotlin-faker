package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
@Suppress("unused")
class Dog internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.CREATURE

    val name = resolve { fakerService.resolve(it, "dog", "name") }
    val breed = resolve { fakerService.resolve(it, "dog", "breed") }
    val sound = resolve { fakerService.resolve(it, "dog", "sound") }
    val memePhrase = resolve { fakerService.resolve(it, "dog", "meme_phrase") }
    val age = resolve { fakerService.resolve(it, "dog", "age") }
    val coatLength = resolve { fakerService.resolve(it, "dog", "coat_length") }
    val size = resolve { fakerService.resolve(it, "dog", "size") }
}
