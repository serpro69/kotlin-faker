package io.github.sergio.igwt.kfaker

import io.github.sergio.igwt.kfaker.provider.FakeDataProvider
import io.kotlintest.assertSoftly
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.FreeSpec
import java.util.Locale
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType

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

                "THEN each function can be called without exceptions and return type is String" {
                    providerProps.forEach { (props: List<KProperty<*>>, provider) ->
                        assertSoftly {
                            props.forEach {
                                it.getter.call(provider.getter.call(faker)).toString() shouldContain "String"
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
