<img src=./logo/kotlin_faker.png width=367.5 height=300/>  

### kotlin-faker
[![Build Status](https://travis-ci.org/serpro69/kotlin-faker.svg?branch=master)](https://travis-ci.org/serpro69/kotlin-faker)
[![Coverage Status](https://coveralls.io/repos/github/serpro69/kotlin-faker/badge.svg)](https://coveralls.io/github/serpro69/kotlin-faker)
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

## ToC
- [About](#about)
- [Usage](#usage)  
  - [Add a dependency](#add-a-dependency)
  - [Initialize Faker](#initialize-faker-singleton)
  - [Generate data](#generate-some-data)
  - [Using custom locales](#using-custom-locales)
  - [Java interop](#java-interop)
- [Data Providers](#data-providers)
- [Contributing](#contributing)
- [Licence](#licence)


## About
Port of a popular ruby [faker](https://github.com/stympy/faker) gem written completely in kotlin.
Generates realistically looking fake data such as names, addresses, banking details, and many more, 
that can be used for testing purposes during development and testing.


## Usage
### Downloading
**With gradle**  
Add bintray repository:  
```groovy
repositories {
    maven {
      url 'https://dl.bintray.com/serpro69/maven/'
    }
}
```  

Add dependency:  
```groovy
dependencies {
    implementation 'io.github.serpro69:kotlin-faker:$kotlinFakerVersion'
}
```  

**With maven**  
Add bintray repository:  
```xml
<repositories>
    <repository>
        <id>serpro69-maven</id>
        <url>https://dl.bintray.com/serpro69/maven/</url>
        <layout>default</layout>
        <releases>
            <enabled>true</enabled>
        </releases>
    </repository>
</repositories>
```  

Add dependency:  
```xml
<dependencies>
    <!--Add dependency-->
    <dependency>
        <groupId>io.github.serpro69</groupId>
        <artifactId>kotlin-faker</artifactId>
        <version>${kotlin-faker.version}</version>
    </dependency>
</dependencies>
```  

**Downloading a jar**  
The jar and pom files can also be found at this [link](https://dl.bintray.com/serpro69/maven/io/github/serpro69/kotlin-faker/)

### Generating data
First initialize `Faker` singleton:
```kotlin
Faker.init()
```  

Then call properties of `Faker` which represent different data categories (i.e. address, name, etc.):  
```kotlin
Faker.name.firstName() // => Ana

Faker.address.city() // => New York
```

### Using custom locales
`Faker` instance can be initialized with a custom locale:  
```kotlin
Faker.init(locale)
```  

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
* `tr`
* `uk`
* `vi`
* `zh-CN`
* `zh-TW`

</p>
</details>

Using a non-default locale will replace the values in some of the providers with the values from localized dictionary.  

```kotlin
Faker.init("es")
Faker.address.city() // => Barcelona
```

*Note that if the localized dictionary file does not contain a category (or a parameter in a category)*
*that is present in the default locale, then non-localized value will be used instead.*  

```kotlin
Faker.init()
Faker.gameOfThrones.cities() // => Braavos

Faker.init("nb-NO")
// `game_of_thrones` category is not localized for `nb-NO` locale
Faker.gameOfThrones.cities() // => Braavos
```

### Java interop
Although this lib was created with Kotlin in mind it is still possible to use from a Java-based project
thanks to great Kotlin-to-Java interop.

First initialize `Faker`: 
```
Faker.init()
```  

Then call the getter methods on any of the available providers:
```
String bldNum = Faker.address.getBuildingNumber().invoke() // => 123
```
*Note the `invoke()` at the end. This is basically the only difference when it comes to using this library from Java.*
*Calling `invoke()` is needed because all the methods in providers' classes are function literals, not properties,*
*therefore to get the `String` value of the method `getBuildingNumber()` an `invoke()` operator should be called.*
*This is not necessary in Kotlin because you can call function literals with just braces like so: `buildingNumber()`*


## Data Providers
Below is the list of available providers that correspond to the dictionary files found in [/locales/en](src/main/resources/locales/en)

<i>Note that not all (although most) of the providers and their functions are implemented at this point.
For more details see the particular `.md` file for each provider below.</i>

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
* [Basketball](doc/basketball.md)
* [Beer](doc/beer.md)
* [BojackHoreseman](doc/bojack_horeseman.md)
* [Book](doc/book.md)
* [BossaNova](doc/bossa_nova.md)
* [BreakingBad](doc/breaking_bad.md)
* [Buffy](doc/buffy.md)
* [Business](doc/business.md)
* [Cannabis](doc/cannabis.md)
* [Cat](doc/cat.md)
* [ChuckNorris](doc/chuck_norris.md)
* [Code](doc/code.md)
* [Coffee](doc/coffee.md)
* [Coin](doc/coin.md)
* [Color](doc/color.md)
* [Commerce](doc/commerce.md)
* [Community](doc/community.md)
* [Company](doc/company.md)
* [Compass](doc/compass.md)
* [Construction](doc/construction.md)
* [Cosmere](doc/cosmere.md)
* [CryptoCoin](doc/crypto_coin.md)
* [CultureSeries](doc/culture_series.md)
* [Currency](doc/currency.md)
* [DcComics](doc/dc_comics.md)
* [Demographic](doc/demographic.md)
* [Dessert](doc/dessert.md)
* [Device](doc/device.md)
* [Dog](doc/dog.md)
* [Dota](doc/dota.md)
* [DragonBall](doc/dragon_ball.md)
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
* [Movie](doc/movie.md)
* [Music](doc/music.md)
* [Myst](doc/myst.md)
* [Name](doc/name.md)
* [Nation](doc/nation.md)
* [NatoPhoneticAlphabet](doc/nato_phonetic_alphabet.md)
* [NewGirl](doc/new_girl.md)
* [OnePiece](doc/one_piece.md)
* [Overwatch](doc/overwatch.md)
* [ParksAndRec](doc/parks_and_rec.md)
* [Phish](doc/phish.md)
* [PhoneNumber](doc/phone_number.md)
* [Pokemon](doc/pokemon.md)
* [PrincessBride](doc/princess_bride.md)
* [ProgrammingLanguage](doc/programming_language.md)
* [Quote](doc/quote.md)
* [Relationship](doc/relationship.md)
* [Restaurant](doc/restaurant.md)
* [RickAndMorty](doc/rick_and_morty.md)
* [RockBand](doc/rock_band.md)
* [Rupaul](doc/rupaul.md)
* [Science](doc/science.md)
* [Seinfeld](doc/seinfeld.md)
* [Shakespeare](doc/shakespeare.md)
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
* [Stripe](doc/stripe.md)
* [Subscription](doc/subscription.md)
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
* [Witcher](doc/witcher.md)
* [WorldCup](doc/world_cup.md)
* [WorldOfWarcraft](doc/world_of_warcraft.md)
* [Yoda](doc/yoda.md)
* [Zelda](doc/zelda.md)

</p>
</details>


## Contributing
Feel free to submit a [pull request](https://github.com/serpro69/kotlin-faker/compare) 
and/or open a [new issue](https://github.com/serpro69/kotlin-faker/issues/new)
if you would like to contribute.


## Licence
This code is free to use under the terms of the MIT licence.
See [LICENCE.md](LICENCE.md).