package io.github.serpro69.kfaker.blns

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldHaveSize

class BlnsTest : DescribeSpec({

    assertSoftly = true

    describe("blns strings") {
        val blns = Blns()

        it("should contain all strings") {
            blns.all shouldHaveSize 515
        }

        it("should contain all base64-encoded strings") {
            blns.allBase64 shouldHaveSize 676
        }

        it("should return a random string") {
            blns.random() shouldBeIn blns.all
            blns.random(base64 = true) shouldBeIn blns.allBase64
        }

        it("should return a random sub-list of strings") {
            blns.sublist(10) shouldBeIn blns.all
            blns.sublist(10, base64 = true) shouldBeIn blns.all
            blns.sublist(10) shouldBeIn blns.all
            blns.sublist(10, base64 = true) shouldBeIn blns.all
        }
    }
})

