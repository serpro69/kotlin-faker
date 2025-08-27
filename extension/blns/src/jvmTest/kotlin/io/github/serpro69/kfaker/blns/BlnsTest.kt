package io.github.serpro69.kfaker.blns

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize

class BlnsTest :
    DescribeSpec({
        assertSoftly = true

        describe("blns strings") {
            val blns = Blns()

            it("should get strings for a category") { blns.get(Category.EMOJI) shouldHaveSize 11 }

            it("should get strings for several categories") {
                blns.get(Category.EMOJI, Category.KAOMOJI).flatMap { it.value } shouldHaveSize 23
            }

            it("should contain all strings") { blns.all shouldHaveSize 515 }

            it("should contain all base64-encoded strings") { blns.allBase64 shouldHaveSize 676 }

            it("should return a random string") {
                blns.random() shouldBeIn blns.all
                blns.random(base64 = true) shouldBeIn blns.allBase64
            }

            it("should return a random sub-list of strings") {
                blns.sublist(10) shouldHaveSize 10
                blns.sublist(10, base64 = true) shouldHaveSize 10
                blns.all shouldContainAll blns.sublist(10)
                blns.all shouldContainAll blns.sublist(1..9)
                blns.allBase64 shouldContainAll blns.sublist(10, base64 = true)
                blns.allBase64 shouldContainAll blns.sublist(1..9, base64 = true)
            }
        }
    })
