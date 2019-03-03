package io.github.sergio.igwt.kfaker

import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec

internal class RandomServiceTest : FreeSpec({
    "GIVEN RandomService instance" - {
        val randomService = RandomService()

        "WHEN calling nextInt(min, max)" - {
            val values = List(100) { randomService.nextInt(6..8) }

            "THEN return value should be within specified range" {
                values.all { it in 6..8 } shouldBe true
            }
        }

        "WHEN calling nextInt(intRange)" - {
            val values = List(100) { randomService.nextInt(3..9) }

            "THEN return value should be within specified range" {
                values.all { it in 3..9 } shouldBe true
            }
        }

        "WHEN calling randomValue<T>(list)" - {
            val values = List(100) { randomService.nextInt(3..9) }
            val value = randomService.randomValue(values)

            "THEN return value should be in the list" {
                values shouldContain value
            }
        }
    }
})