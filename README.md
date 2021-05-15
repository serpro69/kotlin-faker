### <a href="https://github.com/serpro69/kotlin-faker"> <img src=./logo/name.png alt="kotlin-faker"/> </a>

> **Generates realistically-looking fake data**  
> Just like this fake-logo, but not quite so.  
> <img src=./logo/kotlin_faker.png height="144" alt="fake-logo"/>

[![Build Status](https://travis-ci.org/serpro69/kotlin-faker.svg?branch=master)](https://travis-ci.org/serpro69/kotlin-faker)
[![Version Badge](https://api.bintray.com/packages/serpro69/maven/kotlin-faker/images/download.svg) ](https://bintray.com/serpro69/maven/kotlin-faker/_latestVersion)
[![RC Version Badge](https://api.bintray.com/packages/serpro69/maven-release-candidates/kotlin-faker/images/download.svg) ](https://bintray.com/serpro69/maven-release-candidates/kotlin-faker/_latestVersion)
[![Coverage Status](https://coveralls.io/repos/github/serpro69/kotlin-faker/badge.svg)](https://coveralls.io/github/serpro69/kotlin-faker)
[![Issues Badge](https://img.shields.io/github/issues/serpro69/kotlin-faker.svg)](https://github.com/serpro69/kotlin-faker/issues)
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
[![Licence Badge](https://img.shields.io/github/license/serpro69/kotlin-faker.svg)](LICENCE.md)

## ToC

- [About](#about)
- [Comparison with other JVM-based faker libraries](#comparison-with-other-jvm-based-faker-libs)
- [Usage](#usage)
    - [Downloading](#downloading)
    - [Generating data](#generating-data)
    - [Configuring Faker](#configuring-faker)
        - [Default configuration](#default-configuration)
        - [Deterministic Random](#deterministic-random)
        - [Generating unique values](#generating-unique-values)
        - [Localized dictionary](#localized-dictionary)
    - [Java interop](#java-interop)
- [CLI](#cli)
- [Data Providers](#data-providers)
    - [Generating a random instance of any class](#generating-a-random-instance-of-any-class)
- [Migrating to 1.0](#migrating-to-10)
    - [For kotlin users](#for-kotlin-users)
    - [For java users](#for-java-users)
- [For developers](#for-developers)
- [Build and Deploy](#build-and-deploy)
- [Contributing](#contributing)
- [Thanks](#thanks)
- [Licence](#licence)

## About

Port of a popular ruby [faker](https://github.com/stympy/faker) gem written in kotlin. Generates realistically looking fake data such as names, addresses, banking details, and many more, that can be used for testing purposes during development and testing.

## Comparison with similar jvm-based "faker libs"

While there are several other libraries out there with similar functionalities, I had several reasons for creating this one:
- most of the ones I've found are written in java and I wanted to use kotlin
- none of them had the functionality I needed
- I didn't feel like forking an existing kotlin-based lib - fakeit - and refactoring the entire codebase, especially with it not being maintained for the past couple of years.

So why use this one instead? I've decided to make a comparison between kotlin-faker, and others libs that have been out there for quite some time.

<i>The benchmarks time is an average execution time of 10 consecutive runs. Each run includes creating a new Faker instance and generating a 1_000_000 values with the function returning a person's full name.

Note: benchmarks for `blocoio/faker` could not be done due to unexpected exceptions coming from the lib, benchmarks for `moove-it/fakeit` could not be done due to android dependencies in the lib</i>

|                                                             | **kotlin-faker** | [DiUS/java-faker](https://github.com/DiUS/java-faker) | [Devskiller/jfairy](https://github.com/Devskiller/jfairy) | [blocoio/faker](https://github.com/blocoio/faker) | [moove-it/fakeit](https://github.com/moove-it/fakeit) |
|-------------------------------------------------------------|------------------|-------------------------------------------------------|-----------------------------------------------------------|---------------------------------------------------|-------------------------------------------------------|
| **language**                                                | kotlin           | java                                                  | java                                                      | java                                              | kotlin                                                |
| **number of available providers** (`address`, `name`, etc.) | 171              | 73                                                    | 8                                                         | 21                                                | 36                                                    |
| **number of available locales**                             | 55               | 47                                                    | 10                                                        | 46                                                | 44                                                    |
| **extra functionality** (i.e. `randomClassInstance` gen)    | &#9989;          | &#10062;                                              | &#10062;                                                  | &#10062;                                          | &#10062;                                              |
| **actively maintained**                                     | &#9989;          | &#9989;                                               | &#9989;                                                   | &#9989;                                           | &#10062;                                              |
| **[cli-bot app](cli-bot)**                                  | &#9989;          | &#10062;                                              | &#10062;                                                  | &#10062;                                          | &#10062;                                              |
| **benchmarks**                                              | 5482ms           | 17529.9ms                                             | 15036.5ms                                                 | NA                                                | NA                                                    |

## Usage

### Downloading

Latest releases are always available on maven central.

**With gradle**

```groovy
dependencies {
    implementation 'io.github.serpro69:kotlin-faker:$version'
}
```  

**With maven**

```xml
<dependencies>
    <dependency>
        <groupId>io.github.serpro69</groupId>
        <artifactId>kotlin-faker</artifactId>
        <version>${version}</version>
    </dependency>
</dependencies>
```  

***Snapshots are also available using the following repository: `https://oss.sonatype.org/content/repositories/snapshots/`***

For example, to enable snapshots with gradle:
```groovy
repositories {
    maven {
        url = 'https://oss.sonatype.org/content/repositories/snapshots/'
    }
}
```

**Downloading a jar**  
The jar and pom files can also be found at
this [link](https://dl.bintray.com/serpro69/maven/io/github/serpro69/kotlin-faker/)

### Generating data

```kotlin
val faker = Faker()

faker.name.firstName() // => Ana
faker.address.city() // => New York
```

### Configuring Faker

#### Default configuration

If no `FakerConfig` instance is passed to `Faker` constructor then default configuration will be used:

- `locale` is set to `en`
- `random` is seeded with a pseudo-randomly generated number.
- `uniqueGeneratorRetryLimit` is set to `100`

#### Deterministic Random

Faker supports seeding of it's PRNG (pseudo-random number generator) through configuration to provide deterministic
output of repeated function invocations.

```kotlin
val fakerConfig = FakerConfig.builder().create {
    random = Random(42)
}

val faker = Faker(fakerConfig)
val city1 = faker.address.city()
val name1 = faker.name.name()

val otherFaker = Faker(fakerConfig)
val city1 = otherFaker.address.city()
val name1 = otherFaker.name.name()

city1 == city2 // => true
name1 == name2 // => true
```

#### Generating unique values

Faker supports generation of unique values. There are basically two ways to generate unique values:

**Unique values for entire provider**

```kotlin
val faker = Faker()
faker.unique.configure {
    enable(faker::address) // enable generation of unique values for address provider
}

// will generate unique country each time it's called
repeat(10) { faker.address.country() }
```

To clear the record of unique values that were already generated:

```kotlin
faker.unique.clear(faker::address) // clears used values for address provider

faker.unique.clearAll() // clears used values for all providers
```

To disable generation of unique values:

```kotlin
faker.unique.disable(faker::address) // disables generation of unique values for address provider and clears all used values

faker.unique.disableAll() // disables generation of unique values for all providers and clears all used values
```

**Unique values for particular functions of a provider**

```kotlin
val faker = Faker()

repeat(10) { faker.address.unique.country() } // will generate unique country each time `country()` is prefixed with `unique`

repeat(10) { faker.address.city() } // this will not necessarily be unique (unless `faker.unique.enable(faker::address)` was called previously)
```

To clear the record of unique values that were already generated:

```kotlin
faker.address.unique.clear("city") // clears used values for `faker.address.unique.city()` function

faker.address.unique.clearAll() // clears used values for all functions of address provider
```

**Configuring retry limit**  
If the retry count of unique generator exceeds the configured value (defaults to `100`)
then `RetryLimitException` will be thrown.

It is possible to re-configure the default value through `FakerConfig`:

```kotlin
val config = FakerConfig.builder().create {
    uniqueGeneratorRetryLimit = 1000
}

val faker = Faker(config)
```

**Excluding values from generation**
It is possible to exclude values from being generated with unique generator. This is configured on the `faker` level for
each (or in some cases - all) of the providers.

```kotlin
val faker = Faker()

faker.unique.configuration {
    // Enable generation of unique values for Address provider
    // Any unique generation configuration will only affect "enabled" providers
    enable(faker::address)

    // Exclude listOfValues from being generated 
    // in all providers that are enabled for unique generation
    exclude(listOfValues)

    // Exclude values starting with "A" from being generated 
    // in all providers that are enabled for unique generation
    exclude { listOf(Regex("^A")) }

    // Additional configuration for particular provider
    // First enable generation of unique values for Name provider
    enable(faker::name) {
        // Exclude listOfNames from being generated by any Name provider function
        excludeFromProvider<Name>(listOfNames)

        // Exclude listOfLastNames from being generated by Name#lastName function
        excludeFromFunction(Name::lastName, listOfLastNames)

        // Exclude values starting with "B" from being generated by any Name provider function
        excludeFromProvider<Name> { listOf(Regex("^B")) }

        // Exclude values starting with "C" from being generated by Name#country function
        excludeFromFunction(Name::lastName) { listOf(Regex("^C")) }
    }
}

// Based on the above config the following will be true in addition to generating unique values:
val city = faker.address.city()
assertTrue(listOfValues.contains(city) == false)
assertTrue(city.startsWith("A") == false)

val firstName = faker.name.firstName()
val lastName = faker.name.lastName()
assertTrue(listOfValues.contains(firstName) == false)
assertTrue(listOfValues.contains(lastName) == false)
assertTrue(listOfNames.contains(firstName) == false)
assertTrue(listOfNames.contains(lastName) == false)
assertTrue(listOfLastNames.contains(lastName) == false)
assertTrue(firstName.startsWith("A") == false)
assertTrue(lastName.startsWith("A") == false)
assertTrue(firstName.startsWith("B") == false)
assertTrue(lastName.startsWith("B") == false)
assertTrue(lastName.startsWith("C") == false)
```

This is only applicable when the whole category, i.e. `Address` or `Name` is enabled for unique generation of values.

```kotlin
faker.address.unique.country() // will still generate unique values, but won't consider exclusions, if any
```

#### Localized dictionary

`Faker` can be configured to use a localized dictionary file instead of the default `en` locale.

```kotlin
val fakerConfig = FakerConfig.builder().create {
    locale = "nb-NO"
}

val faker = Faker(fakerConfig)
val city1 = faker.address.city() // => Oslo
```  

##### Available Locales

<details><summary><b>List of available locales (clickable):</b></summary>
<p>

* `ar`
* `bg`
* `ca`
* `ca-CAT`
* `da-DK`
* `de`
* `de-AT`
* `de-CH`
* `ee`
* `en` - default
* `en-AU`
* `en-au-ocker`
* `en-BORK`
* `en-CA`
* `en-GB`
* `en-IND`
* `en-MS`
* `en-NEP`
* `en-NG`
* `en-NZ`
* `en-PAK`
* `en-SG`
* `en-TH`
* `en-UG`
* `en-US`
* `en-ZA`
* `es`
* `es-MX`
* `fa`
* `fi-FI`
* `fr`
* `fr-CA`
* `fr-CH`
* `he`
* `hy`
* `id`
* `it`
* `ja`
* `ko`
* `lv`
* `nb-NO`
* `nl`
* `no`
* `pl`
* `pt`
* `pt-BR`
* `ru`
* `sk`
* `sv`
* `th`
* `tr`
* `uk`
* `vi`
* `zh-CN`
* `zh-TW`

</p>
</details>

Using a non-default locale will replace the values in some of the providers with the values from localized dictionary.

```kotlin
val fakerConfig = FakerConfig.builder().create {
    locale = "es"
}
val faker = Faker(fakerConfig)
faker.address.city() // => Barcelona
```

<i>Note that if the localized dictionary file does not contain a category (or a parameter in a category)
that is present in the default locale, then non-localized value will be used instead.</i>

```kotlin
val faker = Faker()
faker.gameOfThrones.cities() // => Braavos

val fakerConfig = FakerConfig.builder().create {
    locale = "nb-NO"
}
val localizedFaker = Faker(fakerConfig)
// `game_of_thrones` category is not localized for `nb-NO` locale
localizedFaker.gameOfThrones.cities() // => Braavos
```

### Java interop

Although this lib was created with Kotlin in mind it is still possible to use from a Java-based project thanks to great
Kotlin-to-Java interop.

Configuring `Faker`:

```java
FakerConfig fakerConfig=FakerConfigBuilder.create(FakerConfig.builder(),fromConsumer(builder->{
    builder.setRandom(new Random(42));
    builder.setLocale("en-AU");
    }));
```

If `builder` parameter is not called with help of `fromConsumer` method, then explicit return should be specified:

```java
FakerConfig fakerConfig=FakerConfigBuilder.create(FakerConfig.builder(),builder->{
    builder.setRandom(new Random(42));
    builder.setLocale("en-AU");
    return Unit.INSTANCE;
    });
```

Calling `Faker` methods:

```java
new Faker(fakerConfig).getName().firstName(); // => John
```

## CLI

Command line application can be used for a quick lookup of faker functions. See [faker-bot README](cli-bot/README.md)
for installation and usage details.

## Data Providers

Below is the list of available providers that correspond to the dictionary files found
in [core/locales/en](core/src/main/resources/locales/en)

<i>Note that not all (although most) of the providers and their functions are implemented at this point. For more
details see the particular `.md` file for each provider below.</i>

<details><summary><b>List of available providers (clickable):</b></summary>
<p>

* [Address](doc/address.md)
* [Ancient](doc/ancient.md)
* [Animal](doc/animal.md)
* [App](doc/app.md)
* [Appliance](doc/appliance.md)
* [AquaTeenHungerForce](doc/aqua_teen_hunger_force.md)
* [Artist](doc/artist.md)
* [BackToTheFuture](doc/back_to_the_future.md)
* [Bank](doc/bank.md)
* [Barcode](doc/barcode.md)
* [Basketball](doc/basketball.md)
* [Beer](doc/beer.md)
* [BigBangTheory](doc/big_bang_theory.md)
* [Blood](doc/blood.md)
* [BojackHorseman](doc/bojack_horseman.md)
* [Book](doc/book.md)
* [BossaNova](doc/bossa_nova.md)
* [BreakingBad](doc/breaking_bad.md)
* [Buffy](doc/buffy.md)
* [Business](doc/business.md)
* [Cannabis](doc/cannabis.md)
* [Cat](doc/cat.md)
* [Chiquito](doc/chiquito.md)
* [ChuckNorris](doc/chuck_norris.md)
* [Code](doc/code.md)
* [Coffee](doc/coffee.md)
* [Coin](doc/coin.md)
* [Color](doc/color.md)
* [Commerce](doc/commerce.md)
* [Community](doc/community.md)
* [Company](doc/company.md)
* [Compass](doc/compass.md)
* [Computer](doc/computer.md)
* [Construction](doc/construction.md)
* [Control](doc/control.md)
* [Cosmere](doc/cosmere.md)
* [CryptoCoin](doc/crypto_coin.md)
* [CultureSeries](doc/culture_series.md)
* [Currency](doc/currency.md)
* [CurrencySymbol](doc/currency_symbol.md)
* [DcComics](doc/dc_comics.md)
* [Demographic](doc/demographic.md)
* [Departed](doc/departed.md)
* [Dessert](doc/dessert.md)
* [Device](doc/device.md)
* [DnD](doc/dnd.md)
* [Dog](doc/dog.md)
* [Dota](doc/dota.md)
* [DragonBall](doc/dragon_ball.md)
* [DrivingLicense](doc/driving_license.md)
* [Drone](doc/drone.md)
* [DrWho](doc/dr_who.md)
* [DumbAndDumber](doc/dumb_and_dumber.md)
* [Dune](doc/dune.md)
* [Educator](doc/educator.md)
* [ElderScrolls](doc/elder_scrolls.md)
* [ElectricalComponents](doc/electrical_components.md)
* [ESport](doc/esport.md)
* [Fallout](doc/fallout.md)
* [FamilyGuy](doc/family_guy.md)
* [File](doc/file.md)
* [Finance](doc/finance.md)
* [Food](doc/food.md)
* [Football](doc/football.md)
* [FreshPriceOfBelAir](doc/fresh_price_of_bel_air.md)
* [Friends](doc/friends.md)
* [FunnyName](doc/funny_name.md)
* [Futurama](doc/futurama.md)
* [Game](doc/game.md)
* [GameOfThrones](doc/game_of_thrones.md)
* [Gender](doc/gender.md)
* [GhostBusters](doc/ghost_busters.md)
* [GratefulDead](doc/grateful_dead.md)
* [GreekPhilosophers](doc/greek_philosophers.md)
* [Hacker](doc/hacker.md)
* [HalfLife](doc/half_life.md)
* [HarryPotter](doc/harry_potter.md)
* [Heroes](doc/heroes.md)
* [HeroesOfTheStorm](doc/heroes_of_the_storm.md)
* [HeyArnold](doc/hey_arnold.md)
* [Hipster](doc/hipster.md)
* [HitchhikersGuideToTheGalaxy](doc/hitchhikers_guide_to_the_galaxy.md)
* [Hobbit](doc/hobbit.md)
* [Horse](doc/horse.md)
* [House](doc/house.md)
* [HowIMetYourMother](doc/how_i_met_your_mother.md)
* [IdNumber](doc/id_number.md)
* [IndustrySegments](doc/industry_segments.md)
* [Internet](doc/internet.md)
* [Invoice](doc/invoice.md)
* [Job](doc/job.md)
* [KPop](doc/kpop.md)
* [LeagueOfLegends](doc/league_of_legends.md)
* [Lebowski](doc/lebowski.md)
* [LordOfTheRings](doc/lord_of_the_rings.md)
* [Lorem](doc/lorem.md)
* [Lovecraft](doc/lovecraft.md)
* [Markdown](doc/markdown.md)
* [Marketing](doc/marketing.md)
* [Measurement](doc/measurement.md)
* [MichaelScott](doc/michael_scott.md)
* [Military](doc/military.md)
* [Minecraft](doc/minecraft.md)
* [Money](doc/money.md)
* [Movie](doc/movie.md)
* [Music](doc/music.md)
* [Myst](doc/myst.md)
* [Name](doc/name.md)
* [Nation](doc/nation.md)
* [NatoPhoneticAlphabet](doc/nato_phonetic_alphabet.md)
* [NewGirl](doc/new_girl.md)
* [OnePiece](doc/one_piece.md)
* [Opera](doc/opera.md)
* [Overwatch](doc/overwatch.md)
* [ParksAndRec](doc/parks_and_rec.md)
* [PearlJam](doc/pearl_jam.md)
* [Phish](doc/phish.md)
* [PhoneNumber](doc/phone_number.md)
* [Pokemon](doc/pokemon.md)
* [Prince](doc/prince.md)
* [PrincessBride](doc/princess_bride.md)
* [ProgrammingLanguage](doc/programming_language.md)
* [Quote](doc/quote.md)
* [Rajnikanth](doc/rajnikanth.md)
* [RandomProvider](doc/random_provider.md)
* [Relationship](doc/relationship.md)
* [Restaurant](doc/restaurant.md)
* [RickAndMorty](doc/rick_and_morty.md)
* [RockBand](doc/rock_band.md)
* [Rupaul](doc/rupaul.md)
* [Rush](doc/rush.md)
* [Science](doc/science.md)
* [Seinfeld](doc/seinfeld.md)
* [Separator](doc/separator.md)
* [Shakespeare](doc/shakespeare.md)
* [Show](doc/show.md)
* [SiliconValley](doc/silicon_valley.md)
* [Simpsons](doc/simpsons.md)
* [SlackEmoji](doc/slack_emoji.md)
* [SonicTheHedgehog](doc/sonic_the_hedgehog.md)
* [Source](doc/source.md)
* [SouthPark](doc/south_park.md)
* [Space](doc/space.md)
* [Stargate](doc/stargate.md)
* [StarTrek](doc/star_trek.md)
* [StarWars](doc/star_wars.md)
* [StrangerThings](doc/stranger_things.md)
* [StreetFighter](doc/street_fighter.md)
* [Stripe](doc/stripe.md)
* [Subscription](doc/subscription.md)
* [Suits](doc/Suits.md)
* [Superhero](doc/superhero.md)
* [SuperSmashBros](doc/super_smash_bros.md)
* [SwordArtOnline](doc/sword_art_online.md)
* [Team](doc/team.md)
* [TheExpanse](doc/the_expanse.md)
* [TheITCrowd](doc/the_it_crowd.md)
* [TheThickOfIt](doc/the_thick_of_it.md)
* [TwinPeaks](doc/twin_peaks.md)
* [UmphreysMcgee](doc/umphreys_mcgee.md)
* [University](doc/university.md)
* [Vehicle](doc/vehicle.md)
* [VentureBros](doc/venture_bros.md)
* [Verbs](doc/verbs.md)
* [VForVendetta](doc/v_for_vendetta.md)
* [WarhammerFantasy](doc/warhammer_fantasy.md)
* [Witcher](doc/witcher.md)
* [WorldCup](doc/world_cup.md)
* [WorldOfWarcraft](doc/world_of_warcraft.md)
* [Yoda](doc/yoda.md)
* [Zelda](doc/zelda.md)

</p>
</details>

### Generating a random instance of any class

There are some rules when creating a random instance of a class:
- The constructor with the least number of arguments is used (This can be configured - read on.)
- `kotlin.collection.*` and `kolin.Array` types in the constructor are not supported at the moment

To generate a random instance of any class use `Faker().randomProvider`. For example:

```kotlin
class Foo(val a: String)
class Bar(val foo: Foo)

class Test {
    @Test
    fun test() {
        val faker = Faker()

        val foo: Foo = faker.randomProvider.randomClassInstance()
        val bar: Bar = faker.randomProvider.randomClassInstance()
    }
}
```

#### Pre-Configuring type generation for constructor params

Some, or all, of the constructor params can be instantiated with values following some pre-configured logic using `typeGenerator` function. Consider the following example:

```kotlin
class Baz(val id: Int, val uuid: UUID)

class Test {
    @Test
    fun test() {
        val faker = Faker()

        val baz: Baz = faker.randomProvider.randomClassInstance {
            typeGenerator<UUID> { UUID.fromString("00000000-0000-0000-0000-000000000000") }
            typeGenerator<Int> { 0 }
        }

    }
}
```

For each instance of `Baz` the following will be true:

```
baz.id == 0
baz.uuid == UUID.fromString("00000000-0000-0000-0000-000000000000")
```

The example itself does not make that much sense, since we're using "static" values, but we could also do something like:

```kotlin
val baz: Baz = faker.randomProvider.randomClassInstance {
    typeGenerator<UUID> { UUID.randomUUID() }
}
```

or even:

```kotlin
class Person(val id: Int, val name: String)

class Test {
    @Test
    fun test() {
        val faker = Faker()

        val person: Person = faker.randomProvider.randomClassInstance {
            typeGenerator<String> { faker.name.fullName() }
        }
    }
}
```

#### Deterministic constructor selection

By default, the constructor with the least number of args is used when creating a random instance of the class. This might not always be desirable and can be configured. Consider the following data classes:

```kotlin
class Foo
class Bar(val int: Int)
class Baz(val foo: Foo, val string: String)

class FooBarBaz {
    var foo: Foo? = null
        private set
    var bar: Bar? = null
        private set
    var baz: Baz? = null
        private set

    constructor(foo: Foo) {
        this.foo = foo
    }

    constructor(foo: Foo, bar: Bar) : this(foo) {
        this.bar = bar
    }

    constructor(foo: Foo, bar: Bar, baz: Baz) : this(foo, bar) {
        this.baz = baz
    }
}
```

If there is a need to use the constructor with 3 arguments when creating an instance of `FooBarBaz`, we can do it like so:
```kotlin
class Test {
    @Test
    fun test() {
        val faker = Faker()

        val fooBarBaz: FooBarBaz = randomProvider.randomClassInstance {
            constructorParamSize = 3
            fallbackStrategy = FallbackStrategy.USE_MAX_NUM_OF_ARGS
        }
    }
}
```

In the above example, `FooBarBaz` will be instantiated with the first discovered constructor that has `parameters.size == 3`; if there are multiple constructors that satisfy this condition - a random one will be used. Failing that (for example, if there is no such constructor), a constructor with the maximum number of arguments will be used to create an instance of the class.

Alternatively to `constructorParamSize` a `constructorFilterStrategy` config property can be used as well:
```kotlin
class Test {
    @Test
    fun test() {
        val faker = Faker()

        val fooBarBaz: FooBarBaz = randomProvider.randomClassInstance {
            constructorFilterStrategy = ConstructorFilterStrategy.MAX_NUM_OF_ARGS
        }
    }
}
```

There are some rules to the above:
- `constructorParamSize` config property takes precedence over `constructorFilterStrategy`
- both can be specified at the same time, though in most cases it probably makes more sense to use `failbackStrategy` with `constructorParamSize` as it just makes things a bit more readable
- configuration properties that are set in `randomClassInstance` block will be applied to all "children" classes. For example classes `Foo`, `Bar`, and `Baz` will use the same random instance configuration settings when instances of those classes are created in `FooBarBaz` class.

## Migrating to 1.0

**Prior to version 1.0:**

- `Faker` was a singleton.
- Random seed was provided through `Faker.Config` instance.
- Locale was provided as parameter to `init()` function.
- Provider functions were function literals. If `invoke()` was explicitly specified, then it will have to be removed (
  See below.)

**After version 1.0:**

- `Faker` is a class.
- Configuration (rng, locale) is set with `FakerConfig` class. An instance of `FakerConfig` can be passed to `Faker`
  constructor.
- Provider functions are no longer function literals. Explicit calls to `invoke()` will throw compilation error.

### For kotlin users

```diff
- // prior to version 1.0
- Faker.Config.random = Random(42)
- Faker.init(locale)
- Faker.address.city()
- // or with explicit `invoke()`
- Faker.address.country.invoke()
+ // since version 1.0
+ // locale and random configuration is set with `FakerConfig` class (See Usage in this readme)
+ val faker = Faker(fakerConfig)
+ faker.address.city()
+ // explicit calls to `invoke()` have to be removed
+ faker.address.country()
```

### For java users

Apart from changes to configuring locale and random seed and instantiating `Faker` through constructor instead of using
a singleton instance (see kotlin examples), the main difference for java users is that provider functions are no longer
function literals, therefore calls to `invoke()` operator will have to be removed and getters replaced with function
calls.

```diff
- // prior to version 1.0
- Faker.init(locale);
- Faker.getAddress().getCity().invoke();
+ // since version 1.0
+ Faker faker = new Faker(fakerConfig);
+ // note `city()` function is called instead of getter 
+ // and no call to `invoke()` operator 
+ faker.getAddress().city();
```

## For developers

### Adding a new dictionary (provider)

When adding a new dictionary yml file the following places need to reflect changes:

- [Dictionary.kt](core/src/main/kotlin/io/github/serpro69/kfaker/dictionary/Dictionary.kt) - add a new class
  to `CategoryName` enum. This is only necessary if the category is not already there.
- [Constants.kt](core/src/main/kotlin/io/github/serpro69/kfaker/Constants.kt) - the new dictionary filename should be
  added to `defaultFileNames` property.
- [Faker.kt](core/src/main/kotlin/io/github/serpro69/kfaker/Faker.kt) - add a new faker provider property to `Faker`
  class.
- The provider implementation class should go into [provider](core/src/main/kotlin/io/github/serpro69/kfaker/provider)
  package.
- [doc](doc) - add an `.md` file for the new provider.
- [reflect-config.json](cli-bot/src/main/resources/META-INF/native-image/io.github.serpro69/cli-bot/reflect-config.json)
  has to be updated to build the native image with graal.
- And of course unit tests.

## Build and Deploy

To deploy to OSS Sonatype repo:

- set the following properties in `~/.gradle/gradle.properties`
    - `signing.keyId=<key_id>`
    - `signing.password=<key_passphrase>`
    - `signing.secretKeyRingFile=/home/user/.gnupg/secring.gpg`
    - `sonatypeUsername=<oss_user_token>`
    - `sonatypePassword=<oss_password_token>`
- running `publishFakerCorePublicationToSonatypeRepository` will publish the artifacts to either staging release repo or to snapshots repo, depending on the current version

### Bumping versions

Versions need to be bumped manually through a tag with the next release version that has to follow the
[semver](https://semver.org/) rules, and the tag has to be pushed to origin.

Creating the tag can be either done manually with `git tag` or by using `gradlew tag` task.

#### Pre-releases

To create a new pre-release version (new release candidate)
the following can be used: `./gradlew clean tag -Prelease -PnewPreRelease -PbumpComponent={comp}`, where `comp` can be
one of the following values: `major`, `minor`, or `patch`.

To bump an existing pre-release to the next version (next release candidate for the same release version)
the following can be used: `./gradlew clean tag -Prelease -PpreRelease`.

#### Releases

To promote a pre-release to a release version the following can be used:
`./gradlew clean tag -Prelease -PpromoteToRelease`,

To create a new release version the following can be used:
`./gradlew clean tag -Prelease -PbumpComponent={comp}`, where `comp` can be one of the following values: `major`
, `minor`, or `patch`.

#### Make targets

Alternatively to the above targets from [Makefile](Makefile) can be used for the same purposes.

## Contributing

Feel free to submit a [pull request](https://github.com/serpro69/kotlin-faker/compare)
and/or open a [new issue](https://github.com/serpro69/kotlin-faker/issues/new)
if you would like to contribute.

## Thanks

Many thanks to these awesome tools that help us in creating open-source software:  
[![Intellij IDEA](https://cloud.google.com/tools/images/icon_IntelliJIDEA.png)](http://www.jetbrains.com/idea)
[![YourKit Java profiler](https://www.yourkit.com/images/yklogo.png)](https://www.yourkit.com/features/)

## Licence

This code is free to use under the terms of the MIT licence. See [LICENCE.md](LICENCE.md).
