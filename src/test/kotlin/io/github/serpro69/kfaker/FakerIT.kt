package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.*
import io.kotlintest.*
import io.kotlintest.specs.*
import java.io.File
import java.util.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Suppress("UNCHECKED_CAST")
class FakerIT : FreeSpec({
    "GIVEN every public function in each provider is invoked without exceptions" - {
        val faker = Faker.init()

        // Get a list of all publicly visible providers
        val providers: List<KProperty<*>> = faker::class.declaredMemberProperties.filter {
            it.visibility == KVisibility.PUBLIC && it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType)
        }

        // Get a list of all publicly visible functions in each provider
        val providerProps = providers.associateBy { provider ->
            provider.getter.call(faker)!!::class.declaredMemberProperties.filter {
                it.visibility == KVisibility.PUBLIC && !it.annotations.any { ann -> ann is Deprecated }
            }
        }

        assertSoftly {
            providerProps.forEach { (props, provider) ->
                props.forEach {
                    val call = it.getter.call(provider.getter.call(faker))
                    val returnType = call.toString()

                    "WHEN result value for ${provider.name + it.getter.name} is resolved correctly" - {
                        val regex = Regex("""#\{.*}|#++""")

                        val value = when (returnType) {
                            "() -> kotlin.String" -> (call as () -> String).invoke()
                            "(kotlin.String) -> kotlin.String" -> (call as (String) -> String).invoke("")
                            "(kotlin.String) -> kotlin.String!" -> (call as (String) -> String).invoke("")
                            else -> throw AssertionError("Incorrect return type '$returnType' for ${provider.name + it.getter.name}")
                        }

                        "THEN resolved value should not contain yaml expression" {
                            if (
                                !value.contains("#chuck and #norris")
                                && (provider.name != "invoice" && it.getter.name != "<get-pattern>")
                                && (provider.name != "markdown" && it.getter.name != "<get-headers>")
                            ) {
                                if (value.contains(regex)) {
                                    throw AssertionError("Value '$value' for '${provider.name + it.getter.name}' should not contain regex '$regex'")
                                }
                            }
                        }

                        "THEN resolved value should not be empty string" {
                            if (value == "") {
                                throw AssertionError("Value for '${provider.name + it.getter.name}' should not be empty string")
                            }
                        }

                        "THEN resolved value should not contain duplicates" {
                            val values = value.split(" ")

                            fun List<String>.odds() = this.mapIndexedNotNull { index, s ->
                                if (index % 2 == 0) s else null
                            }

                            fun List<String>.evens() = this.mapIndexedNotNull { index, s ->
                                if (index % 2 != 0) s else null
                            }

                            // Accounting for some exceptional cases where values are repeated
                            // in resolved expression
                            if (
                                value != "Tiger! Tiger!" // book<get-title>
                                && (provider.name != "coffee" && it.getter.name != "<get-notes>")
                                && (provider.name != "onePiece" && it.getter.name != "<get-akumasNoMi>")
                                && value != "Girls Girls" // kPop<get-girlsGroups>
                                && value != "Two Two" // kPop<get-firstGroups>
                                && value != "woof woof" // creature<get-dog><sound>
                                && (provider.name != "lorem" && it.getter.name != "<get-punctuation>" && value != " ")
                                && value != "Duran Duran" // rockBand<get-name>
                                && value != "Li Li"
                                && value != "Dee Dee"
                            ) {
                                // Since there's no way to modify assertion message in KotlinTest it's better to throw a custom error
                                if (values.odds() == values.evens()) {
                                    throw AssertionError("Value '$value' for '${provider.name + it.getter.name}' should not contain duplicates")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    "GIVEN Faker instance is initialized with default locale" - {
        Faker.init()
        val defaultCountryUS = Faker.address.defaultCountry()
        val peruOne = Faker.address.countryByCode("PE")

        "WHEN it is re-initialized with another locale value" - {
            Faker.init(Locale.forLanguageTag("nb-NO"))

            "THEN matching keys should be overwritten in the re-initialized dictionary" {
                val defaultCountryNO = Faker.address.defaultCountry()

                assertSoftly {
                    defaultCountryNO shouldBe "Norge"
                    defaultCountryNO shouldNotBe defaultCountryUS
                }
            }

            "THEN non-matching default keys should be present in the re-initialized dictionary" {
                val peruTwo = Faker.address.countryByCode("PE")
                peruOne shouldBe peruTwo
            }
        }

        "WHEN it is again re-initialized with default locale" - {
            Faker.init()

            "THEN matching keys should be overwritten back to defaults" {
                val defaultCountry = Faker.address.defaultCountry()
                val peruThree = Faker.address.countryByCode("PE")

                assertSoftly {
                    defaultCountry shouldBe defaultCountryUS
                    peruThree shouldBe peruOne
                }
            }
        }
    }

    "GIVEN Faker instance is initialized with custom locale" - {
        val localeDir = requireNotNull(this::class.java.classLoader.getResource("locales/"))

        val locales = File(localeDir.toURI()).listFiles()?.mapNotNull {
            if (it.isFile && it.extension == "yml") {
                it.nameWithoutExtension
            } else null
        }

        "THEN Faker should be initialized without exceptions" {
            locales?.forEach { Faker.init(it) }
        }
    }
})
