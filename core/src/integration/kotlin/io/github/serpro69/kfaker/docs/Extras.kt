package io.github.serpro69.kfaker.docs

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.provider.misc.ConstructorFilterStrategy
import io.github.serpro69.kfaker.provider.misc.DefaultValuesStrategy.PICK_RANDOMLY
import io.github.serpro69.kfaker.provider.misc.DefaultValuesStrategy.USE_DEFAULTS
import io.github.serpro69.kfaker.provider.misc.FallbackStrategy
import io.github.serpro69.kfaker.provider.misc.RandomProvider
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.containOnly
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAnyOf
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import java.util.*
import kotlin.reflect.KClass

class Extras : DescribeSpec({
    val faker = Faker()
    describe("Random instance of any class") {
        it("should generate a random instance of a class") {
            // --8<-- [start:extras_random_instance_one]
            class Foo(val a: String)
            class Bar(val foo: Foo)

            val foo: Foo = faker.randomClass.randomClassInstance()
            val bar: Bar = faker.randomClass.randomClassInstance()
            // --8<-- [end:extras_random_instance_one]
        }

        context("configurable constructor arg type generation") {
            it("should generate pre-configured constructor params") {
                fun randomString() = "X3a8s813dcb"

                // --8<-- [start:extras_random_instance_two]
                class Baz(val id: Int, val uuid: UUID, val relatedUuid: UUID, val user: String)

                val baz: Baz = faker.randomClass.randomClassInstance {
                    typeGenerator<UUID> { UUID.fromString("00000000-0000-0000-0000-000000000000") }
                    typeGenerator<Int> { 0 }
                    typeGenerator<String> { parameterInfo -> "${parameterInfo.name}_${randomString()}" }
                    namedParameterGenerator("relatedUuid") { UUID.fromString("11111111-1111-1111-1111-111111111111") }
                }
                // --8<-- [end:extras_random_instance_two]

                assertEquals(baz.id, 0)
                assertEquals(baz.user, "user_X3a8s813dcb")
                assertEquals(baz.uuid, UUID.fromString("00000000-0000-0000-0000-000000000000"))
                assertEquals(baz.relatedUuid, UUID.fromString("11111111-1111-1111-1111-111111111111"))
            }

            context("collection element generation") {
                it("should generate pre-configured collection elements for constructor params") {
                    // --8<-- [start:extras_random_instance_sixteen]
                    fun randomListString() = "list"
                    fun randomString() = "string"

                    class Baz(val list: List<String>, val set: Set<String>)

                    val baz: Baz = faker.randomClass.randomClassInstance {
                        collectionElementTypeGenerator<String> {
                            // customize generators for different collection types
                            if ((it.type.classifier as KClass<*>) == List::class) {
                                // generate random string elements for parameters of List<String> type
                                randomListString()
                            } else {
                                // generate random string elements for parameters of Set type
                                randomString()
                            }
                        }
                    }
                    // --8<-- [end:extras_random_instance_sixteen]

                    assertEquals(baz.list.all { it == "list" }, true)
                    assertEquals(baz.set.all { it == "string" }, true)
                }

                it("should generate pre-configured map key/value pairs for constructor params") {
                    fun randomKey() = "key"
                    fun randomValue() = "value"

                    // --8<-- [start:extras_random_instance_seventeen]
                    class Baz(val map: Map<String, String>)

                    val baz: Baz = faker.randomClass.randomClassInstance {
                        mapEntryKeyTypeGenerator { randomKey() }
                        mapEntryValueTypeGenerator { randomValue() }
                    }
                    // --8<-- [end:extras_random_instance_seventeen]

                    assertEquals(baz.map.keys.all { it == "key" }, true)
                    assertEquals(baz.map.values.all { it == "value" }, true)
                }

                it("should allow nullable element types in collections") {
                    // --8<-- [start:extras_random_instance_eighteen]
                    data class Nullable(val ints: List<Int?>, val longs: Set<Long?>, val map: Map<Char?, String?>)

                    val nullable = faker.randomClass.randomClassInstance<Nullable> {
                        collectionsSize = 10
                        collectionElementTypeGenerator<Int?> { if (faker.random.nextBoolean()) null else 42 }
                        collectionElementTypeGenerator<Long?> { if (!faker.random.nextBoolean()) 0L else null }
                        mapEntryKeyTypeGenerator<Char> {
                            faker.random.randomValue(
                                listOf(
                                    'a',
                                    'b',
                                    'c',
                                    'd',
                                    'e',
                                    'f'
                                )
                            )
                        }
                        mapEntryValueTypeGenerator<String?> { if (faker.random.nextBoolean()) null else "foo" }
                    }
                    nullable.ints shouldContain 42
                    // we allow nullable values, but `null` as a value will never be returned
                    nullable.ints shouldNotContain null
                    // with above config, if nextBoolean returns false, we say "return null",
                    // but since nulls are never returned as value, all nulls will be returned as random instance,
                    // hence we won't have all 42's
                    nullable.ints shouldNot containOnly(42)

                    nullable.longs shouldContain 0L
                    nullable.longs shouldNotContain null
                    nullable.longs shouldNot containOnly(0L)

                    nullable.map.keys shouldNotContain null
                    nullable.map.keys shouldContainAnyOf listOf('a', 'b', 'c', 'd', 'e', 'f')
                    nullable.map.values shouldNotContain null
                    nullable.map.values shouldContain "foo"
                    nullable.map.values shouldNot containOnly("foo")
                    // --8<-- [end:extras_random_instance_eighteen]
                }
            }
        }

        context("configurable number of constructor args") {
            // --8<-- [start:extras_random_instance_three]
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
            // --8<-- [end:extras_random_instance_three]

            it("should generate class with configured number of constructor args") {
                // --8<-- [start:extras_random_instance_four]
                val fooBarBaz: FooBarBaz = faker.randomClass.randomClassInstance {
                    constructorParamSize = 3
                    fallbackStrategy = FallbackStrategy.USE_MAX_NUM_OF_ARGS
                }
                assertNotEquals(fooBarBaz.foo, null)
                assertNotEquals(fooBarBaz.bar, null)
                assertNotEquals(fooBarBaz.baz, null)
                // --8<-- [end:extras_random_instance_four]
            }

            it("should use constructor filter strategy") {
                // --8<-- [start:extras_random_instance_five]
                val fooBarBaz: FooBarBaz = faker.randomClass.randomClassInstance {
                    constructorFilterStrategy = ConstructorFilterStrategy.MAX_NUM_OF_ARGS
                }
                assertNotEquals(fooBarBaz.foo, null)
                assertNotEquals(fooBarBaz.bar, null)
                assertNotEquals(fooBarBaz.baz, null)
                // --8<-- [end:extras_random_instance_five]
            }

            it("should generate collections with size 1 be default") {
                // --8<-- [start:extras_random_instance_six]
                class Foo(
                    val list: List<String>,
                    val set: Set<String>,
                    val map: Map<String, Int>
                )

                val foo = faker.randomClass.randomClassInstance<Foo>()

                assertEquals(foo.list.size, 1)
                assertEquals(foo.set.size, 1)
                assertEquals(foo.map.size, 1)
                // --8<-- [end:extras_random_instance_six]
            }

            it("should generate collections with configured size") {
                // --8<-- [start:extras_random_instance_seven]
                class Foo(
                    val list: List<String>,
                    val set: Set<String>,
                    val map: Map<String, Int>
                )

                val foo = faker.randomClass.randomClassInstance<Foo> {
                    collectionsSize = 6
                }

                assertEquals(foo.list.size, 6)
                assertEquals(foo.set.size, 6)
                assertEquals(foo.map.size, 6)
                // --8<-- [end:extras_random_instance_seven]
            }

            it("should generate a set of size 10") {
                // --8<-- [start:extras_random_instance_eight]
                class TestClass(
                    val string: String,
                    val set: Set<String>
                )

                val testClass = faker.randomClass.randomClassInstance<TestClass> {
                    typeGenerator { "a string" }
                    collectionsSize = 10
                }

                assertEquals(testClass.string, "a string")
                assertEquals(testClass.set.size, 10)
                // --8<-- [end:extras_random_instance_eight]
            }

            it("should generate Collections with pre-configured type generation") {
                // --8<-- [start:extras_random_instance_nine]
                class Foo
                class Bar(
                    val list: List<Foo>,
                    val set: Set<String>,
                    val map: Map<String, Int>
                )

                val bar = faker.randomClass.randomClassInstance<Bar> {
                    typeGenerator { emptyList<Foo>() }
                    typeGenerator { setOf("one", "two", "fortytwo") }
                    typeGenerator { mapOf("pwd" to 12177) }
                }
                assertEquals(bar.list, emptyList<Foo>())
                assertEquals(bar.set, setOf("one", "two", "fortytwo"))
                assertEquals(bar.map, mapOf("pwd" to 12177))
                // --8<-- [end:extras_random_instance_nine]
            }
        }

        context("configurable default values selection") {
            // --8<-- [start:extras_random_instance_nineteen]
            class Foo(val i: Int)
            class TestClass(
                val iMin: Int = Int.MIN_VALUE,
                val iMax: Int = Int.MAX_VALUE,
                val s: String = "sometimes a string... is just a string",
                val foo: Foo = Foo(369)
            )
            // --8<-- [end:extras_random_instance_nineteen]

            it("should use random values") {
                // --8<-- [start:extras_random_instance_twenty]
                val testClass: TestClass = Faker().randomClass.randomClassInstance()
                assertNotEquals(Int.MIN_VALUE, testClass.iMin)
                assertNotEquals(Int.MAX_VALUE, testClass.iMax)
                assertNotEquals("sometimes a string... is just a string", testClass.s)
                assertNotEquals(369, testClass.foo.i)
                // --8<-- [end:extras_random_instance_twenty]
            }

            it("should use defaults") {
                // --8<-- [start:extras_random_instance_twenty_one]
                val testClass: TestClass = Faker().randomClass.randomClassInstance {
                    defaultValuesStrategy = USE_DEFAULTS
                }
                assertEquals(Int.MIN_VALUE, testClass.iMin)
                assertEquals(Int.MAX_VALUE, testClass.iMax)
                assertEquals("sometimes a string... is just a string", testClass.s)
                assertEquals(369, testClass.foo.i)
                // --8<-- [end:extras_random_instance_twenty_one]
            }

            // --8<-- [start:extras_random_instance_twenty_two]
            it("should randomly pick a default or a random value") {
                val testClass: TestClass = Faker().randomClass.randomClassInstance {
                    defaultValuesStrategy = PICK_RANDOMLY
                }
                assert(
                    testClass.iMin == Int.MIN_VALUE
                        || testClass.iMax == Int.MAX_VALUE
                        || testClass.s == "sometimes a string... is just a string"
                        || testClass.foo.i == 369
                )
                assert(!(
                    testClass.iMin == Int.MIN_VALUE
                        && testClass.iMax == Int.MAX_VALUE
                        && testClass.s == "sometimes a string... is just a string"
                        && testClass.foo.i == 369
                ))
                // --8<-- [end:extras_random_instance_twenty_two]
            }
        }

        context("Configuration levels") {
            // --8<-- [start:extras_random_instance_ten]
            class Foo
            data class Bar(val int: Int, val uuid: UUID)
            data class Baz(val foo: Foo, val bar: Bar, val string: String)
            // --8<-- [end:extras_random_instance_ten]

            it("should configure random class instance from fakerConfig") {
                // --8<-- [start:extras_random_instance_eleven]
                val cfg = fakerConfig {
                    randomClassInstance {
                        typeGenerator<Bar> { Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")) }
                    }
                }
                val f = Faker(cfg)
                val baz: Baz = f.randomClass.randomClassInstance<Baz>()
                assertEquals(baz.bar, Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")))
                val anotherBaz = f.randomClass.new().randomClassInstance<Baz>()
                assertEquals(anotherBaz.bar, Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")))
                // --8<-- [end:extras_random_instance_eleven]
            }

            it("should configure random class instance from randomClass") {
                // --8<-- [start:extras_random_instance_twelve]
                val cfg = fakerConfig {
                    randomClassInstance {
                        typeGenerator<Bar> { Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")) }
                    }
                }
                val f = Faker(cfg).also {
                    it.randomClass.configure {
                        typeGenerator<UUID> { UUID.fromString("00000000-0000-0000-0000-000000000000") }
                    }
                }

                val bar: Bar = f.randomClass.randomClassInstance()
                val baz: Baz = f.randomClass.randomClassInstance()
                assertEquals(bar.uuid, UUID.fromString("00000000-0000-0000-0000-000000000000"))
                assertEquals(baz.bar, Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")))
                // --8<-- [end:extras_random_instance_twelve]
            }

            it("should configure random class instance from function") {
                // --8<-- [start:extras_random_instance_thirteen]
                faker.randomClass.configure {
                    typeGenerator<Bar> { Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")) }
                }
                val baz: Baz = faker.randomClass.randomClassInstance {
                    typeGenerator<Bar> { Bar(1, UUID.fromString("00000000-0000-0000-0000-000000000000")) }
                }
                assertEquals(baz.bar, Bar(1, UUID.fromString("00000000-0000-0000-0000-000000000000")))
                // --8<-- [end:extras_random_instance_thirteen]
            }
        }

        context("New instances and copies") {
            class Foo
            data class Bar(val int: Int, val uuid: UUID)
            data class Baz(val foo: Foo, val bar: Bar, val string: String)

            it("should create a new instance") {
                // --8<-- [start:extras_random_instance_fourteen]
                val cfg = fakerConfig {
                    randomClassInstance { // ❶
                        typeGenerator<Bar> { Bar(1, UUID.fromString("00000000-0000-0000-0000-000000000000")) }
                    }
                }
                val f = Faker(cfg)
                f.randomClass.configure { // ❷
                    typeGenerator<Bar> { Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")) }
                }
                val new = f.randomClass.new() // ❸
                val baz: Baz = f.randomClass.randomClassInstance<Baz>()
                val newBaz: Baz = new.randomClassInstance<Baz>()
                assertEquals(Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")), baz.bar)
                assertEquals(Bar(1, UUID.fromString("00000000-0000-0000-0000-000000000000")), newBaz.bar)
                // --8<-- [end:extras_random_instance_fourteen]
            }

            it("should make a copy") {
                // --8<-- [start:extras_random_instance_fifteen]
                val cfg = fakerConfig { // ❶
                    randomClassInstance {
                        typeGenerator<Bar> { Bar(1, UUID.fromString("00000000-0000-0000-0000-000000000000")) }
                    }
                }
                val f = Faker(cfg)
                f.randomClass.configure { // ❷
                    typeGenerator<Bar> { Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")) }
                }
                val copy = f.randomClass.copy() // ❸
                val baz: Baz = f.randomClass.randomClassInstance<Baz>()
                val bazCopy: Baz = copy.randomClassInstance<Baz>()
                assertEquals(Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")), baz.bar)
                assertEquals(Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")), bazCopy.bar)

                copy.configure { // ❹
                    typeGenerator<Bar> { Bar(0, UUID.fromString("22222222-2222-2222-2222-222222222222")) }
                }
                val originalBaz: Baz = f.randomClass.randomClassInstance<Baz>()
                val reconfiguredBazCopy = copy.randomClassInstance<Baz>()
                assertEquals(Bar(42, UUID.fromString("11111111-1111-1111-1111-111111111111")), originalBaz.bar)
                assertEquals(Bar(0, UUID.fromString("22222222-2222-2222-2222-222222222222")), reconfiguredBazCopy.bar)
                // --8<-- [end:extras_random_instance_fifteen]
            }
        }

    }

    describe("Random Everything") {
        it("should generate random stuff") {
            // --8<-- [start:extras_random_everything_one]
            faker.random.nextBoolean()
            faker.random.nextChar()
            faker.random.nextDouble()
            faker.random.nextFloat()
            faker.random.nextInt()
            faker.random.nextInt(bound = 100)
            faker.random.nextInt(min = 100, max = 999)
            faker.random.nextInt(intRange = (0..99))
            faker.random.nextLetter(upper = false)
            // --8<-- [end:extras_random_everything_one]
        }

        it("should generate random enum") {
            // --8<-- [start:extras_random_everything_three]
            faker.random.nextEnum<Foo>()
            faker.random.nextEnum(enum = Foo::class.java)
            faker.random.nextEnum(values = Foo.values())
            faker.random.nextEnum(enum = Foo::class.java) { it != Foo.ONE }
            faker.random.nextEnum<Foo>("ONE")
            // --8<-- [end:extras_random_everything_three]
        }

        it("should generate random string of English chars") {
            // --8<-- [start:extras_random_everything_four]
            faker.random.randomString(
                length = 42,
                numericalChars = false
            )
            // --8<-- [end:extras_random_everything_four]
        }

        it("should generate random string in a given locale") {
            // --8<-- [start:extras_random_everything_five]
            faker.random.randomString(
                length = 24,
                locale = Locale.forLanguageTag("nb-NO"),
                indexChars = true,
                auxiliaryChars = true,
                punctuationChars = true,
                numericalChars = true,
            )
            // --8<-- [end:extras_random_everything_five]
        }

        it("should generate random sublist") {
            // --8<-- [start:extras_random_everything_six]
            val list = List(100) { it }
            faker.random.randomSublist(list, size = 10, shuffled = true)
            faker.random.randomSublist(list, sizeRange = 6..42, shuffled = true)
            // --8<-- [end:extras_random_everything_six]
        }

        it("should generate random subset") {
            // --8<-- [start:extras_random_everything_seven]
            val set = setOf(*List(100) { it }.toTypedArray())
            faker.random.randomSubset(set, size = 10, shuffled = true)
            faker.random.randomSubset(set, sizeRange = 66..99, shuffled = true)
            // --8<-- [end:extras_random_everything_seven]
        }

        it("should return random element from a list") {
            // --8<-- [start:extras_random_everything_eight]
            val list = listOf(1, 2, 3)
            faker.random.randomValue(list)
            // --8<-- [end:extras_random_everything_eight]
        }

        it("should generate random UUID") {
            // --8<-- [start:extras_random_everything_nine]
            faker.random.nextUUID()
            // --8<-- [end:extras_random_everything_nine]
        }

        it("should generate unique integers via local-unique-provider") {
            // --8<-- [start:extras_random_everything_ten]
            val ints = List(21) {
                faker.random.unique.nextInt(42)
            }
            assert(ints.distinct().size == 21)
            // cleanup of unique values via enum key for nextInt function
            faker.random.unique.clear(RandomProvider.Key.NEXT_INT)
            // --8<-- [end:extras_random_everything_ten]
        }

        it("should generate unique integers via global-unique-provider") {
            // --8<-- [start:extras_random_everything_eleven]
            faker.unique.configuration { enable(faker::random) }
            val uniqueInts = List(21) {
                faker.random.nextInt(42)
            }
            assert(uniqueInts.distinct().size == 21)
            // cleanup global unique values for Random provider
            faker.unique.clear(faker::random)
            // disable global unique values for Random provider
            faker.unique.configuration { disable(faker::random) }
            val ints = List(21) {
                faker.random.nextInt(42)
            }
            assert(ints.distinct().size < 21)
            // --8<-- [end:extras_random_everything_eleven]
        }
    }

    describe("Random Strings from Templates") {
        it("should replace template chars with actual ones") {
            // --8<-- [start:extras_random_strings_from_templates_zero]
            faker.string.numerify("123###").all { it.isDigit() } shouldBe true
            faker.string.letterify("foo???").all { it.isLetter() } shouldBe true
            faker.string.letterify("???BAR", true).all { it.isUpperCase() } shouldBe true
            faker.string.letterify("???bar", false).all { it.isLowerCase() } shouldBe true
            faker.string.bothify("foo???bar###")
            faker.string.regexify("""\d{42}""").all { it.isDigit() } shouldBe true
            faker.string.regexify("""\d{42}""").length shouldBe 42
            // --8<-- [end:extras_random_strings_from_templates_zero]
        }
    }
})

// --8<-- [start:extras_random_everything_two]
enum class Foo {
    ONE,
    TWO,
    FORTY_TWO
}
// --8<-- [end:extras_random_everything_two]
