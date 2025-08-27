package io.github.serpro69.kfaker.edu

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory.EDUCATOR
import io.github.serpro69.kfaker.edu.provider.Educator
import io.github.serpro69.kfaker.fakerConfig
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeIn

internal class FakerServiceTest :
    DescribeSpec({
        describe("dictionary is loaded") {
            context("resolving raw expression") {
                context("expression matches the curly-brace-regex") {
                    context("expression calls are chained with a dot '.' char") {
                        val degreeType = fakerService().resolve(EDUCATOR, "degree")

                        it("is resolved by functionName") {
                            degreeType.split(" ").take(2).joinToString(" ") shouldBeIn
                                listOf("Associate Degree", "Bachelor of", "Master of")
                        }
                    }
                }
            }
        }
    })

private fun fakerService(): FakerService {
    val f = TestFaker()
    return f.service
}

@Suppress("unused")
class TestFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig {}) :
    AbstractFaker(config) {

    // expose FakerService for tests
    val service = fakerService

    // don't lazy-init so we always have the dictionary loaded
    val educator: Educator = Educator(service)
}
