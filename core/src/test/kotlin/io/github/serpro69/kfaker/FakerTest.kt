package io.github.serpro69.kfaker

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlin.random.Random

@Suppress("UNCHECKED_CAST")
class FakerTest : DescribeSpec({
    describe("faker{} DSL") {
        it("should have default config if not explicitly configured") {
            val faker = faker { }
            assertSoftly {
                faker.config.locale shouldBe "en"
                faker.config.uniqueGeneratorRetryLimit shouldBe 100
            }
        }

        it("should be able to provide custom configuration") {
            val r = Random(42)
            val faker = faker {
                fakerConfig {
                    locale = "uk"
                    uniqueGeneratorRetryLimit = 42
                    random = r
                }
            }
            assertSoftly {
                faker.config.locale shouldBe "uk"
                faker.config.uniqueGeneratorRetryLimit shouldBe 42
                faker.config.random shouldBe r
            }
        }
    }
})
