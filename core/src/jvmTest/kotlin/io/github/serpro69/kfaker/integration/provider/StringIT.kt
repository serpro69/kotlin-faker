package io.github.serpro69.kfaker.integration.provider

import io.github.serpro69.kfaker.faker
import io.github.serpro69.kfaker.provider.misc.StringProvider
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.collections.shouldNotContainAnyOf
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.beLowerCase
import io.kotest.matchers.string.beUpperCase
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain

class StringIT : DescribeSpec() {
    private val faker = faker { fakerConfig { uniqueGeneratorRetryLimit = 1000 } }
    private val sourceString = "foo###bar???"

    override suspend fun beforeEach(testCase: TestCase) {
        faker.unique.clearAll()
        faker.string.unique.clearAll()
    }

    init {
        describe("String Provider") {
            context("numerify") {
                it("should NOT contain # chars") {
                    faker.string.numerify(sourceString) shouldNotContain "#"
                    faker.string.numerify(sourceString) shouldContain "?"
                }
                it("can have duplicates") {
                    val list = List(1000) { faker.string.numerify(sourceString) }
                    list.distinct().size shouldBeLessThan 1000
                }
            }

            context("letterify") {
                it("should NOT contain ? chars") {
                    faker.string.letterify(sourceString) shouldNotContain "?"
                    faker.string.letterify(sourceString) shouldContain "#"
                }
                it("can have duplicates") {
                    val list = List(4200) { faker.string.letterify(sourceString) }
                    list.distinct().size shouldBeLessThan 4200
                }
                it("should be upper") { faker.string.letterify("###", true) should beUpperCase() }
                it("should be lower") { faker.string.letterify("###", false) should beLowerCase() }
            }

            context("bothify") {
                it("should NOT contain ? and # chars") {
                    faker.string.bothify(sourceString) shouldNotContain "?"
                    faker.string.bothify(sourceString) shouldNotContain "#"
                }
                it("can have duplicates") {
                    val list = List(1000) { faker.string.bothify("foo#bar?") }
                    list.distinct().size shouldBeLessThan 1000
                }
                it("should be upper") { faker.string.bothify("###", true) should beUpperCase() }
                it("should be lower") { faker.string.bothify("###", false) should beLowerCase() }
            }
            context("regexify") {
                it("should resolve the regex template to a string") {
                    faker.string.regexify("""\d{6}""").all { it.isDigit() } shouldBe true
                }
                it("should resolve the regex to a string") {
                    faker.string.regexify(Regex("""\d{6}""")).all { it.isDigit() } shouldBe true
                }
                it("can have duplicates") {
                    val list = List(1000) { faker.string.regexify("""\d\w""") }
                    list.distinct().size shouldBeLessThan 1000
                }
            }
        }

        describe("Local unique provider") {
            context("numerify") {
                it("should NOT contain duplicates") {
                    val numerify = List(600) { faker.string.unique.numerify(sourceString) }
                    numerify.distinct().size shouldBe 600
                }
            }
            context("letterify") {
                it("should NOT contain duplicates") {
                    val letterify = List(1000) { faker.string.unique.letterify(sourceString) }
                    letterify.distinct().size shouldBe 1000
                }
            }
            context("bothify") {
                it("should NOT contain duplicates") {
                    val bothify = List(10000) { faker.string.unique.bothify(sourceString) }
                    bothify.distinct().size shouldBe 10000
                }
            }
            context("regexify") {
                it("should NOT contain duplicates") {
                    val regexify = List(10000) { faker.string.unique.regexify("""\d{3}\w{3}""") }
                    regexify.distinct().size shouldBe 10000
                }
            }
        }

        describe("Global unique provider") {
            context("excluding values from a function") {
                repeat(100) {
                    it("should not contain excluded values run#$it") {
                        val exclusions = listOf("foo963bar", "foo369bar", "foo666bar")
                        faker.unique.configuration {
                            enable(faker::string)
                            excludeFromFunction<StringProvider>("numerify", exclusions)
                        }
                        val list = List(600) { faker.string.numerify("foo###bar") }
                        list shouldNotContainAnyOf exclusions
                    }
                }
            }
        }
    }
}
