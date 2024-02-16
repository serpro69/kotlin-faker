package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.exception.RetryLimitException
import io.github.serpro69.kfaker.provider.Address
import io.github.serpro69.kfaker.provider.misc.StringProvider
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.beUnique
import io.kotest.matchers.collections.containDuplicates
import io.kotest.matchers.collections.shouldContainAnyOf
import io.kotest.matchers.collections.shouldNotContainAnyOf
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.shouldNotBe

class UniqueDataProviderIT : DescribeSpec({
    describe("unique generation of values enabled for provider through configuration") {
        val config = fakerConfig { uniqueGeneratorRetryLimit = 100 }

        // repeat 10 times to make sure values are not included in the collection
        repeat(10) {
            context("collection of values is generated #run$it") {
                val faker = Faker(config)
                faker.unique.configuration { enable(faker::address) }

                val countries = (0..18).map { faker.address.country() }
                val newCountries = (0..30).map { faker.address.country() }

                it("should have unique values") {
                    assertSoftly {
                        countries should beUnique()
                        newCountries should beUnique()
                        newCountries shouldNotContainAnyOf countries
                    }
                }
            }
        }
    }

    describe("collection of values is used to exclude values from being generated for specific provider") {
        val config = fakerConfig { uniqueGeneratorRetryLimit = 100 }

        // repeat 10 times to make sure values are not included in the collection
        repeat(10) {
            val faker = Faker(config)
            faker.unique.configuration { enable(faker::address) }
            val countries = (0..30).map { faker.address.country() }

            context("collection of unique values is generated run#$it") {
                val excludedCountries = listOf(
                    "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola"
                )
                faker.unique.configuration { excludeFromFunction(Address::country, excludedCountries) }
                val newCountries = (0..30).map { faker.address.country() }

                val moreExcludedCountries = listOf(
                    "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic"
                )
                faker.unique.configuration { excludeFromProvider<Address>(moreExcludedCountries) }
                val moreCountries = (0..30).map { faker.address.country() }

                val excludedCountryCodes = listOf("AD", "AE", "AF", "AG", "AI", "AL", "AM", "AO", "AQ")
                faker.unique.configuration { excludeFromProvider<Address>(excludedCountryCodes) }
                val countryCodes = (0..30).map { faker.address.countryCode() }

                it("excluded values through config should not be included in the generation") {
                    assertSoftly {
                        newCountries shouldNotContainAnyOf excludedCountries
                        moreCountries shouldNotContainAnyOf moreExcludedCountries
                        countryCodes shouldNotContainAnyOf excludedCountryCodes
                    }
                }

                it("already generated values through config should not be included in the generation") {
                    assertSoftly {
                        newCountries shouldNotContainAnyOf countries
                        moreCountries shouldNotContainAnyOf newCountries
                        moreCountries shouldNotContainAnyOf countries
                    }
                }
            }
        }
    }

    describe("use collections to exclude values from being generated for all providers") {
        // repeat 10 times to make sure values are not included in the collection
        repeat(10) {
            val faker = Faker()

            val excludedCountries = listOf(
                "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
                "Angola", "Anguilla", "Antarctica (the territory South of 60 deg S)", "Antigua and Barbuda",
                "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain",
                "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
                "Bosnia and Herzegovina", "Botswana", "Bouvet Island (Bouvetoya)", "Brazil",
                "British Indian Ocean Territory (Chagos Archipelago)", "Brunei Darussalam", "Bulgaria",
                "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
                "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands",
                "Colombia", "Comoros", "Congo", "Congo", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia",
                "Cuba", "Cyprus", "Czech Republic",
            )
            val excludedNames = listOf(
                "Abbott", "Abernathy", "Abshire", "Adams", "Altenwerth", "Anderson", "Ankunding", "Armstrong", "Auer",
                "Aufderhar", "Bahringer", "Bailey", "Balistreri", "Barrows", "Bartell", "Bartoletti", "Barton",
                "Bashirian", "Batz", "Bauch", "Baumbach", "Bayer", "Beahan", "Beatty", "Bechtelar", "Becker", "Bednar",
                "Beer", "Beier", "Berge", "Bergnaum", "Bergstrom", "Bernhard", "Bernier", "Bins", "Blanda", "Blick",
                "Block", "Bode", "Boehm", "Bogan", "Bogisich", "Borer", "Bosco", "Botsford", "Boyer", "Boyle",
                "Bradtke", "Brakus", "Braun", "Breitenberg", "Brekke", "Brown", "Bruen", "Buckridge"
            )
            val excludedDomains = listOf("com", "biz", "info")
            val excludeAll = listOf(excludedCountries, excludedNames, excludedDomains).flatten()

            faker.unique.configuration {
                enable(faker::address)
                enable(faker::name)
                exclude(excludeAll)
            }

            context("collection of unique values is generated run#$it") {
                val countries = (0..30).map { faker.address.country() }
                val names = (0..30).map { faker.name.lastName() }
                // Unique generation not enabled for Internet
                val domainSuffixes = (0..30).map { faker.internet.domainSuffix() }

                it("should not contain excluded values") {
                    assertSoftly {
                        countries shouldNotContainAnyOf excludeAll
                        names shouldNotContainAnyOf excludeAll
                        // Unique generation not enabled for Internet
                        domainSuffixes shouldNot beUnique()
                        domainSuffixes shouldContainAnyOf excludedDomains
                    }
                }
            }
        }
    }

    describe("use regex patterns to exclude values from being generated for all providers") {
        repeat(10) {
            val faker = Faker()

            faker.unique.configuration {
                // Enable unique generation
                enable(faker::address)
                enable(faker::name)
                // Exclude all values starting with "A"
                exclude { listOf(Regex("^[Cc]")) }
            }

            it("should not contain values matching pattern run#$it") {
                val countries = (0..30).map { faker.address.country() }
                val names = (0..30).map { faker.name.lastName() }
                // Unique generation not enabled for Bank
                val domainSuffixes = (0..30).map { faker.internet.domainSuffix() }

                assertSoftly {
                    countries.none { s -> s.startsWith("C") } shouldBe true
                    countries should beUnique()
                    names.none { s -> s.startsWith("C") } shouldBe true
                    names should beUnique()
                    // Unique generation not enabled for Internet
                    domainSuffixes.any { s -> s.startsWith("c") } shouldBe true
                    domainSuffixes shouldNot beUnique()
                }
            }
        }
    }

    describe("use regex patterns to exclude values from being generated for specific provider") {
        repeat(10) {
            val faker = Faker()

            faker.unique.configuration {
                // Enable unique generation and exclude by patterns
                enable(faker::address) {
                    excludeFromProvider<Address> { listOf(Regex("^A")) }
                    excludeFromFunction(Address::country) { listOf(Regex("^B")) }
                }
            }

            it("excluded values by pattern should not be included in the generation for the provider run#$it") {
                val countries = (0..30).map { faker.address.country() }
                val cities = (0..30).map { faker.address.city() }

                assertSoftly {
                    countries.none { s -> s.startsWith("A") } shouldBe true
                    countries.none { s -> s.startsWith("B") } shouldBe true
                    cities.none { s -> s.startsWith("A") } shouldBe true
                }
            }
        }
    }

    describe("unique generation of values for category") {
        val config = fakerConfig { uniqueGeneratorRetryLimit = 100 }

        context("collection of values is generated") {
            val faker = Faker(config)

            faker.unique.enable(faker::address)
            faker.unique.enable(faker::ancient)

            val countries = (0..20).map { faker.address.country() }

            it("should not contain duplicates") {
                countries should beUnique()
            }
        }

        context("used values are cleared") {
            val faker = Faker(config).also {
                it.unique.enable(it::address)
            }
            val countries = (0..20).map { faker.address.country() }

            faker.unique.clear(faker::address)
            val newCountries = (0..20).map { faker.address.country() }

            it("new collection should not contain duplicates") {
                newCountries should beUnique()
            }

            it("new collection should not equal old collection") {
                newCountries shouldNotBe countries
            }
        }

        context("exclude values from being generated") {
            // repeat 10 times to make sure values are not included in the collection
            repeat(10) {
                val faker = Faker(config)
                faker.unique.enable(faker::address)

                val countries = (0..5).map { faker.address.country() }

                context("collection of values was marked for exclusion run#$it") {
                    val excludedCountries = listOf(
                        "Afghanistan",
                        "Albania",
                        "Algeria",
                        "American Samoa",
                        "Andorra",
                        "Angola"
                    )

                    faker.unique.exclude<Address>("country", excludedCountries)

                    val newCountries = (0..20).map { faker.address.country() }

                    it("excluded values should not be included in the generation") {
                        newCountries shouldNotContainAnyOf excludedCountries
                    }

                    it("already generated values should not be included in the generation") {
                        newCountries shouldNotContainAnyOf countries
                    }
                }

            }
        }

        context("unique generation is not enabled for category") {
            val faker = Faker(config)

            context("some values were marked for exclusion") {
                val excludedCountries = listOf(
                    "Afghanistan",
                    "Albania",
                    "Algeria",
                    "American Samoa",
                    "Andorra",
                    "Angola",
                    "Anguilla",
                    "Antarctica",
                    "Antigua And Barbuda",
                    "Argentina",
                    "Armenia",
                    "Aruba",
                    "Australia",
                    "Austria",
                    "Aland Islands",
                    "Azerbaijan",
                    "Bahamas",
                    "Bahrain",
                    "Bangladesh",
                    "Barbados",
                    "Belarus",
                    "Belgium",
                    "Belize",
                    "Benin",
                    "Bermuda",
                    "Bhutan",
                    "Bolivia",
                    "Bonaire",
                    "Bosnia And Herzegovina",
                    "Botswana",
                    "Bouvet Island"
                )

                faker.unique.exclude<Address>("country", excludedCountries)

                val countries = (0..100).map { faker.address.country() }

                it("collection can have duplicates") {
                    countries should containDuplicates()
                }

                it("collection can have excluded values") {
                    countries.any { excludedCountries.contains(it) } shouldBe true
                }
            }
        }

        context("values are generated for another category that is not marked for unique generation") {
            val faker = Faker(config)
            faker.unique.enable(faker::address)

            val animalNames = (0..100).map { faker.animal.name() }

            it("collection can have duplicates") {
                animalNames shouldNot beUnique()
            }
        }

        context("unique generation for category is disabled") {
            val faker = Faker(config)
            faker.unique.enable(faker::address)
            (0..20).map { faker.address.country() }

            faker.unique.disable(faker::address)

            context("collection of values is generated") {
                val countries = (0..100).map { faker.address.country() }

                it("collection can have duplicates") {
                    countries shouldNot beUnique()
                }
            }
        }

        context("unique generation is disabled for all categories") {
            val faker = Faker(config)
            faker.unique.enable(faker::address)
            faker.unique.enable(faker::ancient)

            faker.unique.disableAll()

            context("collections of values are generated") {
                val countries = (0..100).map { faker.address.country() }
                val gods = (0..100).map { faker.ancient.god() }

                it("collection can have duplicates") {
                    assertSoftly {
                        countries shouldNot beUnique()
                        gods shouldNot beUnique()
                    }
                }
            }

            context("unique generation for a category is re-enabled") {
                faker.unique.enable(faker::address)

                context("collection of values is generated") {
                    val countries = (0..20).map { faker.address.country() }

                    it("collection should not contain duplicates") {
                        countries should beUnique()
                    }
                }
            }
        }

        context("unique generation is cleared for all categories") {
            val faker = Faker(config)
            faker.unique.enable(faker::address)
            faker.unique.enable(faker::ancient)

            // Generate some values first
            (0..20).map { faker.address.country() }
            (0..20).map { faker.ancient.hero() }

            faker.unique.clearAll()

            context("collections of values are generated") {
                val countries = (0..20).map { faker.address.country() }
                val heroes = (0..20).map { faker.ancient.hero() }

                it("collections should be unique") {
                    assertSoftly {
                        countries should beUnique()
                        heroes should beUnique()
                    }
                }
            }
        }
    }

    describe("local unique generation") {
        val config = fakerConfig { uniqueGeneratorRetryLimit = 100 }
        val faker = Faker(config)

        context("unique property prefixes the category function invocation") {
            val countries = (0..20).map {
                faker.address.unique.country()
            }

            it("collection should not contain duplicates") {
                countries should beUnique()
            }

            it("other functions of the same category should not be marked as unique") {
                // This will produce an error if used with `unique` prefix
                // because there is only one value in the dictionary
                (0..10).map { faker.address.defaultCountry() }
            }

            context("values are cleared for the function name") {
                faker.address.unique.clear("country")

                val newCountries = (0..20).map {
                    faker.address.unique.country()
                }

                it("collection should not contain duplicates") {
                    newCountries should beUnique()
                }
            }

            context("values are cleared for all functions") {
                repeat(20) { faker.address.unique.country() }
                repeat(20) { faker.address.unique.state() }

                faker.address.unique.clearAll()

                it("re-generated collections should be unique") {
                    val newCountries = (0..20).map { faker.address.unique.country() }
                    val states = (0..20).map { faker.address.unique.state() }

                    assertSoftly {
                        newCountries should beUnique()
                        states should beUnique()
                    }
                }
            }
        }
    }

    it("should throw an exception when retry limit exceeds") {
        val faker = faker { }
        faker.unique.configuration {
            enable(faker::string) {
                excludeFromFunction<StringProvider>("numerify", "foo123bar")
            }
        }
        shouldThrow<RetryLimitException> { faker.string.numerify("foo123bar") }
    }
})
