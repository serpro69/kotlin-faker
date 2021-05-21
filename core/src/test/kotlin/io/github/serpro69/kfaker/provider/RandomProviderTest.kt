package io.github.serpro69.kfaker.provider

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.instanceOf
import java.util.*

@Suppress("unused")
class RandomProviderTest : DescribeSpec({
    val randomProvider = RandomProvider(Random())

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

        context("creating a random instance of the class") {
            val testClass: TestClass = randomProvider.randomClassInstance()

            it("it should be instance of TestClass") {
                testClass shouldBe instanceOf(TestClass::class)
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
})

@Suppress("unused")
enum class TestEnum {
    KOTLIN,
    JAVA,
    GO
}

sealed class TestSealedCls {
    object Kotlin : TestSealedCls()
    class Java : TestSealedCls()
    class Go(val name: String) : TestSealedCls()
}
