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
                class Baz(val id: Int, val uuid: UUID, val relatedUuid: UUID)

                val baz: Baz = faker.randomProvider.randomClassInstance {
                    typeGenerator<UUID> { UUID.fromString("00000000-0000-0000-0000-000000000000") }
                    typeGenerator<Int> { 0 }
                    namedParameterGenerator("relatedUuid") { UUID.fromString("11111111-1111-1111-1111-111111111111") }
                }
                // END extras_random_instance_two

                assertEquals(baz.id, 0)
                assertEquals(baz.uuid, UUID.fromString("00000000-0000-0000-0000-000000000000"))
                assertEquals(baz.relatedUuid, UUID.fromString("11111111-1111-1111-1111-111111111111"))
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

            it("should generate collections with size 1 be default") {
                // START extras_random_instance_six
                class Foo(
                    val list: List<String>,
                    val set: Set<String>,
                    val map: Map<String, Int>
                )

                val foo = faker.randomProvider.randomClassInstance<Foo>()

                assertEquals(foo.list.size, 1)
                assertEquals(foo.set.size, 1)
                assertEquals(foo.map.size, 1)
                // END extras_random_instance_six
            }

            it("should generate collections with configured size") {
                // START extras_random_instance_seven
                class Foo(
                    val list: List<String>,
                    val set: Set<String>,
                    val map: Map<String, Int>
                )

                val foo = faker.randomProvider.randomClassInstance<Foo> {
                    collectionsSize = 6
                }

                assertEquals(foo.list.size, 6)
                assertEquals(foo.set.size, 6)
                assertEquals(foo.map.size, 6)
                // END extras_random_instance_seven
            }

            it("should generate a set of size 10") {
                // START extras_random_instance_eight
                class TestClass(
                    val string: String,
                    val set: Set<String>
                )

                val testClass = faker.randomProvider.randomClassInstance<TestClass> {
                    typeGenerator { "a string" }
                    collectionsSize = 10
                }

                assertEquals(testClass.string, "a string")
                assertEquals(testClass.set.size, 10)
                // END extras_random_instance_eight
            }

            it("should generate Collections with pre-configured type generation") {
                // START extras_random_instance_nine
                class Foo
                class Bar(
                    val list: List<Foo>,
                    val set: Set<String>,
                    val map: Map<String, Int>
                )

                val bar = faker.randomProvider.randomClassInstance<Bar> {
                    typeGenerator { emptyList<Foo>() }
                    typeGenerator { setOf("one", "two", "fortytwo") }
                    typeGenerator { mapOf("pwd" to 12177) }
                }
                assertEquals(bar.list, emptyList<Foo>())
                assertEquals(bar.set, setOf("one", "two", "fortytwo"))
                assertEquals(bar.map, mapOf("pwd" to 12177))
                // END extras_random_instance_nine
            }
        }
    }

    describe("Random Everything") {
        it("should generate random stuff") {
            // START extras_random_everything_one
            faker.random.nextBoolean()
            faker.random.nextChar()
            faker.random.nextDouble()
            faker.random.nextFloat()
            faker.random.nextInt()
            faker.random.nextInt(bound = 100)
            faker.random.nextInt(min = 100, max = 999)
            faker.random.nextInt(intRange = (0..99))
            faker.random.nextLetter(upper = false)
            // END extras_random_everything_one
        }

        it("should generate random enum") {
            // START extras_random_everything_three
            faker.random.nextEnum<Foo>()
            faker.random.nextEnum(enum = Foo::class.java)
            faker.random.nextEnum(values = Foo.values())
            faker.random.nextEnum(enum = Foo::class.java) { it != Foo.ONE }
            faker.random.nextEnum<Foo>(excludeName = "ONE")
            // END extras_random_everything_three
        }

        it("should generate random string of English chars") {
            // START extras_random_everything_four
            faker.random.randomString(
                length = 42,
                numericalChars = false
            )
            // END extras_random_everything_four
        }

        it("should generate random string in a given locale") {
            // START extras_random_everything_five
            faker.random.randomString(
                length = 24,
                locale = Locale.forLanguageTag("nb-NO"),
                auxiliaryChars = true,
                numericalChars = true
            )
            // END extras_random_everything_five
        }

        it("should generate random sublist") {
            // START extras_random_everything_six
            val list = List(100) { it }
            faker.random.randomSublist(list, size = 10, shuffled = true)
            // END extras_random_everything_six
        }

        it("should generate random subset") {
            // START extras_random_everything_seven
            val set = setOf(*List(100) { it }.toTypedArray())
            faker.random.randomSubset(set, size = 10, shuffled = true)
            // END extras_random_everything_seven
        }

        it("should return random element from a list") {
            // START extras_random_everything_eight
            val list = listOf(1, 2, 3)
            faker.random.randomValue(list)
            // END extras_random_everything_eight
        }

        it("should generate random UUID") {
            // START extras_random_everything_nine
            faker.random.nextUUID()
            // END extras_random_everything_nine
        }

    }
})

// START extras_random_everything_two
enum class Foo {
    ONE,
    TWO,
    FORTY_TWO
}
// END extras_random_everything_two

