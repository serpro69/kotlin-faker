package io.github.sergio.igwt.kfaker

import io.github.sergio.igwt.kfaker.provider.FakeDataProvider
import io.kotlintest.assertSoftly
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.specs.FreeSpec
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
})
