package io.github.serpro69.kfaker.integration.provider

import io.github.serpro69.kfaker.faker
import io.github.serpro69.kfaker.provider.Name
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldNotContain

@Suppress("unused")
class NameIT :
    DescribeSpec({
        describe("Name provider") {
            val name: (locale: String) -> Name = { faker { fakerConfig { locale = it } }.name }

            context("uk locale") {
                it("generates a name") {
                    val names = List(42) { name("uk").name() }
                    names shouldNotContain ""
                }
            }

            context("ru locale") {
                it("generates lastName") {
                    val lastNames = List(42) { name("ru").lastName() }
                    lastNames shouldNotContain ""
                }

                it("generates maleLastName") {
                    val maleLastNames = List(42) { name("ru").lastName() }
                    maleLastNames shouldNotContain ""
                }

                it("generates femaleLastName") {
                    val femaleLastNames = List(42) { name("ru").lastName() }
                    femaleLastNames shouldNotContain ""
                }
            }

            context("de-DE locale") {
                it("generates firstsName") {
                    val firstNames = List(42) { name("de-DE").firstName() }
                    firstNames shouldNotContain ""
                }
            }
        }
    })
