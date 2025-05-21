package io.github.serpro69.kfaker.provider.misc

import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.provider.misc.DefaultValuesStrategy.PICK_RANDOMLY
import io.github.serpro69.kfaker.provider.misc.DefaultValuesStrategy.USE_DEFAULTS
import io.github.serpro69.kfaker.random
import io.github.serpro69.kfaker.randomClassInstance
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.containOnly
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAnyOf
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.types.instanceOf
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.random.Random
import kotlin.reflect.full.declaredMemberProperties

@Suppress("unused")
class RandomClassProviderTest : DescribeSpec({
    assertSoftly = true

    val config = fakerConfig {}
    val randomProvider = RandomClassProvider(config)

    describe("a TestClass with an empty constructor") {
        class TestClass

        context("creating a random instance of the class") {
            val testClass: TestClass = randomProvider.randomClassInstance()

            it("it should be instance of TestClass") {
                testClass shouldBe instanceOf(TestClass::class)
            }
        }

        context("creating a random instance from kClass") {
            val testClass: TestClass = randomProvider.randomClassInstance(TestClass::class)

            it("it should be instance of TestClass") {
                testClass shouldBe instanceOf(TestClass::class)
            }
        }
    }

    describe("a TestClass with non-empty constructor") {
        class Foo
        class TestClass(val foo: Foo)

        context("creating a random instance of the class") {
            val testClass: TestClass = randomProvider.randomClassInstance()

            it("should be instance of TestClass") {
                testClass shouldBe instanceOf(TestClass::class)
            }
            it("should follow precedence rules and try to generate a 'default instance' before using 'predefined instance'") {
                class Bar(val id: Int)
                class Baz(val bar: Bar)

                val bar = randomProvider.randomClassInstance<Bar> {
                    typeGenerator<Bar> { Bar(0) }
                }

                val baz = randomProvider.randomClassInstance<Baz> {
                    typeGenerator<Baz> { Baz(Bar(1)) }
                }

                bar.id shouldNotBe 0
                baz.bar.id shouldNotBe 1
            }
        }
    }

    describe("a TestClass with non-empty constructor and default values") {
        class Foo(val i: Int)
        class TestClass(
            val b: Boolean = true,
            val i: Int = Int.MAX_VALUE,
            val s: String = "foobar",
            val foo: Foo = Foo(369)
        )

        context("creating a random instance of the class") {
            val r = RandomClassProvider(config)
            it("should be instance of TestClass") {
                val testClass: TestClass = r.randomClassInstance()
                testClass shouldBe instanceOf(TestClass::class)
            }
            it("should use default constructor values") {
                val testClass: TestClass = r.copy()
                    .also { it.configure { defaultValuesStrategy = USE_DEFAULTS } }
                    .randomClassInstance()
                testClass.b shouldBe true
                testClass.i shouldBe Int.MAX_VALUE
                testClass.s shouldBe "foobar"
                testClass.foo.i shouldBe 369
            }
            it("should randomly pick between a default and random values") {
                val testClass: TestClass = r.copy()
                    .also { it.configure { defaultValuesStrategy = PICK_RANDOMLY } }
                    .randomClassInstance()
                (testClass.b
                    || testClass.i == Int.MAX_VALUE
                    || testClass.s == "foobar"
                    || testClass.i == 369) shouldBe true
            }
        }
    }

    describe("a TestClass with non-empty constructor with object type parameter") {
        class TestClass(val testObject: TestObject)

        context("creating a random instance of the class") {
            val testClass: TestClass = randomProvider.randomClassInstance()

            it("it should be instance of TestClass") {
                testClass shouldBe instanceOf(TestClass::class)
            }
        }
    }

    describe("a TestClass with non-empty constructor with primitive type parameters") {
        class TestClass(
            val double: Double,
            val float: Float,
            val long: Long,
            val int: Int,
            val short: Short,
//            val byte: Byte, // TODO: 11.06.19
            val string: String,
            val char: Char,
            val boolean: Boolean
//            val array: Array<String> // TODO: 12.06.19
        )

        context("creating a random instance of the class") {
            val testClass: TestClass = randomProvider.randomClassInstance()

            it("it should be instance of TestClass") {
                testClass shouldBe instanceOf(TestClass::class)
            }
        }
    }

    describe("a TestClass with non-empty constructor with primitive type and custom-type parameters") {
        class Foo(val string: String)

        class TestClass(
            val foo: Foo,
            val double: Double,
            val float: Float,
            val long: Long,
            val int: Int,
            val short: Short,
            val byte: Byte,
            val string: String,
            val char: Char,
            val boolean: Boolean
        )

        context("creating a random instance of the class") {
            val testClass: TestClass = randomProvider.randomClassInstance()

            it("it should be instance of TestClass") {
                testClass shouldBe instanceOf(TestClass::class)
            }
        }
    }

    describe("a TestClass with non-empty constructor with nullable type parameters") {
        class Foo(val string: String?)
        class TestClass(val foo: Foo?)

        context("creating a random instance of the class") {
            val testClass: TestClass = randomProvider.randomClassInstance()

            it("it should be instance of TestClass") {
                testClass shouldBe instanceOf(TestClass::class)
            }
        }
    }

    describe("internal constructor") {
        it("should create an instance with a default no-argument constructor") {
            val t = randomProvider.randomClassInstance<TestClassInternal>()
            t.id shouldBe 42
        }
        it("should use a non-default constructor") {
            val t = randomProvider.randomClassInstance<TestClassInternal>() {
                this.constructorParamSize = 1
            }
            t.id shouldNotBe 42
        }
    }

    describe("no public or internal constructors") {
        context("creating a random instance of a class") {
            it("should return a predefined instance via typeGenerator") {
                val testClassMin = randomProvider.randomClassInstance<TestClassNoPublic> {
                    typeGenerator<TestClassNoPublic> { TestClassNoPublic.MIN }
                }
                val testClassMax = randomProvider.randomClassInstance<TestClassNoPublic> {
                    typeGenerator<TestClassNoPublic> { TestClassNoPublic.MAX }
                }
                testClassMin.id shouldBe Int.MIN_VALUE
                testClassMax.id shouldBe Int.MAX_VALUE
            }
            it("exception is thrown") {
                val exception = shouldThrow<NoSuchElementException> {
                    randomProvider.randomClassInstance<TestClassNoPublic>()
                }
                exception.message shouldBe "No suitable constructor or predefined instance found for ${TestClassNoPublic::class}"
            }
        }
        context("creating a random instance of an interface") {
            it("should return a predefined interface instance via typeGenerator") {
                val testInterface = randomProvider.randomClassInstance<TestInterface> {
                    typeGenerator<TestInterface> {
                        object : TestInterface {
                            override val id: Int = 42
                            override val name: String = "Deep Thought"
                        }
                    }
                }

                testInterface.id shouldBe 42
                testInterface.name shouldBe "Deep Thought"
            }
        }
    }

    describe("a TestClass with non-empty constructor with enum type param") {
        class TestClass(val enum: TestEnum)

        context("creating a random instance of the class") {
            val testClass: TestClass = randomProvider.randomClassInstance()

            it("it should be instance of TestClass") {
                testClass shouldBe instanceOf(TestClass::class)
            }
        }
    }

    describe("a TestClass with non-empty constructor with sealed type param") {
        class TestClass(val sealed: TestSealedCls)

        repeat(10) {
            context("creating a random instance of the class run#$it") {
                val testClass: TestClass = randomProvider.randomClassInstance()

                it("it should be instance of TestClass") {
                    testClass shouldBe instanceOf(TestClass::class)
                }
            }
        }
    }

    describe("a TestClass with non-empty constructor for custom type generators") {
        class Foo(val int: Int)
        class TestClass(
            val bar: String,
            val id: UUID,
            val int: Int,
            val long: Long,
            val foo: Foo
        )

        val givenUuid = UUID.fromString("00000000-0000-0000-0000-000000000000")
        val givenInt = 0

        context("creating a random instance of the class with custom generators") {
            val testClass: TestClass = randomProvider.randomClassInstance {
                typeGenerator<UUID> { givenUuid }
                typeGenerator<Int> { givenInt }
                typeGenerator<String> { pInfo -> pInfo.toString() }
            }

            it("it should be a predefined UUID and primitives") {
                testClass shouldBe instanceOf(TestClass::class)
                testClass.id shouldBe givenUuid
                testClass.int shouldBe givenInt
                testClass.foo.int shouldBe givenInt
                testClass.bar shouldBe "ParameterInfo(index=0, name=bar, isOptional=false, isVararg=false, type=kotlin.String, kind=VALUE)"
            }
        }
    }

    describe("a TestClass with non-empty constructor for custom parameter generators") {
        class TestClass(
            val id: UUID,
            val id2: UUID,
            val nullableId: UUID?,
            val nullableId2: UUID?,
            val foo: String? = " "
        )

        val typeGeneratedId = UUID.fromString("00000000-0000-0000-0000-000000000000")
        val namedParameterGeneratedId = UUID.randomUUID()
        val nullableNamedParameterGeneratedId = UUID.randomUUID()

        context("creating a random instance of the class with custom parameter and type generators") {
            val testClass: TestClass = randomProvider.randomClassInstance {
                namedParameterGenerator("id") { namedParameterGeneratedId }
                namedParameterGenerator("nullableId") { nullableNamedParameterGeneratedId }
                namedParameterGenerator("nullableId2") { null }
                typeGenerator<UUID> { typeGeneratedId }
                namedParameterGenerator("foo") { pInfo -> pInfo.toString() }
            }

            it("should use predefined UUIDs with parameter generators taking precedence over type generators") {
                testClass shouldBe instanceOf(TestClass::class)
                testClass.id shouldBe namedParameterGeneratedId
                testClass.id2 shouldBe typeGeneratedId
                testClass.nullableId shouldBe nullableNamedParameterGeneratedId
                testClass.nullableId2 shouldBe null
                testClass.foo shouldBe "ParameterInfo(index=4, name=foo, isOptional=true, isVararg=false, type=kotlin.String?, kind=VALUE)"
            }
        }
    }

    describe("a TestClass with non-empty constructor for nullable custom type generators") {
        class Foo(val int: Int?)
        class TestClass(
            val id: UUID,
            val int: Int?,
            val nullableLong: Long?,
            val notNullLong: Long,
            val foo: Foo
        )

        val givenUuid = UUID.fromString("00000000-0000-0000-0000-000000000000")
        val givenInt = 1
        val givenNullableLong: Long? = null
        val givenLong = 1L

        context("creating a random instance of the class with nullable custom generators") {
            val testClass: TestClass = randomProvider.randomClassInstance {
                typeGenerator<UUID> { givenUuid }
                typeGenerator<Int> { givenInt } // use the not-null type generator and verify it is used for Int?
                nullableTypeGenerator<Long?> { givenNullableLong }
                typeGenerator<Long> { givenLong }
            }

            it("it should be a predefined UUID and primitives with nulls") {
                testClass shouldBe instanceOf(TestClass::class)
                testClass.id shouldBe givenUuid
                testClass.int shouldBe givenInt
                testClass.nullableLong shouldBe givenNullableLong
                testClass.notNullLong shouldBe givenLong
                testClass.foo.int shouldBe givenInt
            }
        }
    }

    describe("a TestClass with 3 non-default constructors") {

        class Foo
        class Bar(val int: Int)
        class Baz(val foo: Foo, val string: String)

        class TestClass {
            var foo: Foo? = null
                private set
            var bar: Bar? = null
                private set
            var baz: Baz? = null
                private set

            constructor()

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

        context("creating a random instance of the class with constructor args size") {
            val testClass: TestClass = randomProvider.randomClassInstance {
                constructorParamSize = 3
            }

            it("constructor with specified args size is used") {
                testClass.foo shouldNotBe null
                testClass.bar shouldNotBe null
                testClass.baz shouldNotBe null
            }
        }

        context("creating a random instance of the class with constructor filter strategy") {
            val testClass: TestClass = randomProvider.randomClassInstance {
                constructorFilterStrategy = ConstructorFilterStrategy.MAX_NUM_OF_ARGS
            }

            it("constructor with specified filter is used") {
                testClass.foo shouldNotBe null
                testClass.bar shouldNotBe null
                testClass.baz shouldNotBe null
            }
        }

        context("creating a random instance of the class with invalid number of args and a fallback strategy") {
            val testClass: TestClass = randomProvider.randomClassInstance {
                constructorParamSize = 6
                fallbackStrategy = FallbackStrategy.USE_MAX_NUM_OF_ARGS
            }

            it("constructor with specified fallback is used") {
                testClass.foo shouldNotBe null
                testClass.bar shouldNotBe null
                testClass.baz shouldNotBe null
            }
        }

        context("creating a random instance of the class with invalid number of args and a filter strategy") {
            val testClass: TestClass = randomProvider.randomClassInstance {
                constructorParamSize = 6
                constructorFilterStrategy = ConstructorFilterStrategy.MAX_NUM_OF_ARGS
            }

            it("constructor with specified fallback is used") {
                testClass.foo shouldNotBe null
                testClass.bar shouldNotBe null
                testClass.baz shouldNotBe null
            }
        }

        context("precedence of using constructor param size over a filter strategy") {
            val testClass: TestClass = randomProvider.randomClassInstance {
                constructorParamSize = 2
                constructorFilterStrategy = ConstructorFilterStrategy.MAX_NUM_OF_ARGS
            }

            it("constructor with specified fallback is used") {
                testClass.foo shouldNotBe null
                testClass.bar shouldNotBe null
                testClass.baz shouldBe null
            }
        }
    }

    describe("a TestClass with non-empty constructor with Collection-type parameters") {
        class Foo
        class Bar(val int: Int)
        class Baz(val foo: Foo, val string: String)
        class TestClass(
            val list: List<Foo>,
            val set: Set<Bar>,
            val map: Map<String, Baz>,
            // "primitives" (see https://github.com/serpro69/kotlin-faker/issues/204 )
            val charList: List<Char>,
            val intSet: Set<Int>,
            val boolMap: Map<Boolean, Byte>,
            // enums (see https://github.com/serpro69/kotlin-faker/issues/204 )
            val enumList: List<TestEnum>,
            val enumSet: Set<TestEnum>,
            val enumMap: Map<TestEnum, TestEnum>,
        )

        it("should generate Collections with default size 1") {
            val testClass = randomProvider.randomClassInstance<TestClass>()
            testClass.list shouldHaveSize 1
            testClass.set shouldHaveSize 1
            testClass.map shouldHaveSize 1
            testClass.charList shouldHaveSize 1
            testClass.intSet shouldHaveSize 1
            testClass.boolMap shouldHaveSize 1
            testClass.enumList shouldHaveSize 1
            testClass.enumSet shouldHaveSize 1
            testClass.enumMap shouldHaveSize 1
        }

        it("should generate Collections with pre-configured size") {
            val testClass = randomProvider.randomClassInstance<TestClass> {
                collectionsSize = 10
            }
            testClass.list shouldHaveSize 10
            testClass.set shouldHaveSize 10
            testClass.map shouldHaveSize 10
            testClass.charList shouldHaveSize 10
            testClass.intSet shouldHaveSize 10
            // boolean-key based map can only have 1 or 2 entries, depending on the randomness of the generated key
            testClass.boolMap.size shouldBeInRange 1..2
            testClass.enumList shouldHaveSize 10
            // we only have 3 enum classes, so a set can't have more values total than that
            testClass.enumSet.size shouldBeInRange 1..3
            // enum-key based map can only have up to 3 entries in this case, depending on the randomness of the generated key
            testClass.enumMap.size shouldBeInRange 1..3
        }

        it("should generate Collections with pre-configured type generation") {
            val testClass = randomProvider.randomClassInstance<TestClass> {
                typeGenerator<List<Foo>> { listOf() }
                typeGenerator<List<Char>> { listOf() }
                typeGenerator<List<TestEnum>> { listOf() }
            }
            testClass.list shouldHaveSize 0
            testClass.set shouldHaveSize 1
            testClass.map shouldHaveSize 1
            testClass.charList shouldHaveSize 0
            testClass.intSet shouldHaveSize 1
            testClass.boolMap shouldHaveSize 1
            testClass.enumList shouldHaveSize 0
            testClass.enumSet shouldHaveSize 1
            testClass.enumMap shouldHaveSize 1
        }

        it("should generate elements in a collection with predefined collection generator") {
            val foo = Foo()
            val bar = Bar(42)
            val baz = Baz(foo, "foo")
            val testClass = randomProvider.randomClassInstance<TestClass> {
                collectionElementTypeGenerator<Foo> { foo }
                collectionElementTypeGenerator<Bar> { bar }
                mapEntryKeyTypeGenerator<String> { "map" }
                mapEntryValueTypeGenerator<Baz> { baz }
                collectionElementTypeGenerator<String> { "string" }
                collectionElementTypeGenerator<Char> { 'c' }
                collectionElementTypeGenerator<Boolean> { true }
                collectionElementTypeGenerator<Int> { 42 }
                collectionElementTypeGenerator<Byte> { Byte.MAX_VALUE }
                mapEntryKeyTypeGenerator<Boolean> { false }
                mapEntryValueTypeGenerator<Byte> { Byte.MAX_VALUE }
                collectionElementTypeGenerator<TestEnum> { TestEnum.GO }
                mapEntryKeyTypeGenerator<TestEnum> { TestEnum.JAVA }
                mapEntryValueTypeGenerator<TestEnum> { TestEnum.KOTLIN }
            }
            testClass.set shouldHaveSize 1
            testClass.set.first() shouldBe bar
            testClass.map.all { it.key == "map" && it.value == baz } shouldBe true
            testClass.charList.all { it == 'c' } shouldBe true
            testClass.intSet shouldHaveSize 1
            testClass.intSet.first() shouldBe 42
            testClass.boolMap.all { !it.key && it.value == Byte.MAX_VALUE } shouldBe true
            testClass.enumList.all { it == TestEnum.GO } shouldBe true
            testClass.enumSet shouldHaveSize 1
            testClass.enumSet.all { it == TestEnum.GO } shouldBe true
            testClass.enumMap.keys.all { it == TestEnum.JAVA } shouldBe true
            testClass.enumMap.values.all { it == TestEnum.KOTLIN } shouldBe true
        }

        it("typeGenerator should have precedence over collection and map generators") {
            val testClass = randomProvider.randomClassInstance<TestClass> {
                collectionElementTypeGenerator<TestEnum> { TestEnum.JAVA }
                mapEntryKeyTypeGenerator<TestEnum> { TestEnum.KOTLIN }
                mapEntryValueTypeGenerator<TestEnum> { TestEnum.GO }
                typeGenerator<List<TestEnum>> { TestEnum.entries }
                typeGenerator<Set<TestEnum>> { emptySet() }
                typeGenerator<Map<TestEnum, TestEnum>> { mapOf(TestEnum.JAVA to TestEnum.KOTLIN) }
            }
            testClass.enumList shouldBe TestEnum.entries
            testClass.enumSet shouldBe emptySet()
            testClass.enumMap shouldBe mapOf(TestEnum.JAVA to TestEnum.KOTLIN)
        }

        it("should generate null elements in collections") {
            data class Nullable(val ints: List<Int?>, val longs: Set<Long?>, val map: Map<Char?, String?>)

            val nullable = randomProvider.randomClassInstance<Nullable> {
                collectionsSize = 10
                collectionElementTypeGenerator<Int?> { if (random.nextBoolean()) null else 42 }
                collectionElementTypeGenerator<Long?> { if (!random.nextBoolean()) 0L else null }
                mapEntryKeyTypeGenerator<Char> { listOf('a', 'b', 'c', 'd', 'e', 'f').random() }
                mapEntryValueTypeGenerator<String?> { if (random.nextBoolean()) null else "foo" }
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
        }
    }

    describe("a TestClass with with abstract type constructor parameter") {
        class Foo
        class Bar(val foo: Foo)
        class Baz(val foo: Foo, val bar: Bar)
        class TestClass(
            val foo: Foo,
            val bar: Bar,
            val baz: Baz,
            val number: Number,
        )

        it("should throw exception for Number type") {
            val ex = shouldThrow<InstantiationException> { randomProvider.randomClassInstance<TestClass>() }
            ex.message shouldBe "Failed to instantiate class kotlin.Number"
            ex.cause shouldBe InstantiationException()
        }

        it("should custom typeGenerator for Number") {
            val testClass = randomProvider.randomClassInstance<TestClass> {
                typeGenerator<Number> { 42 }
            }
            testClass.number shouldBe 42
        }
    }

    describe("a primitive type") {
        it("should not throw an exception when no suitable constructor exists") {
            shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Double>() }
            shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Float>() }
            shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Long>() }
            shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Int>() }
            shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Short>() }
            shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Byte>() }
            shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Boolean>() }
            shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Char>() }
        }

        it("should generate a random string") {
            randomProvider.randomClassInstance<String>() shouldHaveLength 24
        }
    }

    describe("an inner class") {
        it("should throw exception when generated directly") {
            assertThrows<UnsupportedOperationException> { randomProvider.randomClassInstance<Go.BuildNum>() }
        }
        it("should throw exception when included as constructor parameter") {
            class Test(val goBuild: Go.BuildNum)
            assertThrows<UnsupportedOperationException> { randomProvider.randomClassInstance<Test>() }
        }
    }

    describe("a sealed class") {
        it("should return a random sealed class implementation") {
            val cls = randomProvider.randomClassInstance<TestSealedCls>()
            cls shouldBe instanceOf<TestSealedCls>()
        }
        context("with constructor parameters") {
            it("should return a random sealed class implementation") {
                val cls = randomProvider.randomClassInstance<TestSealedClsWithParams>()
                cls shouldBe instanceOf<TestSealedClsWithParams>()
            }
        }
    }

    describe("an enum class") {
        it("should return a random enum implementation") {
            val cls = randomProvider.randomClassInstance<TestEnum>()
            cls shouldBe instanceOf<TestEnum>()
        }
        context("with constructor parameters") {
            it("should return a random sealed class implementation") {
                val cls = randomProvider.randomClassInstance<TestEnumWithParams>()
                cls shouldBe instanceOf<TestEnumWithParams>()
            }
        }
    }

    describe("RandomClassProvider configuration") {
        class Foo(val int: Int)
        class Bar(val foo: Foo)
        class Baz(val foo: Foo, val bar: Bar, val test: Int, val nullable: String?)

        val cfg: () -> FakerConfig = {
            fakerConfig {
                randomClassInstance {
                    typeGenerator { Foo(0) }
                    nullableTypeGenerator { "nn" }
                    namedParameterGenerator("test") { 1 }
                }
            }
        }

        it("should configure all random instances") {
            with(RandomClassProvider(config)) {
                configure {
                    typeGenerator { Foo(42) }
                    nullableTypeGenerator { "non-null" }
                    namedParameterGenerator("test") { 32167 }
                }
                randomClassInstance<Bar>().foo.int shouldBe 42
                randomClassInstance<Baz>().foo.int shouldBe 42
                randomClassInstance<Baz>().bar.foo.int shouldBe 42
                randomClassInstance<Baz>().test shouldBe 32167
                randomClassInstance<Baz>().nullable shouldBe "non-null"
            }
        }

        context("precedence") {
            it("should configure random providers from fakerConfig") {
                with(RandomClassProvider(cfg())) {
                    randomClassInstance<Bar>().foo.int shouldBe 0
                    randomClassInstance<Baz>().foo.int shouldBe 0
                    randomClassInstance<Baz>().bar.foo.int shouldBe 0
                    randomClassInstance<Baz>().test shouldBe 1
                    randomClassInstance<Baz>().nullable shouldBe "nn"
                }
            }
            it("should have defaults if not configured on fakerConfig level") {
                with(RandomClassProvider(fakerConfig { })) {
                    randomClassInstance<Bar>().foo.int shouldNotBe 0
                    randomClassInstance<Baz>().foo.int shouldNotBe 0
                    randomClassInstance<Baz>().bar.foo.int shouldNotBe 0
                    randomClassInstance<Baz>().test shouldNotBe 1
                    randomClassInstance<Baz>().nullable shouldNotBe "nn"
                }
            }
            it("should override configuration from RandomProvider level") {
                with(RandomClassProvider(cfg())) {
                    configure {
                        typeGenerator { Foo(42) }
                        nullableTypeGenerator { "just a string" }
                        namedParameterGenerator("test") { 32167 }
                    }
                    randomClassInstance<Bar>().foo.int shouldBe 42
                    randomClassInstance<Baz>().foo.int shouldBe 42
                    randomClassInstance<Baz>().bar.foo.int shouldBe 42
                    randomClassInstance<Baz>().test shouldBe 32167
                    randomClassInstance<Baz>().nullable shouldBe "just a string"
                }
            }
            it("should re-configure random providers") {
                with(RandomClassProvider(cfg())) {
                    configure {
                        typeGenerator { Foo(42) }
                        nullableTypeGenerator { "just a string" }
                        namedParameterGenerator("test") { 32167 }
                    }
                    randomClassInstance<Bar>().foo.int shouldBe 42
                    randomClassInstance<Baz>().foo.int shouldBe 42
                    randomClassInstance<Baz>().bar.foo.int shouldBe 42
                    randomClassInstance<Baz>().test shouldBe 32167
                    randomClassInstance<Baz>().nullable shouldBe "just a string"

                    configure {
                        typeGenerator { Foo(12177) }
                        nullableTypeGenerator<String> { null }
                    }
                    randomClassInstance<Bar>().foo.int shouldBe 12177
                    randomClassInstance<Baz>().foo.int shouldBe 12177
                    randomClassInstance<Baz>().bar.foo.int shouldBe 12177
                    randomClassInstance<Baz>().test shouldBe 32167
                    randomClassInstance<Baz>().nullable shouldBe null
                }
            }
            it("should use own configuration on function level") {
                with(RandomClassProvider(cfg())) {
                    configure {
                        typeGenerator { Foo(12177) }
                        namedParameterGenerator("test") { 32167 }
                        nullableTypeGenerator<String> { null }
                    }
                    randomClassInstance<Baz> { typeGenerator { Foo(36) } }.also {
                        it.foo.int shouldBe 36
                        it.bar.foo.int shouldBe 36
                        it.test shouldNotBe 1
                        it.test shouldNotBe 32167
                        it.nullable shouldNotBe "nn"
                        it.nullable shouldNotBe null
                    }
                }
            }
        }

        context("resetting to defaults") {
            it("should reset configuration to defaults on RandomProvider level") {
                with(RandomClassProvider(cfg())) {
                    configure { reset() }
                    randomClassInstance<Bar>().foo.int shouldNotBe 0
                    randomClassInstance<Baz>().foo.int shouldNotBe 0
                    randomClassInstance<Baz>().bar.foo.int shouldNotBe 0
                    randomClassInstance<Baz>().test shouldNotBe 1
                    randomClassInstance<Baz>().nullable shouldNotBe "nn"
                }
            }
            it("should re-configure random providers") {
                with(RandomClassProvider(cfg())) {
                    configure {
                        reset()
                        typeGenerator { Foo(12177) }
                        nullableTypeGenerator<String> { null }
                    }
                    randomClassInstance<Bar>().foo.int shouldBe 12177
                    randomClassInstance<Baz>().foo.int shouldBe 12177
                    randomClassInstance<Baz>().bar.foo.int shouldBe 12177
                    randomClassInstance<Baz>().test shouldNotBe 1
                    randomClassInstance<Baz>().nullable shouldBe null
                }
            }
            /*            it("should reset configuration to defaults on fakerConfig level") {
                            val c = cfg()
                            with(RandomClassProvider(c)) {
                                configure {
                                    typeGenerator { Foo(12177) }
                                    nullableTypeGenerator<String> { null }
                                }
                                c.randomProviderConfig?.reset()
                                randomClassInstance<Bar>().foo.int shouldNotBe 12177
                                randomClassInstance<Baz>().foo.int shouldNotBe 12177
                                randomClassInstance<Baz>().bar.foo.int shouldNotBe 12177
                                randomClassInstance<Baz>().test shouldNotBe 1
                                randomClassInstance<Baz>().nullable shouldNotBe null
                            }
                        }*/
        }
    }

    describe("new instance of RandomClassProvider") {
        val cfg: () -> FakerConfig = {
            fakerConfig {
                randomClassInstance {
                    collectionsSize = random.nextInt()
                    constructorParamSize = random.nextInt()
                    constructorFilterStrategy = ConstructorFilterStrategy.MAX_NUM_OF_ARGS
                    fallbackStrategy = FallbackStrategy.FAIL_IF_NOT_FOUND
                }
            }
        }
        val configProperties: (RandomProviderConfig) -> List<Any?> = {
            it::class.declaredMemberProperties.map { p -> p.call(it) }
        }
        it("should create a new instance") {
            val rcp = RandomClassProvider(cfg())
            rcp shouldNotBeSameInstanceAs rcp.new()
        }
        it("should have same configuration as defined on fakerConfig level") {
            val c = cfg()
            with(RandomClassProvider(c)) {
                configure {
                    reset()
                    collectionsSize = 100
                    constructorParamSize = 10
                    constructorFilterStrategy = ConstructorFilterStrategy.MIN_NUM_OF_ARGS
                    fallbackStrategy = FallbackStrategy.USE_MIN_NUM_OF_ARGS
                    typeGenerator { "42" }
                    typeGenerator { 42 }
                    typeGenerator { 42.0 }
                }
                val new = new()
                configProperties(new.config) shouldBe configProperties(c.randomProviderConfig!!)
                new.config shouldNotBeSameInstanceAs c.randomProviderConfig
                new.config shouldNotBeSameInstanceAs this@with.config
            }
        }
        it("should have default configuration if none was defined on fakerConfig level") {
            val c = fakerConfig { }
            with(RandomClassProvider(c)) {
                configure {
                    reset()
                    collectionsSize = 100
                    constructorParamSize = 10
                    constructorFilterStrategy = ConstructorFilterStrategy.MIN_NUM_OF_ARGS
                    fallbackStrategy = FallbackStrategy.USE_MIN_NUM_OF_ARGS
                    typeGenerator { "42" }
                    typeGenerator { 42 }
                    typeGenerator { 42.0 }
                }
                val new = new()
                configProperties(new.config) shouldBe configProperties(RandomProviderConfig())
                new.config shouldNotBeSameInstanceAs this@with.config
                new.config shouldNotBeSameInstanceAs c.randomProviderConfig
            }
        }
    }

    context("copy of RandomClassProvider") {
        val cfg: () -> FakerConfig = {
            fakerConfig {
                randomClassInstance {
                    collectionsSize = random.nextInt()
                    constructorParamSize = random.nextInt()
                    constructorFilterStrategy = ConstructorFilterStrategy.MAX_NUM_OF_ARGS
                    fallbackStrategy = FallbackStrategy.FAIL_IF_NOT_FOUND
                }
            }
        }
        val configProperties: (RandomProviderConfig) -> List<Any?> = {
            it::class.declaredMemberProperties.map { p -> p.call(it) }
        }
        it("should have same configuration as defined on RandomClassProvider level") {
            val c = cfg()
            with(RandomClassProvider(c)) {
                configure {
                    collectionsSize = 100
                    constructorParamSize = 10
                    constructorFilterStrategy = ConstructorFilterStrategy.MIN_NUM_OF_ARGS
                    fallbackStrategy = FallbackStrategy.USE_MIN_NUM_OF_ARGS
                    typeGenerator { "42" }
                    typeGenerator { 42 }
                    typeGenerator { 42.0 }
                    nullableTypeGenerator<String> { null }
                    namedParameterGenerator("foo") { "bar" }
                    collectionElementTypeGenerator { 36 }
                    mapEntryKeyTypeGenerator { 0 }
                    mapEntryValueTypeGenerator { 1 }
                }
                val copy = copy()
                configProperties(copy.config) shouldBe configProperties(this@with.config)
                copy.config shouldNotBeSameInstanceAs this@with.config
                copy.config shouldNotBeSameInstanceAs c.randomProviderConfig
            }
        }

        it("should have same configuration as defined on fakerConfig level if not defined on RandomClassProvider level") {
            val c = cfg()
            with(RandomClassProvider(c)) {
                val copy = copy()
                configProperties(copy.config) shouldBe configProperties(c.randomProviderConfig!!)
                copy.config shouldNotBeSameInstanceAs c.randomProviderConfig
                copy.config shouldNotBeSameInstanceAs this@with.config
            }
        }
    }

    context("randomClassProvider top-level functions") {
        class Foo(val int: Int)
        class Bar(val foo: Foo)
        class Baz(val foo: Foo, val bar: Bar, val test: Int, val nullable: String?)

        it("should generate random class instance") {
            randomClassInstance<Foo>() shouldBe instanceOf<Foo>()
        }

        it("should configure all random instances via configurator") {
            val bar = randomClassInstance<Bar> {
                typeGenerator { Foo(42) }
                nullableTypeGenerator { "non-null" }
                namedParameterGenerator("test") { 32167 }
            }
            val baz = randomClassInstance<Baz> {
                typeGenerator { Foo(42) }
                nullableTypeGenerator { "non-null" }
                namedParameterGenerator("test") { 32167 }
            }
            bar.foo.int shouldBe 42
            baz.foo.int shouldBe 42
            baz.bar.foo.int shouldBe 42
            baz.test shouldBe 32167
            baz.nullable shouldBe "non-null"
        }

        it("should configure all random instances via fakerConfig") {
            val cfg: () -> FakerConfig = {
                fakerConfig {
                    randomClassInstance {
                        typeGenerator { Foo(0) }
                        nullableTypeGenerator { "nn" }
                        namedParameterGenerator("test") { 1 }
                    }
                }
            }
            val bar = randomClassInstance<Bar>(cfg())
            val baz = randomClassInstance<Baz>(cfg())
            bar.foo.int shouldBe 0
            baz.foo.int shouldBe 0
            baz.bar.foo.int shouldBe 0
            baz.test shouldBe 1
            baz.nullable shouldBe "nn"
        }

        context("precedence") {
            val cfg: () -> FakerConfig = {
                fakerConfig {
                    randomClassInstance {
                        typeGenerator { Foo(0) }
                        nullableTypeGenerator { "nn" }
                        namedParameterGenerator("test") { 1 }
                    }
                }
            }

            it("should configure random providers from fakerConfig") {
                randomClassInstance<Bar>(cfg()).foo.int shouldBe 0
                randomClassInstance<Baz>(cfg()).foo.int shouldBe 0
                randomClassInstance<Baz>(cfg()).bar.foo.int shouldBe 0
                randomClassInstance<Baz>(cfg()).test shouldBe 1
                randomClassInstance<Baz>(cfg()).nullable shouldBe "nn"
            }
            it("should override configuration via configurator") {
                val bar = randomClassInstance<Bar>(cfg()) {
                    typeGenerator { Foo(42) }
                }
                bar.foo.int shouldBe 42

                val baz = randomClassInstance<Baz>(cfg()) {
                    typeGenerator { Foo(42) }
                    nullableTypeGenerator { "just a string" }
                    namedParameterGenerator("test") { 32167 }
                }
                baz.foo.int shouldBe 42
                baz.bar.foo.int shouldBe 42
                baz.test shouldBe 32167
                baz.nullable shouldBe "just a string"
            }
        }
    }
})

enum class TestEnumWithParams(val id: Int) {
    THREE(3),
    SIX(6),
    FORTY_TWO(42),
}

@Suppress("unused")
enum class TestEnum {
    KOTLIN,
    JAVA,
    GO
}

sealed class TestSealedClsWithParams(val id: Int) {
    data object Three : TestSealedClsWithParams(3)
    class Six : TestSealedClsWithParams(6)
    object Nine : TestSealedClsWithParams(9)
    class Cls(private val ownId: Int) : TestSealedClsWithParams(ownId)
    data class Data(private val ownId: Int) : TestSealedClsWithParams(ownId)
}

@Suppress("CanSealedSubClassBeObject", "unused")
sealed class TestSealedCls {
    data object Kotlin : TestSealedCls()
    class Java : TestSealedCls()
    object JS : TestSealedCls()
}

@Suppress("unused")
class Go(val name: String) : TestSealedCls() {

    class Version(val ver: String)

    inner class BuildNum(v: Version, i: Int) {
        val innerVersion = "${v.ver}+$i"
    }
}

object TestObject

class TestClassInternal internal constructor() {
    var id = 42
        private set

    internal constructor(id: Int) : this() {
        this.id = id
    }
}

class TestClassNoPublic private constructor(val id: Int) {
    companion object {
        val MIN = TestClassNoPublic(Int.MIN_VALUE)
        val MAX = TestClassNoPublic(Int.MAX_VALUE)
    }
}

interface TestInterface {
    val id: Int
    val name: String
}
