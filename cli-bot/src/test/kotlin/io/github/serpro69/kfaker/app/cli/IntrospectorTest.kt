package io.github.serpro69.kfaker.app.cli

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.games.GamesFaker
import io.github.serpro69.kfaker.games.provider.Dota
import io.github.serpro69.kfaker.provider.Address
import io.github.serpro69.kfaker.travel.TravelFaker
import io.github.serpro69.kfaker.travel.provider.Airport
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactly
import kotlin.reflect.KClass

class IntrospectorTest : DescribeSpec() {
    private val faker = Faker()

    init {
        describe("Introspector class") {
            context("list all Faker providers") {
                val introspector = Introspector(faker)
                val providers = introspector.providers
                    .map { it.getter.returnType.classifier as KClass<*> }
                    .map { it.simpleName }

                it("should contain all providers") {
                    val expectedProviders = listOf(
                        "Address",
                        "Color",
                        "CryptographyProvider",
                        "Currency",
                        "CurrencySymbol",
                        "File",
                        "Gender",
                        "IdNumber",
                        "Internet",
                        "Measurement",
                        "Money",
                        "Name",
                        "Person",
                        "PhoneNumber",
                        "Separator",
                    )
                    providers.toList() shouldContainExactly expectedProviders
                }
            }

            context("list available functions for each provider") {
                val introspector = Introspector(faker)
                val providerData = introspector.providerData

                it("should contain all providers") {
                    val providers = providerData.map { it.key.name }
                    val expectedProviders = listOf(
                        "address",
                        "color",
                        "crypto",
                        "currency",
                        "currencySymbol",
                        "file",
                        "gender",
                        "idNumber",
                        "internet",
                        "measurement",
                        "money",
                        "name",
                        "person",
                        "phoneNumber",
                        "separator",
                    )

                    providers shouldContainExactly expectedProviders
                }

                it("should contain all functions of the provider") {
                    val addressFunctions = providerData.entries.first { (provider, _) ->
                        (provider.returnType.classifier as KClass<*>) == Address::class
                    }.value.first.map { it.name }

                    val expectedFunctions = listOf(
                        "buildingNumber",
                        "city",
                        "cityPrefix",
                        "citySuffix",
                        "cityWithState",
                        "community",
                        "communityPrefix",
                        "communitySuffix",
                        "country",
                        "countryByCode",
                        "countryByName",
                        "countryCode",
                        "countryCodeLong",
                        "defaultCountry",
                        "fullAddress",
                        "mailbox",
                        "postcode",
                        "postcodeByState",
                        "secondaryAddress",
                        "state",
                        "stateAbbr",
                        "streetAddress",
                        "streetName",
                        "streetSuffix",
                        "timeZone"
                    )

                    addressFunctions.toList() shouldContainExactly expectedFunctions
                }

            }

            context("deprecated functionality") {
                it("should not contain deprecated functions") {
                    val introspector = Introspector(GamesFaker())
                    val providerData = introspector.providerData
                    val addressFunctions = providerData.entries.first { (provider, _) ->
                        (provider.returnType.classifier as KClass<*>) == Dota::class
                    }.value.first.map { it.name }

                    val expectedFunctions = listOf("building", "hero", "item", "player", "team")

                    addressFunctions.toList() shouldContainExactly expectedFunctions
                }
            }

            context("sub-providers") {
                it("should contain all sub-providers of the provider") {
                    val introspector = Introspector(TravelFaker())
                    val providerData = introspector.providerData
                    val subProviders = providerData.entries.first { (provider, _) ->
                        (provider.returnType.classifier as KClass<*>) == Airport::class
                    }.value.second

                    subProviders.map { it.key.name } shouldContainExactly listOf("europeanUnion", "unitedStates")
                }
            }
        }
    }
}
