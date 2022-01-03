package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.fakerConfig
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.types.instanceOf
import java.util.*

@Suppress("unused")
class RandomProviderTest : DescribeSpec({
    val config = fakerConfig { random = Random() }
    val randomProvider = RandomProvider(config)

    describe("a TestClass with an empty constructor") {
        class TestClass

        context("creating a random instance of the class") {
            val testClass: TestClass = randomProvider.randomClassInstance()

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

            it("it should be instance of TestClass") {
                testClass shouldBe instanceOf(TestClass::class)
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

    describe("a TestClass with private constructor") {
        class TestClass private constructor()

        context("creating a random instance of the class") {
            it("exception is thrown") {
                val exception = shouldThrow<NoSuchElementException> {
                    randomProvider.randomClassInstance<TestClass>()
                }

                exception.message shouldBe "No suitable constructor found for ${TestClass::class}"
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
                @Suppress("RemoveExplicitTypeArguments")
                typeGenerator<Int> { givenInt }
            }

            it("it should be a predefined UUID and primitives") {
                assertSoftly {
                    testClass shouldBe instanceOf(TestClass::class)
                    testClass.id shouldBe givenUuid
                    testClass.int shouldBe givenInt
                    testClass.foo.int shouldBe givenInt
                }
            }
        }
    }

    describe("a TestClass with non-empty constructor for custom parameter generators") {
        class TestClass(
            val id: UUID,
            val id2: UUID,
            val nullableId: UUID?,
            val nullableId2: UUID?
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
            }

            it("should use predefined UUIDs with parameter generators taking precedence over type generators") {
                assertSoftly {
                    testClass shouldBe instanceOf(TestClass::class)
                    testClass.id shouldBe namedParameterGeneratedId
                    testClass.id2 shouldBe typeGeneratedId
                    testClass.nullableId shouldBe nullableNamedParameterGeneratedId
                    testClass.nullableId2 shouldBe null
                }
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
                @Suppress("RemoveExplicitTypeArguments")
                typeGenerator<UUID> { givenUuid }
                @Suppress("RemoveExplicitTypeArguments")
                typeGenerator<Int> { givenInt } // use the not-null type generator and verify it is used for Int?
                nullableTypeGenerator<Long?> { givenNullableLong }
                @Suppress("RemoveExplicitTypeArguments")
                typeGenerator<Long> { givenLong }
            }

            it("it should be a predefined UUID and primitives with nulls") {
                assertSoftly {
                    testClass shouldBe instanceOf(TestClass::class)
                    testClass.id shouldBe givenUuid
                    testClass.int shouldBe givenInt
                    testClass.nullableLong shouldBe givenNullableLong
                    testClass.notNullLong shouldBe givenLong
                    testClass.foo.int shouldBe givenInt
                }
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
                assertSoftly {
                    testClass.foo shouldNotBe null
                    testClass.bar shouldNotBe null
                    testClass.baz shouldNotBe null
                }
            }
        }

        context("creating a random instance of the class with constructor filter strategy") {
            val testClass: TestClass = randomProvider.randomClassInstance {
                constructorFilterStrategy = ConstructorFilterStrategy.MAX_NUM_OF_ARGS
            }

            it("constructor with specified filter is used") {
                assertSoftly {
                    testClass.foo shouldNotBe null
                    testClass.bar shouldNotBe null
                    testClass.baz shouldNotBe null
                }
            }
        }

        context("creating a random instance of the class with invalid number of args and a fallback strategy") {
            val testClass: TestClass = randomProvider.randomClassInstance {
                constructorParamSize = 6
                fallbackStrategy = FallbackStrategy.USE_MAX_NUM_OF_ARGS
            }

            it("constructor with specified fallback is used") {
                assertSoftly {
                    testClass.foo shouldNotBe null
                    testClass.bar shouldNotBe null
                    testClass.baz shouldNotBe null
                }
            }
        }

        context("creating a random instance of the class with invalid number of args and a filter strategy") {
            val testClass: TestClass = randomProvider.randomClassInstance {
                constructorParamSize = 6
                constructorFilterStrategy = ConstructorFilterStrategy.MAX_NUM_OF_ARGS
            }

            it("constructor with specified fallback is used") {
                assertSoftly {
                    testClass.foo shouldNotBe null
                    testClass.bar shouldNotBe null
                    testClass.baz shouldNotBe null
                }
            }
        }

        context("precedence of using constructor param size over a filter strategy") {
            val testClass: TestClass = randomProvider.randomClassInstance {
                constructorParamSize = 2
                constructorFilterStrategy = ConstructorFilterStrategy.MAX_NUM_OF_ARGS
            }

            it("constructor with specified fallback is used") {
                assertSoftly {
                    testClass.foo shouldNotBe null
                    testClass.bar shouldNotBe null
                    testClass.baz shouldBe null
                }
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
            val map: Map<String, Baz>
        )

        it("should generate Collections with default size 1") {
            val testClass = randomProvider.randomClassInstance<TestClass>()
            assertSoftly {
                testClass.list shouldHaveSize 1
                testClass.set shouldHaveSize 1
                testClass.map shouldHaveSize 1
            }
        }

        it("should generate Collections with pre-configured size") {
            val testClass = randomProvider.randomClassInstance<TestClass> {
                collectionsSize = 10
            }
            assertSoftly {
                testClass.list shouldHaveSize 10
                testClass.set shouldHaveSize 10
                testClass.map shouldHaveSize 10
            }
        }
        it("should generate Collections with pre-configured type generation") {
            val testClass = randomProvider.randomClassInstance<TestClass> {
                typeGenerator<List<Foo>> { listOf() }
            }
            assertSoftly {
                testClass.list shouldHaveSize 0
                testClass.set shouldHaveSize 1
                testClass.map shouldHaveSize 1
            }
        }
    }

    describe("a primitive type") {
        it("should not throw an exception when no suitable constructor exists") {
            assertSoftly {
                shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Double>() }
                shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Float>() }
                shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Long>() }
                shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Int>() }
                shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Short>() }
                shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Byte>() }
                shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Boolean>() }
                shouldNotThrow<NoSuchElementException> { randomProvider.randomClassInstance<Char>() }
            }
        }

        it("should generate a random string") {
            randomProvider.randomClassInstance<String>() shouldHaveLength 24
        }
    }
})

@Suppress("unused")
enum class TestEnum {
    KOTLIN,
    JAVA,
    GO
}

@Suppress("CanSealedSubClassBeObject")
sealed class TestSealedCls {
    object Kotlin : TestSealedCls()
    class Java : TestSealedCls()
}

class Go(val name: String) : TestSealedCls()

object TestObject
