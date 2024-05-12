package io.github.serpro69.kfaker.tech.provider

import io.github.serpro69.kfaker.tech.faker
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.string.shouldNotContain

class VehicleIT : DescribeSpec({
    describe("Vehicle Provider") {
        val vehicle = faker { }.vehicle

        context("regexifying strings") {
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
