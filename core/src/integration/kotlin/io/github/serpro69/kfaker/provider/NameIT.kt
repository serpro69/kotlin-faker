package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.faker
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldNotContain

@Suppress("unused")
class NameIT : DescribeSpec({
    describe("Name provider") {
        context("ru locale") {
            val faker = faker {
                fakerConfig { locale = "ru" }
            }
            val name = faker.name

            it("generates lastName") {
                val lastNames = List(42) { name.lastName() }
                lastNames shouldNotContain ""
            }

            it("generates maleLastName") {
                val maleLastNames = List(42) { name.lastName() }
                maleLastNames shouldNotContain ""
            }

            it("generates femaleLastName") {
                val femaleLastNames = List(42) { name.lastName() }
                femaleLastNames shouldNotContain ""
            }
        }
    }
})
