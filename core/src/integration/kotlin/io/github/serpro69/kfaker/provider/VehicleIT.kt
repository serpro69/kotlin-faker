package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.faker
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotHave
import io.kotest.matchers.string.shouldNotContain

class VehicleIT : DescribeSpec({
    describe("Vehicle Provider") {
        val vehicle = faker { }.vehicle

        context("generexifying strings") {
            repeat(10) {
                it("licensePlateByState() does NOT contain regex expressions run#$it") {
                    assertSoftly {
                        vehicle.licencePlateByState("MT") shouldNotContain "["
                        vehicle.licencePlateByState("MT") shouldNotContain "]"
                    }
                }
            }
        }
    }
})
