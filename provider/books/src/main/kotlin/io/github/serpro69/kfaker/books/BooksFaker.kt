package io.github.serpro69.kfaker.books

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.books.provider.Bible
import io.github.serpro69.kfaker.books.provider.Book
import io.github.serpro69.kfaker.books.provider.Cosmere
import io.github.serpro69.kfaker.books.provider.CultureSeries
import io.github.serpro69.kfaker.books.provider.DcComics
import io.github.serpro69.kfaker.books.provider.Dune
import io.github.serpro69.kfaker.books.provider.Lovecraft
import io.github.serpro69.kfaker.books.provider.Shakespeare
import io.github.serpro69.kfaker.books.provider.TheKingkillerChronicle
import io.github.serpro69.kfaker.books.provider.Tolkien
import io.github.serpro69.kfaker.fakerConfig

/**
 * Typealias for the [BooksFaker]
 */
typealias Faker = BooksFaker

/**
 * Provides access to fake data generators within the Books domain.
 *
 * Each category (generator) from this [BooksFaker] is represented by a property
 * that (usually) has the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class BooksFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }) : AbstractFaker(config) {

    val bible: Bible by lazy { Bible(fakerService) }
    val book: Book by lazy { Book(fakerService) }
    val cosmere: Cosmere by lazy { Cosmere(fakerService) }
    val cultureSeries: CultureSeries by lazy { CultureSeries(fakerService) }
    val dcComics: DcComics by lazy { DcComics(fakerService) }
    val dune: Dune by lazy { Dune(fakerService, randomService) }
    val lovecraft: Lovecraft by lazy { Lovecraft(fakerService) }
    val shakespeare: Shakespeare by lazy { Shakespeare(fakerService) }
    val theKingkillerChronicle: TheKingkillerChronicle by lazy { TheKingkillerChronicle(fakerService) }
    val tolkien: Tolkien by lazy { Tolkien(fakerService) }

    @FakerDsl
    /**
     * DSL builder for creating instances of [Faker]
     */
    class Builder internal constructor() : AbstractFaker.Builder<Faker>() {

        /**
         * Builds an instance of [Faker] with this [config].
         */
        override fun build(): Faker = Faker(config)
    }
}

/**
 * Applies the [block] function to [BooksFaker.Builder]
 * and returns as an instance of [BooksFaker] from that builder.
 */
fun faker(block: BooksFaker.Builder.() -> Unit): BooksFaker = BooksFaker.Builder().apply(block).build()
