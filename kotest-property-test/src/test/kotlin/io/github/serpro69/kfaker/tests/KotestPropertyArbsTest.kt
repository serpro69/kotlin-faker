@file:FakerArb(Faker::class, BFaker::class)

package io.github.serpro69.kfaker.tests

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.books.BooksFaker
import io.github.serpro69.kfaker.books.arb
import io.github.serpro69.kfaker.books.booksFaker
import io.github.serpro69.kfaker.faker
import io.github.serpro69.kfaker.kotest.FakerArb
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.forAll
import io.github.serpro69.kfaker.books.Faker as BFaker

class KotestPropertyArbsTest : DescribeSpec({

    describe("Standard kotest Arbs") {
        it("is allowed to drink in US") {
            forAll(Arb.int(21..150)) { a ->
                a in 21..150
            }
        }
        it("is allowed to drink in London") {
            forAll(Arb.int(18..150)) { a ->
                a in 18..150
            }
        }
    }

    describe("Custom kotlin-faker Arbs") {
        it("should generate quotes from annotated local variable") {
            // TODO should maybe configure code-generation via FakerConfig instead of using annotation?
            val b = BooksFaker()
            forAll(b.arb.bible.quote()) { q: String ->
                q.isNotBlank()
            }
        }
        it("should generate quotes from annotated expression") {
            // this won't be supported since KSP doesn't support symbols from expressions
//            forAll(@FakerArb BooksFaker().arb.bible.quote()) { q: String ->
//                q.isNotBlank()
//            }
        }
        it("should generate quotes from companion object") {
            forAll(Arb.booksFaker.bible.quote()) { q: String ->
                q.isNotBlank()
            }
            forAll(Arb.faker.address.city()) { q: String ->
                q.isNotBlank()
            }
        }
    }
})

/*
// pseudo-generated code below this line
// core faker
val Arb.Companion.faker get() = ArbFaker(Faker())
val Faker.arb: ArbFaker get() = ArbFaker(this)

class ArbFaker(faker: Faker) {
    val address: ArbAddress by lazy { ArbAddress(faker.address) }
    val name: ArbName by lazy { ArbName(faker.name) }
}

class ArbAddress(private val address: Address) {
    fun city(): Arb<String> = arbitrary { address.city() }
}

class ArbName(private val name: Name) {
    fun name(): Arb<String> = arbitrary { name.name() }
}

// books faker
val Arb.Companion.booksFaker get() = ArbBooks(BooksFaker())
val BooksFaker.arb: ArbBooks get() = ArbBooks(this)

class ArbBooks(booksFaker: BooksFaker) {
    val bible: ArbBible by lazy { ArbBible(booksFaker.bible) }
}

class ArbBible(private val bible: Bible) {
    fun character(): Arb<String> = arbitrary { bible.character() }

    fun location(): Arb<String> = arbitrary { bible.location() }

    fun quote(): Arb<String> = arbitrary { bible.quote() }
}

*/
