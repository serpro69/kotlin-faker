package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.faker
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldMatch

class PhoneNumberIT : DescribeSpec({
    describe("PhoneNumber Provider") {
        val phoneNumber: (locale: String) -> PhoneNumber = { faker { fakerConfig { locale = it } }.phoneNumber }

        context("uk") {
            val phone = phoneNumber("uk")
            it("phone number has correct format") {
                phone.phoneNumber() shouldMatch Regex("""^\(\d{3}\) \d{3}-\d{2}-\d{2}$""")
            }
            it("cell phone number has correct format") {
                phone.cellPhone.number() shouldMatch Regex("""^\(\d{3}\) \d{3}-\d{2}-\d{2}$""")
            }
            it("country code is resolved using default format") {
                phone.countryCode() shouldBeIn defaultCountryCodes
            }
        }

        context("ja") {
            val phone = phoneNumber("ja")
            it("phone number starts with correct format") {
                phone.phoneNumber() shouldContain Regex("""^0\d""")
            }
            it("cell phone number starts with correct format") {
                phone.cellPhone.number() shouldContain Regex("""^0[987]0""")
            }
            it("country code is resolved using default format") {
                phone.countryCode() shouldBeIn defaultCountryCodes
            }
        }

        context("fr") {
            val phone = phoneNumber("fr")
            it("country code is resolved using default format") {
                phone.countryCode() shouldBe "33"
            }
        }

        context("en-US") {
            val phone = phoneNumber("en-US")
            repeat(100) {
                it("should generate a phoneNumber run#$it") {
                    phone.phoneNumber().filter { c -> c.isDigit() } shouldNotBe ""
                }
                it("should generate a cellPhone.number run#$it") {
                    phone.cellPhone.number().filter { c -> c.isDigit() } shouldNotBe ""
                }
                it("should generate a countryCode run#$it") {
                    phone.countryCode() shouldNotBe ""
                }
            }
        }
    }
})

private val defaultCountryCodes = listOf(
    "1", "1-242", "1-246", "1-264", "1-268", "1-284", "1-340", "1-345", "1-441", "1-473", "1-649",
    "1-670", "1-671", "1-684", "1-758", "1-784", "1-787", "1-868", "1-869", "1-876", "1-939", "20",
    "212", "213", "216", "218", "220", "221", "222", "223", "224", "225", "226", "227", "228", "229",
    "230", "231", "232", "233", "234", "235", "236", "238", "238", "239", "240", "241", "242", "243",
    "244", "245", "247", "248", "249", "250", "251", "252", "253", "254", "255", "256", "257", "258",
    "260", "261", "262", "263", "264", "265", "266", "267", "268", "269", "269", "27", "290", "291",
    "297", "298", "299", "30", "31", "32", "33", "33", "34", "350", "351", "352", "353", "354", "355",
    "356", "357", "358", "359", "36", "370", "371", "372", "373", "374", "375", "376", "378", "380",
    "381", "381", "381", "385", "386", "387", "389", "39", "39", "40", "41", "420", "421", "423", "43",
    "44", "45", "46", "47", "48", "49", "500", "501", "502", "503", "504", "505", "506", "507", "508",
    "509", "51", "52", "53", "54", "55", "56", "57", "58", "591", "592", "593", "594", "595", "596",
    "596", "597", "598", "599", "60", "61", "61-8", "62", "63", "64", "65", "66", "670", "672", "673",
    "674", "675", "676", "677", "678", "679", "680", "681", "682", "683", "685", "686", "687", "688",
    "689", "690", "691", "692", "7", "7-6", "7-7", "767", "809", "809", "809", "81", "82", "84", "850",
    "850", "852", "853", "855", "855", "856", "86", "880", "886", "886", "90", "91", "92", "93", "94",
    "95", "960", "961", "962", "963", "964", "965", "966", "967", "968", "971", "972", "973", "974",
    "975", "976", "977", "98", "993", "994", "995", "996"
)

