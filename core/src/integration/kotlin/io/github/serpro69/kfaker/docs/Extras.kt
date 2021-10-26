package io.github.serpro69.kfaker.docs

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.provider.ConstructorFilterStrategy
import io.github.serpro69.kfaker.provider.FallbackStrategy
import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import java.util.*

class Extras : DescribeSpec({
    val faker = Faker()
    describe("Random instance of any class") {
        it("should generate a random instance of a class") {
            // START extras_random_instance_one
            class Foo(val a: String)
            class Bar(val foo: Foo)

            val foo: Foo = faker.randomProvider.randomClassInstance()
            val bar: Bar = faker.randomProvider.randomClassInstance()
            // END extras_random_instance_one
        }

        context("configurable constructor arg type generation") {
            it("should generate pre-configured constructor params") {
                // START extras_random_instance_two
                class Baz(val id: Int, val uuid: UUID)

                val baz: Baz = faker.randomProvider.randomClassInstance {
                    typeGenerator<UUID> { UUID.fromString("00000000-0000-0000-0000-000000000000") }
                    typeGenerator<Int> { 0 }
                }

                assertEquals(baz.id, 0)
                assertEquals(baz.uuid, UUID.fromString("00000000-0000-0000-0000-000000000000"))
                // END extras_random_instance_two
            }
        }

        context("configurable number of constructor args") {
            // START extras_random_instance_three
            class Foo
            class Bar(val int: Int)
            class Baz(val foo: Foo, val string: String)
            class FooBarBaz {
                var foo: Foo? = null
                    private set
                var bar: Bar? = null
                    private set
                var baz: Baz? = null
                    private set

                constructor(foo: Foo) {
                    this.foo = foo
                }

                constructor(foo: Foo, bar: Bar) : this(foo) {
                    this.bar = bar
                }

                constructor(foo: Foo, bar: Bar, baz: Baz) : this(foo, bar) {
                    this.baz = baz
                }
            }
            // END extras_random_instance_three

            it("should generate class with configured number of constructor args") {
                // START extras_random_instance_four
                val fooBarBaz: FooBarBaz = faker.randomProvider.randomClassInstance {
                    constructorParamSize = 3
                    fallbackStrategy = FallbackStrategy.USE_MAX_NUM_OF_ARGS
                }
                assertNotEquals(fooBarBaz.foo, null)
                assertNotEquals(fooBarBaz.bar, null)
                assertNotEquals(fooBarBaz.baz, null)
                // END extras_random_instance_four
            }

            it("should use constructor filter strategy") {
                // START extras_random_instance_five
                val fooBarBaz: FooBarBaz = faker.randomProvider.randomClassInstance {
                    constructorFilterStrategy = ConstructorFilterStrategy.MAX_NUM_OF_ARGS
                }
                assertNotEquals(fooBarBaz.foo, null)
                assertNotEquals(fooBarBaz.bar, null)
                assertNotEquals(fooBarBaz.baz, null)
                // END extras_random_instance_five
            }
        }
    }

    describe("Random Everything") {

    }
    // START extras_random_instance_
    // END extras_random_instance_

})

