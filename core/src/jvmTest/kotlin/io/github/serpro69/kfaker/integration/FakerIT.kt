package io.github.serpro69.kfaker.integration

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.faker
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.File
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty
import org.junit.jupiter.api.assertDoesNotThrow

class FakerIT :
    AbstractIT({ t ->
        `describe all public generators` { faker, provider: KProperty<*>, f: KFunction<*> ->
            val regex = Regex("""#\{.*}|#++""")

            val value = value(f, provider, faker)

            it("resolved value should not contain yaml expression") {
                if (
                    !value.contains("#chuck and #norris") &&
                        (provider.name != "markdown" && f.name != "headers") &&
                        value !in t.valuesWithHashKey
                ) {
                    if (value.contains(regex)) {
                        throw AssertionError(
                            "Value '$value' for '${provider.name} ${f.name}' should not contain regex '$regex'"
                        )
                    }
                }
            }

            it("resolved value should not be empty string") {
                if (value == "") {
                    throw AssertionError(
                        "Value for '${provider.name} ${f.name}' should not be empty string"
                    )
                }
            }

            it("resolved value should not contain duplicates") {
                val values = value.split(" ")

                // Accounting for some exceptional cases where values are repeated
                // in resolved expression
                if (
                    (provider.name != "coffee" && f.name != "notes") &&
                        (provider.name != "onePiece" && f.name != "akumasNoMi") &&
                        (provider.name != "lorem" && f.name != "punctuation" && value != " ") &&
                        value !in t.duplicatedValues
                ) {
                    // Since there's no way to modify assertion message in KotlinTest
                    // it's better to throw a custom error
                    if (values.odds() == values.evens()) {
                        throw AssertionError(
                            "Value '$value' for '${provider.name} ${f.name}' should not contain duplicates"
                        )
                    }
                }
            }
        }

        `describe all public sub-providers` {
            provider: FakeDataProvider,
            sub: KProperty<*>,
            f: KFunction<*> ->
            val regex = Regex("""#\{.*}|#++""")

            val value = value(f, sub, provider)

            it("resolved value should not contain yaml expression") {
                if (
                    !value.contains("#chuck and #norris") &&
                        (sub.name != "markdown" && f.name != "headers") &&
                        value !in t.valuesWithHashKey
                ) {
                    if (value.contains(regex)) {
                        throw AssertionError(
                            "Value '$value' for '${sub.name} ${f.name}' should not contain regex '$regex'"
                        )
                    }
                }
            }

            it("resolved value should not be empty string") {
                if (value == "") {
                    throw AssertionError(
                        "Value for '${sub.name} ${f.name}' should not be empty string"
                    )
                }
            }

            it("resolved value should not contain duplicates") {
                val values = value.split(" ")

                // Accounting for some exceptional cases where values are repeated
                // in resolved expression
                if (
                    (sub.name != "coffee" && f.name != "notes") &&
                        (sub.name != "onePiece" && f.name != "akumasNoMi") &&
                        (sub.name != "lorem" && f.name != "punctuation" && value != " ") &&
                        value !in t.duplicatedValues
                ) {
                    // Since there's no way to modify assertion message in KotlinTest
                    // it's better to throw a custom error
                    if (values.odds() == values.evens()) {
                        throw AssertionError(
                            "Value '$value' for '${sub.name} ${f.name}' should not contain duplicates"
                        )
                    }
                }
            }
        }

        describe("Faker instance is initialized with default locale") {
            val faker = Faker()

            val defaultCountryUS = faker.address.defaultCountry()
            val peruOne = faker.address.countryByCode("PE")

            context("it is re-initialized with another locale value") {
                val config = fakerConfig { locale = "nb-NO" }
                val otherFaker = Faker(config)

                it("matching keys should be overwritten in the re-initialized dictionary") {
                    val defaultCountryNO = otherFaker.address.defaultCountry()

                    assertSoftly {
                        defaultCountryNO shouldBe "Norge"
                        defaultCountryNO shouldNotBe defaultCountryUS
                    }
                }

                it("non-matching default keys should be present in the re-initialized dictionary") {
                    val peruTwo = otherFaker.address.countryByCode("PE")
                    peruOne shouldBe peruTwo
                }
            }

            /*        context("it is again re-initialized with default locale") {
                Faker.init()

                it("matching keys should be overwritten back to defaults") {
                    val defaultCountry = Faker.address.defaultCountry()
                    val peruThree = Faker.address.countryByCode("PE")

                    assertSoftly {
                        defaultCountry shouldBe defaultCountryUS
                        peruThree shouldBe peruOne
                    }
                }
            }*/
        }

        describe("Faker instance is initialized with custom locale") {
            val localeDir = requireNotNull(this::class.java.classLoader.getResource("locales/"))

            val locales =
                File(localeDir.toURI()).listFiles().mapNotNull {
                    if (
                        (it.isFile && it.extension == "yml") || (it.isDirectory && it.name != "en")
                    ) {
                        it.nameWithoutExtension
                    } else null
                }

            locales.forEach {
                it("Faker with locale '$it' should be initialized without exceptions") {
                    assertDoesNotThrow { faker { fakerConfig { locale = it } } }
                }
            }
        }
    })

/**
 * Return the value from the [f] member function of [p] property.
 *
 * @param args arguments to call [p] property getter
 */
private fun value(f: KFunction<*>, p: KProperty<*>, vararg args: Any?) =
    when (f.parameters.size) {
        1 -> f.call(p.getter.call(*args)).toString()
        2 -> {
            if (
                f.parameters[1].isOptional
            ) { // optional params are enum typed (e.g. see functions in Dune, Finance or Tron)
                f.callBy(mapOf(f.parameters[0] to p.getter.call(*args))).toString()
            } else f.call(p.getter.call(*args), "").toString()
        }
        3 -> {
            if (f.parameters[1].isOptional && f.parameters[2].isOptional) {
                f.callBy(mapOf(f.parameters[0] to p.getter.call(*args))).toString()
            } else f.call(p.getter.call(*args), "", "").toString()
        }
        else -> throw IllegalArgumentException("")
    }

private fun List<String>.odds() =
    this.mapIndexedNotNull { index, s -> if (index % 2 == 0) s else null }

private fun List<String>.evens() =
    this.mapIndexedNotNull { index, s -> if (index % 2 != 0) s else null }
