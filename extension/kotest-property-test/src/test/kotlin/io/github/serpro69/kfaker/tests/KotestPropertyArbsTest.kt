@file:FakerArb(Faker::class, BFaker::class, EduFaker::class)

package io.github.serpro69.kfaker.tests

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.arb
import io.github.serpro69.kfaker.books.BooksFaker
import io.github.serpro69.kfaker.books.arb
import io.github.serpro69.kfaker.books.booksFaker
import io.github.serpro69.kfaker.edu.EduFaker
import io.github.serpro69.kfaker.edu.arb
import io.github.serpro69.kfaker.kotest.FakerArb
import io.github.serpro69.kfaker.randomClass
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.property.Arb
import io.kotest.property.forAll
import io.github.serpro69.kfaker.books.Faker as BFaker

class KotestPropertyArbsTest : DescribeSpec({
    describe("Custom kotlin-faker Arbs") {
        it("should generate quotes from annotated local variable") {
            val b = BooksFaker()
            forAll(b.arb.bible.quote()) { q: String ->
                q.isNotBlank()
            }
            val f = Faker()
            forAll(f.arb.address.city()) { q ->
                q.isNotBlank()
            }
            forAll(f.arb.address.city(), f.arb.address.streetName()) { city, street ->
                city.isNotBlank()
                street.isNotBlank()
            }
//            forAll(f.arb.randomClass.randomInstance<String>()) { q ->
//                q.isNotBlank()
//            }
            // TODO support secondary providers via property, like educator.tertiary
            val e = EduFaker()
            forAll(e.arb.educator.campus()) { q ->
                q.isNotBlank()
            }
        }
        it("should generate quotes from companion object") {
            forAll(Arb.booksFaker.bible.quote()) { q: String ->
                q.isNotBlank()
            }
        }
        it("should generate random class instance") {
            forAll(Arb.randomClass.instance()) { foo: Foo -> foo.bar.s.isNotBlank() }
            forAll(
                Arb.randomClass.instance<Foo> {
                    typeGenerator { "hello faker" }
                },
            ) {
                it.bar.s == "hello faker"
            }
        }
        it("should generate person with address") {
            val f = Faker()
            val person: () -> Arb<Person> = {
                Arb.randomClass.instance<Person> {
                    namedParameterGenerator("name") { f.name.name() }
                    namedParameterGenerator("age") { f.random.nextInt(20, 30) }
                }
            }
            val address: () -> Arb<Address> = {
                Arb.randomClass.instance<Address> {
                    namedParameterGenerator("city") { f.address.city() }
                    namedParameterGenerator("streetName") { f.address.streetName() }
                    namedParameterGenerator("streetAddress") { f.address.streetAddress() }
                }
            }
            forAll(person(), address()) { p: Person, a: Address ->
                p.name.isNotBlank()
                p.age in 20..30
                a.city.isNotBlank()
                a.streetName.isNotBlank()
                a.streetAddress.isNotBlank()
            }
        }
    }
})

class Foo(val bar: Bar)

class Bar(val s: String)

class Person(val name: String, val age: Int)

class Address(val city: String, val streetName: String, val streetAddress: String)

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
