package io.github.serpro69.kfaker

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.property.Arb
import io.kotest.property.forAll

class ArbExtensionsTest : DescribeSpec({
    describe("RandomClassProvider Arb extensions") {
        context("Arb.randomClass.instance") {
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
    }
})

class Foo(val bar: Bar)

class Bar(val s: String)

class Person(val name: String, val age: Int)

class Address(val city: String, val streetName: String, val streetAddress: String)
