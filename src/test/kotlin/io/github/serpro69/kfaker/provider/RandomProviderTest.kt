package io.github.serpro69.kfaker.provider

import io.kotlintest.matchers.*
import io.kotlintest.shouldBe
import io.kotlintest.specs.*
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
                val exception = io.kotlintest.shouldThrow<NoSuchElementException> {
                    randomProvider.randomClassInstance<TestClass>()
                }

                exception.message shouldBe "No suitable constructor found for ${TestClass::class}"
            }
        }
    }
})
