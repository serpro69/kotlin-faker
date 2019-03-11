package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.*
import io.kotlintest.*
import io.kotlintest.matchers.string.*
import io.kotlintest.specs.*
import java.util.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Suppress("UNCHECKED_CAST")
class FakerIT : FreeSpec({
    "GIVEN Faker instance is initialized" - {
        val faker = Faker.init()

        "WHEN getting all public providers" - {
            val providers: List<KProperty<*>> = faker::class.declaredMemberProperties.filter {
                it.visibility == KVisibility.PUBLIC && it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType)
            }

            "AND getting all public functions in each provider" - {
                val providerProps = providers.associateBy { provider ->
                    provider.getter.call(faker)!!::class.declaredMemberProperties.filter {
                        it.visibility == KVisibility.PUBLIC
                    }
                }

                "AND each function is invoked without exceptions" - {
                    assertSoftly {
                        providerProps.forEach { (props, provider) ->
                            props.forEach {
                                val call = it.getter.call(provider.getter.call(faker))
                                val returnType = call.toString()

                                "AND result value for ${provider.name + it.getter.name} should be resolved correctly" - {
                                    val regex = Regex("""#\{.*\}|#++""")

                                    "#{expression}" shouldContain regex
                                    "### Something with number" shouldContain regex
                                    "### Something with numbers ###" shouldContain regex
                                    "######" shouldContain regex

                                    val value = when (returnType) {
                                        "() -> kotlin.String" -> (call as () -> String).invoke()
                                        "(kotlin.String) -> kotlin.String" -> (call as (String) -> String).invoke("")
                                        "(kotlin.String) -> kotlin.String!" -> (call as (String) -> String).invoke("")
                                        else -> ""
                                    }

                                    "THEN resolved value should not contain yaml expression" {
                                        if (!value.contains("#chuck and #norris")) value shouldNotContain regex
                                    }

                                    "AND resolved value should not be empty string" {
                                        value shouldNotBe ""
                                    }

                                    "AND resolved value should not contain duplicates" {
                                        val values = value.split(" ")

                                        values.forEachIndexed { index, s ->
                                            if (index < values.size - 1) {
                                                // Accounting for some exceptional cases where values are repeated
                                                // in resolved expression
                                                if (
                                                    !value.startsWith("Officer Meow Meow")
                                                    && !value.startsWith("Hello? Hello?")
                                                    && !value.startsWith("No, no, no, no, no")
                                                    && !value.startsWith("Hello? Hello?")
                                                    && !value.startsWith("Yes. Yes.")
                                                    && value != "Dance Dance Dance"
                                                    && value != "Tiger! Tiger!"
                                                    && (provider.name != "coffee" && it.getter.name != "<get-notes>")
                                                ) {
                                                    values.elementAt(index + 1) shouldNotBe s
                                                }
                                            }
                                        }
                                    }
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
})
