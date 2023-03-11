package io.github.serpro69.kfaker.docs

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.provider.misc.ConstructorFilterStrategy
import io.github.serpro69.kfaker.provider.misc.FallbackStrategy
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
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
                fun randomString() = "X3a8s813dcb";

                // START extras_random_instance_two
                class Baz(val id: Int, val uuid: UUID, val relatedUuid: UUID, val user: String)

                val baz: Baz = faker.randomProvider.randomClassInstance {
                    typeGenerator<UUID> { UUID.fromString("00000000-0000-0000-0000-000000000000") }
                    typeGenerator<Int> { 0 }
                    typeGenerator<String> { parameterInfo -> "${parameterInfo.name}_${randomString()}" }
                    namedParameterGenerator("relatedUuid") { UUID.fromString("11111111-1111-1111-1111-111111111111") }
                }
                // END extras_random_instance_two

                assertEquals(baz.id, 0)
                assertEquals(baz.user, "user_X3a8s813dcb")
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

        context("Configuration levels") {
            // START extras_random_instance_ten
            class Foo
            data class Bar(val int: Int, val uuid: UUID)
            data class Baz(val foo: Foo, val bar: Bar, val string: String)
            // END extras_random_instance_ten

            it("should configure random class instance from fakerConfig") {
                // START extras_random_instance_eleven
                val cfg = fakerConfig {
                    randomClassInstance {
                        typeGenerator<Bar> { Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")) }
                    }
                }
                val f = Faker(cfg)
                val baz: Baz = f.randomProvider.randomClassInstance<Baz>()
                assertEquals(baz.bar, Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")))
                val anotherBaz = f.randomProvider.new().randomClassInstance<Baz>()
                assertEquals(anotherBaz.bar, Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")))
                // END extras_random_instance_eleven
            }

            it("should configure random class instance from randomProvider") {
                // START extras_random_instance_twelve
                val cfg = fakerConfig {
                    randomClassInstance {
                        typeGenerator<Bar> { Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")) }
                    }
                }
                val f = Faker(cfg).also {
                    it.randomProvider.configure {
                        typeGenerator<UUID> { UUID.fromString("00000000-0000-0000-0000-000000000000") }
                    }
                }

                val bar: Bar = f.randomProvider.randomClassInstance()
                val baz: Baz = f.randomProvider.randomClassInstance()
                assertEquals(bar.uuid, UUID.fromString("00000000-0000-0000-0000-000000000000"))
                assertEquals(baz.bar, Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")))
                // END extras_random_instance_twelve
            }

            it("should configure random class instance from function") {
                // START extras_random_instance_thirteen
                faker.randomProvider.configure {
                    typeGenerator<Bar> { Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")) }
                }
                val baz: Baz = faker.randomProvider.randomClassInstance {
                    typeGenerator<Bar> { Bar(1, UUID.fromString("00000000-0000-0000-0000-000000000000")) }
                }
                assertEquals(baz.bar, Bar(1, UUID.fromString("00000000-0000-0000-0000-000000000000")))
                // END extras_random_instance_thirteen
            }
        }

        context("New instances and copies") {
            class Foo
            data class Bar(val int: Int, val uuid: UUID)
            data class Baz(val foo: Foo, val bar: Bar, val string: String)

            it("should create a new instance") {
                // START extras_random_instance_fourteen
                val cfg = fakerConfig {
                    randomClassInstance { // ❶
                        typeGenerator<Bar> { Bar(1, UUID.fromString("00000000-0000-0000-0000-000000000000")) }
                    }
                }
                val f = Faker(cfg)
                f.randomProvider.configure { // ❷
                    typeGenerator<Bar> { Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")) }
                }
                val new = f.randomProvider.new() // ❸
                val baz: Baz = f.randomProvider.randomClassInstance<Baz>()
                val newBaz: Baz = new.randomClassInstance<Baz>()
                assertEquals(Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")), baz.bar)
                assertEquals(Bar(1, UUID.fromString("00000000-0000-0000-0000-000000000000")), newBaz.bar)
                // END extras_random_instance_fourteen
            }

            it("should make a copy") {
                // START extras_random_instance_fifteen
                val cfg = fakerConfig { // ❶
                    randomClassInstance {
                        typeGenerator<Bar> { Bar(1, UUID.fromString("00000000-0000-0000-0000-000000000000")) }
                    }
                }
                val f = Faker(cfg)
                f.randomProvider.configure { // ❷
                    typeGenerator<Bar> { Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")) }
                }
                val copy = f.randomProvider.copy() // ❸
                val baz: Baz = f.randomProvider.randomClassInstance<Baz>()
                val bazCopy: Baz = copy.randomClassInstance<Baz>()
                assertEquals(Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")), baz.bar)
                assertEquals(Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")), bazCopy.bar)

                copy.configure { // ❹
                    typeGenerator<Bar> { Bar(0, UUID.fromString("22222222-2222-2222-2222-222222222222")) }
                }
                val originalBaz: Baz = f.randomProvider.randomClassInstance<Baz>()
                val reconfiguredBazCopy = copy.randomClassInstance<Baz>()
                assertEquals(Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")), originalBaz.bar)
                assertEquals(Bar(0, UUID.fromString("22222222-2222-2222-2222-222222222222")), reconfiguredBazCopy.bar)
                // END extras_random_instance_fifteen
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
                indexChars = true,
                auxiliaryChars = true,
                punctuationChars = true,
                numericalChars = true,
            )
            // END extras_random_everything_five
        }

        it("should generate random sublist") {
            // START extras_random_everything_six
            val list = List(100) { it }
            faker.random.randomSublist(list, size = 10, shuffled = true)
            faker.random.randomSublist(list, sizeRange = 6..42, shuffled = true)
            // END extras_random_everything_six
        }

        it("should generate random subset") {
            // START extras_random_everything_seven
            val set = setOf(*List(100) { it }.toTypedArray())
            faker.random.randomSubset(set, size = 10, shuffled = true)
            faker.random.randomSubset(set, sizeRange = 66..99, shuffled = true)
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

    describe("Random Strings from Templates") {
        it("should replace template chars with actual ones") {
            // START extras_random_strings_from_templates_zero
            faker.string.numerify("123###").all { it.isDigit() } shouldBe true
            faker.string.letterify("foo???").all { it.isLetter() } shouldBe true
            faker.string.letterify("???BAR", true).all { it.isUpperCase() } shouldBe true
            faker.string.letterify("???bar", false).all { it.isLowerCase() } shouldBe true
            faker.string.bothify("foo???bar###")
            faker.string.regexify("""\d{42}""").all { it.isDigit() } shouldBe true
            faker.string.regexify("""\d{42}""").length shouldBe 42
            // END extras_random_strings_from_templates_zero
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
