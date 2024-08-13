package io.github.serpro69.kfaker.kotest

import io.github.serpro69.kfaker.Faker
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.property.Arb
import io.kotest.property.forAll

class ArbExtensionsTest : DescribeSpec({
    describe("Arb extensions") {
        context("String-based fake generators") {
            val f = Faker()
            it("generates a random city") {
                forAll(Arb.of(f.address::city)) { c ->
                    c.isNotBlank()
                }
            }
        }
        context("RandomClassProvider generator") {
            it("should generate random class instance") {
                val f = Faker()
                forAll(Arb.of(f.randomClass::randomClassInstance)) { foo: Foo -> foo.bar.s.isNotBlank() }
            }

            it("should generate person with address") {
                val f = Faker()
                f.randomClass.configure {
                    namedParameterGenerator("name") { f.name.name() }
                    namedParameterGenerator("age") { f.random.nextInt(20, 30) }
                    namedParameterGenerator("city") { f.address.city() }
                    namedParameterGenerator("streetName") { f.address.streetName() }
                    namedParameterGenerator("streetAddress") { f.address.streetAddress() }
                }
                forAll(Arb.of(f.randomClass::randomClassInstance), Arb.of(f.randomClass::randomClassInstance)) { p: Person, a: Address ->
                    p.name.isNotBlank()
                    p.age in 20..30
                    a.city.isNotBlank()
                    a.streetName.isNotBlank()
                    a.streetAddress.isNotBlank()
                }
            }
        }
    }
})

class Foo(val bar: Bar)

class Bar(val s: String)

class Person(val name: String, val age: Int)

class Address(val city: String, val streetName: String, val streetAddress: String)
